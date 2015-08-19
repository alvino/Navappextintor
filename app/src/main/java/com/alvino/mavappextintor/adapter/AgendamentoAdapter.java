package com.alvino.mavappextintor.adapter;

import android.content.Context;
import android.content.DialogInterface;
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
        BDCliente db = new BDCliente(c);
        ClienteEntity cliente = db.buscarPorId(mDataSet.get(i).getClienteId());

        viewHolder.tvCliente.setText(cliente.getNome());
        viewHolder.tvData.setText(mDataSet.get(i).getformatData());
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
        public TextView tvCliente;
        public TextView tvData;

        public ViewHolder(View itemView) {
            super(itemView);


            tvCliente = (TextView) itemView.findViewById(R.id.tv_cliente);
            tvData    = (TextView) itemView.findViewById(R.id.tv_data);

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
