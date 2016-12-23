package com.forksystem.constituicao.fragment;

import android.content.res.AssetFileDescriptor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.forksystem.constituicao.MainActivity;
import com.forksystem.constituicao.R;
import com.forksystem.constituicao.databinding.FragmentPrincipalBinding;


import java.io.IOException;
import java.io.InputStream;


/**
 * Created by sm on 24/11/2016.
 */

public class PrincipalFragment extends Fragment {


    AssetFileDescriptor descriptor;
    String data="";

   FragmentPrincipalBinding principalBinding;

    public PrincipalFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.activity.getSupportActionBar().setTitle(R.string.app_name);
        MainActivity.toggle.syncState();


        try {
            InputStream inputStream=getActivity().getAssets().open("pernambulo.txt");

            int size=inputStream.available();
            byte[] buffer= new byte[size];
            inputStream.read(buffer);
            inputStream.close();
             data=new String(buffer);
            String lineSep = System.getProperty("line.separator");
            data=data.replaceAll("<br>",lineSep);
               } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         principalBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_principal,container,false);
        View view = principalBinding.getRoot();

         principalBinding.principalTitulo.setText(data);
        MainActivity.activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MainActivity.toggle.syncState();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}






