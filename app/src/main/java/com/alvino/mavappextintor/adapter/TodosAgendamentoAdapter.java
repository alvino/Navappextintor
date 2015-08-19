package com.alvino.mavappextintor.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alvino.mavappextintor.R;
import com.alvino.mavappextintor.bancodados.BDCliente;
import com.alvino.mavappextintor.bancodados.entity.AgendamentoEntity;
import com.alvino.mavappextintor.bancodados.entity.ClienteEntity;

import java.util.List;

/**
 * Created by alvino on 19/08/15.
 */
public class TodosAgendamentoAdapter extends RecyclerView.Adapter<TodosAgendamentoAdapter.ViewHolder> {

    private final List<AgendamentoEntity> mDataSet;
    private final LayoutInflater mLayoutInflate;
    private final Context context;

    public TodosAgendamentoAdapter(Context c, List<AgendamentoEntity> mDataSet) {
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
        AgendamentoEntity agendamento = mDataSet.get(position);
        BDCliente db = new BDCliente(context);
        ClienteEntity cliente = db.buscarPorId(agendamento.getClienteId());

        holder.tvNome.setText(cliente.getNome());
        if (agendamento.getVisitado() != 1) {
            holder.tvData.setText(agendamento.getformatData());
        } else {
            holder.tvData.setText("Visitado " + agendamento.getformatData());
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
