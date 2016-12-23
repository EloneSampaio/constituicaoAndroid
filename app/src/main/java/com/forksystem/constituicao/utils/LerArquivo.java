package com.forksystem.constituicao.utils;


import android.content.res.AssetFileDescriptor;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

/**
 * Created by sm on 24/11/2016.
 */

public  class LerArquivo {


    public LerArquivo(){

    }

    public JSONObject readArquivo(AssetFileDescriptor fileDescriptor){

        JSONObject jsonObject=null;
        JSONParser jsonParser=new JSONParser();
        try {
            FileReader reader = new FileReader(fileDescriptor.getFileDescriptor());
             Object  object= (JSONObject) jsonParser.parse(reader);
            jsonObject=(JSONObject) object;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
