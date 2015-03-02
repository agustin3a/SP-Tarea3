package com.example.carlos.sp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.carlos.sp.data.Comment.CommentEntry;
import com.example.carlos.sp.data.PhotoContract.PhotoEntry;
import com.example.carlos.sp.data.Store.StoreEntry;



/**
 * Created by Carlos on 24/02/15.
 */
public class DbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 9;

    static final String DATABASE_NAME = "stores.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_STORE_TABLE = "CREATE TABLE " + StoreEntry.TABLE_NAME + " (" +
                StoreEntry._ID + " INTEGER PRIMARY KEY," +
                StoreEntry.COLUMN_NAME + " TEXT UNIQUE NOT NULL, " +
                StoreEntry.COLUMN_ADDRESS + " TEXT NOT NULL," +
                StoreEntry.COLUMN_PHONE + " TEXT NOT NULL," +
                StoreEntry.COLUMN_SCHEDULE + " TEXT NOT NULL," +
                StoreEntry.COLUMN_WEBSITE + " TEXT NOT NULL," +
                StoreEntry.COLUMN_EMAIL + " TEXT NOT NULL," +
                StoreEntry.COLUMN_FAVORITES_COUNTER + " INTEGER NOT NULL," +
                StoreEntry.COLUMN_LOCATION + " TEXT NOT NULL" +
                ");";

        final String SQL_CREATE_PHOTO_TABLE = "CREATE TABLE " + PhotoEntry.TABLE_NAME + " (" +
                PhotoEntry._ID + " INTEGER PRIMARY KEY," +
                PhotoEntry.COLUMN_URL + " TEXT NOT NULL, " +
                PhotoEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL," +
                PhotoEntry.COLUMN_FAVORITES_COUNTER + " INTEGER NOT NULL," +
                PhotoEntry.COLUMN_STORE_KEY + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + PhotoEntry.COLUMN_STORE_KEY+ ") REFERENCES " +
                StoreEntry.TABLE_NAME + " (" + StoreEntry._ID + ")" +
                ");";

        final String SQL_CREATE_COMMENT_TABLE = "CREATE TABLE " + CommentEntry.TABLE_NAME + " (" +
                CommentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CommentEntry.COLUMN_TEXT + " TEXT NOT NULL, " +
                CommentEntry.COLUMN_STORE_KEY + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + CommentEntry.COLUMN_STORE_KEY+ ") REFERENCES " +
                StoreEntry.TABLE_NAME + " (" + StoreEntry._ID + ")" +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_STORE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_PHOTO_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_COMMENT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + StoreEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PhotoEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CommentEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


}
