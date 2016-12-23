package com.forksystem.constituicao.utils;

import android.support.annotation.NonNull;
import android.widget.SearchView;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by sam on 11/12/16.
 */

public class Utilitario {

    public static boolean validarString(String texto) {
        String valor = texto;
        boolean valido = true;

        if (texto.isEmpty()){
            valido=false;
        }
        for (int i = 0; i < valor.length(); i++) {
            Character caractere = valor.charAt(i);
            if (!Character.isDigit(caractere)) {
                //É String
                valido = false;
            }
        }
        //É numero
        return valido == true;
    }

    public static String converterCor(int cor){

        return String.format("#%06X", (0xFFFFFF & cor));
    }


}
