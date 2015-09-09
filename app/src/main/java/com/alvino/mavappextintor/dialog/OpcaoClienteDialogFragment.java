package com.alvino.mavappextintor.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alvino.mavappextintor.R;
import com.alvino.mavappextintor.bancodados.ClienteProvider;
import com.alvino.mavappextintor.inteface.RecyclerViewOnClickListener;
import com.github.clans.fab.FloatingActionButton;

/**
 * Created by alvino on 08/09/15.
 */
public class OpcaoClienteDialogFragment extends DialogFragment implements View.OnClickListener {

    private static final String ID = "id";
    private static final String POSICAO = "posicao";
    private RecyclerViewOnClickListener mRecyclerViewOnClickListener;
    private long mId;
    private FloatingActionButton fbExtintor;
    private FloatingActionButton fbEditar;
    private FloatingActionButton fbDeletar;
    private FloatingActionButton fbAgendar;
    private int mPosicao;


    public static OpcaoClienteDialogFragment newInstance(long id, int posicao) {

        OpcaoClienteDialogFragment dialogFragment = new OpcaoClienteDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putLong(ID, id);
        args.putInt(POSICAO, posicao);
        dialogFragment.setArguments(args);

        return dialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mId = getArguments().getLong(ID);
            mPosicao = getArguments().getInt(POSICAO);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_opcoe_cliente, container, false);

        fbExtintor = (FloatingActionButton) v.findViewById(R.id.fab_extintor_cliente);
        fbEditar = (FloatingActionButton) v.findViewById(R.id.fab_editar_cliente);
        fbDeletar = (FloatingActionButton) v.findViewById(R.id.fab_deletar_cliente);
        fbAgendar = (FloatingActionButton) v.findViewById(R.id.fab_agendar_cliente);

        fbAgendar.setOnClickListener(this);
        fbDeletar.setOnClickListener(this);
        fbEditar.setOnClickListener(this);
        fbExtintor.setOnClickListener(this);

        getDialog().setTitle(new ClienteProvider(getActivity()).get(mId).getNome_fantazia());
        return v;
    }

    public void setRecyclerViewOnClickListener(RecyclerViewOnClickListener mRecyclerViewOnClickListener) {
        this.mRecyclerViewOnClickListener = mRecyclerViewOnClickListener;
    }

    @Override
    public void onClick(View v) {
        if (mRecyclerViewOnClickListener != null) {
            mRecyclerViewOnClickListener.onClickListener(v, mPosicao);
            getDialog().cancel();
        } else {
            Toast.makeText(getActivity(),"RecyclerViewOnClickListener null.",Toast.LENGTH_SHORT).show();
        }

    }
}
