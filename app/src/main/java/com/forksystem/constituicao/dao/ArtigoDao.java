package com.forksystem.constituicao.dao;

import android.database.Cursor;
import android.util.Log;

import com.forksystem.constituicao.database.DaoModel;
import com.forksystem.constituicao.database.DatabaseHelper;
import com.forksystem.constituicao.model.Artigo;
import com.forksystem.constituicao.utils.Constants;
import com.forksystem.constituicao.utils.Utilitario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sam on 06/12/16.
 */

public class ArtigoDao implements DaoModel<Artigo> {

    DatabaseHelper databaseHelper;

    public ArtigoDao() {


        databaseHelper=DatabaseHelper.getInstance();

    }


    @Override
    public List<Artigo> getAllById(int ID) {return null;}

    @Override
    public List<Artigo> getAll() {



        List<Artigo> objectList = new ArrayList<>();
        Artigo object;


        String query="SELECT * FROM "+ Constants.TB_ARTIGO;

        Cursor cursor = databaseHelper.getWritableDatabase().rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            object = new Artigo(cursor.getInt(0), cursor.getString(1));
            objectList.add(object);
            cursor.moveToNext();
        }
        cursor.close();

        databaseHelper.getWritableDatabase().close();

        return  objectList;
    }



    public List<Artigo> getByTituloOrArtigo(String s) {


        List<Artigo> objectList = new ArrayList<>();
        Artigo object;
        Cursor cursor = null;

        if (!Utilitario.validarString(s)) {
            String[] args = new String[1];
            args[0] = "%" + s + "%";


            String query = "SELECT * FROM " + Constants.TB_ARTIGO + " WHERE titulo LIKE ?";

            cursor = databaseHelper.getWritableDatabase().rawQuery(query, args);

        }else {

            String query = "SELECT * FROM " + Constants.TB_ARTIGO + " WHERE id="+s;

            cursor = databaseHelper.getWritableDatabase().rawQuery(query, null);

        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            object = new Artigo(cursor.getInt(0), cursor.getString(1));
            objectList.add(object);
            cursor.moveToNext();

        }
        cursor.close();
        databaseHelper.getWritableDatabase().close();

        return objectList;
    }


    public Observable<List<Artigo>> getDataObservable(final int limit, final int offset) {


        Observable<Artigo> observable = Observable.from(getAll());


        if (offset == 245) {
            return Observable.just(Collections.<Artigo>emptyList())
                    .delay(1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io());
        } else if (offset != 0) { // next part
            return observable
                    .skip(offset)
                    .take(limit)
                    .buffer(limit)
                    .delay(5, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io());
        } else { // first part
            return observable
                    .take(limit)
                    .buffer(limit)
                    .delay(5, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io());
        }
    }


    public Observable<List<Artigo>> getDataObservableByTitulo(String query) {


        Observable<Artigo> observable = Observable
                            .from(getByTituloOrArtigo(query))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation());


            return observable.toList();

    }




}
