package com.alvino.mavappextintor.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alvino.mavappextintor.R;
import com.alvino.mavappextintor.bancodados.Cliente;
import com.alvino.mavappextintor.dialog.OpcaoClienteDialogFragment;
import com.alvino.mavappextintor.inteface.RecyclerViewOnClickListener;

import java.util.List;

/**
 * Created by alvino on 19/08/15.
 */
public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolder> {

    private final List<Cliente> mDataSet;
    private final Context context;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListener mRecyclerViewOnClickListener = null;
    private FragmentManager mFragmentManager;

    public ClienteAdapter(FragmentManager fragmentManager, Context activity, List<Cliente> mDataSet) {
        this.mDataSet = mDataSet;
        this.mLayoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = activity;
        this.mFragmentManager = fragmentManager;
    }

    @Override
    public ClienteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.modelo_lista_recyclerview_cliente_com_dialog, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ClienteAdapter.ViewHolder holder, int position) {
        Cliente c = mDataSet.get(position);
        holder.tvName.setText(c.getNome_fantazia());
    }

    public void setRecyclerViewOnClickListener(RecyclerViewOnClickListener mRecyclerViewOnClickListener) {
        this.mRecyclerViewOnClickListener = mRecyclerViewOnClickListener;
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void removeItem(int position) {
        mDataSet.remove(position);
        notifyItemRemoved(position);
    }

    public Cliente getItemCliente(int position) {
        return mDataSet.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvName;
        public ImageButton ibMenu;

        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tv_nome_lista_cliente);
            ibMenu = (ImageButton) itemView.findViewById(R.id.ibMenu);
            ibMenu.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mRecyclerViewOnClickListener != null) {
                int position = getAdapterPosition();
                Cliente c = mDataSet.get(position);
                OpcaoClienteDialogFragment opcaoClienteDialogFragment = OpcaoClienteDialogFragment.newInstance(c.getId(), position);
                opcaoClienteDialogFragment.setRecyclerViewOnClickListener(mRecyclerViewOnClickListener);
                opcaoClienteDialogFragment.show(mFragmentManager, "OpcaoClienteDialogFragment");
            }
        }
    }

}
