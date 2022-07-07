//package usask.cyberlab.smalisimulator.simsmali.androidMocks;
//
//import android.annotation.SuppressLint;
//import android.app.ActionBar;
//import android.app.Activity;
//import android.app.ActivityManager;
//import android.app.Application;
//import android.app.Dialog;
//import android.app.Fragment;
//import android.app.FragmentManager;
//import android.app.LoaderManager;
//import android.app.PendingIntent;
//import android.app.PictureInPictureParams;
//import android.app.SharedElementCallback;
//import android.app.TaskStackBuilder;
//import android.app.VoiceInteractor;
//import android.app.assist.AssistContent;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentSender;
//import android.content.SharedPreferences;
//import android.content.res.Configuration;
//import android.content.res.Resources;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.PersistableBundle;
//import android.transition.Scene;
//import android.transition.TransitionManager;
//import android.util.AttributeSet;
//import android.view.ActionMode;
//import android.view.ContextMenu;
//import android.view.DragAndDropPermissions;
//import android.view.DragEvent;
//import android.view.KeyEvent;
//import android.view.KeyboardShortcutGroup;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.MotionEvent;
//import android.view.SearchEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.view.WindowManager;
//import android.view.accessibility.AccessibilityEvent;
//import android.widget.Toolbar;
//
//
//import java.io.File;
//import java.io.FileDescriptor;
//import java.io.PrintWriter;
//import java.util.List;
//
//import usask.cyberlab.smalisimulator.simsmali.emulator.AmbiguousValue;
//import usask.cyberlab.smalisimulator.simsmali.exceptions.AmbiguousValueReturnException;
//
//import android.support.v7.app.AppCompatActivity;
//
//@SuppressLint("MissingSuperCall")
//public class MockAppCompatActivity extends AppCompatActivity {
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
//    public MockAppCompatActivity() {
//
//    }
//
//    @Override
//    public String toString() {
//        return "MockAppCompatActivity";
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
//    @Override
//    public PendingIntent createPendingResult(int requestCode, Intent data, int flags) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean dispatchGenericMotionEvent(MotionEvent ev) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean dispatchTrackballEvent(MotionEvent ev) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void dump( String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
//    }
//
//    @Override
//    public boolean enterPictureInPictureMode(PictureInPictureParams params) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void enterPictureInPictureMode() {
//    }
//
//    @Override
//    public <T extends View> T findViewById(int id) {
//        throw new UnsupportedOperationException();
//    }
//
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
//    @Override
//    public ActionBar getActionBar() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public ComponentName getCallingActivity() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public String getCallingPackage() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public int getChangingConfigurations() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public ComponentName getComponentName() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Scene getContentScene() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public TransitionManager getContentTransitionManager() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public View getCurrentFocus() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public FragmentManager getFragmentManager() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Intent getIntent() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Object getLastNonConfigurationInstance() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public LayoutInflater getLayoutInflater() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public LoaderManager getLoaderManager() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public String getLocalClassName() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public int getMaxNumPictureInPictureActions() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public MenuInflater getMenuInflater() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Intent getParentActivityIntent() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public SharedPreferences getPreferences(int mode) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Uri getReferrer() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public int getRequestedOrientation() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public int getTaskId() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public VoiceInteractor getVoiceInteractor() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Window getWindow() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public WindowManager getWindowManager() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean hasWindowFocus() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void invalidateOptionsMenu() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean isActivityTransitionRunning() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean isChangingConfigurations() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean isDestroyed() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean isFinishing() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean isImmersive() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean isInMultiWindowMode() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean isInPictureInPictureMode() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean isLocalVoiceInteractionSupported() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean isTaskRoot() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean isVoiceInteraction() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean isVoiceInteractionRoot() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean moveTaskToBack(boolean nonRoot) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean navigateUpTo(Intent upIntent) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean navigateUpToFromChild(Activity child, Intent upIntent) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onActionModeFinished(ActionMode mode) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onActionModeStarted(ActionMode mode) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onActivityReenter(int resultCode, Intent data) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onAttachFragment(Fragment fragment) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onAttachedToWindow() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onBackPressed() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onConfigurationChanged( Configuration newConfig) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onContentChanged() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean onContextItemSelected( MenuItem item) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onContextMenuClosed( Menu menu) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public CharSequence onCreateDescription() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onCreateNavigateUpTaskStack(TaskStackBuilder builder) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean onCreatePanelMenu(int featureId, Menu menu) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public View onCreatePanelView(int featureId) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean onCreateThumbnail(Bitmap outBitmap, Canvas canvas) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public View onCreateView(String name, Context context, AttributeSet attrs) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onDetachedFromWindow() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onEnterAnimationComplete() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean onGenericMotionEvent(MotionEvent event) {
//        throw new UnsupportedOperationException();
//    }
//
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onLocalVoiceInteractionStarted() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onLocalVoiceInteractionStopped() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onLowMemory() {
//        throw new UnsupportedOperationException();
//    }
//
//
//    @Override
//    public boolean onMenuOpened(int featureId, Menu menu) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onMultiWindowModeChanged(boolean isInMultiWindowMode, Configuration newConfig) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean onNavigateUp() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean onNavigateUpFromChild(Activity child) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onOptionsMenuClosed(Menu menu) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onPanelClosed(int featureId, Menu menu) {
//        throw new UnsupportedOperationException();
//    }
//
//
//    @Override
//    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
//        throw new UnsupportedOperationException();
//    }
//
//
//    @Override
//    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onPrepareNavigateUpTaskStack(TaskStackBuilder builder) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean onPreparePanel(int featureId, View view, Menu menu) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void onProvideAssistContent(AssistContent outContent) {
//        throw new UnsupportedOperationException();
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
//    //    @Override
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
//    //    @Override
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
//
//
//}
