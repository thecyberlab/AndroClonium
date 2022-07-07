package usask.cyberlab.smalisimulator.simsmali.types.classes;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliSimulationException;
import usask.cyberlab.smalisimulator.simsmali.types.objects.AbstractObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;

/**
 * This class models a loaded class in the system. A loaded class can be a java/android class
 * that already exists in the system or the mock class of user defined type from a smali file
 * which has been created using byte-buddy.
 * This class is a wrapper around the "Class" class which shows a loaded type into system.
 * These wrappers are important since they are also responsible for remembering
 * the fields that we have assigned an AmbiguousValue to them beside some extra
 * functionality for ease of use.
 */
public abstract class Clazz {

    protected final ClazzLoader clazzLoader;
    protected final String className;
    protected Class<?> mirroringClass;
    protected Objekt classObjekt; // the mirroringClass wrapped in the Objekt class


    Clazz(ClazzLoader loader, String className){
        this.clazzLoader = loader;
        this.className = className;
    }

    Clazz(ClazzLoader loader, String className, Class<?> mirroringClass){
        this.clazzLoader = loader;
        this.className = className;
        this.mirroringClass = mirroringClass;
    }

    public String getClassPath(){
        return className;
    }

    public abstract String getParentClassPath();

    public abstract Object getStaticFieldValue(String fieldName);

    public abstract void setStaticFieldValue(String fieldName, Object newValue);

    public String getFieldType(String fieldName) {
        try {
            String type = resolveField(mirroringClass, fieldName).getType().getName();
            return SimulationUtils.makeSmaliStyleClassPath(type);
        } catch (Exception e) {
            throw new SmaliSimulationException(e);
        }
    }

    /**
     *
     * @param classPath the classPath of queried type in Smali syntax
     * @return true if type represented by this object extends from or implements the queried type, False otherwise
     */
    public boolean isSubTypeOf(String classPath) {
        Clazz clazz = clazzLoader.getClazz(classPath);
        Class<?> cls = clazz.mirroringClass;
        return cls.isAssignableFrom(this.mirroringClass);
    }

    /**
     *
     * @param classPath the classPath of queried type in Smali syntax
     * @return true if the queried type extends from or implements the type represented by this object, False otherwise
     */
    public boolean isSuperTypeOf(String classPath) {
        Clazz maybeSubClazz = clazzLoader.getClazz(classPath);
        return maybeSubClazz.isSubTypeOf(this.getClassPath());
    }

    /**
     * Each loaded class into system has a corresponding Class object in java.
     * The class object is itself a normal object which inherits from java.lang.Object.
     * Due to this fact we also need to treat Class object also as an normal object which in our
     * system should be wrapped around in an Objekt.
     * @return return the mirroring class wrapped around in an Objekt.
     */
    public Objekt getClassObjekt() {
        if(classObjekt == null){
            Clazz clazz = clazzLoader.getClazz("Ljava/lang/Class;");
            classObjekt = new Objekt(clazz, this.mirroringClass);
        }
        return classObjekt;
    }

    public ClazzLoader getClazzLoader(){
        return clazzLoader;
    }

    public boolean isEnumClass() {
        return mirroringClass.isEnum();
    }

    public boolean isInterfaceClass() {
        return Modifier.isInterface(mirroringClass.getModifiers());
    }

    public boolean isArray(){
        return mirroringClass.isArray();
    }

    public boolean isAbstract() {
        return Modifier.isAbstract(mirroringClass.getModifiers());
    }

    public boolean isFinal(){
        return Modifier.isFinal(mirroringClass.getModifiers());
    }

    public Class getMirroringClass(){
        return mirroringClass;
    }

    public static Field resolveField(Class<?> cls, String fieldName) throws NoSuchFieldException {
        try {
            return callGetFieldApiViaReflection(cls, fieldName);
        } catch (NoSuchFieldException e) {
            Class<?> tmpCls = cls;
            while (tmpCls != null) {
                try {
                    Field f = callGetDeclaredFieldApuViaReflection(tmpCls, fieldName);
                    f.setAccessible(true);
                    return f;
                } catch (NoSuchFieldException ex) {
                    tmpCls = tmpCls.getSuperclass();
                }
            }
            throw new NoSuchFieldException();
        }
    }


    public static Method resolveMethod(Register referenceRegister,
                                              Clazz invocationTypeClazz,
                                              String methodName,
                                              Class<?>[] methodArgs){
        Class toUseClass = null;
        try {
            // if the referenced object is Ambiguous value and is subtype of invocationTypeClazz
            // we will use the subtype given on Ambiguous value
            if(referenceRegister.containsAmbiguousValue() && referenceRegister.getAmbiguousValue() != null){
                AmbiguousValue av = referenceRegister.getAmbiguousValue();
                if(invocationTypeClazz.isSuperTypeOf(av.getType())){
                    Clazz avTypeClazz = invocationTypeClazz.getClazzLoader().getClazz(av.getType());
                    toUseClass = avTypeClazz.mirroringClass;
                }
                else {
                    toUseClass = invocationTypeClazz.mirroringClass;
                }

            }
            // if the reference object is null we look at the
            // invocationTypeClazz
            else if(referenceRegister.getReferencedObjectValue() == null){
                toUseClass = invocationTypeClazz.mirroringClass;
            }
            // if reference register is valid objekt we try to use the
            // actual mirroring object
            else {
                AbstractObjekt abstractObjekt = referenceRegister.getReferencedObjectValue();
                if(abstractObjekt.getMirroringObject() ==null){
                    toUseClass = invocationTypeClazz.mirroringClass;
                }
                else {
                    //TODO check referenced object is instance of invocationTypeClazz
                    toUseClass = abstractObjekt.getMirroringObject().getClass();
                }
            }

            return Clazz.resolveMethodInClass(toUseClass, methodName , methodArgs);

        }
        catch (Exception e){
            StringBuilder s = new StringBuilder(methodName + "(");
            for(Class cls:methodArgs){
                s.append(cls.getName()).append(",");
            }
            if(s.toString().endsWith(",")){
                s = new StringBuilder(s.substring(0, s.length() - 1));
            }
            s.append(") not found");
            if(toUseClass!=null){
                s.append(" in class object ").append(toUseClass.getName());
            }
            throw new SmaliSimulationException(s.toString(), e);
        }

    }

    public static  Method resolveMethodInClass(Class<?> cls, String methodName, Class<?>[] methodArgs) throws NoSuchMethodException  {
        try {
            return callGetMethodApiViaReflection(cls, methodArgs, methodName);
        } catch (NoSuchMethodException e) {
            Class<?> tmpCls = cls;
            while (tmpCls != null) {
                try {
                    Method m = callGetDeclaredMethodApiViaReflection(tmpCls, methodArgs, methodName);
                    m.setAccessible(true);
                    return m;
                } catch (NoSuchMethodException ex) {
                    if(tmpCls.getSuperclass() == null && tmpCls.isInterface()){
                        tmpCls = Object.class;
                    }
                    else {
                        tmpCls = tmpCls.getSuperclass();
                    }
                }
            }
            throw new NoSuchMethodException();
        }
    }


    // We need use an extra layer of reflection to see Android fields with @hide annotation
    private static Field callGetFieldApiViaReflection(Class<?> cls, String fieldName) throws NoSuchFieldException{
        Method m;
        try {
            m = Class.class.getMethod("getField", String.class);
            m.setAccessible(true);
        }
        // This never happens
        catch (NoSuchMethodException e) {
            throw new SmaliSimulationException(e);
        }
        try {
            return (Field) m.invoke(cls, fieldName);
        }
        // This never happens
        catch (IllegalAccessException e) {
            throw new SmaliSimulationException(e);
        }
        catch (InvocationTargetException e) {
            if(e.getCause() instanceof NoSuchFieldException){
                throw (NoSuchFieldException) e.getCause();
            }
            // This should not ever happen
            throw new SmaliSimulationException(e);
        }
    }

    // We need use an extra layer of reflection to see Android fields with @hide annotation
    private static Field callGetDeclaredFieldApuViaReflection(Class<?> cls, String fieldName) throws NoSuchFieldException{
        Method m;
        try {
            m = Class.class.getMethod("getDeclaredField", String.class);
            m.setAccessible(true);
        }
        // This never happens
        catch (NoSuchMethodException e) {
            throw new SmaliSimulationException(e);
        }
        try {
            return (Field) m.invoke(cls, fieldName);
        }
        // This never happens
        catch (IllegalAccessException e) {
            throw new SmaliSimulationException(e);
        }
        catch (InvocationTargetException e) {
            if(e.getCause() instanceof NoSuchFieldException){
                throw (NoSuchFieldException) e.getCause();
            }
            throw new SmaliSimulationException(e);
        }
    }

    // We need use an extra layer of reflection to see Android methods with @hide annotation
    private static Method callGetMethodApiViaReflection(Class<?> cls, Class<?>[] methodArgs, String methodName) throws NoSuchMethodException{
        Method m;
        try {
            m = Class.class.getMethod("getMethod", String.class, Class[].class);
            m.setAccessible(true);
        }
        // This never happens
        catch (NoSuchMethodException e){
            throw new SmaliSimulationException(e);
        }
        try {
            return (Method) m.invoke(cls, methodName, methodArgs);
        }
        //This never happens
        catch (IllegalAccessException e) {
            throw new SmaliSimulationException(e);
        }
        catch (InvocationTargetException e) {
            if(e.getCause() instanceof NoSuchMethodException){
                throw (NoSuchMethodException) e.getCause();
            }
            // This should not ever happen
            throw new SmaliSimulationException(e);
        }
    }

    // We need use an extra layer of reflection to see Android methods with @hide annotation
    private static Method callGetDeclaredMethodApiViaReflection(Class<?> cls, Class<?>[] methodArgs, String methodName) throws NoSuchMethodException{
        Method m;
        try {
            m = Class.class.getMethod("getDeclaredMethod", String.class, Class[].class);
            m.setAccessible(true);
        }
        // This never happens
        catch (NoSuchMethodException e){
            throw new SmaliSimulationException(e);
        }
        try {
            return (Method) m.invoke(cls, methodName, methodArgs);
        }
        //This never happens
        catch (IllegalAccessException e) {
            throw new SmaliSimulationException(e);
        }
        catch (InvocationTargetException e) {
            if(e.getCause() instanceof NoSuchMethodException){
                throw (NoSuchMethodException) e.getCause();
            }
            // This should not ever happen
            throw new SmaliSimulationException(e);
        }
    }
}
