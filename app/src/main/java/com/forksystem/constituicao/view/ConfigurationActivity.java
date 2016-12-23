package com.forksystem.constituicao.view;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.codevscolor.materialpreference.activity.MaterialPreferenceActivity;
import com.codevscolor.materialpreference.callback.MaterialPreferenceCallback;
import com.codevscolor.materialpreference.util.MaterialPrefUtil;
import com.forksystem.constituicao.R;
import com.forksystem.constituicao.utils.Constants;


public class ConfigurationActivity extends MaterialPreferenceActivity implements MaterialPreferenceCallback  {

    //initialization of the sdk should be done here
    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        //register this class as listener for preference change
        setPreferenceChangedListener(this);

        //use dark theme or not . Default is light theme
        useDarkTheme(true);

        //set toolbar title
        setToolbarTitle("Preferencias");

        //set primary color

        //default secondary color for tinting widgets, if no secondary color is used yet
        setDefaultSecondaryColor(this, MaterialPrefUtil.COLOR_BLUE);

        //set application package name and xml resource name of preference
        setAppPackageName("com.forksystem.constituicao");
        //set xml resource name
        setXmlResourceName("preferences");

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String valor = sharedPrefs.getString(getString(R.string.preference_cor),"NULL");
        Log.d("Cor",valor);
        switch (valor){
            case Constants.AZUL:
                setPrimaryColor(MaterialPrefUtil.COLOR_LIGHT_BLUE);
                break;

            case Constants.VERDE:
                setPrimaryColor(MaterialPrefUtil.COLOR_GREEN);
                break;

            case Constants.VERMELHO:
                setPrimaryColor(MaterialPrefUtil.COLOR_RED);
                break;
            default: setPrimaryColor(MaterialPrefUtil.COLOR_LIGHT_BLUE);


        }


    }


    /**
     * callback for preference changes
     *
     * @param sharedPreferences
     * @param name
     */
    @Override
    public void onPreferenceSettingsChanged(SharedPreferences sharedPreferences, String name) {



    }
}
