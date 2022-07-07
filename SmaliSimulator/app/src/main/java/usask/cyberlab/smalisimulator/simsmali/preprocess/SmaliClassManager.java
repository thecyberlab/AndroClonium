package usask.cyberlab.smalisimulator.simsmali.preprocess;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliFileNotFoundException;
import usask.cyberlab.smalisimulator.simsmali.exceptions.SmaliFileParseException;
import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;

// this class acts as a cache when it comes to reading the .smali files
// a parsed .smali file is represented by a SmaliClass
public class SmaliClassManager {
    private final Map<String, SmaliClass> cache = new HashMap<>();
    private final String basePath;

    public SmaliClassManager(String basePath) {
        this.basePath = basePath;
    }

    public SmaliClass getSmaliClass(String classPath) {
        if(cache.containsKey(classPath)){
            return cache.get(classPath);
        }
        else {
            try {
                SmaliClass sc = SmaliParser.parse(classPath, basePath);
                cache.put(classPath,sc);
                return sc;
            }
            catch (FileNotFoundException e){
                throw new SmaliFileNotFoundException(e);
            }
            catch (Throwable e) {
                throw new SmaliFileParseException("Error parsing : " + classPath, e);
            }
        }
    }


}
