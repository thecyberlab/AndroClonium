//package usask.cyberlab.smalisimulator.simsmali.androidMocks;
//
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.content.res.Resources;
//import android.os.IBinder;
//
//import java.io.File;
//
//import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
//import usask.cyberlab.smalisimulator.simsmali.exceptions.AmbiguousValueReturnException;
//
//@SuppressWarnings("all")
//public abstract class MockService extends Service {
//
//    private MockResources mockResources;
//
//    private int cacheDirId = -1;
//    private int externalCacheDirId = -1;
//    private int filesDirId = -1;
//    private int externalFiledDirId = -1;
//    private int resourcesId = -1;
//
//
//    public MockService() {
//
//    }
//
//    @Override
//    public abstract IBinder onBind(Intent intent);
//
////    @Override
////    public void onConfigurationChanged(Configuration newConfig) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void onCreate() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void onDestroy() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void onLowMemory() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void onRebind(Intent intent) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void onStart(Intent intent, int startId) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public int onStartCommand(Intent intent, int flags, int startId) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void onTaskRemoved(Intent rootIntent) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void onTrimMemory(int level) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean onUnbind(Intent intent) {
////        throw new UnsupportedOperationException();
////    }
////
////
////    @Override
////    public void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void attachBaseContext(Context base) {
////        throw new UnsupportedOperationException();
////    }
////
////
////    @Override
////    public Context getBaseContext() {
////        throw new UnsupportedOperationException();
////    }
//
//
////    @Override
////    public boolean bindIsolatedService(Intent service, int flags, String instanceName, Executor executor, ServiceConnection conn) {
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public boolean bindService(Intent service, int flags, Executor executor, ServiceConnection conn) {
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public boolean bindService(Intent service, ServiceConnection conn, int flags){
////        throw new UnsupportedOperationException();
////    }
////
////
////    @Override
////    public int checkCallingOrSelfPermission(String permission){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public int checkCallingOrSelfUriPermission(Uri uri, int modeFlags){
////        throw new UnsupportedOperationException();
////    }
////
////
////    @Override
////    public int checkCallingPermission(String permission){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public int checkCallingUriPermission(Uri uri, int modeFlags){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public int checkPermission(String permission, int pid, int uid){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public int checkSelfPermission(String permission){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public int checkUriPermission(Uri uri, String readPermission, String writePermission, int pid, int uid, int modeFlags){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public int checkUriPermission(Uri uri, int pid, int uid, int modeFlags){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void clearWallpaper(){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public Context createContextForSplit(String splitName){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public Context createDeviceProtectedStorageContext(){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public Context createDisplayContext(Display display){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public Context createPackageContext(String packageName, int flags){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean deleteDatabase(String name){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean deleteFile(String name){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean deleteSharedPreferences(String name){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void enforceCallingOrSelfPermission(String permission, String message){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void enforceCallingOrSelfUriPermission(Uri uri, int modeFlags, String message){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void enforceCallingPermission(String permission, String message){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void enforceCallingUriPermission(Uri uri, int modeFlags, String message){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void enforcePermission(String permission, int pid, int uid, String message){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void enforceUriPermission(Uri uri, String readPermission, String writePermission, int pid, int uid, int modeFlags, String message){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void enforceUriPermission(Uri uri, int pid, int uid, int modeFlags, String message){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public String[] fileList(){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public Context getApplicationContext(){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public ApplicationInfo getApplicationInfo(){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public AssetManager getAssets(){
////        throw new UnsupportedOperationException();
////    }
//
//    @Override
//    public File getCacheDir(){
//        String appName = MockingMetaInfo.appName;
//        String appVersion = MockingMetaInfo.appVersion;
//        Context simulatorContext = MockingMetaInfo.simulatorContext;
//        if(simulatorContext==null || appName == null || appVersion == null){
//            if(cacheDirId == -1){
//                cacheDirId = System.identityHashCode(new Object());
//            }
//            throw new AmbiguousValueReturnException(new AmbiguousValue("Ljava/io/File;", cacheDirId));
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
////    @Override
////    public File getCodeCacheDir(){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public ContentResolver getContentResolver(){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public File getDataDir(){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public File getDatabasePath(String name){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public File getDir(String name, int mode){
////        throw new UnsupportedOperationException();
////    }
//
//    @Override
//    public File getExternalCacheDir(){
//        String appName = MockingMetaInfo.appName;
//        String appVersion = MockingMetaInfo.appVersion;
//        Context simulatorContext = MockingMetaInfo.simulatorContext;
//        if(simulatorContext==null || appName == null || appVersion == null){
//            if(externalCacheDirId == -1){
//                externalCacheDirId = System.identityHashCode(new Object());
//            }
//            throw new AmbiguousValueReturnException(new AmbiguousValue("Ljava/io/File;", externalCacheDirId));
//        }
//        File f = new File(simulatorContext.getFilesDir(), "simulatedExternalDir");
//        File f2 = new File(f, appName);
//        File f3 = new File(f2, appVersion);
//        File f4 = new File(f3,"cache");
//        f4.mkdirs();
//        return f4;
//    }
//
////    @Override
////    public File[] getExternalCacheDirs(){
////        throw new UnsupportedOperationException();
////    }
//
//    @Override
//    public File getExternalFilesDir(String type){
//        String appName = MockingMetaInfo.appName;
//        String appVersion = MockingMetaInfo.appVersion;
//        Context simulatorContext = MockingMetaInfo.simulatorContext;
//        if(simulatorContext==null || appName == null || appVersion == null){
//            if(externalFiledDirId == -1){
//                externalFiledDirId = System.identityHashCode(new Object());
//            }
//            throw new AmbiguousValueReturnException(new AmbiguousValue("Ljava/io/File;", externalFiledDirId));
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
////    @Override
////    public File getFileStreamPath(String name){
////        throw new UnsupportedOperationException();
////    }
//
//    @Override
//    public File getFilesDir() {
//        String appName = MockingMetaInfo.appName;
//        String appVersion = MockingMetaInfo.appVersion;
//        Context simulatorContext = MockingMetaInfo.simulatorContext;
//        if(simulatorContext==null || appName == null || appVersion == null){
//            if(filesDirId == -1){
//                filesDirId = System.identityHashCode(new Object());
//            }
//            throw new AmbiguousValueReturnException(new AmbiguousValue("Ljava/io/File;", filesDirId));
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
////    @Override
////    public Executor getMainExecutor(){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public Looper getMainLooper(){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public File getNoBackupFilesDir(){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public File getObbDir(){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public File[] getObbDirs(){
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public String getOpPackageName(){
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public String getPackageCodePath(){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public PackageManager getPackageManager(){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public String getPackageName(){
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public String getPackageResourcePath(){
////        throw new UnsupportedOperationException();
////    }
//
//    @Override
//    public Resources getResources() {
//        if(MockingMetaInfo.simulatorContext == null){
//            if(resourcesId == -1){
//                resourcesId = System.identityHashCode(new Object());
//            }
//            throw new AmbiguousValueReturnException(new AmbiguousValue("Ljava/io/File;", resourcesId));
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
////    @Override
////    public void updateServiceGroup(ServiceConnection conn, int group, int importance) {
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public SharedPreferences getSharedPreferences(String name, int mode){
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public String getString(int resId, Object... formatArgs) {
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public String getString(int resId) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public <T> T getSystemService(Class<T> serviceClass) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract Object getSystemService(String name) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract String getSystemServiceName(Class<?> serviceClass) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public CharSequence getText(int resId) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract Resources.Theme getTheme() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract Drawable getWallpaper() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract int getWallpaperDesiredMinimumHeight() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract int getWallpaperDesiredMinimumWidth() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract void grantUriPermission(String toPackage, Uri uri, int modeFlags) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract boolean isDeviceProtectedStorage() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean isRestricted() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean isUiContext() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract boolean moveDatabaseFrom(Context sourceContext, String name) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract boolean moveSharedPreferencesFrom(Context sourceContext, String name) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public TypedArray obtainStyledAttributes(AttributeSet set, int[] attrs) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public TypedArray obtainStyledAttributes(AttributeSet set, int[] attrs, int defStyleAttr, int defStyleRes) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public TypedArray obtainStyledAttributes(int resid, int[] attrs) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public TypedArray obtainStyledAttributes(int[] attrs) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract FileInputStream openFileInput(String name) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract FileOutputStream openFileOutput(String name, int mode) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract Drawable peekWallpaper() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void registerComponentCallbacks(ComponentCallbacks callback) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, int flags) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, String broadcastPermission, Handler scheduler, int flags) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, String broadcastPermission, Handler scheduler) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract void removeStickyBroadcast(Intent intent) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract void removeStickyBroadcastAsUser(Intent intent, UserHandle user) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract void revokeUriPermission(Uri uri, int modeFlags) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract void revokeUriPermission(String toPackage, Uri uri, int modeFlags) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract void sendBroadcast(Intent intent, String receiverPermission) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract void sendBroadcast(Intent intent) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract void sendBroadcastAsUser(Intent intent, UserHandle user) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract void sendBroadcastAsUser(Intent intent, UserHandle user, String receiverPermission) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void sendBroadcastWithMultiplePermissions(Intent intent, String[] receiverPermissions) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void sendOrderedBroadcast(Intent intent, String receiverPermission, String receiverAppOp, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract void sendOrderedBroadcast(Intent intent, String receiverPermission, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract void sendOrderedBroadcast(Intent intent, String receiverPermission) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract void sendOrderedBroadcastAsUser(Intent intent, UserHandle user, String receiverPermission, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract void sendStickyBroadcast(Intent intent) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void sendStickyBroadcast(Intent intent, Bundle options) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract void sendStickyBroadcastAsUser(Intent intent, UserHandle user) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract void sendStickyOrderedBroadcast(Intent intent, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract void sendStickyOrderedBroadcastAsUser(Intent intent, UserHandle user, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract void setTheme(int resid) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract void setWallpaper(Bitmap bitmap) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract void setWallpaper(InputStream data) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract void startActivities(Intent[] intents, Bundle options) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract void startActivities(Intent[] intents) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract void startActivity(Intent intent) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract void startActivity(Intent intent, Bundle options) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract ComponentName startForegroundService(Intent service) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract boolean startInstrumentation(ComponentName className, String profileFile, Bundle arguments) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract void startIntentSender(IntentSender intent, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract void startIntentSender(IntentSender intent, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, Bundle options) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract ComponentName startService(Intent service) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract boolean stopService(Intent service) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract void unbindService(ServiceConnection conn) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void unregisterComponentCallbacks(ComponentCallbacks callback) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public abstract void unregisterReceiver(BroadcastReceiver receiver) {
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public ContextParams getParams();
////
//
//    //    @Override
////    public Application getApplication() {
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public int getForegroundServiceType() {
////        throw new UnsupportedOperationException();
////    }
//
//    //    @Override
////    public void startForeground(int id, Notification notification) {
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public void startForeground(int id, Notification notification, int foregroundServiceType) {
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public void stopForeground(int flags) {
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public void stopForeground(boolean removeNotification) {
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public void stopSelf() {
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public void stopSelf(int startId) {
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public boolean stopSelfResult(int startId) {
////        throw new UnsupportedOperationException();
////    }
//
//    //    @Override
////    public int[] checkCallingOrSelfUriPermissions(List<Uri> uris, int modeFlags) {
////        throw new UnsupportedOperationException();
////    }
//
//
////    @Override
////    public int[] checkCallingUriPermissions(List<Uri> uris, int modeFlags) {
////        throw new UnsupportedOperationException();
////    }
//
//
////    @Override
////    public int[] checkUriPermissions(List<Uri> uris, int pid, int uid, int modeFlags) {
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public Context createAttributionContext(String attributionTag) {
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public Context createContext(ContextParams contextParams) {
////        throw new UnsupportedOperationException();
////    }
//
//
//
////    @Override
////    public Context createWindowContext(int type, Bundle options) {
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public Context createWindowContext(Display display, int type, Bundle options) {
////        throw new UnsupportedOperationException();
////    }
//
//    //    @Override
////    public Display getDisplay() {
////        throw new UnsupportedOperationException();
////    }
//
//
////    @Override
////    public ContextParams getParams() {
////        throw new UnsupportedOperationException();
////    }
//
//
////    @Override
////    public void sendOrderedBroadcast(Intent intent, String receiverPermission, String receiverAppOp, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public void sendOrderedBroadcast(Intent intent, int initialCode, String receiverPermission, String receiverAppOp, BroadcastReceiver resultReceiver, Handler scheduler, String initialData, Bundle initialExtras, Bundle options) {
////        throw new UnsupportedOperationException();
////    }
//
//
////    @Override
////    public void sendStickyBroadcast(Intent intent, Bundle options) {
////        throw new UnsupportedOperationException();
////    }
//
//    //    @Override
////    public boolean bindServiceAsUser(Intent service, ServiceConnection conn, int flags, UserHandle user) {
////        throw new UnsupportedOperationException();
////    }
//
//    //    @Override
////    public int[] checkCallingOrSelfUriPermissions(List<Uri> uris, int modeFlags) {
////        throw new UnsupportedOperationException();
////    }
//
//    //    @Override
////    public int[] checkCallingUriPermissions(List<Uri> uris, int modeFlags) {
////        throw new UnsupportedOperationException();
////    }
//
//    //    @Override
////    public int[] checkUriPermissions(List<Uri> uris, int pid, int uid, int modeFlags) {
////        throw new UnsupportedOperationException();
////    }
//
//    //    @Override
////    public Context createAttributionContext(String attributionTag) {
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public abstract Context createConfigurationContext(Configuration overrideConfiguration) {
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public Context createContext(ContextParams contextParams) {
////        throw new UnsupportedOperationException();
////    }
////
//
//    //    @Override
////    public Context createWindowContext(int type, Bundle options) {
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public Context createWindowContext(Display display, int type, Bundle options) {
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public abstract String[] databaseList() {
////        throw new UnsupportedOperationException();
////    }
//
//    //    @Override
////    public String getAttributionTag();
//
//    //    @Override
////    public int getColor(int id) {
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public ColorStateList getColorStateList(int id) {
////        throw new UnsupportedOperationException();
////    }
//
//    //    @Override
////    public Display getDisplay() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public Drawable getDrawable(int id) {
////        throw new UnsupportedOperationException();
////    }
//
//
//}
