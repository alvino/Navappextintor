package com.alvino.mavappextintor;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.alvino.mavappextintor.adapter.ExtintorAdapter;
import com.alvino.mavappextintor.bancodados.Extintor;
import com.alvino.mavappextintor.bancodados.ExtintorProvider;
import com.alvino.mavappextintor.core.SimplesDataFormatada;
import com.alvino.mavappextintor.dialog.AlertDialogFragment;
import com.alvino.mavappextintor.inteface.RecyclerViewOnClickListener;
import com.github.clans.fab.FloatingActionButton;

import java.util.Date;
import java.util.List;


public class ListaExtintorFragment extends Fragment implements RecyclerViewOnClickListener {

    private static final String CLIENTE = "cliente";

    private Long mCliente;

    private View v;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private List<Extintor> mDataSet;
    private ExtintorAdapter mAdapter;
    //private Button btCadastro;

    private Button cancelar = null;
    private Button salvar = null;

    private boolean flagAtualizar = false;
    private Date data;
    private Fragment fragment;
    private FloatingActionButton fab;


    public static ListaExtintorFragment newInstance(Long cliente) {
        ListaExtintorFragment fragment = new ListaExtintorFragment();
        Bundle args = new Bundle();
        args.putLong(CLIENTE, cliente);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mCliente = getArguments().getLong(CLIENTE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle(getResources().getString(R.string.title_actionbar_lista_extintor));

        v = inflater.inflate(R.layout.fragment_recycler_view_lista_com_floating, container, false);

        fab = (FloatingActionButton) v.findViewById(R.id.fab_bt);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new CadastroExtintorFragment().newInstance(null, mCliente, null, null);

                getFragmentManager().beginTransaction().replace(R.id.container, fragment)
                        .commit();
            }
        });
        mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_lista);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy < 0){
                    fab.hide(true);
                } else {
                    fab.show(true);
                }
            }
        });

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ExtintorProvider db = new ExtintorProvider(getActivity().getApplicationContext());
        mDataSet = db.allCliente(mCliente);

        mAdapter = new ExtintorAdapter(getFragmentManager(), getActivity(), mDataSet);
        mAdapter.setmRecyclerViewOnClickListener(this);
        mRecyclerView.setAdapter(mAdapter);


        return v;
    }


    @Override
    public void onClickListener(View view, final int position) {
        final Extintor e = mAdapter.getItemExtintor(position);

        switch (view.getId()) {
           case R.id.fab_editar_extintor:

                Fragment fragment = new CadastroExtintorFragment().newInstance(
                        e.getId(),
                        e.getCliente(),
                        e.getTipo(),
                        SimplesDataFormatada.formatar(e.getData_validade(), SimplesDataFormatada.DDMYYYY )
                        );

                getFragmentManager().beginTransaction().replace(R.id.container, fragment)
                        .commit();

                break;
            case R.id.fab_deletar_extintor:


                DialogInterface.OnClickListener ok = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String texto = "Removido o extintor do tipo: " + e.getTipo() + " com sucesso.";

                        new ExtintorProvider(getActivity()).delete(e);

                        Toast to = Toast.makeText(getActivity(), texto, Toast.LENGTH_SHORT);
                        to.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
                        to.show();
                        mAdapter.removeItem(position);
                    }
                };

                DialogInterface.OnClickListener cancelar = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                };

                AlertDialogFragment dialogFragment = new AlertDialogFragment("", "Remover extintor", ok, cancelar);
                dialogFragment.show(getFragmentManager(), "ALERTDIALOGFRAGMENT");
                break;
        }

    }
}
