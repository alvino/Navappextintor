package com.alvino.mavappextintor.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alvino.mavappextintor.CadastroClienteFragment;
import com.alvino.mavappextintor.R;
import com.alvino.mavappextintor.bancodados.BDAgendamento;
import com.alvino.mavappextintor.bancodados.BDCliente;
import com.alvino.mavappextintor.bancodados.entity.AgendamentoEntity;
import com.alvino.mavappextintor.bancodados.entity.ClienteEntity;
import com.alvino.mavappextintor.dialog.AlertDialogFragment;
import com.alvino.mavappextintor.dialog.DateDialog;


import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class AdapterListaCliente extends ArrayAdapter<ClienteEntity> {

    private Context context = null;
    private FragmentManager supportFragmentManager;
    private Date data;


    public AdapterListaCliente(FragmentManager fragmentManager, Context context, int resource, List<ClienteEntity> items) {
        super(context, resource, items);
        supportFragmentManager = fragmentManager;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.modelo_lista_cliente, null);
        }

        TextView txtValor1 = (TextView) view.findViewById(R.id.textView1);
        ImageButton remover = (ImageButton) view.findViewById(R.id.remover);
        ImageButton editar = (ImageButton) view.findViewById(R.id.editar);
        ImageButton agendamento = (ImageButton) view.findViewById(R.id.agendamento);

        //final ClienteEntity clienteEntity = itens.get(position);
        final ClienteEntity clienteEntity = getItem(position);

        txtValor1.setText(clienteEntity.getNome());

        remover.setOnClickListener(new OnClickListener() {


            public void onClick(View v) {

                List<AgendamentoEntity> buscaPorCliente = new BDAgendamento(getContext()).buscaPorCliente(clienteEntity.getId());
                if (buscaPorCliente.size() != 0) {
                    Toast to = Toast.makeText(getContext(), "Cliente possui dados de agendamento", Toast.LENGTH_SHORT);
                    to.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
                    to.show();
                }

                DialogInterface.OnClickListener ok = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removerCliente(clienteEntity);
                        remove(clienteEntity);
                    }
                };

                DialogInterface.OnClickListener cancelar = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                };

                AlertDialogFragment dialogFragment = new AlertDialogFragment("", "Remover o usuario", ok, cancelar);
                dialogFragment.show(supportFragmentManager, "ALERTDIALOGFRAGMENT");

            }

        });

        editar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                Fragment fragment = new CadastroClienteFragment().newInstance(
                        clienteEntity.getId(),
                        clienteEntity.getNome(),
                        clienteEntity.getEmail(),
                        clienteEntity.getTelefone(),
                        clienteEntity.getEndereco()
                );
                supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack("AdapterListaCliente")
                        .commit();


            }
        });

        agendamento.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener setListener = new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        data = calendar.getTime();

                        String msg = "";

                        AgendamentoEntity agendamento = new AgendamentoEntity();
                        agendamento.setClienteId(clienteEntity.getId());
                        agendamento.setData(data);

                        BDAgendamento bd = new BDAgendamento(getContext());
                        bd.inserir(agendamento);

                        msg = "Salvo com sucesso. ";

                        Toast toast = Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                };


                DateDialog dateDialog = new DateDialog(setListener);
                dateDialog.show(supportFragmentManager, "DATEDIALOG");
            }
        });


        return view;
    }


    private void removerCliente(ClienteEntity cliente) {

        List<AgendamentoEntity> buscaPorCliente = new BDAgendamento(getContext()).buscaPorCliente(cliente.getId());
        if (buscaPorCliente.size() != 0) {
            for (AgendamentoEntity agendamento : buscaPorCliente) {
                new BDAgendamento(getContext()).deletar(agendamento);
            }
        }
        String texto = "Removido o(s) agendamento(s) e o cliente: " + cliente.getNome() + " com sucesso.";
        new BDCliente(getContext()).deletar(cliente);

        Toast to = Toast.makeText(getContext(), texto, Toast.LENGTH_SHORT);
        to.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        to.show();
    }

}
