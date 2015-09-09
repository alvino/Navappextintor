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
import com.alvino.mavappextintor.bancodados.Extintor;
import com.alvino.mavappextintor.core.SimplesDataFormatada;
import com.alvino.mavappextintor.dialog.OpcaoClienteDialogFragment;
import com.alvino.mavappextintor.dialog.OpcaoExtintorDialogFragment;
import com.alvino.mavappextintor.inteface.RecyclerViewOnClickListener;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

/**
 * Created by alvino on 19/08/15.
 */
public class ExtintorAdapter extends RecyclerView.Adapter<ExtintorAdapter.ViewHolder> {

    private final List<Extintor> mDataSet;
    private final Context context;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListener mRecyclerViewOnClickListener = null;
    private FragmentManager mFragmentManager;

    public ExtintorAdapter(FragmentManager fragmentManager, Context activity, List<Extintor> mDataSet) {
        this.mDataSet = mDataSet;
        this.mLayoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = activity;
        this.mFragmentManager = fragmentManager;
    }

    @Override
    public ExtintorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.modelo_lista_recyclerview_extintor_com_dialog, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ExtintorAdapter.ViewHolder holder, int position) {
        Extintor e = mDataSet.get(position);

        holder.tvTipo.setText(e.getTipo());
        holder.tvData.setText(SimplesDataFormatada.formatar(e.getData_validade(),SimplesDataFormatada.MYY));

    }

    public void setmRecyclerViewOnClickListener(RecyclerViewOnClickListener mRecyclerViewOnClickListener) {
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

    public Extintor getItemExtintor(int position) {
        return mDataSet.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView tvTipo;
        public TextView tvData;
        private ImageButton ibMenu;


        public ViewHolder(View itemView) {
            super(itemView);

            tvTipo = (TextView) itemView.findViewById(R.id.tv_tipo_lista_extintor);
            tvData = (TextView) itemView.findViewById(R.id.tv_data_lista_extintor);
            ibMenu = (ImageButton) itemView.findViewById(R.id.ibMenu);


            ibMenu.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mRecyclerViewOnClickListener != null) {
                int position = getAdapterPosition();
                Extintor e = mDataSet.get(position);

                OpcaoExtintorDialogFragment opcaoExtintorDialogFragment = OpcaoExtintorDialogFragment.newInstance(e.getId(), position);
                opcaoExtintorDialogFragment.setRecyclerViewOnClickListener(mRecyclerViewOnClickListener);
                opcaoExtintorDialogFragment.show(mFragmentManager, "OpcaoExtintorDialogFragment");
            }
        }
    }

}
