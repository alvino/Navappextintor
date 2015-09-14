package com.alvino.mavappextintor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;


public class MainActivity extends ActionBarActivity {

    final static private String TAG = "MainActivity";

    private int pressBackPressed = 0;
    private Toolbar mToolbar = null;
    private Drawer.Result navigationDrawer;
    private int mPositionClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        mToolbar.setLogo(R.drawable.ic_extintor_white_24dp);
        setSupportActionBar(mToolbar);


        navigationDrawer = new Drawer()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withDisplayBelowToolbar(true)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.LEFT)
                .withSavedInstance(savedInstanceState)
                .withActionBarDrawerToggle(true)

                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        for (int count = 0, tam = navigationDrawer.getDrawerItems().size(); count < tam; count++) {

                            if (count == mPositionClicked && mPositionClicked < 5) {
                                PrimaryDrawerItem aux = (PrimaryDrawerItem) navigationDrawer.getDrawerItems().get(count);
                                aux.setIcon(getResources().getDrawable(getCorretcDrawerIcon(count, false)));
                                break;
                            }
                        }

                        if (i < 5) {
                            ((PrimaryDrawerItem) iDrawerItem).setIcon(getResources().getDrawable(getCorretcDrawerIcon(i, true)));
                        }

                        mPositionClicked = i;
                        navigationDrawer.getAdapter().notifyDataSetChanged();


                        FragmentManager fragmentManager = getSupportFragmentManager();
                        Fragment fragment = null;
                        switch (mPositionClicked) {
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
                        if (fragment != null) {
                            fragmentManager.beginTransaction()
                                    .replace(R.id.container, fragment)
                                    .commit();
                        }
                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        Toast.makeText(MainActivity.this, "onItemLongClick: " + i, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .build();

        navigationDrawer.addItem(new PrimaryDrawerItem()
                .withName(R.string.title_actionbar_lista_agendamento)
                .withIcon(R.drawable.ic_view_agenda_black_24dp));
        navigationDrawer.addItem(new PrimaryDrawerItem()
                .withName(R.string.title_actionbar_lista_cliente)
                .withIcon(R.drawable.ic_account_box_black_24dp));
        navigationDrawer.addItem(new PrimaryDrawerItem()
                .withName(R.string.title_actionbar_todos_os_agendamento)
                .withIcon(R.drawable.ic_view_list_black_24dp));
        navigationDrawer.addItem(new PrimaryDrawerItem()
                .withName(R.string.title_actionbar_converte_dados_para_csv)
                .withIcon(R.drawable.ic_settings_backup_restore_black_24dp));
        navigationDrawer.addItem(new PrimaryDrawerItem()
                .withName(R.string.title_actionbar_sobre)
                .withIcon(R.drawable.ic_live_help_black_24dp));

        navigationDrawer.setSelection(0);

    }


     private int getCorretcDrawerIcon(int position, boolean isSelecetd){
         switch(position){
             case 0:
                 return( isSelecetd ? R.drawable.ic_view_agenda_white_24dp : R.drawable.ic_view_agenda_black_24dp );
             case 1:
                 return( isSelecetd ? R.drawable.ic_account_box_white_24dp : R.drawable.ic_account_box_black_24dp );
             case 2:
                 return( isSelecetd ? R.drawable.ic_view_list_white_24dp : R.drawable.ic_view_list_black_24dp );
             case 3:
                 return( isSelecetd ? R.drawable.ic_settings_backup_restore_white_24dp : R.drawable.ic_settings_backup_restore_black_24dp );
             case 4:
                 return( isSelecetd ? R.drawable.ic_live_help_white_24dp : R.drawable.ic_live_help_black_24dp );
         }
         return(0);
     }

     /*
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
     */
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
        if (navigationDrawer.isDrawerOpen()) {
            navigationDrawer.closeDrawer();
        } else {
            if (pressBackPressed == 1) {
                super.onBackPressed();
            } else {
                Toast.makeText(getApplicationContext(), "Pressione mais uma vez para fechar.", Toast.LENGTH_SHORT).show();
            }
            pressBackPressed++;
        }
    }
}
