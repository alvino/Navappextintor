package com.alvino.mavappextintor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alvino.mavappextintor.adapter.AdapterListaAgenda;
import com.alvino.mavappextintor.bancodados.BDAgendamento;
import com.alvino.mavappextintor.bancodados.entity.AgendamentoEntity;

import java.util.ArrayList;

public class ListaAgendamentoFragment extends Fragment {


    private ListView listaView = null;
    private ArrayList<AgendamentoEntity> agendamentos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_lista_agendamento, container, false);

        listaView = (ListView) rootView.findViewById(R.id.listViewAgendamento);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        getActivity().setTitle(getResources().getString(R.string.title_actionbar_lista_agendamento));

        agendamentos = null;
        BDAgendamento bd = new BDAgendamento(getActivity().getApplicationContext());
        agendamentos = (ArrayList<AgendamentoEntity>) bd.buscarTodos(0);
        listaView.setAdapter(new AdapterListaAgenda(getFragmentManager(), getActivity().getApplicationContext(), android.R.layout.simple_list_item_2, agendamentos));
    }


}
