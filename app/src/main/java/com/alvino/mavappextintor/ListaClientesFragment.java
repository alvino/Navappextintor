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
import com.alvino.mavappextintor.bancodados.BDAgendamento;
import com.alvino.mavappextintor.bancodados.BDCliente;
import com.alvino.mavappextintor.bancodados.entity.AgendamentoEntity;
import com.alvino.mavappextintor.bancodados.entity.ClienteEntity;
import com.alvino.mavappextintor.dialog.AlertDialogFragment;
import com.alvino.mavappextintor.dialog.DateDialog;
import com.alvino.mavappextintor.inteface.RecyclerViewOnClickListener;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ListaClientesFragment extends Fragment implements RecyclerViewOnClickListener {

    //ListView listaViewCliente = null;
    private View v;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private List<ClienteEntity> mDataSet;
    private ClienteAdapter mAdapter;
    private Date data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle(getResources().getString(R.string.title_actionbar_lista_cliente));

        v = inflater.inflate(R.layout.fragment_recycler_view_lista, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_lista);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        BDCliente db = new BDCliente(getActivity().getApplicationContext());
        mDataSet = db.buscarTodos();

        mAdapter = new ClienteAdapter(getActivity(), mDataSet);
        mAdapter.setmRecyclerViewOnClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        return v;
    }


    @Override
    public void onClickListener(View view, final int position) {
        final ClienteEntity entity = mAdapter.getItemEntity(position);

        switch (view.getId()) {
            case R.id.ib_agendamento_lista_cliente:
                final ClienteEntity cliente = entity;
                DatePickerDialog.OnDateSetListener setListener = new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        data = calendar.getTime();

                        String msg = "";

                        AgendamentoEntity agendamento = new AgendamentoEntity();
                        agendamento.setClienteId(cliente.getId());
                        agendamento.setData(data);

                        BDAgendamento bd = new BDAgendamento(getActivity());
                        bd.inserir(agendamento);

                        msg = "Salvo com sucesso. ";

                        Toast toast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                };


                DateDialog dateDialog = new DateDialog(setListener);
                dateDialog.show(getFragmentManager(), "DATEDIALOG");

                break;
            case R.id.ib_editar_lista_cliente:


                Fragment fragment = new CadastroClienteFragment().newInstance(
                        entity.getId(),
                        entity.getNome(),
                        entity.getEmail(),
                        entity.getTelefone(),
                        entity.getEndereco()
                );

                getFragmentManager().beginTransaction().replace(R.id.container, fragment)
                        .addToBackStack("ListaCliente")
                        .commit();

                break;
            case R.id.ib_remover_lista_cliente:
                List<AgendamentoEntity> buscaPorCliente = new BDAgendamento(getActivity()).buscaPorCliente(entity.getId());
                if (buscaPorCliente.size() != 0) {
                    Toast to = Toast.makeText(getActivity(), "Cliente possui dados de agendamento", Toast.LENGTH_SHORT);
                    to.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
                    to.show();
                }

                DialogInterface.OnClickListener ok = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List<AgendamentoEntity> buscaPorCliente = new BDAgendamento(getActivity()).buscaPorCliente(entity.getId());
                        if (buscaPorCliente.size() != 0) {
                            for (AgendamentoEntity agendamento : buscaPorCliente) {
                                new BDAgendamento(getActivity()).deletar(agendamento);
                            }
                        }
                        String texto = "Removido o(s) agendamento(s) e o cliente: " + entity.getNome() + " com sucesso.";
                        new BDCliente(getActivity()).deletar(entity);

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

                AlertDialogFragment dialogFragment = new AlertDialogFragment("", "Remover o usuario", ok, cancelar);
                dialogFragment.show(getFragmentManager(), "ALERTDIALOGFRAGMENT");
                break;
        }

    }
}
