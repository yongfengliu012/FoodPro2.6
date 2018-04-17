package xwang.george.foodpro2;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by george on 10/9/2017.
 */

public class UserDBContentProvider extends ContentProvider {
    private UserDB userDB;
    private SQLiteDatabase dbReader, dbWriter;
    public static final String USER_TABLE="user";
    public static final Uri USER_URI=Uri.parse("content://xwang.george.foodpro2.UserDBContentProvider");
    @Override
    public boolean onCreate() {
        userDB = new UserDB(getContext());
        dbReader = userDB.getReadableDatabase();
        dbWriter = userDB.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return dbReader.query(USER_TABLE, null, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "";
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        dbWriter.insert(USER_TABLE, null, values);
        return USER_URI;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return dbWriter.delete(USER_TABLE, selection, selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return dbWriter.update(USER_TABLE, values, selection, selectionArgs);
    }
}
