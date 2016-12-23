package com.forksystem.constituicao.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.util.Log;

import com.forksystem.constituicao.database.DaoModel;
import com.forksystem.constituicao.database.DatabaseHelper;
import com.forksystem.constituicao.model.Artigo;
import com.forksystem.constituicao.model.Favorito;
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

public class FavoritoDao implements DaoModel<Favorito> {

    DatabaseHelper databaseHelper;

    public FavoritoDao() {


        databaseHelper = DatabaseHelper.getInstance();

    }


    @Override
    public List<Favorito> getAllById(int ID) {
        return null;
    }

    @Override
    public List<Favorito> getAll() {


        List<Favorito> objectList = new ArrayList<>();
        Favorito object;

        String query = "SELECT * FROM " + Constants.TB_FAVORITO;

        Cursor cursor = databaseHelper.getReadableDatabase().rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            object = new Favorito(cursor.getInt(0), cursor.getString(1));
            objectList.add(object);
            cursor.moveToNext();
        }
        cursor.close();

        databaseHelper.getReadableDatabase().close();

        return objectList;
    }

    public void addFavorito(String favorito) {


        if (favorito != null) {
            databaseHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(Constants.CONTEUDO, favorito);


            databaseHelper.getReadableDatabase().insert(Constants.TB_FAVORITO, null, values);
            databaseHelper.getReadableDatabase().close();
        }
    }


    public Observable<List<Favorito>> getDataObservable(final int limit, final int offset) {

        List<Favorito> favoritos = getAll();
        List<Favorito> favoritoList = new ArrayList<>();
        favoritoList.add(new Favorito(1, "Ainda não foi adicionado nenhum favorito"));

        Observable<Favorito> listaDefault = Observable.from(favoritoList);
        Observable<Favorito> observable = Observable.from(favoritos);

        if (favoritos.size() == 0) {

            return listaDefault
                    .toList()
                    .delay(5, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io());

        } else {

            return observable
                    .take(limit)
                    .buffer(limit)
                    .delay(5, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io());
        }
    }
}







