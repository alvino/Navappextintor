package com.alvino.mavappextintor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alvino.mavappextintor.adapter.AgendamentoAdapter;
import com.alvino.mavappextintor.domain.Visita;
import com.alvino.mavappextintor.bancodados.VisitaProvider;
import com.alvino.mavappextintor.inteface.RecyclerViewOnClickListener;

import java.util.Collections;
import java.util.List;

public class ListaAgendamentoFragment extends Fragment implements RecyclerViewOnClickListener {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private AgendamentoAdapter mAdapter;
    private List<Visita> mDataSet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle(R.string.title_actionbar_lista_agendamento);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recycler_view_lista, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_lista);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        VisitaProvider bd = new VisitaProvider(getActivity().getApplicationContext());
        mDataSet = bd.allNotAtendido();

        Collections.sort(mDataSet);

        mAdapter = new AgendamentoAdapter(getActivity(), mDataSet);
        mAdapter.setRecyclerViewOnClickListener(this);
        mRecyclerView.setAdapter(mAdapter);


        //listaView = (ListView) rootView.findViewById(R.id.listViewAgendamento);
        return rootView;
    }


    @Override
    public void onClickListener(View view, final int position) {


        final CadastroVisitaFragment cadastroFragmente = CadastroVisitaFragment.newInstance(mAdapter.getItemVisita(position).getId(),R.layout.modelo_lista_recyclerview_agendamento);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, cadastroFragmente)
                .commit();


    }
}
