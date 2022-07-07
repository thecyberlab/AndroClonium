package usask.cyberlab.smalisimulator.simsmali.preprocess;


import com.google.common.base.Charsets;

import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.writer.builder.BuilderClassDef;
import org.jf.dexlib2.writer.builder.DexBuilder;
import org.jf.smali.smaliFlexLexer;
import org.jf.smali.smaliParser;
import org.jf.smali.smaliTreeWalker;

import java.io.*;


import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;

public class SmaliParser {

    public static final int DEFAULT_API_LEVEL = 28;

    public static SmaliClass parse(String classPath, String basePath) throws FileNotFoundException, RecognitionException {
        String filePath = basePath + "/" +classPath.replace(";","").substring(1) + ".smali";
        File smaliFile = new File(filePath);
        if (smaliFile.isDirectory()) {
            throw new IllegalArgumentException();
        }

        DexBuilder dexBuilder = new DexBuilder(Opcodes.forApi(SmaliParser.DEFAULT_API_LEVEL));
        InputStream is = new FileInputStream(smaliFile);
        InputStreamReader reader = new InputStreamReader(is, Charsets.UTF_8);
        smaliFlexLexer lexer = new smaliFlexLexer(reader, DEFAULT_API_LEVEL);
        lexer.setSourceFile(smaliFile);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        smaliParser parser = new smaliParser(tokens);
        parser.setApiLevel(DEFAULT_API_LEVEL);

        smaliParser.smali_file_return result = parser.smali_file();
        if ((parser.getNumberOfSyntaxErrors() > 0) || (lexer.getNumberOfSyntaxErrors() > 0)) {
            throw new RuntimeException("Unable to parse: " + smaliFile);
        }

        CommonTree t = result.getTree();
        CommonTreeNodeStream treeStream = new CommonTreeNodeStream(t);
        treeStream.setTokenStream(tokens);

        smaliTreeWalker dexGen = new smaliTreeWalker(treeStream);
        dexGen.setVerboseErrors(false);
        dexGen.setDexBuilder(dexBuilder);
        BuilderClassDef classDef = (BuilderClassDef) dexGen.smali_file();
        if (dexGen.getNumberOfSyntaxErrors() != 0) {
            throw new RuntimeException("Unable to walk: " + smaliFile);
        }

        return new SmaliClass(classDef);
    }
}
