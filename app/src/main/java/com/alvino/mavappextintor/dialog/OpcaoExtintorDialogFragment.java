package com.alvino.mavappextintor.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alvino.mavappextintor.R;
import com.alvino.mavappextintor.bancodados.ExtintorProvider;
import com.alvino.mavappextintor.inteface.RecyclerViewOnClickListener;
import com.github.clans.fab.FloatingActionButton;

/**
 * Created by alvino on 08/09/15.
 */
public class OpcaoExtintorDialogFragment extends DialogFragment implements View.OnClickListener {

    private static final String ID = "id";
    private static final String POSICAO = "posicao";
    private RecyclerViewOnClickListener mRecyclerViewOnClickListener;
    private long mId;
    private FloatingActionButton fbExtintor;
    private FloatingActionButton fbEditar;
    private FloatingActionButton fbDeletar;
    private FloatingActionButton fbAgendar;
    private int mPosicao;


    public static OpcaoExtintorDialogFragment newInstance(long id, int posicao) {

        OpcaoExtintorDialogFragment dialogFragment = new OpcaoExtintorDialogFragment();

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
        View v = inflater.inflate(R.layout.dialog_opcoe_extintor, container, false);

        fbEditar = (FloatingActionButton) v.findViewById(R.id.fab_editar_extintor);
        fbDeletar = (FloatingActionButton) v.findViewById(R.id.fab_deletar_extintor);


        fbDeletar.setOnClickListener(this);
        fbEditar.setOnClickListener(this);

        getDialog().setTitle(new ExtintorProvider(getActivity()).get(mId).getTipo());
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
        }

    }
}
