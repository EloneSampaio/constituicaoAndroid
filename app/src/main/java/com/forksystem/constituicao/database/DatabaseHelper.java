package com.forksystem.constituicao.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


import com.forksystem.constituicao.MainActivity;
import com.forksystem.constituicao.model.Artigo;
import com.forksystem.constituicao.utils.Constants;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DatabaseHelper extends SQLiteAssetHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static DatabaseHelper databaseHelper=null;
    public static String DB_PATH = "/data/data/com.forksystem.constituicao/databases/";


    private SQLiteDatabase mDatabase;
    private Context context;

    public DatabaseHelper() {
        super(MainActivity.getAppContext(), Constants.DB_NAME, null, Constants.DB_VERSION);
        this.context = MainActivity.getAppContext();
    }



    public SQLiteDatabase getmDatabase() {
        return mDatabase;
    }

    public void setmDatabase(SQLiteDatabase mDatabase) {
        this.mDatabase = getReadableDatabase();
    }


    public static synchronized  DatabaseHelper getInstance(){

        if (databaseHelper==null){
            databaseHelper=new DatabaseHelper();

        }
        return databaseHelper;
    }




}