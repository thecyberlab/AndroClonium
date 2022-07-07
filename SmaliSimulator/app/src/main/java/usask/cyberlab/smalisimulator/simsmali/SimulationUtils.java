package usask.cyberlab.smalisimulator.simsmali;


import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;

//import usask.cyberlab.smalisimulator.simsmali.androidMocks.MockActivity;
//import usask.cyberlab.smalisimulator.simsmali.androidMocks.MockAppCompatActivity;
//import usask.cyberlab.smalisimulator.simsmali.androidMocks.MockService;
import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
import usask.cyberlab.smalisimulator.simsmali.emulator.MethodExecution;
import usask.cyberlab.smalisimulator.simsmali.emulator.Register;
import usask.cyberlab.smalisimulator.simsmali.emulator.ResultType;
import usask.cyberlab.smalisimulator.simsmali.instructions.InvokeSuperInstruction;
import usask.cyberlab.smalisimulator.simsmali.instructions.SmaliInstruction;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliMethod;
import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliArrayClazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;
import usask.cyberlab.smalisimulator.simsmali.types.objects.AbstractObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.ArrayObjekt;
import usask.cyberlab.smalisimulator.simsmali.types.objects.Objekt;


public class SimulationUtils {

    public static int getRandomNumberInRange(int firstPossibleNum, int lastPossibleNum){
        int range = lastPossibleNum - firstPossibleNum + 1;
        return (firstPossibleNum + (int)(Math.random() * range));
    }

    public static String padString(char c, String s, int padSize){
        StringBuilder res = new StringBuilder();
        for(int i=0;i<padSize;i++){
            res.insert(0, c);
        }
        return res.toString() + s;
    }

    public static String zeroPadString(String s, int padSize){
        return padString('0', s,padSize);
    }

    public static int countChar(String string, char c) {
        int count = 0;
        for (int i = 0; i < string.length(); i++) {
            if(c == string.charAt(i)){
                count++;
            }
        }
        return count;
    }

    //----------------------------------------------

    /**
     *
     * @param type the queried type in Smali format
     * @return true if type is primitive type, false otherwise
     */
    public static boolean isPrimitiveType(String type){
        if(type.length() != 1){
            return false;
        }
        char c = type.charAt(0);
        if(c == 'I') return true;
        if(c == 'F') return true;
        if(c == 'D') return true;
        if(c == 'J') return true;
        if(c == 'Z') return true;
        if(c == 'S') return true;
        if(c == 'C') return true;
        if(c == 'B') return true;
        return false;
    }

    /**
     *
     * @param type the queried type in normal Java format
     * @return true if type is primitive type, false otherwise
     */
    public static boolean isPrimitiveTypeJavaStyle(String type){
        if(type.equals("int")) return true;
        if(type.equals("float")) return true;
        if(type.equals("long")) return true;
        if(type.equals("double")) return true;
        if(type.equals("boolean")) return true;
        if(type.equals("short")) return true;
        if(type.equals("byte")) return true;
        if(type.equals("char")) return true;
        return false;
    }

    public static String convertPrimitiveTypeFromJavaToSmaliStyle(String type){
        if(type.equals("int")) return "I";
        if(type.equals("float")) return "F";
        if(type.equals("boolean")) return "Z";
        if(type.equals("long")) return "J";
        if(type.equals("double")) return "D";
        if(type.equals("short")) return "S";
        if(type.equals("char")) return "C";
        if(type.equals("byte")) return "B";
        if(type.equals("void")) return "V";
        throw new IllegalArgumentException();
    }

    public static String convertPrimitiveTypeFromSmaliToJavaStyle(String type){
        if(type.equals("I")) return "int";
        if(type.equals("F")) return "float";
        if(type.equals("Z")) return "boolean";
        if(type.equals("J")) return "long";
        if(type.equals("D")) return "double";
        if(type.equals("S")) return "short";
        if(type.equals("B")) return "byte";
        if(type.equals("C")) return "char";
        if(type.equals("V")) return "void";
        throw new IllegalArgumentException();
    }

    public static Class getPrimitiveClassFromSmaliType(char c){
        if(c == 'I') return int.class;
        else if(c == 'F') return float.class;
        else if(c == 'D') return double.class;
        else if(c == 'J') return long.class;
        else if(c == 'Z') return boolean.class;
        else if(c == 'S') return short.class;
        else if(c == 'B') return byte.class;
        else if(c == 'C') return char.class;
        else if(c == 'V') return void.class;
        throw new IllegalArgumentException();
    }

    public static Object getDefaultPrimitiveValue(char c){
        if(c == 'I') return 0;
        if(c == 'F') return 0.0F;
        if(c == 'D') return 0.0D;
        if(c == 'J') return 0L;
        if(c == 'Z') return false;
        if(c == 'S') return ((short) 0);
        if(c == 'C') return ((char) 0);
        if(c == 'B') return ((byte) 0);
        throw new IllegalArgumentException();
    }

    /**
     * This method checks if a type indicated by a string in Smali format
     * is a class that already exists in system or not. These classes include
     * normal java classes such as Object, ArrayList, ... or Android classes
     * such as Activity, Service, etc.
     * @param smaliStyleClassPath
     * @return true if the queried type can be accessed using the Class.forName API and false
     * if any exception or error happens during this API call.
     */
    public static boolean isJavaOrAndroidExistingClass(String smaliStyleClassPath){
        String javaStyleClassPath = SimulationUtils.makeJavaStyleClassPath(smaliStyleClassPath);
        try {
            if(isPrimitiveTypeJavaStyle(javaStyleClassPath)) return true;
            Class.forName(javaStyleClassPath);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
        catch (Throwable e){
            return false;
        }
    }

    public static String makeJavaStyleClassPath(String raw){
        if(isPrimitiveType(raw)){
            return convertPrimitiveTypeFromSmaliToJavaStyle(raw);
        }
        else if(raw.startsWith("[")){
            String res = raw.replace("/", ".");
            return res;
        }
        else {
            String res = raw.replace("/",".").replace(";","");
            if(res.startsWith("L")){
                res = res.substring(1);
            }
            return res;
        }
    }

    public static String makeSmaliStyleClassPath(String raw){
        String res;
        switch (raw){
            case "int":
                res = "I";
                break;
            case "float":
                res = "F";
                break;
            case "long":
                res = "J";
                break;
            case "double":
                res = "D";
                break;
            case "boolean":
                res = "Z";
                break;
            case "char":
                res = "C";
                break;
            case "byte":
                res = "B";
                break;
            case "short":
                res = "S";
                break;
            case "void":
                res = "V";
                break;
            default:
                // this is because of lambda classes that start with - in their name
                // since - is an illegal character for us we have to replace it by _
                // this name change can cause problem when converting back from
                // java names to smali style names so we have to keep a map of
                // classes with these names
                if(ClazzLoader.classesWithReplacedIllegalNames.containsKey(raw)){
                    return ClazzLoader.classesWithReplacedIllegalNames.get(raw);
                }
                res = raw.replace(".", "/");
                if( !raw.startsWith("[") ) res = "L" + res + ";";
        }
        return res;
    }

    public static String[] parseMethodArgumentsString(String argsString){
        ArrayList<String> res = new ArrayList<>();
        boolean parsingObj = false;
        boolean parsingArray = false;
        String temp = null;
        for(char c: argsString.toCharArray()){
            if(parsingObj){
                if(c == ';'){
                    temp = temp.concat(";");
                    parsingArray = false;
                    parsingObj = false;
                }
                else {
                    temp = temp.concat(Character.toString(c));
                }
            }
            else if(c == '['){
                if(parsingArray){
                    temp = temp.concat("[");
                }
                else {
                    if(temp != null){
                        res.add(temp);
                    }
                    parsingArray = true;
                    temp = "[";
                }
            }
            else if(c == 'I'){
                if(parsingArray){
                    temp = temp.concat("I");
                    parsingArray = false;
                }
                else {
                    if(temp != null){
                        res.add(temp);
                    }
                    parsingArray = false;
                    temp = "I";
                }
            }
            else if(c == 'F'){
                if(parsingArray){
                    temp = temp.concat("F");
                    parsingArray = false;
                }
                else {
                    if(temp != null){
                        res.add(temp);
                    }
                    parsingArray = false;
                    temp = "F";
                }
            }
            else if(c == 'Z'){
                if(parsingArray){
                    temp = temp.concat("Z");
                    parsingArray = false;
                }
                else {
                    if(temp != null){
                        res.add(temp);
                    }
                    parsingArray = false;
                    temp = "Z";
                }
            }
            else if(c == 'D'){
                if(parsingArray){
                    temp = temp.concat("D");
                    parsingArray = false;
                }
                else {
                    if(temp != null){
                        res.add(temp);
                    }
                    parsingArray = false;
                    temp = "D";
                }
            }
            else if(c == 'J'){
                if(parsingArray){
                    temp = temp.concat("J");
                    parsingArray = false;
                }
                else {
                    if(temp != null){
                        res.add(temp);
                    }
                    parsingArray = false;
                    temp = "J";
                }
            }
            else if(c == 'S'){
                if(parsingArray){
                    temp = temp.concat("S");
                    parsingArray = false;
                }
                else {
                    if(temp != null){
                        res.add(temp);
                    }
                    parsingArray = false;
                    temp = "S";
                }
            }
            else if(c == 'B'){
                if(parsingArray){
                    temp = temp.concat("B");
                    parsingArray = false;
                }
                else {
                    if(temp != null){
                        res.add(temp);
                    }
                    parsingArray = false;
                    temp = "B";
                }
            }
            else if(c == 'C'){
                if(parsingArray){
                    temp = temp.concat("C");
                    parsingArray = false;
                }
                else {
                    if(temp != null){
                        res.add(temp);
                    }
                    parsingArray = false;
                    temp = "C";
                }
            }
            else if(c == 'L'){
                if(parsingArray){
                    temp = temp.concat("L");
                    parsingObj = true;
                }
                else {
                    if(temp != null){
                        res.add(temp);
                    }
                    parsingObj = true;
                    temp = "L";
                }
            }
            else {
                throw new IllegalStateException();
            }
        }
        if(temp != null){
            res.add(temp);
        }
        String[] resArray = res.toArray(new String[res.size()]);
        return resArray;
    }

    public static String getSmaliMethodSignature(Method method) {
        String methodName = method.getName();
        Class[] params = method.getParameterTypes();
        Class retType = method.getReturnType();
        StringBuilder res = new StringBuilder();
        res.append(methodName);
        res.append("(");
        for(Class c: params){
            res.append(SimulationUtils.makeSmaliStyleClassPath(c.getName()));
        }
        res.append(")");
        res.append(SimulationUtils.makeSmaliStyleClassPath(retType.getName()));
        return res.toString();
    }

    public static String getSmaliMethodSignature(Constructor constructor) {
        Class[] params = constructor.getParameterTypes();
        StringBuilder res = new StringBuilder();
        res.append("<init>");
        res.append("(");
        for(Class c: params){
            res.append(SimulationUtils.makeSmaliStyleClassPath(c.getName()));
        }
        res.append(")");
        res.append("V");
        return res.toString();
    }

    //----------------------------------------------

    public static Class<?>[] getTypeClasses(String[] args, ClazzLoader loader) {
        Class<?>[] classes = new Class[args.length];
        for (int i = 0; i < classes.length; i++) {
            switch (args[i]){
                case "I":
                    classes[i] = int.class;
                    break;
                case "F":
                    classes[i] = float.class;
                    break;
                case "J":
                    classes[i] = long.class;
                    break;
                case "D":
                    classes[i] = double.class;
                    break;
                case "Z":
                    classes[i] = boolean.class;
                    break;
                case "B":
                    classes[i] = byte.class;
                    break;
                case "S":
                    classes[i] = short.class;
                    break;
                case "C":
                    classes[i] = char.class;
                    break;
                default:
                    classes[i] = loader.getClazz(args[i]).getMirroringClass();
                    break;
            }
        }
        return classes;
    }

    public static Object createArray(String arrayClassname, int size) throws ClassNotFoundException {
        Object o;
        switch (arrayClassname){
            case "I":
                o = new int[size];
                break;
            case "F":
                o = new float[size];
                break;
            case "J":
                o = new long[size];
                break;
            case "D":
                o = new double[size];
                break;
            case "Z":
                o = new boolean[size];
                break;
            case "C":
                o = new char[size];
                break;
            case "B":
                o = new byte[size];
                break;
            case "S":
                o = new short[size];
                break;
            default:
                String javaClassName = makeJavaStyleClassPath(arrayClassname);
                Class<?> cls = Class.forName(javaClassName);
                o = Array.newInstance(cls ,size);
        }
        return o;
    }

    public static void setFieldValue(Field field, Object value, Object refValue){
        field.setAccessible(true);
        try {
            String fieldType = SimulationUtils.makeSmaliStyleClassPath(field.getType().getName());
            if(value == null){
                field.set(refValue, null);
            }
            else if (SimulationUtils.isPrimitiveType(fieldType)){
                field.set(refValue, value);
            }
            else {
                AbstractObjekt abstractObjekt = (AbstractObjekt) value;
                Object rawValue = abstractObjekt.getMirroringObject();
                field.set(refValue, rawValue);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Object createWrapperObjektFromRawValue(Object rawValue,boolean isPrimitive, ClazzLoader loader){
        if( rawValue == null ) return null;
        if(isPrimitive) return rawValue;

        String valueType = makeSmaliStyleClassPath(rawValue.getClass().getName());
        Clazz clazz = loader.getClazz(valueType);

        if(clazz instanceof SmaliArrayClazz){
            return new ArrayObjekt(clazz, rawValue);
        }
        else if(clazz instanceof SmaliClazz){
            return new Objekt(clazz, rawValue);
        }
        else {
            if(clazz.getClassPath().startsWith("[")){
                return new ArrayObjekt(clazz, rawValue);
            }
            else {
                return new Objekt(clazz, rawValue);
            }
        }
    }

    public static void setMethodExecutionArguments(MethodExecution methodExecution,
                                                   Object[] args){
        int regCounter = methodExecution.getArgumentStartRegister();
        String[] argTypes = methodExecution.getMethodArgTypes();
        if(args.length != argTypes.length){
            throw new IllegalArgumentException();
        }
        for(int i = 0; i < argTypes.length; i++) {
            String argType = argTypes[i];
            Object arg = args[i];
            Register register = methodExecution.getRegister(regCounter);
            if(arg == null){
                register.set((AbstractObjekt) null);
            }
            else if(arg instanceof AmbiguousValue){
                register.set((AmbiguousValue) arg);
                if(argType.equals("D") || argType.equals("J")){
                    regCounter++;
                }
            }
            else if(argType.equals("I") && arg instanceof Integer){
                register.set((Integer) arg);
            }
            else if(argType.equals("F") && arg instanceof Float){
                register.set((Float) arg);
            }
            else if(argType.equals("D") && arg instanceof Double){
                register.set((Double) arg);
                regCounter++;
            }
            else if(argType.equals("J") && arg instanceof Long){
                register.set((Long) arg);
                regCounter++;
            }
            else if(argType.equals("Z") && arg instanceof Boolean){
                register.set((Boolean) arg);
            }
            else if(argType.equals("C") && arg instanceof Character){
                Character c = (Character) arg;
                register.set(c);
            }
            else if(argType.equals("S") && arg instanceof Short){
                Short s = (Short) arg;
                register.set(s);
            }
            else if(argType.equals("B") && arg instanceof Boolean){
                Boolean b = (Boolean) arg;
                register.set(b);
            }
            else {
                String s = SimulationUtils.makeSmaliStyleClassPath(arg.getClass().getName());
                Clazz clazz = methodExecution.getClazzLoader().getClazz(s);
                if(clazz.isArray()){
                    ArrayObjekt ao = new ArrayObjekt(clazz, arg);
                    register.set(ao);
                }
                else {
                    Objekt o = new Objekt(clazz, arg);
                    register.set(o);
                }
            }
            regCounter++;
        }
    }

    public static void setMethodExecutionWrappedArguments(MethodExecution methodExecution, Object[] args){
        int regOffset = 0;
        String[] argTypes = methodExecution.getMethodArgTypes();
        for (int i = 0; args!= null && i < args.length; i++) {
            Register r = methodExecution.getRegister(methodExecution.getArgumentStartRegister() + regOffset);
            Object o = args[i];
            if(o instanceof Integer) r.set((Integer) o);
            else if(o instanceof Float) r.set((Float) o);
            else if(o instanceof Double){
                r.set((Double) o);
                regOffset++;
            }
            else if(o instanceof Long){
                r.set((Long) o);
                regOffset++;
            }
            else if(o instanceof Boolean) r.set((Boolean) o);
            else if(o instanceof Short) r.set((Short) o);
            else if(o instanceof Byte) r.set((Byte) o);
            else if(o instanceof Character) r.set((Character) o);
            else if(o instanceof AbstractObjekt || o == null) r.set((AbstractObjekt) o);
            else if(o instanceof AmbiguousValue){
                r.set((AmbiguousValue) o);
                if("D".equals(argTypes[i]) || "J".equals(argTypes[i])){
                    regOffset++;
                }
            }
            else {
                throw new IllegalStateException("type checking should not have let this exception to happen!");
            }
            regOffset++;
        }
    }

    //--------------------------------------------------

    public static String getSupperWrapperInjectedConstant(){
        return "_superWrapper_in_";
    }

    public static String getSuperWrapperMethodName(SmaliMethod overridingMethod) {
        String methodOnlySig = overridingMethod.getClassMethodSignature().split("->")[1];
        String methodOnlyName = methodOnlySig.substring(0, methodOnlySig.indexOf('('));
        String normalizedClassName = overridingMethod.getContainingClass().getClassPath();
        // removing L and ;
        normalizedClassName = normalizedClassName.substring(1, normalizedClassName.length()-1);
        // replacing / with _
        normalizedClassName = normalizedClassName.replace('/','_');
        return methodOnlyName + getSupperWrapperInjectedConstant() + normalizedClassName;
    }

//    public static String getSuperWrapperMethodName(Method overridingMethod) {
//        String methodOnlyName = overridingMethod.getName();
//        String normalizedClassName = Utils.makeSmaliStyleClassPath(overridingMethod.getDeclaringClass().getName());
//        // removing L and ;
//        normalizedClassName = normalizedClassName.substring(1, normalizedClassName.length()-1);
//        // replacing / with _
//        normalizedClassName = normalizedClassName.replace('/','_');
//        return methodOnlyName + getSupperWrapperInjectedConstant() + normalizedClassName;
//    }

    public static boolean ClassUsesAnyInvokeSuper(SmaliClass sc) {
        for(SmaliMethod sm: sc.getAllMethods()){
            if(sm.getInstructionList() == null) return false;
            for(SmaliInstruction si: sm.getInstructionList()){
                if(si instanceof InvokeSuperInstruction){
                    return true;
                }
            }
        }
        return false;
    }

    //------------------------------------------------------

    public static HashSet<String> getStaticSafeClasses(AssetManager assetManager){
        HashSet<String> res = new HashSet<>();
        fillHashSet(res, "SafeTypes/StaticSafeClasses.txt", assetManager);
        return res;
    }

    public static HashSet<String> getStaticPartialSafeClasses(AssetManager assetManager){
        HashSet<String> res = new HashSet<>();
        fillHashSet(res, "SafeTypes/StaticPartialSafeClasses.txt", assetManager);
        return res;
    }

    public static HashSet<String> getInstanceSafeClasses(AssetManager assetManager){
        HashSet<String> res = new HashSet<>();
        fillHashSet(res, "SafeTypes/InstanceSafeClasses.txt", assetManager);
        return res;
    }

    public static HashSet<String> getInstancePartialSafeClasses(AssetManager assetManager){
        HashSet<String> res = new HashSet<>();
        fillHashSet(res, "SafeTypes/InstancePartialSafeClasses.txt", assetManager);
        return res;
    }

    public static HashSet<String> getImmutableClasses(AssetManager assetManager){
        HashSet<String> res = new HashSet<>();
        fillHashSet(res, "all_immutable_classes.txt", assetManager);
        return res;
    }

    public static HashSet<String> getPureMethods(AssetManager assetManager){
        HashSet<String> res = new HashSet<>();
        fillHashSet(res, "all_pure_methods.txt", assetManager);
        return res;
    }

    public static HashSet<String> getAndroidEntryPointMethods(AssetManager assetManager){
        HashSet<String> res = new HashSet<>();
        fillHashSet(res, "ExecutionEntryPoints/AppEntryPoints.txt",assetManager);
        return res;
    }

    public static HashSet<String> getAndroidCallBackInterfaces(AssetManager assetManager){
        HashSet<String> res = new HashSet<>();
        fillHashSet(res, "ExecutionEntryPoints/AppCallBackInterfaces.txt",assetManager);
        return res;
    }

    private static void fillHashSet(HashSet<String> set,
                             String assetPath,
                             AssetManager assetManager){
        try {
            InputStream inputStream = assetManager.open(assetPath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = reader.readLine();
            while (line != null){
                set.add(line.trim());
                line = reader.readLine();
            }
        }
        catch (Exception e){
            throw new IllegalStateException(e);
        }
    }

    //-------------------------------------------------------------------

    public static boolean isMethodPure(Clazz actualMethodDefiningClazz,
                                       Method method,
                                       ClazzLoader loader){
        StringBuilder sb = new StringBuilder();
        sb.append(actualMethodDefiningClazz.getClassPath());
        sb.append("->");
        sb.append(method.getName());
        sb.append("(");
        for(Class c: method.getParameterTypes()){
            sb.append(SimulationUtils.makeSmaliStyleClassPath(c.getName()));
        }
        sb.append(")");
        sb.append(SimulationUtils.makeSmaliStyleClassPath(method.getReturnType().getName()));

        String classMethodSig = sb.toString();

        if(loader.getPureMethods().contains(classMethodSig)) return true;

        if(loader.getPureMethods().contains(actualMethodDefiningClazz.getClassPath() + "->*")) return true;

        String methodSig = classMethodSig.split("->")[1];
        if(loader.getPureMethods().contains("*->" + methodSig)) return true;

        return false;
    }

    public static boolean isMethodPure(String classMethodSignature,
                                       ClazzLoader loader){
        if(loader.getPureMethods().contains(classMethodSignature)){
            return true;
        }
        String classPath = classMethodSignature.split("->")[0];
        String methodSig = classMethodSignature.split("->")[1];

        if(loader.getPureMethods().contains(classPath + "->*")) return true;


        if(loader.getPureMethods().contains("*->" + methodSig)) return true;

        return false;
    }

    public static boolean isMethodPure(Clazz actualMethodDefiningClazz,
                                       Constructor ctor,
                                       ClazzLoader loader){
        StringBuilder sb = new StringBuilder();
        sb.append(actualMethodDefiningClazz.getClassPath());
        sb.append("->");
        sb.append("<init>");
        sb.append("(");
        for(Class c: ctor.getParameterTypes()){
            sb.append(SimulationUtils.makeSmaliStyleClassPath(c.getName()));
        }
        sb.append(")V");

        String classMethodSig = sb.toString();
        if(loader.getPureMethods().contains(classMethodSig)){
            return true;
        }

        String classPath = classMethodSig.split("->")[0];
        String methodSig = classMethodSig.split("->")[1];

        if(loader.getPureMethods().contains(classPath + "->*")) return true;


        if(loader.getPureMethods().contains("*->" + methodSig)) return true;


        return false;
    }

//    public static boolean shouldReferenceObjectChangeToAmbiguousValue(Register referenceRegister){
//        if(!referenceRegister.containsRefToObject()){
//            throw new IllegalStateException();
//        }
//        AbstractObjekt ao = referenceRegister.getReferencedObjectValue();
//        return shouldReferenceObjectChangeToAmbiguousValue(ao);
//    }

//    public static boolean shouldReferenceObjectChangeToAmbiguousValue(AbstractObjekt ao){
//        //TODO a better way for checking this should be coded
////        Object o = ao.getMirroringObject();
////        if(o == null) return true;
////        if(o instanceof MockActivity) return false;
////        if(o instanceof MockAppCompatActivity) return false;
////        if(o instanceof MockService) return false;
//        return true;
//    }

    public static boolean isResultTypePrimitiveType(ResultType type){
        if(type == ResultType.INTEGER) return true;
        if(type == ResultType.FLOAT) return true;
        if(type == ResultType.LONG) return true;
        if(type == ResultType.DOUBLE) return true;
        if(type == ResultType.BOOLEAN) return true;
        if(type == ResultType.CHAR) return true;
        if(type == ResultType.SHORT) return true;
        if(type == ResultType.BYTE) return true;
        return false;
    }

    public static String getInvokedMethodInfoJsonStr(SmaliMethod invokedSM){
        if(invokedSM == null) return null;
        StringBuilder sb = new StringBuilder();
        sb.append("{");
//        sb.append("\"first passed argument register\":")
//                .append(invokedSM.getFirstPassedArgumentRegisterNumber())
//                .append(",");

        sb.append("\"local registers\":")
                .append(invokedSM.getNumberOfLocalRegisters())
                .append(",");

        sb.append("\"total registers\":")
                .append(invokedSM.getTotalNumberOfRegistersUsed())
                .append(",");

        //TODO maybe can be not logged
        sb.append("\"signature\":\"")
                .append(invokedSM.getClassMethodSignature())
                .append("\"").append(",");

        //TODO instead of modifiers we can only log is static
        sb.append("\"modifiers\":\"");
        if(invokedSM.isStatic()) sb.append("static ");
        sb.append(invokedSM.getAccessModifierString())
                .append("\"");

        sb.append("}");
        return sb.toString();
    }
}
