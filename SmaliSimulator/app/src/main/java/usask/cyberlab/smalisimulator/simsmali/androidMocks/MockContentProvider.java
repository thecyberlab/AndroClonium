//package usask.cyberlab.smalisimulator.simsmali.androidMocks;
//
//import android.content.ContentProvider;
//import android.content.ContentProviderOperation;
//import android.content.ContentProviderResult;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.pm.ProviderInfo;
//import android.content.res.AssetFileDescriptor;
//import android.content.res.Configuration;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.CancellationSignal;
//import android.os.ParcelFileDescriptor;
//
//import java.io.FileDescriptor;
//import java.io.PrintWriter;
//import java.util.ArrayList;
//
//public abstract class MockContentProvider extends ContentProvider {
//
//    @Override
//    public String toString() {
//        return "MockContentProvider";
//    }
//
////    @Override
////    public ContentProviderResult[] applyBatch(String authority, ArrayList<ContentProviderOperation> operations) {
////        throw new UnsupportedOperationException();
////    }
//
//    @Override
//    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public void attachInfo(Context context, ProviderInfo info) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public int bulkInsert(Uri uri, ContentValues[] values) {
//        throw new UnsupportedOperationException();
//    }
//
////    @Override
////    public Bundle call(String authority, String method, String arg, Bundle extras) {
////        throw new UnsupportedOperationException();
////    }
//
//    @Override
//    public Bundle call(String method, String arg, Bundle extras) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Uri canonicalize(Uri url) {
//        throw new UnsupportedOperationException();
//    }
//
////    @Override
////    public final ContentProvider.CallingIdentity clearCallingIdentity() {
////        throw new UnsupportedOperationException();
////    }
//
//    @Override
//    public int delete(Uri uri, String selection, String[] selectionArgs){
//        throw new UnsupportedOperationException();
//    }
//
////    @Override
////    public int delete(Uri uri, Bundle extras) {
////        throw new UnsupportedOperationException();
////    }
//
//    @Override
//    public void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
//        throw new UnsupportedOperationException();
//    }
//
////    @Override
////    public final String getCallingAttributionTag() {
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public final String getCallingPackage() {
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public final String getCallingPackageUnchecked() {
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public final Context getContext() {
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public final PathPermission[] getPathPermissions() {
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public final String getReadPermission() {
////        throw new UnsupportedOperationException();
////    }
//
//    @Override
//    public String[] getStreamTypes(Uri uri, String mimeTypeFilter) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public String getType(Uri uri) {
//        throw new UnsupportedOperationException();
//    }
//
////    @Override
////    public final String getWritePermission() {
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public Uri insert(Uri uri, ContentValues values, Bundle extras) {
////        throw new UnsupportedOperationException();
////    }
//
//    @Override
//    public Uri insert(Uri uri, ContentValues values) {
//        throw new UnsupportedOperationException();
//    }
//
////    @Override
////    public void onCallingPackageChanged() {
////        throw new UnsupportedOperationException();
////    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public abstract boolean onCreate();
//
//    @Override
//    public void onLowMemory() {
//    }
//
//    @Override
//    public void onTrimMemory(int level) {
//    }
//
//    @Override
//    public AssetFileDescriptor openAssetFile(Uri uri, String mode, CancellationSignal signal) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public AssetFileDescriptor openAssetFile(Uri uri, String mode) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public ParcelFileDescriptor openFile(Uri uri, String mode, CancellationSignal signal) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public ParcelFileDescriptor openFile(Uri uri, String mode) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public <T> ParcelFileDescriptor openPipeHelper(Uri uri, String mimeType, Bundle opts, T args, PipeDataWriter<T> func) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public AssetFileDescriptor openTypedAssetFile(Uri uri, String mimeTypeFilter, Bundle opts, CancellationSignal signal) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public AssetFileDescriptor openTypedAssetFile(Uri uri, String mimeTypeFilter, Bundle opts) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Cursor query(Uri uri, String[] projection, Bundle queryArgs, CancellationSignal cancellationSignal) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignal cancellationSignal) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean refresh(Uri uri, Bundle extras, CancellationSignal cancellationSignal) {
//        throw new UnsupportedOperationException();
//    }
//
////    @Override
////    public final Context requireContext() {
////        throw new UnsupportedOperationException();
////    }
//
////    @Override
////    public final void restoreCallingIdentity(ContentProvider.CallingIdentity identity) {
////        throw new UnsupportedOperationException();
////    }
//
//    @Override
//    public void shutdown() {
//    }
//
//    @Override
//    public Uri uncanonicalize(Uri url) {
//        throw new UnsupportedOperationException();
//    }
//
////    @Override
////    public int update(Uri uri, ContentValues values, Bundle extras) {
////        throw new UnsupportedOperationException();
////    }
//
//    @Override
//    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean isTemporary() {
//        throw new UnsupportedOperationException();
//    }
//
////    @Override
////    public final ParcelFileDescriptor openFileHelper(Uri uri, String mode) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public final void setPathPermissions(PathPermission[] permissions) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public final void setReadPermission(String permission) {
////        throw new UnsupportedOperationException();
////    }
////
////    @Override
////    public final void setWritePermission(String permission) {
////        throw new UnsupportedOperationException();
////    }
//
//
//}
