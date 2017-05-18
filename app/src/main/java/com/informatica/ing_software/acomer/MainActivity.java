package com.informatica.ing_software.acomer;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.informatica.ing_software.acomer.fragments.FavoriteFragment;
import com.informatica.ing_software.acomer.fragments.HelpFragment;
import com.informatica.ing_software.acomer.fragments.MyAccountFragment;
import com.informatica.ing_software.acomer.fragments.SearchFragment;

public class MainActivity extends AppCompatActivity implements SearchFragment.OnFragmentInteractionListener,
        FavoriteFragment.OnFragmentInteractionListener, MyAccountFragment.OnFragmentInteractionListener, HelpFragment.OnFragmentInteractionListener {

    // Prueba2
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creamos una instancia de SearchFragment
        SearchFragment searchFragment = new SearchFragment();

        // Regogegemos los datos pasados del LoginActivity
        bundle = getIntent().getExtras();

        // Pasamos los extras del Intent al fragmento como argumentos
        searchFragment.setArguments(bundle);

        // Añadimos el fragmento al FrameLayout 'fragment_container'
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, searchFragment).commit();

        // Bottom Navigation View
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.accion_buscar:
                                //textView.setText("Buscar");

                                // Creamos una instancia de SearchFragment
                                SearchFragment searchFragment = new SearchFragment();

                                // Pasamos los extras del Intent al fragmento como argumentos
                                searchFragment.setArguments(getIntent().getExtras());

                                // Añadimos el fragmento al FrameLayout 'fragment_container'
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_container, searchFragment).commit();

                                break;
                            case R.id.accion_favoritos:
                                //textView.setText("Favoritos");

                                // Creamos una instancia de SearchFragment
                                FavoriteFragment favoriteFragment = new FavoriteFragment();

                                // Pasamos los extras del Intent al fragmento como argumentos
                                favoriteFragment.setArguments(getIntent().getExtras());

                                // Añadimos el fragmento al FrameLayout 'fragment_container'
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_container, favoriteFragment).commit();
                                break;
                            case R.id.accion_mi_cuenta:
                                //textView.setText("Mi Cuenta");

                                // Creamos una instancia de SearchFragment
                                MyAccountFragment miAccountFragment = new MyAccountFragment();

                                // Pasamos los extras del Intent al fragmento como argumentos
                                miAccountFragment.setArguments(getIntent().getExtras());

                                // Añadimos el fragmento al FrameLayout 'fragment_container'
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_container, miAccountFragment).commit();
                                break;
                            case R.id.accion_ayuda:
                                //textView.setText("Mi Cuenta");

                                // Creamos una instancia de SearchFragment
                                HelpFragment helpFragment = new HelpFragment();

                                // Pasamos los extras del Intent al fragmento como argumentos
                                helpFragment.setArguments(getIntent().getExtras());

                                // Añadimos el fragmento al FrameLayout 'fragment_container'
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_container, helpFragment).commit();
                                break;

                        }

                        return false;
                    }
                }
        );
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}