package com.forksystem.constituicao.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.forksystem.constituicao.R;
import com.forksystem.constituicao.databinding.ListaArtigoBinding;
import com.forksystem.constituicao.model.Artigo;
import com.thefuntasty.infinity.InfinityAdapter;

/**
 * Created by sm on 22/11/2016.
 */


public  class ArtigoAdapter extends InfinityAdapter<Artigo,ArtigoAdapter.ViewHolderConstituicao> {


    private static final String TAG = Artigo.class.getSimpleName();
    private Context context;

    @Override
    public ViewHolderConstituicao onCreateContentViewHolder(ViewGroup parent, int viewType) {
        ListaArtigoBinding listaArtigoBinding= DataBindingUtil
                           .inflate(LayoutInflater
                           .from(parent.getContext()),R.layout.lista_artigo,parent,false);

        return  new ViewHolderConstituicao(listaArtigoBinding);
    }

    @Override
    public void onBindContentViewHolder(ViewHolderConstituicao holder, int position) {

        ((TextView) holder.artigoBinding.tituloArtigo).setText(getContentItem(position).getTitulo());
        ((TextView) holder.artigoBinding.artigoNumero).setText(getContentItem(position).getArtigo()+" - ");
    }

    @Override
    public int getFooterLayout() {
        return R.layout.footer_layout;
    }


    public  static class ViewHolderConstituicao extends RecyclerView.ViewHolder {

         ListaArtigoBinding artigoBinding;


        public ViewHolderConstituicao(ListaArtigoBinding itemView) {
            super(itemView.getRoot());
            artigoBinding=itemView;


        }

    }



}