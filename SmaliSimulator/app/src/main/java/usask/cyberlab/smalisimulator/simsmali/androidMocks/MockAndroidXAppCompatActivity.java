//package usask.cyberlab.smalisimulator.simsmali.androidMocks;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.res.Resources;
//import android.os.Bundle;
//
//import androidx.annotation.LayoutRes;
//import androidx.appcompat.app.AppCompatActivity;
//import java.io.File;
//import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
//import usask.cyberlab.smalisimulator.simsmali.exceptions.AmbiguousValueReturnException;
//
//
//@SuppressLint("MissingSuperCall")
//public class MockAndroidXAppCompatActivity extends AppCompatActivity {
//    private int contentViewResId = -1;
//    private MockResources mockResources;
//
//    private int cacheDirId = -1;
//    private int externalCacheDirId = -1;
//    private int filesDirId = -1;
//    private int externalFiledDirId = -1;
//    private int resourcesId = -1;
//
//    public MockAndroidXAppCompatActivity() {
//    }
//
//    public MockAndroidXAppCompatActivity(int contentLayoutId) {
//    }
//
//    @Override
//    public String toString() {
//        return "MockAndroidXAppCompatActivity";
//    }
//
//    @Override
//    public void setContentView(int layoutResID) {
//        contentViewResId = layoutResID;
//        System.out.println("!!!!! setContentView(I) mock called in MockActivity.");
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        System.out.println("!!!!! onCreate mock called in MockActivity.");
//    }
//
//    @Override
//    public void onDestroy() {
//        System.out.println("!!!!! onDestroy mock called in MockActivity.");
//    }
//
//    @Override
//    public void onPause() {
//        System.out.println("!!!!! onPause mock called in MockActivity.");
//    }
//
//    @Override
//    public void onPostCreate(Bundle savedInstanceState) {
//        System.out.println("!!!!! onPostCreate mock called in MockActivity.");
//    }
//
//    @Override
//    public void onPostResume() {
//        System.out.println("!!!!! onPostResume mock called in MockActivity.");
//    }
//    @Override
//    public void onRestart() {
//        System.out.println("!!!!! onRestart mock called in MockActivity.");
//    }
//
//    @Override
//    public void onResume() {
//        System.out.println("!!!!! onResume mock called in MockActivity.");
//    }
//
//
//    @Override
//    public void onStart() {
//        System.out.println("!!!!! onStart mock called in MockActivity.");
//    }
//
//    @Override
//    public void onStop() {
//        System.out.println("!!!!! onStop mock called in MockActivity.`");
//    }
//
//    @Override
//    public void onTitleChanged(CharSequence title, int color) {
//        System.out.println("!!!!! onTitleChanged mock called in MockActivity.");
//    }
//
//    @Override
//    public File getCacheDir() {
//        String appName = MockingMetaInfo.appName;
//        String appVersion = MockingMetaInfo.appVersion;
//        Context simulatorContext = MockingMetaInfo.simulatorContext;
//        if(simulatorContext==null || appName == null || appVersion == null){
//            if(this.cacheDirId == -1){
//                this.cacheDirId = System.identityHashCode(new Object());
//            }
//            throw new AmbiguousValueReturnException(new AmbiguousValue("Ljava/io/File;", this.cacheDirId));
//        }
//        else {
//            File f = new File(simulatorContext.getFilesDir(), "simulatedAppsInternalFiles");
//            File f2 = new File(f, appName);
//            File f3 = new File(f2, appVersion);
//            File f4 = new File(f3, "cache");
//            f4.mkdirs();
//            return f4;
//        }
//    }
//
//    @Override
//    public File getExternalCacheDir() {
//        String appName = MockingMetaInfo.appName;
//        String appVersion = MockingMetaInfo.appVersion;
//        Context simulatorContext = MockingMetaInfo.simulatorContext;
//        if(simulatorContext==null || appName == null || appVersion == null){
//            if(this.externalCacheDirId == -1){
//                this.externalCacheDirId = System.identityHashCode(new Object());
//            }
//            throw new AmbiguousValueReturnException(new AmbiguousValue("Ljava/io/File;", this.externalCacheDirId));
//        }
//        File f = new File(simulatorContext.getFilesDir(), "simulatedExternalDir");
//        File f2 = new File(f, appName);
//        File f3 = new File(f2, appVersion);
//        File f4 = new File(f3,"cache");
//        f4.mkdirs();
//        return f4;
//    }
//
//
//    @Override
//    public File getExternalFilesDir(String type) {
//        String appName = MockingMetaInfo.appName;
//        String appVersion = MockingMetaInfo.appVersion;
//        Context simulatorContext = MockingMetaInfo.simulatorContext;
//        if(simulatorContext==null || appName == null || appVersion == null){
//            if(externalFiledDirId == -1){
//                externalFiledDirId = System.identityHashCode(new Object());
//            }
//            throw new AmbiguousValueReturnException(new AmbiguousValue("Ljava/io/File;", this.externalFiledDirId));
//        }
//        File f = new File(simulatorContext.getFilesDir(), "simulatedExternalDir");
//        File f2 = new File(f, appName);
//        File f3 = new File(f2, appVersion);
//        File f4 = new File(f3,"files");
//        f4.mkdirs();
//        if(type == null){
//            return f4;
//        }
//        else {
//            File f5 = new File(f4, type);
//            f5.mkdirs();
//            return f5;
//        }
//    }
//
//    @Override
//    public File[] getExternalFilesDirs(String type) {
//        File f = getExternalFilesDir(type);
//        File[] res = {f};
//        return res;
//    }
//
//    @Override
//    public File[] getExternalMediaDirs() {
//        File f = getExternalFilesDir(null);
//        File f2 = new File(f.getParentFile(), "media");
//        File[] res = {f2};
//        return res;
//    }
//
//    @Override
//    public File getFilesDir() {
//        String appName = MockingMetaInfo.appName;
//        String appVersion = MockingMetaInfo.appVersion;
//        Context simulatorContext = MockingMetaInfo.simulatorContext;
//        if(simulatorContext==null || appName == null || appVersion == null){
//            if(this.filesDirId == -1){
//                this.filesDirId = System.identityHashCode(new Object());
//            }
//            throw new AmbiguousValueReturnException(new AmbiguousValue("Ljava/io/File;", this.filesDirId));
//        }
//        else {
//            File f = new File(simulatorContext.getFilesDir(), "simulatedAppsInternalFiles");
//            File f2 = new File(f, appName);
//            File f3 = new File(f2, appVersion);
//            File f4 = new File(f3, "files");
//            f4.mkdirs();
//            return f4;
//        }
//    }
//
//    @Override
//    public Resources getResources() {
//        if(MockingMetaInfo.simulatorContext == null){
//            if(this.resourcesId == -1){
//                this.resourcesId = System.identityHashCode(new Object());
//            }
//            throw new AmbiguousValueReturnException(new AmbiguousValue("Landroid/content/res/Resources;", this.resourcesId));
//        }
//        if(mockResources == null){
//            Context c = MockingMetaInfo.simulatorContext;
//            mockResources = new MockResources(c.getAssets(),null,null);
//            return mockResources;
//        }
//        else {
//            return mockResources;
//        }
//    }
//
//}
