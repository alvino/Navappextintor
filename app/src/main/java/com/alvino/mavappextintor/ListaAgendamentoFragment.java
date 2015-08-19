package com.alvino.mavappextintor;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alvino.mavappextintor.adapter.AgendamentoAdapter;
import com.alvino.mavappextintor.bancodados.BDAgendamento;
import com.alvino.mavappextintor.bancodados.entity.AgendamentoEntity;
import com.alvino.mavappextintor.dialog.AlertDialogFragment;
import com.alvino.mavappextintor.inteface.RecyclerViewOnClickListener;

import java.util.ArrayList;

public class ListaAgendamentoFragment extends Fragment implements RecyclerViewOnClickListener {


    //private ListView listaView = null;
    private ArrayList<AgendamentoEntity> agendamentos;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private AgendamentoAdapter mAdapter;
    private ArrayList<AgendamentoEntity> mDataSet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle(getResources().getString(R.string.title_actionbar_lista_agendamento));

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recycler_view_lista, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_lista);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        BDAgendamento bd = new BDAgendamento(getActivity().getApplicationContext());
        mDataSet = (ArrayList<AgendamentoEntity>) bd.buscarTodos(0);

        mAdapter = new AgendamentoAdapter(getActivity(),mDataSet);
        mAdapter.setRecyclerViewOnClickListener(this);
        mRecyclerView.setAdapter(mAdapter);


        //listaView = (ListView) rootView.findViewById(R.id.listViewAgendamento);
        return rootView;
    }



    @Override
    public void onClickListener(View view, final int position) {
        DialogInterface.OnClickListener ok = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AgendamentoEntity entity = mAdapter.getItemEntity(position);
                entity.setVisitado(1);
                new BDAgendamento(getActivity()).atualizar(entity);
                mAdapter.removeItem(position);
            }
        };

        DialogInterface.OnClickListener cancelar = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        };
        TextView tvCliente = (TextView) view.findViewById(R.id.tv_nome_agendamento);
        String texto =  "Confirmar visita, para "+ tvCliente.getText();
        AlertDialogFragment dialogFragment = new AlertDialogFragment("Visita", texto, ok, cancelar);
        dialogFragment.show(getFragmentManager(), "ALERTDIALOGFRAGMENT");
    }
}
