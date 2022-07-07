import java.io.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashSet;

public class Utils {

    public static int countChar(String string, char c) {
        int count = 0;
        for (int i = 0; i < string.length(); i++) {
            if(c == string.charAt(i)){
                count++;
            }
        }
        return count;
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
//                // this is because of lambda classes that start with - in their name
//                // since - is an illegal character for us we have to replace it by _
//                // this name change can cause problem when converting back from
//                // java names to smali style names so we have to keep a map of
//                // classes with these names
//                if(ClazzLoader.classesWithReplacedIllegalNames.containsKey(raw)){
//                    return ClazzLoader.classesWithReplacedIllegalNames.get(raw);
//                }
                res = raw.replace(".", "/");
                if( !raw.startsWith("[") ) res = "L" + res + ";";
        }
        return res;
    }

    public static File checkForTooLongFileNamesAndFixIfNecessary(File methodAnalysisDir){
        if(methodAnalysisDir.exists() && !methodAnalysisDir.isDirectory()) throw new IllegalStateException();

        String methodName = methodAnalysisDir.getName();
        String className = methodAnalysisDir.getParentFile().getName();

        File appDir = methodAnalysisDir.getParentFile().getParentFile();
        if(!appDir.exists()){
            appDir.mkdirs();
        }

        if(methodName.length() < 256 && className.length() < 256){
            return methodAnalysisDir;
        }
        else {
            MessageDigest md5;
            try {
                md5 = MessageDigest.getInstance("MD5");
            }
            catch (Exception e){
                throw new IllegalStateException(e);
            }

            if(className.length() > 255){
                md5.update(className.getBytes());
                String originalClassName = className;
                className = bytesToHex(md5.digest());
                md5.reset();
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(new File(appDir, "hashedClassNamesMap.txt")));
                    writer.write(originalClassName + "|" + className);
                    writer.flush();
                    writer.close();
                }
                catch (Exception e){
                    throw new IllegalStateException(e);
                }

            }

            File classDir = new File(appDir, className);
            if(!classDir.exists()){
                classDir.mkdirs();
            }

            if(methodName.length() > 255){
                md5.update(methodName.getBytes());
                String originalMethodName = methodName;
                methodName = bytesToHex(md5.digest());
                md5.reset();
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(new File(classDir, "hashedMethodNamesMap.txt")));
                    writer.write(originalMethodName + "|" + methodName);
                    writer.flush();
                    writer.close();
                }
                catch (Exception e){
                    throw new IllegalStateException(e);
                }


            }

            File f = new File(classDir, methodName);
            if(!f.exists()){
                f.mkdirs();
            }
            return f;
        }
    }

    public static String bytesToHex(byte[] bytes) {
        char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static HashSet<String> getStaticSafeClasses(){
        HashSet<String> res = new HashSet<>();
        fillHashSet(res, "StaticSafeClasses.txt");
        return res;
    }

    public static HashSet<String> getStaticPartialSafeClasses(){
        HashSet<String> res = new HashSet<>();
        fillHashSet(res, "StaticPartialSafeClasses.txt");
        return res;
    }

    public static HashSet<String> getInstanceSafeClasses(){
        HashSet<String> res = new HashSet<>();
        fillHashSet(res, "InstanceSafeClasses.txt");
        return res;
    }

    public static HashSet<String> getInstancePartialSafeClasses(){
        HashSet<String> res = new HashSet<>();
        fillHashSet(res, "InstancePartialSafeClasses.txt");
        return res;
    }

    public static HashSet<String> getImmutableClasses(){
        HashSet<String> res = new HashSet<>();
        fillHashSet(res, "all_immutable_classes.txt");
        return res;
    }

    public static HashSet<String> getPureMethods(){
        HashSet<String> res = new HashSet<>();
        fillHashSet(res, "all_pure_methods.txt");
        return res;
    }

    private static void fillHashSet(HashSet<String> set,
                                    String assetPath){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(assetPath));
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

    public static String stackTraceToString(Throwable t){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

}
