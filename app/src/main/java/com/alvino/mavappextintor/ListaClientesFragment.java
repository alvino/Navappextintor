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
import android.widget.DatePicker;
import android.widget.Toast;

import com.alvino.mavappextintor.adapter.ClienteAdapter;
import com.alvino.mavappextintor.domain.Cliente;
import com.alvino.mavappextintor.bancodados.ClienteProvider;
import com.alvino.mavappextintor.domain.Visita;
import com.alvino.mavappextintor.bancodados.VisitaProvider;
import com.alvino.mavappextintor.dialog.AlertDialogFragment;
import com.alvino.mavappextintor.dialog.DateDialog;
import com.alvino.mavappextintor.inteface.RecyclerViewOnClickListener;
import com.github.clans.fab.FloatingActionButton;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ListaClientesFragment extends Fragment implements RecyclerViewOnClickListener {

    private View v;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private List<Cliente> mDataSet;
    private ClienteAdapter mAdapter;
    private Date data;
    private Fragment fragment;
    private FloatingActionButton fab;
    //private Button btCadastro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle(getResources().getString(R.string.title_actionbar_lista_cliente));

        v = inflater.inflate(R.layout.fragment_recycler_view_lista_com_floating, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_lista);

        fab = (FloatingActionButton) v.findViewById(R.id.fab_bt);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new CadastroClienteFragment();

                getFragmentManager().beginTransaction().replace(R.id.container, fragment)
                        .commit();
            }
        });

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0){
                    fab.hide(true);;
                }
                else{
                    fab.show(true);
                }

            }
        });

        ClienteProvider db = new ClienteProvider(getActivity().getApplicationContext());
        mDataSet = db.all();

        mAdapter = new ClienteAdapter(getFragmentManager(),getActivity(), mDataSet);
        mAdapter.setRecyclerViewOnClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }


    @Override
    public void onClickListener(View view, final int position) {
        final Cliente c = mAdapter.getItemCliente(position);


        switch (view.getId()) {

            case R.id.fab_agendar_cliente:
                final Cliente cliente = c;
                DatePickerDialog.OnDateSetListener setListener = new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        data = calendar.getTime();

                        String msg = "";

                        Visita v = new Visita();
                        v.setCliente(cliente.getId());
                        v.setData_agendada(data);
                        v.setData_criacao(new Date());
                        v.setAtendido("nao");

                        VisitaProvider bd = new VisitaProvider(getActivity());
                        bd.insert(v);

                        msg = "Salvo com sucesso. ";

                        Toast toast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                };


                DateDialog dateDialog = new DateDialog(setListener);
                dateDialog.show(getFragmentManager(), "DATEDIALOG");

                break;

            case R.id.fab_editar_cliente:


                fragment = new CadastroClienteFragment().newInstance(
                        c.getId(),
                        c.getNome_fantazia(),
                        c.getProprietario(),
                        c.getResponsavel(),
                        c.getFone(),
                        c.getEmail(),
                        c.getEndereco()
                );

                getFragmentManager().beginTransaction().replace(R.id.container, fragment)
                        .addToBackStack("ListaCliente")
                        .commit();

                break;
            case R.id.fab_deletar_cliente:
                List<Visita> visitaList = (List<Visita>) new VisitaProvider(getActivity()).allCliente(c.getId());
                if (visitaList.size() != 0) {
                    Toast to = Toast.makeText(getActivity(), "Cliente possui dados de agendamento", Toast.LENGTH_SHORT);
                    to.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
                    to.show();
                }

                DialogInterface.OnClickListener ok = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List<Visita> visitaList1 = (List<Visita>) new VisitaProvider(getActivity()).allCliente(c.getId());
                        if (visitaList1.size() != 0) {
                            for (Visita agendamento : visitaList1) {
                                new VisitaProvider(getActivity()).delete(agendamento);
                            }
                        }
                        String texto = "Removido o(s) agendamento(s) e o cliente: " + c.getNome_fantazia() + " com sucesso.";
                        new ClienteProvider(getActivity()).delete(c);

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

                AlertDialogFragment alertDialogFragment = new AlertDialogFragment("", "Remover o usuario", ok, cancelar);
                alertDialogFragment.show(getFragmentManager(), "ALERTDIALOGFRAGMENT");

                break;
            case R.id.fab_extintor_cliente:

                fragment = new ListaExtintorFragment().newInstance(c.getId());

                getFragmentManager().beginTransaction().replace(R.id.container, fragment)
                        .commit();
                break;

        }

    }

}
