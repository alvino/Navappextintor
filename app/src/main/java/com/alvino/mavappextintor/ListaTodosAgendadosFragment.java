package com.alvino.mavappextintor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alvino.mavappextintor.adapter.TodosAgendamentoAdapter;
import com.alvino.mavappextintor.domain.Visita;
import com.alvino.mavappextintor.bancodados.VisitaProvider;
import com.alvino.mavappextintor.inteface.RecyclerViewOnClickListener;

import java.util.Collections;
import java.util.List;


public class ListaTodosAgendadosFragment extends Fragment implements RecyclerViewOnClickListener {


    private RecyclerView mRecyclerView;
    private List<Visita> mDataSet;
    private TodosAgendamentoAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle(getResources().getString(R.string.title_actionbar_todos_os_agendamento));

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recycler_view_lista, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_lista);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mDataSet = (List<Visita>) new VisitaProvider(getActivity()).all();

        Collections.sort(mDataSet);

        mAdapter = new TodosAgendamentoAdapter(getActivity(),mDataSet);
        mAdapter.setRecyclerViewOnClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }

    @Override
    public void onClickListener(View view, final int position) {


        final CadastroVisitaFragment cadastroFragmente = CadastroVisitaFragment.newInstance(mAdapter.getItemVisita(position).getId(), R.layout.modelo_lista_recyclerview_todos_agendamento);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, cadastroFragmente)
                .commit();


    }

}
