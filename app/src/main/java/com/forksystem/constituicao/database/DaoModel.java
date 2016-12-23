package com.forksystem.constituicao.database;



import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sam on 06/12/16.
 */

public interface DaoModel<T> {

    public List<T> getAll();

    public List<T> getAllById(int ID);

}
