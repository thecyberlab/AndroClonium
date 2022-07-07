package usask.cyberlab.smalisimulator.simsmali.representations;


import java.lang.reflect.Modifier;


public abstract class SmaliItem {

    protected final int flags;

    protected SmaliItem(int flags){
        this.flags = flags;
    }

    public int getModifiers(){
        return flags;
    }

    public boolean isStatic() {
        return Modifier.isStatic(flags);
    }

    public boolean isFinal(){
        return Modifier.isFinal(flags);
    }

    public boolean isSynthetic(){
        int isSynthetic = (flags & 0x00001000) / 0x00001000;
        return isSynthetic == 1;
    }

    public boolean isPublic(){
        return Modifier.isPublic(flags);
    }

    public boolean isProtected(){
        return Modifier.isProtected(flags);
    }

    public boolean isPrivate(){
        return Modifier.isPrivate(flags);
    }

    public boolean isPackagePrivate(){
        return !(Modifier.isPublic(flags)
                || Modifier.isPrivate(flags)
                || Modifier.isProtected(flags));
    }

    public int getAccessModifier(){
        if(Modifier.isPrivate(flags)) return Modifier.PRIVATE;
        if(Modifier.isPublic(flags)) return Modifier.PUBLIC;
        if(Modifier.isProtected(flags)) return Modifier.PROTECTED;
        return 0;
    }

    public String getAccessModifierString(){
        if(Modifier.isPrivate(flags)) return "private";
        if(Modifier.isPublic(flags)) return "public";
        if(Modifier.isProtected(flags)) return "protected";
        return "";
    }
}
