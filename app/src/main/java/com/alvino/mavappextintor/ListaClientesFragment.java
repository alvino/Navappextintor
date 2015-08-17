package com.alvino.mavappextintor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alvino.mavappextintor.adapter.AdapterListaCliente;
import com.alvino.mavappextintor.bancodados.BDCliente;
import com.alvino.mavappextintor.bancodados.entity.ClienteEntity;

import java.util.ArrayList;


public class ListaClientesFragment extends Fragment {

    ListView listaViewCliente = null;
    private View raiz = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        raiz = inflater.inflate(R.layout.fragment_lista_clientes, container, false);
        listaViewCliente = (ListView) raiz.findViewById(R.id.listViewCliente);
        return raiz;
    }


    @Override
    public void onStart() {
        super.onStart();

        getActivity().setTitle(getResources().getString(R.string.title_actionbar_lista_cliente));

        BDCliente db = new BDCliente(getActivity().getApplicationContext());
        ArrayList<ClienteEntity> clientes = (ArrayList<ClienteEntity>) db.buscarTodos();
        listaViewCliente.setAdapter(new AdapterListaCliente(getActivity().getSupportFragmentManager(), getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_2, clientes));

    }


}
