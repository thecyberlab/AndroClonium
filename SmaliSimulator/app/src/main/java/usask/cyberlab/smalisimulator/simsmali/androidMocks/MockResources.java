//package usask.cyberlab.smalisimulator.simsmali.androidMocks;
//
//import android.content.res.AssetFileDescriptor;
//import android.content.res.AssetManager;
//import android.content.res.ColorStateList;
//import android.content.res.Configuration;
//import android.content.res.Resources;
//import android.content.res.TypedArray;
//import android.content.res.XmlResourceParser;
//import android.graphics.Movie;
//import android.graphics.Typeface;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.util.AttributeSet;
//import android.util.DisplayMetrics;
//import android.util.TypedValue;
//
//import org.w3c.dom.Document;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
//import java.io.File;
//import java.io.FileFilter;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//
//import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
//import usask.cyberlab.smalisimulator.simsmali.exceptions.AmbiguousValueReturnException;
//import usask.cyberlab.smalisimulator.simsmali.representations.SmaliClass;
//import usask.cyberlab.smalisimulator.simsmali.representations.SmaliField;
//import usask.cyberlab.smalisimulator.simsmali.types.classes.ClazzLoader;
//
//@SuppressWarnings("all")
//public class MockResources extends Resources {
//
//    private HashSet<SmaliClass> R_string_files = null;
//    private HashMap<Integer, Integer> stringResObjIdMap = new HashMap();
//
//    public MockResources(AssetManager assets, DisplayMetrics metrics, Configuration config) {
//        super(assets, metrics, config);
//    }
//
//
//    private List<File> listFilesRecursive(File directory, FileFilter fileFilter) {
//        List<File> files = new ArrayList<>();
//        File[] fList = directory.listFiles(fileFilter);
//        if(fList != null){
//            for (File file : fList) {
//                if (file.isFile()) {
//                    files.add(file);
//                } else if (file.isDirectory()) {
//                    files.addAll(listFilesRecursive(file,fileFilter));
//                }
//            }
//        }
//        return files;
//    }
//
//
//    private void readAllR_StringFiles(File fromDir){
//        if(R_string_files == null){
//            List<File> files = listFilesRecursive(fromDir, new FileFilter() {
//                @Override
//                public boolean accept(File f) {
////                    return true;
//                    return f.getName().equals("R$string.smali");
//                }
//            });
//            R_string_files = new HashSet<>();
//            ClazzLoader loader = MockingMetaInfo.clazzLoader;
//            for(File f: files){
//                String classPath = f.getAbsolutePath().replace(loader.getBasePath(), "");
//                if(classPath.startsWith("/")) classPath = classPath.substring(1);
//                classPath = "L" + classPath.substring(0, classPath.length() - 6) + ";";
//                SmaliClass sc = loader.getSmaliClass(classPath);
//                R_string_files.add(sc);
//            }
//        }
//    }
//
//
////    public addLoaders(ResourcesLoader... loaders){}
//
////    public final void 	finishPreloading(){}
//
////    public final void 	flushLayoutCache(){}
//
//    public XmlResourceParser getAnimation(int id) {
//        throw new UnsupportedOperationException();
//    }
//
////    public final AssetManager getAssets(){}
//
//    public static int getAttributeSetSourceResId(AttributeSet set) {
//        throw new UnsupportedOperationException();
//    }
//
//    public boolean getBoolean(int id) {
//        throw new UnsupportedOperationException();
//    }
//
//    public int getColor(int id) {
//        throw new UnsupportedOperationException();
//    }
//
//    public int getColor(int id, Resources.Theme theme) {
//        throw new UnsupportedOperationException();
//    }
//
//    public ColorStateList getColorStateList(int id, Resources.Theme theme) {
//        throw new UnsupportedOperationException();
//    }
//
//    public ColorStateList getColorStateList(int id) {
//        throw new UnsupportedOperationException();
//    }
//
//    public Configuration getConfiguration() {
//        throw new UnsupportedOperationException();
//    }
//
//    public float getDimension(int id) {
//        throw new UnsupportedOperationException();
//    }
//
//    public int getDimensionPixelOffset(int id) {
//        throw new UnsupportedOperationException();
//    }
//
//    public int getDimensionPixelSize(int id) {
//        throw new UnsupportedOperationException();
//    }
//
//    public DisplayMetrics getDisplayMetrics() {
//        throw new UnsupportedOperationException();
//    }
//
//    public Drawable getDrawable(int id, Resources.Theme theme) {
//        throw new UnsupportedOperationException();
//    }
//
//    public Drawable getDrawable(int id) {
//        throw new UnsupportedOperationException();
//    }
//
//    public Drawable getDrawableForDensity(int id, int density) {
//        throw new UnsupportedOperationException();
//    }
//
//    public Drawable getDrawableForDensity(int id, int density, Resources.Theme theme) {
//        throw new UnsupportedOperationException();
//    }
//
//    public float getFloat(int id) {
//        throw new UnsupportedOperationException();
//    }
//
//    public Typeface getFont(int id) {
//        throw new UnsupportedOperationException();
//    }
//
//    public float getFraction(int id, int base, int pbase) {
//        throw new UnsupportedOperationException();
//    }
//
//    public int getIdentifier(String name, String defType, String defPackage) {
//        throw new UnsupportedOperationException();
//    }
//
//    public int[] getIntArray(int id) {
//        throw new UnsupportedOperationException();
//    }
//
//    public int getInteger(int id) {
//        throw new UnsupportedOperationException();
//    }
//
//    public XmlResourceParser getLayout(int id) {
//        throw new UnsupportedOperationException();
//    }
//
//    public Movie getMovie(int id) {
//        throw new UnsupportedOperationException();
//    }
//
//    public String getQuantityString(int id, int quantity) {
//        throw new UnsupportedOperationException();
//    }
//
//    public String getQuantityString(int id, int quantity, Object... formatArgs) {
//        throw new UnsupportedOperationException();
//    }
//
//    public CharSequence getQuantityText(int id, int quantity) {
//        throw new UnsupportedOperationException();
//    }
//
//    public String getResourceEntryName(int resid) {
//        throw new UnsupportedOperationException();
//    }
//
//    public String getResourceName(int resid) {
//        throw new UnsupportedOperationException();
//    }
//
//    public String getResourcePackageName(int resid) {
//        throw new UnsupportedOperationException();
//    }
//
//    public String getResourceTypeName(int resid) {
//        throw new UnsupportedOperationException();
//    }
//
//    public String getString(int id, Object... formatArgs) {
//        throw new UnsupportedOperationException();
//    }
//
//    public String getString(int id) {
//        int resObjId;
//        if(stringResObjIdMap.containsKey(id)){
//            resObjId = stringResObjIdMap.get(id);
//        }
//        else {
//            resObjId = System.identityHashCode(new Object());
//            stringResObjIdMap.put(id, resObjId);
//        }
//
//        if(MockingMetaInfo.clazzLoader == null ||
//                MockingMetaInfo.uploadedResBasePath == null ||
//                MockingMetaInfo.appName == null ||
//                MockingMetaInfo.appVersion == null ){
//           throw new AmbiguousValueReturnException(new AmbiguousValue("Ljava/lang/String;", resObjId));
//        }
//        else {
//            //read the values/public.xml file
//            File ff0 = new File(MockingMetaInfo.uploadedResBasePath, MockingMetaInfo.appName);
//            File ff1 = new File(ff0, MockingMetaInfo.appVersion);
//            File ff2 = new File(ff1, "values");
//            File ff3 = new File(ff2, "public.xml");
//            if(!ff3.exists()){
//                throw new AmbiguousValueReturnException(new AmbiguousValue("Ljava/lang/String;", resObjId));
//            }
//            String stringName = null;
//            try {
//                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//                DocumentBuilder db = dbf.newDocumentBuilder();
//                Document document = db.parse(ff3);
//
//                NodeList nl = document.getElementsByTagName("public");
//                for(int i=0; i < nl.getLength();i++){
//                    Node n = nl.item(i);
//                    Node nameAttr = n.getAttributes().getNamedItem("name");
//                    Node idAttr = n.getAttributes().getNamedItem("id");
//                    String idAttrValueStr = idAttr.getNodeValue().substring(2);
//                    int idAttrValue = Integer.parseInt(idAttrValueStr,16);
//                    if(idAttrValue == id){
//                        stringName = nameAttr.getNodeValue();
//                        break;
//                    }
//                }
//            }
//            catch (Exception e){
//                throw new AmbiguousValueReturnException(new AmbiguousValue("Ljava/lang/String;", resObjId));
//            }
//
//            if(stringName == null){
//                throw new AmbiguousValueReturnException(new AmbiguousValue("Ljava/lang/String;", resObjId));
//            }
//
//            // we read the values/strings.xml and look for the corresponding value;
//            File f0 = new File(MockingMetaInfo.uploadedResBasePath, MockingMetaInfo.appName);
//            File f1 = new File(f0, MockingMetaInfo.appVersion);
//            File f2 = new File(f1, "values");
//            File f3 = new File(f2, "strings.xml");
//            if(!f3.exists()){
//                throw new AmbiguousValueReturnException(new AmbiguousValue("Ljava/lang/String;", resObjId));
//            }
//            try {
//                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//                DocumentBuilder db = dbf.newDocumentBuilder();
//                Document document = db.parse(f3);
//                NodeList nl = document.getElementsByTagName("string");
//                for(int i=0; i < nl.getLength();i++){
//                    Node n = nl.item(i);
//                    Node nameAttr = n.getAttributes().getNamedItem("name");
//                    if(stringName.equals(nameAttr.getNodeValue())){
//                        String s = n.getTextContent();
//                        return s;
//                    }
//                }
//                throw new AmbiguousValueReturnException(new AmbiguousValue("Ljava/lang/String;", resObjId));
//            }
//            catch (Exception e){
//                throw new AmbiguousValueReturnException(new AmbiguousValue("Ljava/lang/String;", resObjId));
//            }
//        }
//
//
//
//    }
//
//    public String[] getStringArray(int id) {
//        throw new UnsupportedOperationException();
//    }
//
//    public static Resources getSystem() {
//        throw new UnsupportedOperationException();
//    }
//
//    public CharSequence getText(int id, CharSequence def) {
//        throw new UnsupportedOperationException();
//    }
//
//    public CharSequence getText(int id) {
//        throw new UnsupportedOperationException();
//    }
//
//    public CharSequence[] getTextArray(int id) {
//        throw new UnsupportedOperationException();
//    }
//
//    public void getValue(String name, TypedValue outValue, boolean resolveRefs) {
//        throw new UnsupportedOperationException();
//    }
//
//    public void getValue(int id, TypedValue outValue, boolean resolveRefs) {
//        throw new UnsupportedOperationException();
//    }
//
//    public void getValueForDensity(int id, int density, TypedValue outValue, boolean resolveRefs) {
//        throw new UnsupportedOperationException();
//    }
//
//    public XmlResourceParser getXml(int id) {
//        throw new UnsupportedOperationException();
//    }
//
////    public final Resources.Theme 	newTheme(){}
//
//    public TypedArray obtainAttributes(AttributeSet set, int[] attrs) {
//        throw new UnsupportedOperationException();
//    }
//
//    public TypedArray obtainTypedArray(int id) {
//        throw new UnsupportedOperationException();
//    }
//
//    public InputStream openRawResource(int id, TypedValue value) {
//        throw new UnsupportedOperationException();
//    }
//
//    public InputStream openRawResource(int id) {
//        throw new UnsupportedOperationException();
//    }
//
//    public AssetFileDescriptor openRawResourceFd(int id) {
//        throw new UnsupportedOperationException();
//    }
//
//    public void parseBundleExtra(String tagName, AttributeSet attrs, Bundle outBundle) {
//        throw new UnsupportedOperationException();
//    }
//
//    public void parseBundleExtras(XmlResourceParser parser, Bundle outBundle) {
//        throw new UnsupportedOperationException();
//    }
//
////    public void 	removeLoaders(ResourcesLoader... loaders){}
//
//    public void updateConfiguration(Configuration config, DisplayMetrics metrics) {
//        throw new UnsupportedOperationException();
//    }
//
//}
