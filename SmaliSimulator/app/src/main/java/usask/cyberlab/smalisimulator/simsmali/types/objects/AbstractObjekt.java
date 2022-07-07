package usask.cyberlab.smalisimulator.simsmali.types.objects;

import android.content.pm.ApplicationInfo;
import android.os.Debug;

import org.jetbrains.annotations.NotNull;

import usask.cyberlab.smalisimulator.simsmali.types.classes.Clazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
import usask.cyberlab.smalisimulator.simsmali.types.classes.ReflectedClazz;
import usask.cyberlab.smalisimulator.simsmali.types.classes.SmaliClazz;

/**
 * this class base type of Objekt class and ArrayObjekt class and is wrapper
 * for all objects created during execution. This wrapper provides some extra functionality when working with java objects
 *
 */
public abstract class AbstractObjekt {
//    protected final String type;
    protected final Clazz clazz;
    protected Object mirroringObject;

    public AbstractObjekt(Clazz clazz){
        this.clazz = clazz;
//        this.type = type;
    }

    public AbstractObjekt(Clazz clazz, Object mirroringObject){
        this.clazz = clazz;
//        this.type = type;
        this.setMirroringObject(mirroringObject);
    }

    // abstract methods

    public abstract void setMirroringObject(Object mirroringObject);

    // TODO indirect references to objects with ambiguousValues has to be handled later
//    public abstract boolean indirectlyContainsAmbiguousValue();

    // concrete methods
    public boolean isInstanceOf(String queriedClassPath){
        switch (queriedClassPath) {
            case "I":
            case "F":
            case "J":
            case "D":
            case "Z":
            case "C":
            case "B":
            case "S":
                return false;
            default:
                Clazz queriedClazz = getMyClassLoader().getClazz(queriedClassPath);
                return queriedClazz.isSuperTypeOf(this.getType());
        }
    }

    public String getType() {
        return clazz.getClassPath();
    }

    public Clazz getClazz(){
        return this.clazz;
    }

    public ClazzLoader getMyClassLoader(){
        return clazz.getClazzLoader();
    }

    public boolean equals(Object object){
        if(object == this) return true;

        if(object instanceof AbstractObjekt){
            AbstractObjekt objekt = (AbstractObjekt) object;
            return mirroringObject.equals(objekt.mirroringObject);
        }
        else {
            return false;
        }
    }

    public Object getMirroringObject(){
        return mirroringObject;
    }

    @NotNull
    @Override
    public String toString(){
        if(mirroringObject == null){
            return this.getType() + ":(null mirror object!)";
        }
        if((clazz instanceof SmaliClazz) && Debug.isDebuggerConnected()){
            return "not allowed in debug mode!";
        }
        else {
            return mirroringObject.toString();
        }

    }


}
