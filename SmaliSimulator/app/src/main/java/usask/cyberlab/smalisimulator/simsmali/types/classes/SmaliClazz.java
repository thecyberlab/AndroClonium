package usask.cyberlab.smalisimulator.simsmali.types.classes;


import java.lang.reflect.Field;
import java.util.*;

import usask.cyberlab.smalisimulator.Utils;
import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.ExecutionTrace;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.SimSmaliConfig;
//import usask.cyberlab.smalisimulator.simsmali.Interceptors.MethodInterceptor;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.mockerCreation.MockCreator;
import usask.cyberlab.smalisimulator.simsmali.types.objects.AbstractObjekt;


/***
 * SmaliClazz is a wrapper for classes that were created by byte-buddy from
 * a smali file and the loaded into the system.
 *
 */
public class SmaliClazz extends Clazz {
    private final SmaliClass smaliClass;

    protected final HashMap<String, AmbiguousValue> staticFieldsAmbiguousMask = new HashMap<>();
    protected final HashMap<String, AbstractObjekt> staticFieldsWrappers = new HashMap<>();

    private ExecutionTrace clinitExecutionTrace;


    // In this constructor we first create a SmaliClazz based on the corresponding
    // SmaliClass and then we try to create the mirroring type using byte-buddy
    SmaliClazz(SmaliClass smaliClass, ClazzLoader classLoader) {
        super(classLoader, smaliClass.getClassPath());
        this.smaliClass = smaliClass;
        if(clazzLoader.isClazzLoaded(smaliClass.getClassPath())){
            this.mirroringClass = clazzLoader.getClazz(smaliClass.getClassPath()).mirroringClass;
        }
        else {
            // trying to create the mirroring type
            // in case the mirroring type is inner cyclic type
            // this api will throw an InnerCyclicClassException
            // which will be handled in the MockCreation logic of outer cyclic type
            this.mirroringClass = MockCreator.createType(this);
        }
        smaliClass.clearCachedData();
    }

    // This constructor is invoked when we want to create a SmaliClazz for
    // cyclic classes that have been loaded at load time of outer cyclic type
    SmaliClazz(SmaliClass smaliClass, ClazzLoader classLoader, Class cls){
        super(classLoader, smaliClass.getClassPath(), cls);
        this.smaliClass = smaliClass;
        smaliClass.clearCachedData();
    }

    @Override
    public String getClassPath() {
        return smaliClass.getClassPath();
    }

    public SmaliClass getSmaliClass(){
        return this.smaliClass;
    }

    @Override
    public void setStaticFieldValue(String fieldName, Object newValue) {
        // The reason for this field resolving is that it is valid to have Sget, Sput that
        // set and get to fields declared in their parents.
        Clazz fieldDefiningClazz = getFieldDefiningClazz(fieldName);
        if(fieldDefiningClazz == this){
            if(newValue instanceof AmbiguousValue){
                staticFieldsAmbiguousMask.put(fieldName, (AmbiguousValue) newValue);
            }
            else {
                Field field;
                try {
                    field = resolveField(mirroringClass, fieldName);
                } catch (NoSuchFieldException e) {
                    throw new SmaliSimulationException(e);
                }
                // set the values
                SimulationUtils.setFieldValue(field, newValue, mirroringClass);

                // if the field type is not primitive then it has a wrapper and
                // we should also keep the wrapper
                if( ! SimulationUtils.isPrimitiveType(getFieldType(fieldName)) ) {
                    this.staticFieldsWrappers.put(fieldName, (AbstractObjekt) newValue);
                }
                staticFieldsAmbiguousMask.remove(fieldName);
            }
        }
        else {
            fieldDefiningClazz.setStaticFieldValue(fieldName, newValue);
        }
    }

    @Override
    public Object getStaticFieldValue(String fieldName) {
        // The reason for this field resolving is that it is valid to have Sget, Sput that
        // set and get to fields declared in their parents.
        Clazz fieldDefiningClazz = getFieldDefiningClazz(fieldName);
        if(fieldDefiningClazz == this){
            if(staticFieldsAmbiguousMask.containsKey(fieldName)){
                return staticFieldsAmbiguousMask.get(fieldName);
            }
            else {
                Field field;
                try {
                    field = resolveField(mirroringClass, fieldName);
                } catch (NoSuchFieldException e) {
                    throw new SmaliSimulationException(e);
                }

                String fieldType = this.getFieldType(fieldName);
                try {
                    if(SimulationUtils.isPrimitiveType(fieldType)){
                        field.setAccessible(true);
                        Object rawValue = field.get(mirroringClass);
                        return SimulationUtils.createWrapperObjektFromRawValue(rawValue, true, clazzLoader);
                    }
                    else {
                        if(staticFieldsWrappers.containsKey(fieldName)){
                            return this.staticFieldsWrappers.get(fieldName);
                        }
                        else {
                            Object rawValue = field.get(mirroringClass);
                            return SimulationUtils.createWrapperObjektFromRawValue(rawValue, false, clazzLoader);
                        }
                    }
                }
                catch (Exception e){
                    throw new SmaliSimulationException(e);
                }
            }
        }
        else {
            return fieldDefiningClazz.getStaticFieldValue(fieldName);
        }
    }

    private Clazz getFieldDefiningClazz(String fieldName) {
        try {
            Field field = resolveField(this.mirroringClass, fieldName);
            Class<?> declaringClass = field.getDeclaringClass();
            if( declaringClass == this.mirroringClass){
                return this;
            }
            else {
                String declaringClazzType = SimulationUtils.makeSmaliStyleClassPath(declaringClass.getName());
                return clazzLoader.getClazz(declaringClazzType);
            }
        } catch (NoSuchFieldException e) {
            throw new SmaliSimulationException(e);
        }
    }

    public boolean thisClassContainsField(String fieldName){
        try {
            resolveField(mirroringClass, fieldName);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

    public SmaliMethod getSmaliMethod(String methodSignature){
        return smaliClass.getMethod(methodSignature);
    }

    @Override
    public boolean isEnumClass() {
        return smaliClass.isEnum();
    }

    @Override
    public boolean isInterfaceClass() {
        return smaliClass.isInterface();
    }

//    @Override
//    public boolean isAnnotationClass() {
//        return smaliClass.isAnnotation();
//    }

    @Override
    public boolean isArray(){
        return false;
    }

    @Override
    public boolean isAbstract() {
        return smaliClass.isAbstract();
    }

    public void invokeCLinit(ClazzLoader loader) {
        System.out.println("Starting invoking <clinit> : " + this.getClassPath() + " " + Utils.getNowDateTimeString());
        if(!SimSmaliConfig.executeClinit) return;
        try {
            if(this.getSmaliMethod("<clinit>()V") != null){
                MethodExecution methodExecution = new MethodExecution(getSmaliMethod("<clinit>()V"), loader);
                methodExecution.execute();
                loader.saveInitialStaticFieldValues(this);
                if(SimSmaliConfig.logClinitExecutionTraceInSmaliClazz){
                    clinitExecutionTrace = methodExecution.getExecutionTrace();
                }
            }
        }
        catch (RuntimeException e){
            throw new SmaliSimulationException(e);
        }
        System.out.println("Finished invoking <clinit> : " + this.getClassPath() + " " + Utils.getNowDateTimeString());
    }

    @Override
    public String getParentClassPath(){
        return smaliClass.getParentClassPath();
    }

    public List<String> getInterfaces() {
        return smaliClass.getInterfaces();
    }

    public ExecutionTrace getClinitExecutionTrace(){
        return clinitExecutionTrace;
    }

}
