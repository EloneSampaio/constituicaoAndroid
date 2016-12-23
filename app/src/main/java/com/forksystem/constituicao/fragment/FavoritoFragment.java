package com.forksystem.constituicao.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.forksystem.constituicao.MainActivity;
import com.forksystem.constituicao.R;
import com.forksystem.constituicao.adapter.FavoritoAdapter;
import com.forksystem.constituicao.dao.FavoritoDao;
import com.forksystem.constituicao.databinding.FragmentFavoritoBinding;
import com.forksystem.constituicao.model.Favorito;
import com.forksystem.constituicao.utils.RecyclerItemClickListener;
import com.thefuntasty.infinity.InfinityAdapter;
import com.thefuntasty.infinity.InfinityEventListener;
import com.thefuntasty.infinity.InfinityFiller;

import java.util.List;

import rx.functions.Action1;

public class FavoritoFragment extends Fragment {

    View view;
    private InfinityAdapter<Favorito, ?> adapterLista;

    FavoritoDao favoritoDao=null;
    FragmentFavoritoBinding fragmentFavoritoBinding;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentFavoritoBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_favorito,container,false);

        view = fragmentFavoritoBinding.getRoot();
        MainActivity.activity.getSupportActionBar().setTitle(R.string.action_favorito);
        MainActivity.toggle.syncState();

        setupRecyclerview();

        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favoritoDao=new FavoritoDao();

    }



    private void setupRecyclerview() {

        setupAdapter();


//        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_artigo);
        fragmentFavoritoBinding.recyclerFavorito.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentFavoritoBinding.recyclerFavorito.setAdapter(adapterLista);

//        refresh = (SwipeRefreshLayout)view.findViewById(R.id.refresh);




        fragmentFavoritoBinding.recyclerFavorito.addOnItemTouchListener(new RecyclerItemClickListener(
                MainActivity.getAppContext(), fragmentFavoritoBinding.recyclerFavorito, new RecyclerItemClickListener.OnItemClickListener() {


            @Override
            public void onItemClick(View view, int position) {




            }


            @Override
            public void onLongItemClick(View view, int position) {}


        }));


    }



    private void setupAdapter() {

        Log.d("TAG",favoritoDao.getAll().toString());

        adapterLista = new FavoritoAdapter();
        adapterLista.setLimit(10);

        adapterLista.setFiller(new InfinityFiller<Favorito>() {
            @Override
            public void onLoad(int limit, int offset, final Callback<Favorito> callback) {
                favoritoDao.getDataObservable(limit, offset)
                        .subscribe(new Action1<List<Favorito>>() {
                                       @Override
                                       public void call(List<Favorito> favoritos) {
                                            callback.onData(favoritos);

                                       }
                                   }, new Action1<Throwable>() {
                                       @Override
                                       public void call(Throwable throwable) {
                                           callback.onError(throwable);
                                       }
                                   }

                        );
            }
        });



        adapterLista.start();

    }
}
