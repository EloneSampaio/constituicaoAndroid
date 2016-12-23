package com.forksystem.constituicao.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.forksystem.constituicao.MainActivity;
import com.forksystem.constituicao.R;
import com.forksystem.constituicao.utils.Constants;

public class ShareFragment extends Fragment {

    View view;

    public ShareFragment(){}


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_share, container, false);

        MainActivity.activity.getSupportActionBar().setTitle("Partilhar");
        MainActivity.toggle.syncState();

        Button facebook= (Button) view.findViewById(R.id.share_facebook);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Redirecionando",Toast.LENGTH_LONG).show();
                getActivity().startActivity(getOpenFacebookIntent(getActivity().getApplicationContext()));
            }
        });
        return view;

    }

    public static Intent getOpenFacebookIntent(Context context) {

        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.FACEBOOK_PAGE_ID));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.FACEBOOK_PAGE));
        }
    }
}
