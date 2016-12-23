package com.forksystem.constituicao.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.forksystem.constituicao.MainActivity;
import com.forksystem.constituicao.R;
import com.forksystem.constituicao.adapter.ArtigoAdapter;
import com.forksystem.constituicao.dao.ArtigoDao;
import com.forksystem.constituicao.dao.EpigrafoDao;
import com.forksystem.constituicao.database.DatabaseHelper;
import com.forksystem.constituicao.databinding.FragmentArtigoBinding;
import com.forksystem.constituicao.model.Artigo;
import com.forksystem.constituicao.model.Epigrafo;
import com.forksystem.constituicao.utils.Constants;
import com.forksystem.constituicao.utils.RecyclerItemClickListener;
import com.thefuntasty.infinity.InfinityAdapter;
import com.thefuntasty.infinity.InfinityEventListener;
import com.thefuntasty.infinity.InfinityFiller;


import java.util.ArrayList;
import java.util.List;
import rx.functions.Action1;

/**
 * Created by sm on 24/11/2016.
 */

public class ArtigoFragment extends Fragment implements SearchView.OnQueryTextListener {

    private static final String TAG = ArtigoFragment.class.getSimpleName();

    private InfinityAdapter<Artigo, ?> adapterLista;
    private View view;
    private DatabaseHelper databaseHelper;
    List<Artigo> artigoList=new ArrayList<>();
    ArtigoDao artigoDao=null;
    FragmentArtigoBinding fragmentViewBinding;
    FragmentManager manager;



    public ArtigoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentViewBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_artigo,container,false);
        view = fragmentViewBinding.getRoot();
        setupRecyclerview();
        MainActivity.activity.getSupportActionBar().setTitle("Artigos");
        MainActivity.toggle.syncState();
        return view;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        artigoDao=new ArtigoDao();

    }




    private void setupRecyclerview() {

        setupAdapter();


//        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_artigo);
        fragmentViewBinding.recyclerArtigo.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentViewBinding.recyclerArtigo.setAdapter(adapterLista);

//        refresh = (SwipeRefreshLayout)view.findViewById(R.id.refresh);


        fragmentViewBinding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapterLista.restart(true);
            }
        });


        fragmentViewBinding.recyclerArtigo.addOnItemTouchListener(new RecyclerItemClickListener(
                MainActivity.getAppContext(), fragmentViewBinding.recyclerArtigo, new RecyclerItemClickListener.OnItemClickListener() {


            @Override
            public void onItemClick(View view, int position) {

                Bundle bundle = new Bundle();
                final EpigrafoDao epigrafoDao=new EpigrafoDao();

                TextView textView= (TextView) view.findViewById(R.id.artigo_numero);

                ArrayList<Epigrafo> epigrafos= (ArrayList<Epigrafo>) epigrafoDao.getAllById(Integer.valueOf(textView.getText().toString()));

                bundle.putSerializable("epigrafos",epigrafos);

                    Fragment fragment= new DetalheFragment();
                     fragment.setArguments(bundle);
                     MainActivity.changeFragment(fragment,true);
             }

           @Override
            public void onLongItemClick(View view, int position) {}

        }));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        getActivity().getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) item.getActionView();


        searchView.setOnQueryTextListener(this);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }



    private void setupAdapter() {

        adapterLista = new ArtigoAdapter();
        adapterLista.setLimit(10);

        adapterLista.setFiller(new InfinityFiller<Artigo>() {
            @Override
            public void onLoad(int limit, int offset, final Callback<Artigo> callback) {
                artigoDao.getDataObservable(limit, offset)
                        .subscribe(new Action1<List<Artigo>>() {
                                       @Override
                                       public void call(List<Artigo> artigos) {
                                           artigoList.addAll(artigos);
                                           callback.onData(artigos);

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

        adapterLista.setEventListener(new InfinityEventListener(){

            @Override public void onFirstLoaded(boolean pullToRefresh) {
                fragmentViewBinding.refresh.setRefreshing(false);
            }

            @Override public void onFirstUnavailable(Throwable error, boolean pullToRefresh) {
                fragmentViewBinding.refresh.setRefreshing(false);
            }

            @Override public void onFirstEmpty(boolean pullToRefresh) {
                fragmentViewBinding.refresh.setRefreshing(false);
            }



        });

        adapterLista.start();

    }


    @Override
    public boolean onQueryTextSubmit(final String s) {


        return false;
    }

    @Override
    public boolean onQueryTextChange(final String s) {

        artigoDao.getDataObservableByTitulo(s)
                .subscribe(new Action1<List<Artigo>>() {
                    @Override
                    public void call(List<Artigo> artigos) {
                        adapterLista.setInitialContent(artigos);
                        adapterLista.notifyDataSetChanged();
                        Log.d(TAG,artigos.toString());

                    }
                });



        return true;
    }

}