package com.alvino.mavappextintor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alvino.mavappextintor.adapter.AdapterTodosAgendado;
import com.alvino.mavappextintor.bancodados.BDAgendamento;
import com.alvino.mavappextintor.bancodados.entity.AgendamentoEntity;

import java.util.ArrayList;


public class TodosAgendadosFragment extends Fragment {

    private ListView listaView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View raiz = inflater.inflate(R.layout.fragment_todos_agendados, container, false);
        listaView = (ListView) raiz.findViewById(R.id.listViewTodosAgendamento);

        return raiz;
    }

    @Override
    public void onStart() {
        super.onStart();

        getActivity().setTitle(getResources().getString(R.string.title_actionbar_todos_os_agendamento));

        BDAgendamento bd = new BDAgendamento(getActivity().getApplicationContext());
        ArrayList<AgendamentoEntity> agendamentos = (ArrayList<AgendamentoEntity>) bd.buscarTodos();
        listaView.setAdapter(new AdapterTodosAgendado(getActivity().getApplicationContext(), android.R.layout.simple_list_item_2, agendamentos));

    }

}
