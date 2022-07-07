import java.util.ArrayList;

public class InstructionNormalizer {

    private static boolean checkTypeStringStartWithNormalizingPrefixes(String input){
        String[] ss = Config.packagePrefixesForNormalization;
        for(String s: ss){
            if(input.startsWith(s)){
                return true;
            }
        }
        return false;
    }

    private static String normalizeType(String type){
        if(type.startsWith("[")){
            int lastBracketPos = type.lastIndexOf('[');
            String bracketPart = type.substring(0, lastBracketPos + 1);
            String typePart = type.substring(lastBracketPos + 1);
            return bracketPart + normalizeType(typePart);
        }
        else if(type.startsWith("L")){
            if(!checkTypeStringStartWithNormalizingPrefixes(type)){
                return  "Lx;";
            }
            return type;
        }
        return type;
    }

    private static String[] splitMethodArgs(String methodArgsString){
        char[] methodArgsCharArray = methodArgsString.toCharArray();
        ArrayList<String> args = new ArrayList<>();
        StringBuilder tmp = new StringBuilder();
        boolean justAppend = false;
        boolean inBrackets = false;
        for (char c: methodArgsCharArray) {
            if (justAppend){
                tmp.append(c);
                if(inBrackets){
                    if(c != '['){
                        inBrackets = false;
                        if(c != 'L'){
                            justAppend = false;
                            args.add(tmp.toString());
                        }
                    }
                }
                else if(c==';') {
                    justAppend = false;
                    args.add(tmp.toString());
                }
            }
            else if(c=='I' ||
                    c=='F' ||
                    c=='J' ||
                    c=='D' ||
                    c=='Z' ||
                    c=='C' ||
                    c=='S' ||
                    c=='B'){
                args.add(""+c);
            }
            else if (c=='L' || c== '['){
                tmp = new StringBuilder();
                tmp.append(c);
                justAppend = true;
                if(c=='['){
                    inBrackets = true;
                }
            }
        }
        return args.toArray(new String[0]);
    }

    private static String normalizeInvokedMethodSignature(String classMethodSignature) {
        String methodPart = classMethodSignature.split("->")[1];
        String methodArgString = methodPart.split("\\(")[1].split("\\)")[0];
        String methodRetType = methodPart.split("\\)")[1];

        String[] methodArgs = splitMethodArgs(methodArgString);
        StringBuilder newMethodArgString = new StringBuilder();
        for (String argType : methodArgs) {
            newMethodArgString.append(normalizeType(argType));
        }
        return  "_->_" + "(" + newMethodArgString.toString() + ")" + normalizeType(methodRetType);

    }

    static ArrayList<String> normalizeTraceInstructions(ArrayList<TraceInstruction> simpleFragment){
        ArrayList<String> result = new ArrayList<>();
        for (TraceInstruction ti: simpleFragment) {
            String inst = ti.getInstruction();
            String[] splits = inst.split(" ");

            if(inst.startsWith("invoke-")){
                String classMethodSignature = splits[splits.length - 2];
                // invoke-virtual <- (invoke-virtual, invoke-virtual/range)
                // invoke-super <- (invoke-super, invoke-super/range)
                // invoke-direct <- (invoke-direct, invoke-direct/range)
                // invoke-static <- (invoke-static, invoke-static/range)
                // invoke-interface <- (invoke-interface, invoke-interface/range)

                String mnemonic = splits[0].split("/")[0];
                if(checkTypeStringStartWithNormalizingPrefixes(classMethodSignature)){
                    result.add(mnemonic + ":" + splits[splits.length - 2]);
                }
                else{
                    String normalizedClassMethodSignature = normalizeInvokedMethodSignature(classMethodSignature);
                    result.add(mnemonic + ":" + normalizedClassMethodSignature);
                }
            }

            else if(inst.startsWith("iput-object") || inst.startsWith("iget-object") ||
                    inst.startsWith("sput-object") || inst.startsWith("sget-object")){
                String fieldNameType = splits[splits.length-2];
                String fieldType = fieldNameType.split(":")[1];
                if(checkTypeStringStartWithNormalizingPrefixes(fieldType)){

                    result.add(splits[0] + ":" + fieldType);
                }
                else {
                    result.add(splits[0]);
                }
            }

            else if(inst.startsWith("new-instance") || inst.startsWith("new-array")){
                String dataType = splits[splits.length-2];
                if(checkTypeStringStartWithNormalizingPrefixes(dataType)){
                    result.add(splits[0] + ":" + dataType);
                }
                else {
                    result.add(splits[0]);
                }
            }

            else {
                // nop <- nop
                // move <- (move, move/from16, move/16)
                // move-wide <- (move-wide, move-wide/from16, move-wide/16)
                // move-object <- (move-object, move-object/from16, move-object/16)
                // move-result <- move-result
                // move-result-wide <- move-result-wide
                // move-result-object <- move-result-object
                // move-exception <- move-exception
                // return <- return
                // return-wide <- return-wide
                // return-object <- return-object
                // const <- (const/4, const/16, const, const/high16)
                // const-wide <- (const-wide/16, const-wide/32, const-wide, const-wide/high16)
                // const-string <- (const-string, const-string/jumbo)
                // const-class <- const-class
                // monitor-enter <- monitor-enter
                // monitor-exit <- monitor-exit
                // check-cast <- check-cast
                // instance-of <- instance-of
                // array-length <- array-length
                // filled-new-array <- (filled-new-array, filled-new-array/range)
                // fill-array-data <- fill-array-data
                // throw <- throw
                // packed-switch <- packed-switch
                // sparse-switch <- sparse-switch
                // cmpl-float <- cmpl-float
                // cmpg-float <- cmpg-float
                // cmpl-double <- cmpl-double
                // cmpg-double <- cmpg-double
                // cmp-long <- cmp-long
                // ...
                String mnemonic = splits[0];
                // remove different variances of register addressing in instructions
                result.add(mnemonic.split("/")[0]);
            }
        }
        return result;
    }


////    static ArrayList<String> normalizeTraceInstructionsKeepLiterals(ArrayList<TraceInstruction> simpleFragment){
////        ArrayList<String> result = new ArrayList<>();
////        for (TraceInstruction ti: simpleFragment) {
////            String inst = ti.getInstruction();
////            String[] splits = ti.getInstructionSplits();
////            if(inst.startsWith("invoke-")){
////                String classMethodSignature = splits[splits.length - 2];
////                if(checkTypeStringStartWithNormalizingPrefixes(classMethodSignature)){
////                    result.add(splits[0] + ":" + splits[splits.length - 2]);
////
////                }
////                else{
////                    String normalizedClassMethodSignature = normalizeInvokedMethodSignature(classMethodSignature);
////                    result.add(splits[0] + ":" + normalizedClassMethodSignature);
////                }
////            }
////            else if(inst.startsWith("iput-object") || inst.startsWith("iget-object") ||
////                    inst.startsWith("sput-object") || inst.startsWith("sget-object")){
////                String fieldNameType = splits[splits.length-2];
////                String fieldType = fieldNameType.split(":")[1];
////                if(checkTypeStringStartWithNormalizingPrefixes(fieldType)){
////
////                    result.add(splits[0] + ":" + fieldType);
////                }
////                else {
////                    result.add(splits[0]);
////                }
////            }
////            else if(inst.startsWith("const")){
////                String literalValue = splits[splits.length-2];
////                result.add(splits[0] + ":" + literalValue);
////            }
////            else {
////                String mnemonic = splits[0];
////                result.add(mnemonic.split("/")[0]);
////            }
////        }
////        return result;
////    }


}
