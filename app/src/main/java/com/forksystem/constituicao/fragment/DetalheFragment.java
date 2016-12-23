package com.forksystem.constituicao.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.Toast;


import com.forksystem.constituicao.MainActivity;
import com.forksystem.constituicao.R;

import com.forksystem.constituicao.dao.FavoritoDao;
import com.forksystem.constituicao.database.DatabaseHelper;
import com.forksystem.constituicao.databinding.ListaDetalheBinding;
import com.forksystem.constituicao.model.Epigrafo;
import com.forksystem.constituicao.utils.Constants;
import com.forksystem.constituicao.utils.RecyclerItemClickListener;


import java.util.ArrayList;


public class DetalheFragment extends android.support.v4.app.Fragment {

    private static Context mContext;
    private ArrayList<Epigrafo> epigrafos=new ArrayList();

    RecyclerView recyclerView;

    BottomSheetDialog bottomSheetDialog;
    private String textoCopyy;

    private static final String TAG = DetalheFragment.class.getSimpleName();
    private DatabaseHelper databaseHelper;
    View view;



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mContext = MainActivity.getAppContext();



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.activity_detalhe,container,false);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getAppContext());
        String theme = sharedPrefs.getString(getString(R.string.preference_cor),"NULL");


        settingTheme(theme);


        recyclerView=(RecyclerView) view.findViewById(R.id.recycler_detalhe);
        bottomSheetDialog=new BottomSheetDialog(MainActivity.activity);
         View content=LayoutInflater.from(MainActivity.activity).inflate(R.layout.barra_opcoes,null);
        bottomSheetDialog.setContentView(content);
        bottoSheetConfig();

        Bundle bundle = this.getArguments();
        epigrafos=(ArrayList<Epigrafo>) bundle.getSerializable("epigrafos");

        setupRecyclerview();

        return view;
    }



    private void setupRecyclerview() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getAppContext()));
        final MyAdapter myAdapter= new MyAdapter(epigrafos);
        recyclerView.setAdapter(myAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                MainActivity.getAppContext(),recyclerView, new RecyclerItemClickListener.OnItemClickListener(){


            @Override
            public void onItemClick(View view, int position) {
                Log.d(TAG,"Item clicado chamando Detalh activity");


            }


            @Override
            public void onLongItemClick(View view, int position) {
                MyAdapter.ViewHolder viewHolder= (MyAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(position);

                bottomSheetDialog.show();
                textoCopyy=viewHolder.detalheBinding.epigrafoDetalhe.getText().toString();
                Log.d(TAG,"Long click clicado ");
            }


        }));
    }




    public static Context getAppContext() {
        return mContext;
    }


    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

        private ArrayList<Epigrafo> list=new ArrayList<>();
        private      int n=1;

        public MyAdapter(ArrayList<Epigrafo> data){
            this.list=data;


        }
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            ListaDetalheBinding detalheBinding= DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()),R.layout.lista_detalhe,parent,false);

            ViewHolder viewHolder=new ViewHolder(detalheBinding);
            return viewHolder;

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            String texto=list.get(position).getConteudo().toString();
            String artigo=String.valueOf(list.get(position).getArtigo());

            holder.detalheBinding.epigrafoDetalhe.setText(texto);

        }




        @Override
        public int getItemCount() {
            return list.size();
        }




        public  static class ViewHolder extends RecyclerView.ViewHolder {

            ListaDetalheBinding detalheBinding;
            public View mView;


            public ViewHolder(ListaDetalheBinding itemView) {
                super(itemView.getRoot());
                detalheBinding=itemView;

            }

        }
    }







    public void bottoSheetConfig(){

        Button btnCopiar= (Button) bottomSheetDialog.findViewById(R.id.detalhe_copiar);
        Button btnCompartilhar= (Button) bottomSheetDialog.findViewById(R.id.detalhe_compartilhar);
        Button btnMarca= (Button) bottomSheetDialog.findViewById(R.id.detalhe_marcar);

        btnCopiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copiar();
                Toast.makeText(getAppContext(),"Texto copiado",Toast.LENGTH_SHORT).show();
            }
        });

        btnCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compartilhar();
                Toast.makeText(getAppContext(),"compartilhado",Toast.LENGTH_SHORT).show();
            }
        });

        btnMarca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FavoritoDao favoritoDao=new FavoritoDao();

                favoritoDao.addFavorito(textoCopyy);


                Toast.makeText(getAppContext(),"Adicionado aos favoritos",Toast.LENGTH_SHORT).show();
            }
        });





    }

    public void copiar(){

        ClipboardManager myClipboard = (ClipboardManager) getAppContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData myClip = ClipData.newPlainText("text", textoCopyy);
        myClipboard.setPrimaryClip(myClip);

    }

    public void compartilhar(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, textoCopyy);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
    }


    public void settingTheme(String theme) {
        switch (theme) {
            case Constants.AZUL :
                getActivity().setTheme(R.style.AppTheme);
                break;
            case Constants.VERMELHO:
                getActivity().setTheme(R.style.AppTheme2);
                break;
            case Constants.VERDE:
                getActivity().setTheme(R.style.AppTheme3);
                break;

            default:
                getActivity().setTheme(R.style.AppTheme);
                break;
        }
    }


}
