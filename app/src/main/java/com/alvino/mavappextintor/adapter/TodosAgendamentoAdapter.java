package com.alvino.mavappextintor.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alvino.mavappextintor.R;
import com.alvino.mavappextintor.bancodados.Cliente;
import com.alvino.mavappextintor.bancodados.ClienteProvider;
import com.alvino.mavappextintor.bancodados.Visita;

import java.util.List;

/**
 * Created by alvino on 19/08/15.
 */
public class TodosAgendamentoAdapter extends RecyclerView.Adapter<TodosAgendamentoAdapter.ViewHolder> {

    private final List<Visita> mDataSet;
    private final LayoutInflater mLayoutInflate;
    private final Context context;

    public TodosAgendamentoAdapter(Context c, List<Visita> mDataSet) {
        this.mDataSet = mDataSet;
        this.mLayoutInflate = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflate.inflate(R.layout.modelo_lista_recyclerview_todos_agendamento, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Visita visita = mDataSet.get(position);

        Cliente cliente = new ClienteProvider(context).get(visita.getCliente());

        holder.tvNome.setText(cliente.getNome_fantazia());
        if (visita.getData_agendada() != null) {
            holder.tvData.setText(visita.getData_agendada());
        } else {
            holder.tvData.setText("Visitado " + visita.getData_atendimento());
            holder.tvData.setTextSize(14);
            holder.tvNome.setTextColor(Color.rgb(235, 27, 36));
            holder.tvData.setTextColor(Color.rgb(235, 27, 36));
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNome;
        public TextView tvData;

        public ViewHolder(View itemView) {
            super(itemView);

            tvNome = (TextView) itemView.findViewById(R.id.tv_nome_todo_agendamento);
            tvData = (TextView) itemView.findViewById(R.id.tv_data_todo_agendamento);
        }
    }
}
