package com.alvino.mavappextintor;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.Toast;

import com.alvino.mavappextintor.adapter.ClienteAdapter;
import com.alvino.mavappextintor.adapter.ExtintorAdapter;
import com.alvino.mavappextintor.bancodados.Cliente;
import com.alvino.mavappextintor.bancodados.ClienteProvider;
import com.alvino.mavappextintor.bancodados.Extintor;
import com.alvino.mavappextintor.bancodados.ExtintorProvider;
import com.alvino.mavappextintor.bancodados.Visita;
import com.alvino.mavappextintor.bancodados.VisitaProvider;
import com.alvino.mavappextintor.core.SimplesDataFormatada;
import com.alvino.mavappextintor.dialog.AlertDialogFragment;
import com.alvino.mavappextintor.dialog.DateDialog;
import com.alvino.mavappextintor.inteface.RecyclerViewOnClickListener;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;


public class ListaExtintorFragment extends Fragment implements RecyclerViewOnClickListener {

    //ListView listaViewCliente = null;



    private static final String ID = "id";

    private Long mId;

    private View v;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private Set<Extintor> mDataSet;
    private ExtintorAdapter mAdapter;
    private Button btCadastro;

    private Button cancelar = null;
    private Button salvar = null;

    private boolean flagAtualizar = false;
    private Date data;
    private Fragment fragment;


    public static ListaExtintorFragment newInstance(Long id) {
        ListaExtintorFragment fragment = new ListaExtintorFragment();
        Bundle args = new Bundle();
        args.putLong(ID, id);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mId = getArguments().getLong(ID);

            ExtintorProvider db = new ExtintorProvider(getActivity().getApplicationContext());
            mDataSet = db.allCliente(mId);

            mAdapter = new ExtintorAdapter(getActivity(), (List<Extintor>) mDataSet);
            mAdapter.setmRecyclerViewOnClickListener(this);
            mRecyclerView.setAdapter(mAdapter);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle(getResources().getString(R.string.title_actionbar_lista_extintor));

        v = inflater.inflate(R.layout.fragment_recycler_view_lista_extintor, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_lista);
        btCadastro = (Button) v.findViewById(R.id.bt_cadastro_extintor);
        btCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new CadastroExtintorFragment().newInstance(
                        null,
                        mId,
                        null,
                        null
                );

                getFragmentManager().beginTransaction().replace(R.id.container, fragment)
                        .commit();
            }
        });

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);


        return v;
    }


    @Override
    public void onClickListener(View view, final int position) {
        final Extintor e = mAdapter.getItemExtintor(position);

        switch (view.getId()) {
           case R.id.ib_editar_lista_cliente:

                Fragment fragment = new CadastroExtintorFragment().newInstance(
                        e.getId(),
                        e.getCliente(),
                        e.getTipo(),
                        e.getData_validade()
                );

                getFragmentManager().beginTransaction().replace(R.id.container, fragment)
                        .commit();

                break;
            case R.id.ib_remover_lista_cliente:


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
