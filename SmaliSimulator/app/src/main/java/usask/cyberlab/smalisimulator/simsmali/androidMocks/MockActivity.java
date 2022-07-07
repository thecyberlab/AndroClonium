//package usask.cyberlab.smalisimulator.simsmali.androidMocks;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.ActivityManager;
//import android.app.Application;
//import android.app.Dialog;
//import android.app.Fragment;
//import android.app.PictureInPictureParams;
//import android.app.SharedElementCallback;
//import android.app.TaskStackBuilder;
//import android.app.assist.AssistContent;
//import android.content.BroadcastReceiver;
//import android.content.ComponentName;
//import android.content.ContentResolver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.IntentSender;
//import android.content.ServiceConnection;
//import android.content.SharedPreferences;
//import android.content.pm.ApplicationInfo;
//import android.content.pm.PackageManager;
//import android.content.res.AssetManager;
//import android.content.res.Configuration;
//import android.content.res.Resources;
//import android.database.Cursor;
//import android.database.DatabaseErrorHandler;
//import android.database.sqlite.SQLiteDatabase;
//import android.graphics.Bitmap;
//import android.graphics.drawable.Drawable;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.CancellationSignal;
//import android.os.Handler;
//import android.os.Looper;
//import android.os.PersistableBundle;
//import android.os.UserHandle;
//import android.transition.TransitionManager;
//import android.view.ActionMode;
//import android.view.Display;
//import android.view.DragAndDropPermissions;
//import android.view.DragEvent;
//import android.view.KeyboardShortcutGroup;
//import android.view.Menu;
//import android.view.MotionEvent;
//import android.view.SearchEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.Toolbar;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.io.File;
//import java.io.FileDescriptor;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.io.PrintWriter;
//import java.util.List;
//import java.util.concurrent.Executor;
//import java.util.function.Consumer;
//
//import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
//import usask.cyberlab.smalisimulator.simsmali.exceptions.AmbiguousValueReturnException;
//
//@SuppressWarnings("all")
//@SuppressLint("MissingSuperCall")
//public class MockActivity extends Activity {
//
//    private int contentViewResId = -1;
//    private MockResources mockResources;
//
//    private int cacheDirId = -1;
//    private int externalCacheDirId = -1;
//    private int filesDirId = -1;
//    private int externalFiledDirId = -1;
//    private int resourcesId = -1;
//
//    public MockActivity() {
//
//    }
//    @Override
//    public String toString() {
//        return "MockActivity";
//    }
//
//    /// public methods in Activity class
//
//    @Override
//    public void addContentView(View view, ViewGroup.LayoutParams params) {
//    }
//
//    @Override
//    public void closeContextMenu() {
//    }
//
//    @Override
//    public void closeOptionsMenu() {
//    }
//
////    @Override
////    public PendingIntent createPendingResult(int requestCode, Intent data, int flags) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean dispatchGenericMotionEvent(MotionEvent ev) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean dispatchKeyEvent(KeyEvent event) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean dispatchTouchEvent(MotionEvent ev) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean dispatchTrackballEvent(MotionEvent ev) {
////        throw new UnsupportedOperationException();
////    }
//
//    @Override
//    public void dump(@NotNull String prefix, FileDescriptor fd, @NotNull PrintWriter writer, String[] args) {
//    }
//
////    @Override
////    public boolean enterPictureInPictureMode(PictureInPictureParams params) {
////        throw new UnsupportedOperationException();
////    }
//
//    @Override
//    public void enterPictureInPictureMode() {
//    }
//
////    @Override
////    public <T extends View> T findViewById(int id) {
////        throw new UnsupportedOperationException();
////    }
////
//    @Override
//    public void finish() {
//    }
//
//    @Override
//    public void finishActivity(int requestCode) {
//    }
//
//    @Override
//    public void finishActivityFromChild(Activity child, int requestCode) {
//    }
//
//    @Override
//    public void finishAffinity() {
//    }
//
//    @Override
//    public void finishAfterTransition() {
//    }
//
//    @Override
//    public void finishAndRemoveTask() {
//    }
//
//    @Override
//    public void finishFromChild(Activity child) {
//    }
//
////    @Override
////    public ActionBar getActionBar() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public ComponentName getCallingActivity() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public String getCallingPackage() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public int getChangingConfigurations() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public ComponentName getComponentName() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public Scene getContentScene() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public TransitionManager getContentTransitionManager() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public View getCurrentFocus() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public FragmentManager getFragmentManager() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public Intent getIntent() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public Object getLastNonConfigurationInstance() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public LayoutInflater getLayoutInflater() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public LoaderManager getLoaderManager() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public String getLocalClassName() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public int getMaxNumPictureInPictureActions() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public MenuInflater getMenuInflater() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public Intent getParentActivityIntent() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public SharedPreferences getPreferences(int mode) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public Uri getReferrer() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public int getRequestedOrientation() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public int getTaskId() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public VoiceInteractor getVoiceInteractor() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public Window getWindow() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public WindowManager getWindowManager() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean hasWindowFocus() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void invalidateOptionsMenu() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean isActivityTransitionRunning() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean isChangingConfigurations() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean isDestroyed() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean isFinishing() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean isImmersive() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean isInMultiWindowMode() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean isInPictureInPictureMode() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean isLocalVoiceInteractionSupported() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean isTaskRoot() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean isVoiceInteraction() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean isVoiceInteractionRoot() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean moveTaskToBack(boolean nonRoot) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean navigateUpTo(Intent upIntent) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean navigateUpToFromChild(Activity child, Intent upIntent) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void onActionModeFinished(ActionMode mode) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void onActionModeStarted(ActionMode mode) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void onActivityReenter(int resultCode, Intent data) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void onAttachFragment(Fragment fragment) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void onAttachedToWindow() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void onBackPressed() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void onConfigurationChanged(@NotNull Configuration newConfig) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void onContentChanged() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean onContextItemSelected(@NotNull MenuItem item) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void onContextMenuClosed(@NotNull Menu menu) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public CharSequence onCreateDescription() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void onCreateNavigateUpTaskStack(TaskStackBuilder builder) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean onCreateOptionsMenu(Menu menu) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean onCreatePanelMenu(int featureId, Menu menu) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public View onCreatePanelView(int featureId) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean onCreateThumbnail(Bitmap outBitmap, Canvas canvas) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public View onCreateView(String name, Context context, AttributeSet attrs) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void onDetachedFromWindow() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void onEnterAnimationComplete() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean onGenericMotionEvent(MotionEvent event) {
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public void onGetDirectActions(CancellationSignal cancellationSignal, Consumer<List<DirectAction>> callback) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean onKeyDown(int keyCode, KeyEvent event) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean onKeyUp(int keyCode, KeyEvent event) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void onLocalVoiceInteractionStarted() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void onLocalVoiceInteractionStopped() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void onLowMemory() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean onMenuItemSelected(int featureId, MenuItem item) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean onMenuOpened(int featureId, Menu menu) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public void onMultiWindowModeChanged(boolean isInMultiWindowMode, Configuration newConfig) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean onNavigateUp() {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean onNavigateUpFromChild(Activity child) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean onOptionsItemSelected(MenuItem item) {
////        throw new UnsupportedOperationException();
////    }
////
//    @Override
//    public void onOptionsMenuClosed(Menu menu) {
//    }
//
//    @Override
//    public void onPanelClosed(int featureId, Menu menu) {
//    }
//
//    public void onPerformDirectAction(String actionId, Bundle arguments, CancellationSignal cancellationSignal, Consumer<Bundle> resultListener) {
//    }
//
//    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
//    }
//
//    @Override
//    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
//    }
//
//    @Override
//    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
//    }
//
//    @Override
//    public void onPrepareNavigateUpTaskStack(TaskStackBuilder builder) {
//    }
//
////    @Override
////    public boolean onPrepareOptionsMenu(Menu menu) {
////    }
////
////    @Override
////    public boolean onPreparePanel(int featureId, View view, Menu menu) {
////    }
//
//    @Override
//    public void onProvideAssistContent(AssistContent outContent) {
//    }
//
//    @Override
//    public void onProvideAssistData(Bundle data) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, Menu menu, int deviceId) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Uri onProvideReferrer() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Object onRetainNonConfigurationInstance() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean onSearchRequested(SearchEvent searchEvent) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean onSearchRequested() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onStateNotSaved() {
//        throw new UnsupportedOperationException();
//    }
//
////    @Override
//    public void onTopResumedActivityChanged(boolean isTopResumedActivity) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean onTrackballEvent(MotionEvent event) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onTrimMemory(int level) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onUserInteraction() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onVisibleBehindCanceled() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int type) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void openContextMenu(View view) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void openOptionsMenu() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void overridePendingTransition(int enterAnim, int exitAnim) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void postponeEnterTransition() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void recreate() {
//        throw new UnsupportedOperationException();
//    }
//
////    @Override
//    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void registerForContextMenu(View view) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean releaseInstance() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void reportFullyDrawn() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public DragAndDropPermissions requestDragAndDropPermissions(DragEvent event) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean requestVisibleBehind(boolean visible) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void setActionBar(Toolbar toolbar) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void setContentTransitionManager(TransitionManager tm) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void setContentView(View view, ViewGroup.LayoutParams params) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void setContentView(View view) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void setContentView(int layoutResID) {
//        contentViewResId = layoutResID;
//        System.out.println("!!!!! setContentView(I) mock called in MockActivity.");
//    }
//
//    @Override
//    public void setEnterSharedElementCallback(SharedElementCallback callback) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void setExitSharedElementCallback(SharedElementCallback callback) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void setFinishOnTouchOutside(boolean finish) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void setImmersive(boolean i) {
//        throw new UnsupportedOperationException();
//    }
//
////    @Override
////    public void setInheritShowWhenLocked(boolean inheritShowWhenLocked) {
////        throw new UnsupportedOperationException();
////    }
//
//    @Override
//    public void setIntent(Intent newIntent) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void setPictureInPictureParams(PictureInPictureParams params) {
//        throw new UnsupportedOperationException();
//    }
//
//
//    @Override
//    public void setRequestedOrientation(int requestedOrientation) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void setShowWhenLocked(boolean showWhenLocked) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void setTaskDescription(ActivityManager.TaskDescription taskDescription) {
//        throw new UnsupportedOperationException();
//    }
//
//
//    @Override
//    public void setTitle(CharSequence title) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void setTitle(int titleId) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void setTitleColor(int textColor) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void setTurnScreenOn(boolean turnScreenOn) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void setVisible(boolean visible) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void setVrModeEnabled(boolean enabled, ComponentName requestedComponent) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean shouldShowRequestPermissionRationale(String permission) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean shouldUpRecreateTask(Intent targetIntent) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean showAssist(Bundle args) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void showLockTaskEscapeMessage() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public ActionMode startActionMode(ActionMode.Callback callback, int type) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public ActionMode startActionMode(ActionMode.Callback callback) {
//        throw new UnsupportedOperationException();
//    }
//
//
//    @Override
//    public void startActivityForResult(Intent intent, int requestCode) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void startActivityFromChild(Activity child, Intent intent, int requestCode) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void startActivityFromChild(Activity child, Intent intent, int requestCode, Bundle options) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void startActivityFromFragment(Fragment fragment, Intent intent, int requestCode, Bundle options) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void startActivityFromFragment(Fragment fragment, Intent intent, int requestCode) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean startActivityIfNeeded(Intent intent, int requestCode, Bundle options) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean startActivityIfNeeded(Intent intent, int requestCode) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void startIntentSenderForResult(IntentSender intent, int requestCode, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void startIntentSenderForResult(IntentSender intent, int requestCode, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, Bundle options) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void startIntentSenderFromChild(Activity child, IntentSender intent, int requestCode, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, Bundle options) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void startIntentSenderFromChild(Activity child, IntentSender intent, int requestCode, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void startLocalVoiceInteraction(Bundle privateOptions) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void startLockTask() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void startManagingCursor(Cursor c) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean startNextMatchingActivity(Intent intent, Bundle options) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean startNextMatchingActivity(Intent intent) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void startPostponedEnterTransition() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void startSearch(String initialQuery, boolean selectInitialQuery, Bundle appSearchData, boolean globalSearch) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void stopLocalVoiceInteraction() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void stopLockTask() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void stopManagingCursor(Cursor c) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void takeKeyEvents(boolean get) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void triggerSearch(String query, Bundle appSearchData) {
//        throw new UnsupportedOperationException();
//    }
//
////    @Override
////    public void unregisterActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
////        throw new UnsupportedOperationException();
////    }
//
//    @Override
//    public void unregisterForContextMenu(View view) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void attachBaseContext(Context newBase) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onChildTitleChanged(Activity childActivity, CharSequence title) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        System.out.println("!!!!! onCreate mock called in MockActivity.");
//    }
//
//    @Override
//    public Dialog onCreateDialog(int id) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Dialog onCreateDialog(int id, Bundle args) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onDestroy() {
//        System.out.println("!!!!! onDestroy mock called in MockActivity.");
//    }
//
//    @Override
//    public void onNewIntent(Intent intent) {
//        System.out.println("!!!!! onNewIntent mock called in MockActivity.");
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
//
//    @Override
//    public void onPrepareDialog(int id, Dialog dialog, Bundle args) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onPrepareDialog(int id, Dialog dialog) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onRestart() {
//        System.out.println("!!!!! onRestart mock called in MockActivity.");
//    }
//
//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onResume() {
//        System.out.println("!!!!! onResume mock called in MockActivity.");
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        throw new UnsupportedOperationException();
//    }
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
//    public void onUserLeaveHint() {
//        throw new UnsupportedOperationException();
//    }
//
//
//    @Override
//    public void applyOverrideConfiguration(Configuration overrideConfiguration) {
//        throw new UnsupportedOperationException();
//    }
//
//
////    @Override
////    public void setTheme(Resources.Theme theme) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean bindIsolatedService(Intent service, int flags, String instanceName, Executor executor, ServiceConnection conn) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public boolean bindService(Intent service, int flags, Executor executor, ServiceConnection conn) {
////        throw new UnsupportedOperationException();
////    }
//
//    @Override
//    public boolean bindService(Intent service, ServiceConnection conn, int flags) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public int checkCallingOrSelfPermission(String permission) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public int checkCallingOrSelfUriPermission(Uri uri, int modeFlags) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public int checkCallingPermission(String permission) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public int checkCallingUriPermission(Uri uri, int modeFlags) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public int checkPermission(String permission, int pid, int uid) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public int checkSelfPermission(String permission) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public int checkUriPermission(Uri uri, String readPermission, String writePermission, int pid, int uid, int modeFlags) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public int checkUriPermission(Uri uri, int pid, int uid, int modeFlags) {
//        throw new UnsupportedOperationException();
//    }
//
//
//    @Override
//    public void clearWallpaper() {
//        throw new UnsupportedOperationException();
//    }
//
//
//    @Override
//    public Context createConfigurationContext(Configuration overrideConfiguration) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Context createDeviceProtectedStorageContext() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Context createDisplayContext(Display display) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Context createPackageContext(String packageName, int flags) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public String[] databaseList() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean deleteDatabase(String name) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean deleteFile(String name) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean deleteSharedPreferences(String name) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void enforceCallingOrSelfPermission(String permission, String message) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void enforceCallingOrSelfUriPermission(Uri uri, int modeFlags, String message) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void enforceCallingPermission(String permission, String message) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void enforceCallingUriPermission(Uri uri, int modeFlags, String message) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void enforcePermission(String permission, int pid, int uid, String message) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void enforceUriPermission(Uri uri, String readPermission, String writePermission, int pid, int uid, int modeFlags, String message) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void enforceUriPermission(Uri uri, int pid, int uid, int modeFlags, String message) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public String[] fileList() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Context getApplicationContext() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public ApplicationInfo getApplicationInfo() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public AssetManager getAssets() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Context getBaseContext() {
//        throw new UnsupportedOperationException();
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
//    public ClassLoader getClassLoader() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public File getCodeCacheDir() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public ContentResolver getContentResolver() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public File getDataDir() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public File getDatabasePath(String name) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public File getDir(String name, int mode) {
//        throw new UnsupportedOperationException();
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
//    @Override
//    public File[] getExternalCacheDirs() {
//        throw new UnsupportedOperationException();
//    }
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
//    public File getFileStreamPath(String name) {
//        throw new UnsupportedOperationException();
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
//    public Executor getMainExecutor() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Looper getMainLooper() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public File getNoBackupFilesDir() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public File getObbDir() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public File[] getObbDirs() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public String getPackageCodePath() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public PackageManager getPackageManager() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public String getPackageName() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public String getPackageResourcePath() {
//        throw new UnsupportedOperationException();
//    }
//
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
//    @Override
//    public SharedPreferences getSharedPreferences(String name, int mode) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Object getSystemService(String name) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public String getSystemServiceName(Class<?> serviceClass) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Resources.Theme getTheme() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Drawable getWallpaper() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public int getWallpaperDesiredMinimumHeight() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public int getWallpaperDesiredMinimumWidth() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void grantUriPermission(String toPackage, Uri uri, int modeFlags) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean isDeviceProtectedStorage() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean isRestricted() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean moveDatabaseFrom(Context sourceContext, String name) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean moveSharedPreferencesFrom(Context sourceContext, String name) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public FileInputStream openFileInput(String name) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public FileOutputStream openFileOutput(String name, int mode) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Drawable peekWallpaper() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, int flags) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, String broadcastPermission, Handler scheduler, int flags) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, String broadcastPermission, Handler scheduler) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void removeStickyBroadcast(Intent intent) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void removeStickyBroadcastAsUser(Intent intent, UserHandle user) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void revokeUriPermission(Uri uri, int modeFlags) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void revokeUriPermission(String targetPackage, Uri uri, int modeFlags) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void sendBroadcast(Intent intent, String receiverPermission) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void sendBroadcast(Intent intent) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void sendBroadcastAsUser(Intent intent, UserHandle user) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void sendBroadcastAsUser(Intent intent, UserHandle user, String receiverPermission) {
//        throw new UnsupportedOperationException();
//    }
//
//    public void sendOrderedBroadcast(Intent intent, String receiverPermission, String receiverAppOp, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
//        throw new UnsupportedOperationException();
//    }
//
//    public void sendOrderedBroadcast(Intent intent, int initialCode, String receiverPermission, String receiverAppOp, BroadcastReceiver resultReceiver, Handler scheduler, String initialData, Bundle initialExtras, Bundle options) {
//        throw new UnsupportedOperationException();
//    }
//
//    public void sendOrderedBroadcast(Intent intent, String receiverPermission, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
//        throw new UnsupportedOperationException();
//    }
//
//    public void sendOrderedBroadcast(Intent intent, String receiverPermission) {
//        throw new UnsupportedOperationException();
//    }
//
//    public void sendOrderedBroadcastAsUser(Intent intent, UserHandle user, String receiverPermission, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
//        throw new UnsupportedOperationException();
//    }
//
//    public void sendStickyBroadcast(Intent intent) {
//        throw new UnsupportedOperationException();
//    }
//
//    public void sendStickyBroadcast(Intent intent, Bundle options) {
//        throw new UnsupportedOperationException();
//    }
//
//    public void sendStickyBroadcastAsUser(Intent intent, UserHandle user) {
//        throw new UnsupportedOperationException();
//    }
//
//    public void sendStickyOrderedBroadcast(Intent intent, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
//        throw new UnsupportedOperationException();
//    }
//
//    public void sendStickyOrderedBroadcastAsUser(Intent intent, UserHandle user, BroadcastReceiver resultReceiver, Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {
//        throw new UnsupportedOperationException();
//    }
//
//    public void setTheme(int resid) {
//        throw new UnsupportedOperationException();
//    }
//
//    public void setWallpaper(Bitmap bitmap) {
//        throw new UnsupportedOperationException();
//    }
//
//    public void setWallpaper(InputStream data) {
//        throw new UnsupportedOperationException();
//    }
//
//    public void startActivities(Intent[] intents, Bundle options) {
//        throw new UnsupportedOperationException();
//    }
//
//    public void startActivities(Intent[] intents) {
//        throw new UnsupportedOperationException();
//    }
//
//    public void startActivity(Intent intent) {
//        throw new UnsupportedOperationException();
//    }
//
//    public void startActivity(Intent intent, Bundle options) {
//        throw new UnsupportedOperationException();
//    }
//
//    public ComponentName startForegroundService(Intent service) {
//        throw new UnsupportedOperationException();
//    }
//
//    public boolean startInstrumentation(ComponentName className, String profileFile, Bundle arguments) {
//        throw new UnsupportedOperationException();
//    }
//
//    public void startIntentSender(IntentSender intent, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags) {
//        throw new UnsupportedOperationException();
//    }
//
//    public void startIntentSender(IntentSender intent, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, Bundle options) {
//        throw new UnsupportedOperationException();
//    }
//
////    public ComponentName startService(Intent service) {
////        throw new UnsupportedOperationException();
////    }
//
////    public boolean stopService(Intent name) {
////        throw new UnsupportedOperationException();
////    }
//
//    public void unbindService(ServiceConnection conn) {
//        throw new UnsupportedOperationException();
//    }
//
//    public void unregisterReceiver(BroadcastReceiver receiver) {
//        throw new UnsupportedOperationException();
//    }
//
//    public void updateServiceGroup(ServiceConnection conn, int group, int importance) {
//        throw new UnsupportedOperationException();
//    }
//
//
////    public Display getDisplay() {
////        throw new UnsupportedOperationException();
////    }
//
//    public void checkCallingOrSelfUriPermissions(List<Uri> uris, int modeFlags) {
//        throw new UnsupportedOperationException();
//    }
//
//    public void checkCallingUriPermissions(List<Uri> uris, int modeFlags) {
//        throw new UnsupportedOperationException();
//    }
//
////    public void createAttributionContext(String attributionTag) {
////        throw new UnsupportedOperationException();
////    }
//
//    public void checkUriPermissions(List<Uri> uris, int pid, int uid, int modeFlags) {
//        throw new UnsupportedOperationException();
//    }
//
////    public void createWindowContext(int type, Bundle options) {
////        throw new UnsupportedOperationException();
////    }
//
////    public Context createWindowContext(Display display, int type, Bundle options) {
////        throw new UnsupportedOperationException();
////    }
//
//
//
//
//// Final methods
////public final String getString (int resId){
////}
////public String getString (int resId, Object... formatArgs){
////}
////  @Override
////  public void dismissDialog(int id) {
////  }
////  @Override
////  public void dismissKeyboardShortcutsHelper() {
////  }
////  @Override
////  public Application getApplication(){
////      throw new UnsupportedOperationException();
////  }
//// @Override
//// public void getMediaController(){
////    throw new UnsupportedOperationException();
//// }
//// @Override
//// public void getParent(){
////    throw new UnsupportedOperationException();
//// }
//// @Override
//// public void getSearchEvent(){
////    throw new UnsupportedOperationException();
//// }
////
////@Override
////public SplashScreen getSplashScreen() {
////    throw new UnsupportedOperationException();
////}
////@Override
////public void getTitle(){
////    throw new UnsupportedOperationException();
////}
////
////@Override
////public void getTitleColor() {
////    throw new UnsupportedOperationException();
////}
////
////
////@Override
////public void getVolumeControlStream(){
////    throw new UnsupportedOperationException();
////}
////@Override
////public boolean isChild(){
////    throw new UnsupportedOperationException();
////}
////@Override
////public void managedQuery(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
////    throw new UnsupportedOperationException();
////}
////@Override
////public void removeDialog(int id){
////    throw new UnsupportedOperationException();
////}
////@Override
////public void requestPermissions(String[] permissions, int requestCode){
////    throw new UnsupportedOperationException();
////}
////
////@Override
////public void requestShowKeyboardShortcuts(){
////    throw new UnsupportedOperationException();
////}
////@Override
////public void requestWindowFeature(int featureId){
////    throw new UnsupportedOperationException();
////}
////@Override
////public void requireViewById(int id) {
////    throw new UnsupportedOperationException();
////}
////@Override
////public void runOnUiThread(Runnable action) {
////    throw new UnsupportedOperationException();
////}
////@Override
////public void setDefaultKeyMode(int mode) {
////    throw new UnsupportedOperationException();
////}
////@Override
////public void setFeatureDrawable(int featureId, Drawable drawable) {
////    throw new UnsupportedOperationException();
////}
////@Override
////public void setFeatureDrawableAlpha(int featureId, int alpha) {
////    throw new UnsupportedOperationException();
////}
////@Override
////public void setFeatureDrawableResource(int featureId, int resId){
////    throw new UnsupportedOperationException();
////}
////@Override
////public void setFeatureDrawableUri(int featureId, Uri uri) {
////    throw new UnsupportedOperationException();
////}
////@Override
////public void setMediaController(MediaController controller){
////    throw new UnsupportedOperationException();
////}
////@Override
////public void setProgress(int progress) {
////    throw new UnsupportedOperationException();
////}
////
////@Override
////public void setProgressBarIndeterminate(boolean indeterminate) {
////    throw new UnsupportedOperationException();
////}
////
////@Override
////public void setProgressBarIndeterminateVisibility(boolean visible) {
////    throw new UnsupportedOperationException();
////}
////
////@Override
////public void setProgressBarVisibility(boolean visible) {
////    throw new UnsupportedOperationException();
////}
////@Override
////public void setResult(int resultCode, Intent data) {
////    throw new UnsupportedOperationException();
////}
////
////@Override
////public void  setResult(int resultCode) {
////    throw new UnsupportedOperationException();
////}
////
////@Override
////public void setSecondaryProgress(int secondaryProgress){
////    throw new UnsupportedOperationException();
////}
////@Override
////public void setVolumeControlStream(int streamType){
////    throw new UnsupportedOperationException();
////}
////@Override
////public void showDialog(int id, Bundle args){
////    throw new UnsupportedOperationException();
////}
////
////@Override
////public void showDialog(int id) {
////    throw new UnsupportedOperationException();
////}
//
//// other problematic methods
//// they are added in later versions of SDK that I am using now
////@Override
////public boolean onPictureInPictureRequested() {
////    throw new UnsupportedOperationException();
////}
////@Override
////public void setLocusContext(LocusId locusId, Bundle bundle){
////    throw new UnsupportedOperationException();
////}
////@Override
////public void setTranslucent(boolean translucent){
////    throw new UnsupportedOperationException();
////}
////    @Override
////    public void createContext(ContextParams contextParams) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public ContextParams getParams() {
////        throw new UnsupportedOperationException();
////    }
//
//}
