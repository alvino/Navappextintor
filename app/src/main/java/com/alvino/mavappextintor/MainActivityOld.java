package com.alvino.mavappextintor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivityOld extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    final static private String TAG = "MainActivity";

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private int pressBackPressed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        /*
        * savedInstanceState
        */
        if (savedInstanceState == null) {
            onNavigationDrawerItemSelected(0);
        }


    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new ListaAgendamentoFragment();
                break;
            case 1:
                fragment = new ListaClientesFragment();
                break;
            case 2:
                fragment = new ListaTodosAgendadosFragment();
                break;
            case 3:
                fragment = new ConverteBancoEmCSVFragment();
                break;
            case 4:
                fragment = new SobreFragment();
                break;

        }
        if(fragment != null) {
            onSectionAttached(position);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
        pressBackPressed = 0;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        pressBackPressed = 0;
    }

    /**
     * Esta vinculado dentro do metodo MainActivity.onNavigationDrawerItemSelected
     *
     * @param number retorna a string corespondente dentro do array nav_drawer_menu
     */
    public void onSectionAttached(int number) {
        //String[] navDrawerMenu = getResources().getStringArray(R.array.nav_drawer_menu);
        //mTitle = navDrawerMenu[number];
    }


    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        //actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        restoreActionBar();

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(pressBackPressed == 1){
            super.onBackPressed();
        } else {
            Toast.makeText(getApplicationContext(),"Pressione mais uma vez para fechar.",Toast.LENGTH_SHORT).show();
        }
        pressBackPressed ++;
    }
}
