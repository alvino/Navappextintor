package com.alvino.mavappextintor.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alvino.mavappextintor.R;
import com.alvino.mavappextintor.domain.Cliente;
import com.alvino.mavappextintor.bancodados.ClienteProvider;
import com.alvino.mavappextintor.domain.Visita;
import com.alvino.mavappextintor.core.SimplesDataFormatada;
import com.alvino.mavappextintor.inteface.RecyclerViewOnClickListener;

import java.util.List;

/**
 * Created by alvino on 18/08/15.
 */
public class AgendamentoAdapter extends RecyclerView.Adapter<AgendamentoAdapter.ViewHolder> {

    private List<Visita> mDataSet;
    private LayoutInflater mLayoutInflater;
    private Context context;
    private RecyclerViewOnClickListener mRecyclerViewOnClickListener = null;

    public AgendamentoAdapter(Context c,List<Visita> mDataSet) {
        this.mDataSet = mDataSet;
        this.mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = mLayoutInflater.inflate(R.layout.modelo_lista_recyclerview_agendamento,viewGroup, false);
        ViewHolder mViewHolder = new ViewHolder(v);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Visita v = mDataSet.get(i);

        ClienteProvider db = new ClienteProvider(context);

        Cliente cliente = db.get(mDataSet.get(i).getCliente());

        viewHolder.tvNome.setText(cliente.getNome_fantazia());
        viewHolder.tvData.setText(SimplesDataFormatada.formatar(v.getData_agendada(), SimplesDataFormatada.DDMYYYY));

        if (v.isPaint()) {
            viewHolder.tvNome.setTextColor(Color.rgb(235, 27, 36));
            viewHolder.tvData.setTextColor(Color.rgb(235, 27, 36));
        }
    }
    
    public void setRecyclerViewOnClickListener(RecyclerViewOnClickListener clickListener){
        this.mRecyclerViewOnClickListener = clickListener;
    }

    @Override
    public int getItemCount() {

        return mDataSet.size();
    }

    public Visita getItemVisita(int position) {
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
