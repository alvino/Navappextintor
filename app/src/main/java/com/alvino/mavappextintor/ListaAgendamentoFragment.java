package com.alvino.mavappextintor;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alvino.mavappextintor.adapter.AgendamentoAdapter;
import com.alvino.mavappextintor.bancodados.Visita;
import com.alvino.mavappextintor.bancodados.VisitaProvider;
import com.alvino.mavappextintor.core.SimplesDataFormatada;
import com.alvino.mavappextintor.dialog.AlertDialogFragment;
import com.alvino.mavappextintor.dialog.VisitaDialogFragmente;
import com.alvino.mavappextintor.inteface.RecyclerViewOnClickListener;

import java.util.Date;
import java.util.List;

public class ListaAgendamentoFragment extends Fragment implements RecyclerViewOnClickListener {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private AgendamentoAdapter mAdapter;
    private List<Visita> mDataSet;

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

        VisitaProvider bd = new VisitaProvider(getActivity().getApplicationContext());
        mDataSet = (List<Visita>) bd.allNotAtendido();

        mAdapter = new AgendamentoAdapter(getActivity(),mDataSet);
        mAdapter.setRecyclerViewOnClickListener(this);
        mRecyclerView.setAdapter(mAdapter);


        //listaView = (ListView) rootView.findViewById(R.id.listViewAgendamento);
        return rootView;
    }



    @Override
    public void onClickListener(View view, final int position) {



        final VisitaDialogFragmente dialogFragmente = VisitaDialogFragmente.newInstance(mAdapter.getItemVisita(position).getId());
        dialogFragmente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.cancelar_dialog_visita:
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        Fragment fragment = new ListaAgendamentoFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, fragment)
                                .commit();
                        break;

                    case R.id.salvar_dialog_visita:

                        Visita visita = mAdapter.getItemVisita(position);
                        visita.setData_atendimento( SimplesDataFormatada.formatar( new Date() ) );
                        visita.setManutenido(dialogFragmente.getManutenido());
                        visita.setObs(dialogFragmente.getObs());

                        new VisitaProvider(getActivity()).upgrade(visita);

                        Toast.makeText(getActivity(),"Visita salva na data: "+ visita.getData_atendimento(),Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        /*
        DialogInterface.OnClickListener ok = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Visita v = mAdapter.getItemVisita(position);
                v.setData_atendimento( SimplesDataFormatada.formatar(new Date()) );
                new VisitaProvider(getActivity()).upgrade(v);
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
        */
    }
}
