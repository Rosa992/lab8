package com.example.guardiansearch;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLite helper to store favorite articles.
 */
public class FavoritesDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "favorites.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE = "favorites";
    public static final String COL_ID = "_id"; // for cursor adapters
    public static final String COL_ARTICLE_ID = "article_id";
    public static final String COL_TITLE = "title";
    public static final String COL_SECTION = "section";
    public static final String COL_URL = "url";

    public FavoritesDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + TABLE + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_ARTICLE_ID + " TEXT UNIQUE," +
                COL_TITLE + " TEXT," +
                COL_SECTION + " TEXT," +
                COL_URL + " TEXT" +
                ");";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    /**
     * Insert a favorite. Returns true if inserted.
     */
    public boolean addFavorite(Article a) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_ARTICLE_ID, a.getId());
        cv.put(COL_TITLE, a.getTitle());
        cv.put(COL_SECTION, a.getSection());
        cv.put(COL_URL, a.getUrl());
        long id = db.insertWithOnConflict(TABLE, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
        return id != -1;
    }

    /**
     * Delete favorite by article id.
     */
    public boolean deleteFavorite(String articleId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE, COL_ARTICLE_ID + "=?", new String[]{articleId});
        return rows > 0;
    }

    /**
     * Return list of favorite articles.
     */
    public List<Article> getAllFavorites() {
        List<Article> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE, null, null, null, null, null, COL_ID + " DESC");
        if (c != null) {
            while (c.moveToNext()) {
                String aid = c.getString(c.getColumnIndexOrThrow(COL_ARTICLE_ID));
                String title = c.getString(c.getColumnIndexOrThrow(COL_TITLE));
                String section = c.getString(c.getColumnIndexOrThrow(COL_SECTION));
                String url = c.getString(c.getColumnIndexOrThrow(COL_URL));
                list.add(new Article(aid, title, section, url));
            }
            c.close();
        }
        return list;
    }
}
