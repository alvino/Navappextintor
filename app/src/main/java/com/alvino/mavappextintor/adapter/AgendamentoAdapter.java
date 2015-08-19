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
import com.alvino.mavappextintor.inteface.RecyclerViewOnClickListener;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by alvino on 18/08/15.
 */
public class AgendamentoAdapter extends RecyclerView.Adapter<AgendamentoAdapter.ViewHolder> {

    private ArrayList<AgendamentoEntity> mDataSet;
    private LayoutInflater mLayoutInflater;
    private Context c;
    private RecyclerViewOnClickListener mRecyclerViewOnClickListener = null;

    public AgendamentoAdapter(Context c,ArrayList<AgendamentoEntity> mDataSet) {
        this.mDataSet = mDataSet;
        this.mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.c = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = mLayoutInflater.inflate(R.layout.modelo_lista_recyclerview_agendamento,viewGroup, false);
        ViewHolder mViewHolder = new ViewHolder(v);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        AgendamentoEntity agendamento = mDataSet.get(i);

        Date hoje = new Date();
        long milissegundos = agendamento.getData().getTime() - hoje.getTime();
        long dias = milissegundos / (24 * 60 * 60 * 1000);

        BDCliente db = new BDCliente(c);

        ClienteEntity cliente = db.buscarPorId(mDataSet.get(i).getClienteId());

        viewHolder.tvNome.setText(cliente.getNome());
        viewHolder.tvData.setText(agendamento.getformatData());

        if ((dias > -1.0) && (dias < 15.0)) {
            viewHolder.tvNome.setTextColor(Color.rgb(235, 27, 36));
            viewHolder.tvData.setTextColor(Color.rgb(235, 27, 36));
        } else if(dias < 0.0) {
            viewHolder.tvNome.setTextColor(Color.rgb(255, 138, 128));
            viewHolder.tvData.setTextColor(Color.rgb(255, 138, 128));
        }
    }
    
    public void setRecyclerViewOnClickListener(RecyclerViewOnClickListener clickListener){
        this.mRecyclerViewOnClickListener = clickListener;
    }

    @Override
    public int getItemCount() {

        return mDataSet.size();
    }

    public AgendamentoEntity getItemEntity(int position) {
        return mDataSet.get(position);
    }

    public void removeItem(int position) {
        mDataSet.remove(position);
        notifyItemRemoved(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tvNome;
        public TextView tvData;

        public ViewHolder(View itemView) {
            super(itemView);


            tvNome = (TextView) itemView.findViewById(R.id.tv_nome_agendamento);
            tvData    = (TextView) itemView.findViewById(R.id.tv_data_agendamento);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if(mRecyclerViewOnClickListener != null) {
                mRecyclerViewOnClickListener.onClickListener(v, getPosition());
            }
        }
    }
}
