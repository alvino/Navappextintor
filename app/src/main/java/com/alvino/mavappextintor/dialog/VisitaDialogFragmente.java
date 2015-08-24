package com.alvino.mavappextintor.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.alvino.mavappextintor.R;
import com.alvino.mavappextintor.bancodados.Visita;
import com.alvino.mavappextintor.bancodados.VisitaProvider;

/**
 * Created by alvino on 24/08/15.
 */
public class VisitaDialogFragmente extends DialogFragment {

    private static final String ID = "id";

    private EditText etManutenido;
    private EditText etObs;
    private Button btCancelar;
    private Button btSalvar;

    private long mId;
    private View.OnClickListener clickeListener;

    public static VisitaDialogFragmente newInstance(Long l) {
        VisitaDialogFragmente f = new VisitaDialogFragmente();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putLong(ID, l);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            mId = getArguments().getLong(ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_fragment_visita,container,false);
        etManutenido = (EditText) v.findViewById(R.id.etManutenido_dialog_visita);
        etObs = (EditText) v.findViewById(R.id.etObs_dialog_visita);

        btCancelar = (Button) v.findViewById(R.id.cancelar_dialog_visita);
        btSalvar = (Button) v.findViewById(R.id.salvar_dialog_visita);
        return v;

    }

    public void setOnClickListener(View.OnClickListener clickListener){
        this.clickeListener = clickListener;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getArguments() != null) {

            Visita v = new VisitaProvider(getActivity()).get(mId);

            etManutenido.setText(v.getManutenido());
            etObs.setText(v.getObs());
        }

        if( clickeListener != null){
            btSalvar.setOnClickListener(clickeListener);
            btCancelar.setOnClickListener(clickeListener);
        }

    }

    public String getManutenido() {
        return etManutenido.getText().toString();
    }

    public String getObs() {
        return etObs.getText().toString();
    }
}
