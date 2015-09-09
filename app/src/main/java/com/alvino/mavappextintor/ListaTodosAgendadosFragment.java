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

import java.util.List;


public class ListaTodosAgendadosFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private List<Visita> mDataSet;
    private TodosAgendamentoAdapter mAdpater;

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

        mAdpater = new TodosAgendamentoAdapter(getActivity(),mDataSet);
        mRecyclerView.setAdapter(mAdpater);

        return v;
    }

}
