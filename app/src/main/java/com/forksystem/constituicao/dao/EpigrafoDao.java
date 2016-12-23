package com.forksystem.constituicao.dao;

import android.database.Cursor;
import android.util.Log;

import com.forksystem.constituicao.database.DaoModel;
import com.forksystem.constituicao.database.DatabaseHelper;
import com.forksystem.constituicao.model.Epigrafo;
import com.forksystem.constituicao.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sam on 06/12/16.
 */

public class EpigrafoDao implements DaoModel{


    DatabaseHelper databaseHelper;

    public EpigrafoDao() {
        databaseHelper= DatabaseHelper.getInstance();
    }

    @Override
    public List getAll() {
        return null;
    }

    @Override
    public List<Epigrafo> getAllById(int ID) {

        Log.d("Epigrafo",ID+"");
         String query=" SELECT * FROM "+Constants.TB_EPIGRAFO+" WHERE artigo_id=?";
        Cursor cursor=null;


        List<Epigrafo> objectList = new ArrayList<>();
        Epigrafo object;


         cursor = databaseHelper.getReadableDatabase().rawQuery(query,new String[]{String.valueOf(ID)}, null);
        if (cursor!=null && cursor.moveToFirst()){

            while (!cursor.isAfterLast()) {


                object = new Epigrafo(cursor.getString(1),cursor.getInt(2));
                objectList.add(object);
                cursor.moveToNext();
            }
            cursor.close();
        }


        databaseHelper.getReadableDatabase().close();

        return  objectList;
    }
}
