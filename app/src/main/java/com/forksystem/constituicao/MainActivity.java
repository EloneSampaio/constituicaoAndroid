package com.forksystem.constituicao;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.forksystem.constituicao.database.DatabaseHelper;
import com.forksystem.constituicao.fragment.ArtigoFragment;
import com.forksystem.constituicao.fragment.FavoritoFragment;
import com.forksystem.constituicao.fragment.PrincipalFragment;
import com.forksystem.constituicao.fragment.ShareFragment;
import com.forksystem.constituicao.utils.Constants;
import com.forksystem.constituicao.view.ConfigurationActivity;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    private static Context mContext;
    public static   FragmentManager manager;
    private static Fragment myFragment;
    private Toolbar toolbar;
    private Bundle bundle;
    public static AppCompatActivity activity;
    public  Drawer result;
    public static ActionBarDrawerToggle toggle;
    private Boolean estado=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String theme = sharedPrefs.getString(getString(R.string.preference_cor),"NULL");
        settingTheme(theme);

        bundle=savedInstanceState;
        mContext = getApplicationContext();
        activity=MainActivity.this;
        manager=getSupportFragmentManager();
        setContentView(R.layout.activity_main);

        showToolbar();
        showDraweLayout();
        toggle = new ActionBarDrawerToggle(this, result.getDrawerLayout(), R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        MainActivity.activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MainActivity.activity.getSupportActionBar().setTitle(R.string.app_name);
        MainActivity.toggle.syncState();

        changeFragment(new PrincipalFragment(),false);
        new DatabaseHelper().getInstance();


    }



    private void showToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public static void changeFragment(Fragment fragment, boolean doAddToBackStack) {
           myFragment=fragment;
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.content_main, fragment);
            if (doAddToBackStack) {
                transaction.addToBackStack(null);
                activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left);
            } else {
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                toggle.syncState();
            }
            transaction.commit();

    }

    @Override
    public void onBackPressed() {
        Fragment fragment = manager.findFragmentById(R.id.content_main);
        if(fragment!=null) {
            String fragmentName = fragment.getClass().getSimpleName();
            ArrayList lista = new ArrayList();
            lista.add(Constants.FRAGMENT_PRINCIPAL_TAG);
            lista.add(Constants.FRAGMENT_FAVORITO_TAG);
            lista.add(Constants.FRAGMENT_LISTA_TAG);
            lista.add(Constants.FRAGMENT_SHARE_TAG);

            if (lista.contains(fragmentName)) {
                result.openDrawer();
            } else if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }else {
            super.onBackPressed();
        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            case  R.id.action_settings  :
                Intent intent = new Intent(this, ConfigurationActivity.class);
                startActivity(intent);
                return true;
             default: return super.onOptionsItemSelected(item);

            }



    }


    public void menuClick(IDrawerItem item) {
        // Handle navigation view item clicks here.
        int id= (int) item.getIdentifier();

        switch (id){

            case 1:
                estado=false;
                changeFragment(new PrincipalFragment(),false);
                break;
            case 2 :
                estado=false;
                changeFragment(new ArtigoFragment(),false);

                break;

            case 3 :
                estado=false;
                changeFragment(new FavoritoFragment(),false);
                break;

            case 4:

                Intent  intent=new Intent(this,ConfigurationActivity.class);
                startActivity(intent);

                break;

            case 5:
                estado=false;
                changeFragment(new ShareFragment(),false);
                break;
            case 6: finish();
                System.exit(0);
                break;

            default:  estado=false;
                changeFragment(new PrincipalFragment(),false);

        }
        if (myFragment!=null){
             changeFragment(myFragment,estado);

        }




    }


    public static Context getAppContext() {
        return mContext;
    }


    public void settingTheme(String theme) {
        switch (theme) {
            case Constants.AZUL :
                setTheme(R.style.AppTheme);
                break;
            case Constants.VERMELHO:
                setTheme(R.style.AppTheme2);
                break;
            case Constants.VERDE:
                setTheme(R.style.AppTheme3);
                break;

            default:
                setTheme(R.style.AppTheme);
                break;
        }
    }

    public  void showDraweLayout(){

        SecondaryDrawerItem item1 = new SecondaryDrawerItem().withName(R.string.app_name).withIcon(GoogleMaterial.Icon.gmd_home).withIdentifier(1);

        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName(getString(R.string.autor)).withEmail(getString(R.string.email)).withIcon(getResources().getDrawable(R.drawable.profile))
                )
                .withSavedInstance(bundle)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        //create the drawer and remember the `Drawer` result object
       result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withTranslucentStatusBar(false)
                .addDrawerItems(

                        item1,
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.action_artigos).withIcon(GoogleMaterial.Icon.gmd_description).withIdentifier(2),
                        new SecondaryDrawerItem().withName(R.string.action_favorito).withIcon(GoogleMaterial.Icon.gmd_favorite).withIdentifier(3),
                        new SecondaryDrawerItem().withName(R.string.action_settings).withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(4),
                        new DividerDrawerItem().withTag("Encontre-me"),
                        new SecondaryDrawerItem().withName(R.string.action_compartilhar).withIcon(GoogleMaterial.Icon.gmd_share).withIdentifier(5),
                       new SecondaryDrawerItem().withName(R.string.action_sair).withIcon(GoogleMaterial.Icon.gmd_exit_to_app).withIdentifier(6)

        )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D

                        menuClick(drawerItem);


                        return false;
                    }
                })
                .build();

//        result.setSelection(1, true);

    }

    }
