package com.alvino.mavappextintor.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alvino.mavappextintor.R;
import com.alvino.mavappextintor.bancodados.entity.AgendamentoEntity;
import com.alvino.mavappextintor.bancodados.entity.ClienteEntity;
import com.alvino.mavappextintor.inteface.RecyclerViewOnClickListener;

import java.util.List;

/**
 * Created by alvino on 19/08/15.
 */
public class ClienteAdapter  extends RecyclerView.Adapter<ClienteAdapter.ViewHolder> {

    private final List<ClienteEntity> mDataSet;
    private final Context context;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListener mRecyclerViewOnClickListener = null;

    public ClienteAdapter(Context activity, List<ClienteEntity> mDataSet) {
        this.mDataSet = mDataSet;
        this.mLayoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = activity;
    }

    @Override
    public ClienteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.modelo_lista_recyclerview_cliente,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ClienteAdapter.ViewHolder holder, int position) {
        ClienteEntity entity = mDataSet.get(position);
        holder.tvName.setText(entity.getNome());
    }

    public void setmRecyclerViewOnClickListener(RecyclerViewOnClickListener mRecyclerViewOnClickListener) {
        this.mRecyclerViewOnClickListener = mRecyclerViewOnClickListener;
    }

    public ClienteEntity getItemEntity(int position) {
        return mDataSet.get(position);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void removeItem(int position) {
        mDataSet.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvName;
        public ImageButton ibRemove;
        public ImageButton ibAgendar;
        public ImageButton ibEditar;

        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tv_nome_lista_cliente);
            ibRemove = (ImageButton) itemView.findViewById(R.id.ib_remover_lista_cliente);
            ibEditar = (ImageButton) itemView.findViewById(R.id.ib_editar_lista_cliente);
            ibAgendar = (ImageButton) itemView.findViewById(R.id.ib_agendamento_lista_cliente);

            ibAgendar.setOnClickListener(this);
            ibEditar.setOnClickListener(this);
            ibRemove.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mRecyclerViewOnClickListener.onClickListener(v,getAdapterPosition());
        }
    }

}
