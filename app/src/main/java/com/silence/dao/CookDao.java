package com.silence.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.silence.pojo.Cook;
import com.silence.utils.DBOpenHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Silence on 2016/2/5 0005.
 */
public class CookDao {
    private DBOpenHelper mDBOpenHelper;
    private String mRootPath;

    public CookDao(Context context) {
        mDBOpenHelper = DBOpenHelper.getInstance(context);
        mRootPath = context.getFilesDir() + File.separator;
    }

    public List<Cook> getFavors() {
        String sql = "select _id, name, material, method from cookbook where is_favorite=1;";
        SQLiteDatabase db = mDBOpenHelper.getDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<Cook> cooks = null;
        if (cursor.moveToFirst()) {
            cooks = new ArrayList<>();
            Cook cook;
            do {
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String method = cursor.getString(cursor.getColumnIndex("method"));
                String material = cursor.getString(cursor.getColumnIndex("material"));
                String path = mRootPath + "recipe_" + id + ".jpg";
                cook = new Cook();
                cook.setId(id);
                cook.setPath(path);
                cook.setMaterial(material);
                cook.setMethod(method);
                cook.setIsFavorite(true);
                cook.setName(name);
                cooks.add(cook);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return cooks;
    }

    public List<Cook> getCookByType(int typeId) {
        String sql = "select _id, name, material, method, is_favorite from cookbook where type_id=?;";
        SQLiteDatabase db = mDBOpenHelper.getDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(typeId)});
        List<Cook> cooks = null;
        if (cursor.moveToFirst()) {
            cooks = new ArrayList<>();
            Cook cook;
            do {
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String method = cursor.getString(cursor.getColumnIndex("method"));
                String material = cursor.getString(cursor.getColumnIndex("material"));
                int isFavor = cursor.getInt(cursor.getColumnIndex("is_favorite"));
                String path = mRootPath + "recipe_" + id + ".jpg";
                cook = new Cook(id, typeId, name, material, method, isFavor == 1, path);
                cooks.add(cook);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return cooks;
    }

    public boolean doFavor(int cookId, boolean isFavorite) {
        String sql = "update cookbook set is_favorite=? where _id=?";
        SQLiteDatabase db = mDBOpenHelper.getDatabase();
        try {
            db.execSQL(sql, new Object[]{isFavorite ? 0 : 1, cookId});
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Cook getCook(int cookId) {
        String sql = "select type_id, name, material, method, is_favorite from cookbook where _id=?;";
        SQLiteDatabase db = mDBOpenHelper.getDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(cookId)});
        Cook cook = null;
        if (cursor.moveToFirst()) {
            int typeId = cursor.getInt(cursor.getColumnIndex("type_id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String method = cursor.getString(cursor.getColumnIndex("method"));
            String material = cursor.getString(cursor.getColumnIndex("material"));
            String path = mRootPath + "recipe_" + cookId + ".jpg";
            int isFavor = cursor.getInt(cursor.getColumnIndex("is_favorite"));
            cook = new Cook(cookId, typeId, name, material, method, isFavor == 1, path);
        }
        cursor.close();
        return cook;
    }

    public List<Cook> findCooks(String filterName) {
        List<Cook> cooks = null;
        String sql = "select _id, type_id, name, material, method, is_favorite from cookbook where name like ?;";
        SQLiteDatabase db = mDBOpenHelper.getDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{"%" + filterName + "%"});
        if (cursor.moveToFirst()) {
            cooks = new ArrayList<>(cursor.getCount());
            Cook cook;
            do {
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String method = cursor.getString(cursor.getColumnIndex("method"));
                String material = cursor.getString(cursor.getColumnIndex("material"));
                int isFavor = cursor.getInt(cursor.getColumnIndex("is_favorite"));
                int typeId = cursor.getInt(cursor.getColumnIndex("type_id"));
                String path = mRootPath + "recipe_" + id + ".jpg";
                cook = new Cook(id, typeId, name, material, method, isFavor == 1, path);
                cooks.add(cook);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return cooks;
    }

    public int getCount() {
        SQLiteDatabase db = mDBOpenHelper.getDatabase();
        String sql = "select count(*) num from cookbook;";
        Cursor cursor = db.rawQuery(sql, null);
        int num = 0;
        if (cursor.moveToFirst()) {
            num = cursor.getInt(cursor.getColumnIndex("num"));
        }
        cursor.close();
        return num;
    }
}