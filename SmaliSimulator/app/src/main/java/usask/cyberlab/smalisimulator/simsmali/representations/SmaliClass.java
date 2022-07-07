package usask.cyberlab.smalisimulator.simsmali.representations;

import org.jf.dexlib2.util.ReferenceUtil;
import org.jf.dexlib2.writer.builder.BuilderClassDef;
import org.jf.dexlib2.writer.builder.BuilderMethod;
import org.jf.dexlib2.writer.builder.BuilderField;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import usask.cyberlab.smalisimulator.simsmali.SimulationUtils;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliFileNotFoundException;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliFileParseException;
import usask.cyberlab.smalisimulator.simsmali.instructions.CheckCastInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.ConstInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.InstanceOfInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.InstanceOperationInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.InvokeInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.NewArrayInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.NewInstanceInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.SmaliInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.StaticOperationInstruction;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ReflectedClazz;


/**
 * this class represents a parsed smali file.
 */
public class SmaliClass extends SmaliItem{

    private final String classPath;
    private final String parentClassPath;
    private final List<String> interfaces;

    private final Map<String, SmaliMethod> smaliMethodMap = new HashMap<>();
    private final Map<String, SmaliField> smaliFieldsMap = new HashMap<>();

    // In this set we keep classPath of all smali types that are directly
    // referenced by this smali class which are needed for mock class loading.
    // These references include: 1-parent type 2-implemented interfaces
    // 3-field types 4- constructor and method argument types 5- method return types
    // in case of arrays, we keep track of array base type
    private HashSet<String> hasDirectDependencyOnSmaliTypes;

    // This set keeps the set of all smali types that are directly
    // or indirectly referenced by this smali class. we need this set
    // in the mock class loading. These references are computed lazily
    // based on 'hasDirectDependencyOnSmaliTypes'.
    private HashSet<String> indirectDependenciesOnSmaliTypes;

    private HashSet<String> directlyReferencedTypesInInstructions;


    public SmaliClass(BuilderClassDef classDef){
        super(classDef.getAccessFlags());
        this.parentClassPath = classDef.getSuperclass();
        this.classPath = classDef.getType();
        this.interfaces = classDef.getInterfaces();

        for (BuilderMethod methodDef : classDef.getMethods()) {
            SmaliMethod method = new SmaliMethod(this, methodDef);
            String methodSig = ReferenceUtil.getMethodDescriptor(methodDef).split("->")[1];
            smaliMethodMap.put(methodSig, method);
        }

        for(BuilderField fieldDef : classDef.getFields()){
            SmaliField smaliField = new SmaliField(this, fieldDef);
            String fieldName = fieldDef.getName();
            smaliFieldsMap.put(fieldName,smaliField);
        }
    }

    public String getParentClassPath(){
        return this.parentClassPath;
    }

    public String getClassPath(){
        return this.classPath;
    }

    public List<String> getInterfaces(){
        return interfaces;
    }

    public List<SmaliField> getAllFields(){
        return new ArrayList<>(smaliFieldsMap.values());
    }

    public List<SmaliField> getAllStaticFields(){
        ArrayList<SmaliField> res = new ArrayList<>();
        for(SmaliField sf: this.smaliFieldsMap.values()){
            if(sf.isStatic()){
                res.add(sf);
            }
        }
        return res;
    }

    public List<SmaliMethod> getAllMethods(){
        return new ArrayList<>(smaliMethodMap.values());
    }

    public List<SmaliMethod> getAllStaticMethods(){
        ArrayList<SmaliMethod> res = new ArrayList<>();
        for(SmaliMethod sm: this.smaliMethodMap.values()){
            if(sm.isStatic()){
                res.add(sm);
            }
        }
        return res;
    }

    public SmaliMethod getMethod(String methodName) {
        return smaliMethodMap.get(methodName);
    }

    public boolean containsMethod(String methodName) {
        return smaliMethodMap.containsKey(methodName);
    }

    public SmaliField getField(String fieldName) {
        return smaliFieldsMap.get(fieldName);
    }

    public boolean isInterface(){
        return Modifier.isInterface(flags);
    }

    public boolean isAbstract(){
        return Modifier.isAbstract(flags);
    }

    public boolean isEnum(){
        return (flags & 16384) == 16384;
    }

    public boolean isAnnotation(){
        return (flags & 8192) == 8192;
    }

    public String getModifiersString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.getAccessModifierString());
        if(this.isFinal()) sb.append(" final");
        if(this.isInterface()) sb.append(" interface");
        if(this.isAbstract()) sb.append(" abstract");
        if(this.isAnnotation()) sb.append(" annotation");
        if(this.isEnum()) sb.append(" enum");
        return sb.toString().trim();
    }

    private void initDirectHasDependencyOnSmaliTypesIfNecessary(){
        if(hasDirectDependencyOnSmaliTypes == null){
            hasDirectDependencyOnSmaliTypes = new HashSet<>();

            // add parent type if it is smali class
            if(!SimulationUtils.isJavaOrAndroidExistingClass(this.getParentClassPath())) {
                hasDirectDependencyOnSmaliTypes.add(this.getParentClassPath());
            }

            // adding interfaces
            for(String s: this.getInterfaces()){
                if(s.startsWith("Ljava/lang/")) continue;
                if(s.startsWith("Ljava/util/")) continue;
//                if(s.startsWith("Landroid/os/")) continue;
//                if(s.startsWith("Landroid/app/")) continue;
                if(SimulationUtils.isJavaOrAndroidExistingClass(s)) continue;
                hasDirectDependencyOnSmaliTypes.add(s);
            }

            // adding field types
            for(SmaliField f:this.getAllFields()){
                String s = f.getType();
                if(s.contains("[")) s = s.replace("[","");
                if(s.length() == 1) continue;
                if(s.startsWith("Ljava/lang/")) continue;
                if(s.startsWith("Ljava/util/")) continue;
//                if(s.startsWith("Landroid/os/")) continue;
//                if(s.startsWith("Landroid/app/")) continue;
                if(SimulationUtils.isJavaOrAndroidExistingClass(s)) continue;
                hasDirectDependencyOnSmaliTypes.add(s);
            }

            // adding method and constructor arg types and return type
            // if they are smali
            for(SmaliMethod sm: this.getAllMethods()){
                for(String argType: sm.getArgumentTypes()){
                    String s = argType;
                    if(s.contains("[")) s = s.replace("[","");
                    if(s.length() == 1) continue;
                    if(s.startsWith("Ljava/lang/")) continue;
                    if(s.startsWith("Ljava/util/")) continue;
//                    if(s.startsWith("Landroid/os/")) continue;
//                    if(s.startsWith("Landroid/app/")) continue;
                    if(SimulationUtils.isJavaOrAndroidExistingClass(s)) continue;
                    hasDirectDependencyOnSmaliTypes.add(s);
                }

                String returnType = sm.getReturnType();
                if(returnType.contains("[")) returnType = returnType.replace("[","");
                if(returnType.length() == 1) continue;
                if(returnType.startsWith("Ljava/lang/")) continue;
                if(returnType.startsWith("Ljava/util/")) continue;
//                if(returnType.startsWith("Landroid/os/")) continue;
//                if(returnType.startsWith("Landroid/app/")) continue;
                if(SimulationUtils.isJavaOrAndroidExistingClass(returnType)) continue;
                hasDirectDependencyOnSmaliTypes.add(returnType);
            }
        }
    }

    public HashSet<String> getAllIndirectDependentSmaliTypes(ClazzLoader loader){
        if(indirectDependenciesOnSmaliTypes != null) {
            return indirectDependenciesOnSmaliTypes;
        }
        initDirectHasDependencyOnSmaliTypesIfNecessary();
        HashSet<String> checkedSoFar = new HashSet<>();
        HashSet<String> res = getAllIndirectDependentSmaliTypes(checkedSoFar, loader);
        this.indirectDependenciesOnSmaliTypes = res;
        return res;
    }

    private HashSet<String> getAllIndirectDependentSmaliTypes(HashSet<String> checkedSoFar, ClazzLoader loader){
        if(indirectDependenciesOnSmaliTypes != null) return indirectDependenciesOnSmaliTypes;
        initDirectHasDependencyOnSmaliTypesIfNecessary();
        HashSet<String> res = new HashSet<>();
        res.addAll(hasDirectDependencyOnSmaliTypes);
        checkedSoFar.add(this.getClassPath());

        for(String s: hasDirectDependencyOnSmaliTypes){
            if(s.equals(this.getClassPath()))continue;
            if(checkedSoFar.contains(s)) continue;
            SmaliClass sc;
            try {
                sc = loader.getSmaliClass(s);
            }
            catch (SmaliFileNotFoundException e){
                continue;
            }
            checkedSoFar.add(s);
            res.addAll(sc.getAllIndirectDependentSmaliTypes(checkedSoFar, loader));
        }
        return res;
    }

    public boolean hasIndirectDependencyOnType(String type, ClazzLoader loader){
        if(indirectDependenciesOnSmaliTypes != null){
            return indirectDependenciesOnSmaliTypes.contains(type);
        }
        else {
            getAllIndirectDependentSmaliTypes(loader);
            return indirectDependenciesOnSmaliTypes.contains(type);
        }
    }

    public boolean hasIndirectDependencyOnTypeExtendingFrom(SmaliClass toCheck, ClazzLoader loader){
        if(this.equals(toCheck)) return false;
        if(this.extendedFromSmaliClass(toCheck, loader)) return true;

        for(String s: getAllIndirectDependentSmaliTypes(loader)){
            if(s.equals(this.getClassPath()))continue;
            SmaliClass sc;
            try {
               sc = loader.getSmaliClass(s);
            }
            catch (SmaliFileNotFoundException e){
                continue;
            }

            if (sc.extendedFromSmaliClass(toCheck, loader)) return true;
        }
        return false;
    }

    /**
     * This method checks if this Smali class extends from the
     * given type. Note that this method does not check interfaces
     * that are implemented in a class
     *
     * @param type the potential ancestor type
     * @param loader clazzLoader object which is used for parsing and getting
     *               other parent SmaliClass object if necessary
     * @return True if this smali class extends from given type, False otherwise.
     */
    public boolean extendsFrom(String type, ClazzLoader loader){
        if(this.parentClassPath.equals(type)) return true;
        // smaliClass parent
        if(!SimulationUtils.isJavaOrAndroidExistingClass(this.getParentClassPath())){
            SmaliClass parentSmaliClass;
            try {
                parentSmaliClass = loader.getSmaliClass(parentClassPath);
            }
            catch (SmaliFileParseException e){
                return false;
            }

            return parentSmaliClass.extendsFrom(type, loader);
        }
        // java parent
        else {
            ReflectedClazz clazz = (ReflectedClazz) loader.getClazz(parentClassPath);
            Class c = clazz.getMirroringClass();
            while (c != null){
                String smaliStyleClassPath = SimulationUtils.makeSmaliStyleClassPath(c.getName());
                if(smaliStyleClassPath.equals(type)) return true;
                c = c.getSuperclass();
            }
            return false;
        }
    }

    /**
     * This method checks if this Smali class extends from the given SmaliClass object.
     * @param sc
     * @param loader
     * @return
     */
    private boolean extendedFromSmaliClass(SmaliClass sc, ClazzLoader loader){
        if(this.getParentClassPath().equals(sc.getClassPath())) return true;
        if(SimulationUtils.isJavaOrAndroidExistingClass(this.getParentClassPath())) return false;
        SmaliClass parentSmaliClass;
        try {
            parentSmaliClass = loader.getSmaliClass(this.getParentClassPath());
        }
        catch (SmaliFileParseException e){
            return false;
        }
        return parentSmaliClass.extendedFromSmaliClass(sc, loader);
    }

    public HashSet<String> getDirectlyReferencedTypesInInstructions() {
        initDirectlyReferencedTypesInInstructions();
        return directlyReferencedTypesInInstructions;
    }

    private void initDirectlyReferencedTypesInInstructions(){
        if(directlyReferencedTypesInInstructions == null) {
            initDirectHasDependencyOnSmaliTypesIfNecessary();
            directlyReferencedTypesInInstructions = new HashSet<>();
            // for all methods and constructors
            for(SmaliMethod sm: getAllMethods()){
                // for all instructions in the SmaliMethod
                ArrayList<SmaliInstruction> instructions = sm.getInstructionList();
                if(instructions == null)continue;
                for(SmaliInstruction instruction: instructions){
                    String referencedClassPath = null;
                    String referencedClassPath2 = null;

                    if(instruction instanceof ConstInstruction){
                        ConstInstruction constInstruction = (ConstInstruction) instruction;
                        if(constInstruction.getConstInstructionType() == ConstInstruction.ConstInstructionType.CLASS) {
                            referencedClassPath = constInstruction.getDereferencedValue();
                        }
                    }
                    else if(instruction instanceof CheckCastInstruction){
                        CheckCastInstruction checkCastInstruction = (CheckCastInstruction) instruction;
                        referencedClassPath = checkCastInstruction.getCheckingType();
                    }
                    else if(instruction instanceof InstanceOfInstruction){
                        InstanceOfInstruction instanceOfInstruction = (InstanceOfInstruction) instruction;
                        referencedClassPath = instanceOfInstruction.getClassPathString();
                    }
                    else if(instruction instanceof NewInstanceInstruction){
                        NewInstanceInstruction newInstanceInstruction = (NewInstanceInstruction) instruction;
                        referencedClassPath = newInstanceInstruction.getToCreateTypeClassPath();
                    }
                    else if(instruction instanceof NewArrayInstruction){
                        NewArrayInstruction newArrayInstruction = (NewArrayInstruction) instruction;
                        referencedClassPath = newArrayInstruction.getArrayType()
                                .replace("[","");
                    }
                    else if(instruction instanceof InstanceOperationInstruction){
                        InstanceOperationInstruction instanceOperationInstruction = (InstanceOperationInstruction) instruction;
                        String s = instanceOperationInstruction.getClassFieldSignature();
                        referencedClassPath = s.split("->")[0];
                        referencedClassPath2 = s.split("->")[1].split(":")[1];
                    }
                    else if(instruction instanceof StaticOperationInstruction){
                        StaticOperationInstruction staticOperationInstruction = (StaticOperationInstruction) instruction;

                        referencedClassPath = staticOperationInstruction.getReferencedTypeClassPath();
                        referencedClassPath2 = staticOperationInstruction.getTargetFieldType();
                    }
                    else if(instruction instanceof InvokeInstruction){
                        InvokeInstruction invokeInstruction = (InvokeInstruction) instruction;
                        referencedClassPath = invokeInstruction.getClassPath();
                    }


                    if(referencedClassPath != null && // if the instruction is referencing a type
                            referencedClassPath.length() > 1 // if referenced type is not primitive
                    ){
                        // if type is array we look at base type
                        if(referencedClassPath.contains("["))
                            referencedClassPath = referencedClassPath.replace("[","");

                        // if is smali type we add it
                        if(!SimulationUtils.isJavaOrAndroidExistingClass(referencedClassPath)) {
                            directlyReferencedTypesInInstructions.add(referencedClassPath);
                        }
                    }

                    if(referencedClassPath2 != null && // if the instruction is referencing a type
                            referencedClassPath2.length() > 1 // if referenced type is not primitive
                    ){
                        // if type is array we look at base type
                        if(referencedClassPath2.contains("["))
                            referencedClassPath2 = referencedClassPath2.replace("[","");
                        // if is smali type we add it
                        if(!SimulationUtils.isJavaOrAndroidExistingClass(referencedClassPath2)) {
                            directlyReferencedTypesInInstructions.add(referencedClassPath2);
                        }
                    }

                }
            }
        }
    }

    public HashSet<String> getIndirectlyReferencedTypesInInstructions(ClazzLoader loader){
        HashSet<String> res = new HashSet<>();
        HashSet<String> directRef = getDirectlyReferencedTypesInInstructions();
        res.addAll(directRef);
        HashSet<String> checkedSoFar = new HashSet<>();
        checkedSoFar.add(this.classPath);
        return getIndirectlyReferencedTypesInInstructions(loader, res, checkedSoFar);

    }

    private HashSet<String> getIndirectlyReferencedTypesInInstructions(ClazzLoader loader, HashSet<String> res, HashSet<String> checkedSoFar){
        initDirectlyReferencedTypesInInstructions();
        for(String s: this.directlyReferencedTypesInInstructions){
            if(s.equals(this.classPath)) continue;
            if(checkedSoFar.contains(s)) continue;

            checkedSoFar.add(s);
            SmaliClass sc;
            try {
             sc = loader.getSmaliClass(s);
            }
            catch (SmaliFileNotFoundException e){
                continue;
            }
            HashSet<String> r = sc.getIndirectlyReferencedTypesInInstructions(loader, res, checkedSoFar);
            res.addAll(r);
        }
        return res;
    }

    // This method is used to set the HashSets to null
    // This can be used to reduce memory usage when these sets
    // are not needed anymore
    public void clearCachedData(){
        hasDirectDependencyOnSmaliTypes = null;
        indirectDependenciesOnSmaliTypes = null;
        directlyReferencedTypesInInstructions = null;
    }

    public String toString(){
        return classPath;
    }
}
