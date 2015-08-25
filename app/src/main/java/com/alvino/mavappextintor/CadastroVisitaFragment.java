package com.alvino.mavappextintor;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alvino.mavappextintor.bancodados.Extintor;
import com.alvino.mavappextintor.bancodados.ExtintorProvider;
import com.alvino.mavappextintor.bancodados.Visita;
import com.alvino.mavappextintor.bancodados.VisitaProvider;
import com.alvino.mavappextintor.core.SimplesDataFormatada;
import com.alvino.mavappextintor.dialog.DateDialog;

import java.util.Date;

public class CadastroVisitaFragment extends Fragment {

    private static final String ID = "id";

    private Long mId = null;


    private EditText etManutenido;
    private EditText etObs;
    private Button btCancelar;
    private Button btSalvar;

    private View.OnClickListener clickeListener;


    public static CadastroVisitaFragment newInstance(Long id) {
        CadastroVisitaFragment fragment = new CadastroVisitaFragment();
        Bundle args = new Bundle();
        if (id != null) args.putLong(ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            if (getArguments().getLong(ID) == 0) {
                mId = null;
            } else {
                mId = getArguments().getLong(ID);
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_fragment_visita,container,false);
        etManutenido = (EditText) v.findViewById(R.id.etManutenido_dialog_visita);
        etObs = (EditText) v.findViewById(R.id.etObs_dialog_visita);

        btCancelar = (Button) v.findViewById(R.id.cancelar_dialog_visita);
        btSalvar = (Button) v.findViewById(R.id.salvar_dialog_visita);
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Visita visita = new VisitaProvider(getActivity()).get(mId);
                visita.setData_atendimento(new Date());
                visita.setAtendido("sim");
                visita.setManutenido(etManutenido.getText().toString().toUpperCase());
                visita.setObs(etObs.getText().toString());

                new VisitaProvider(getActivity().getApplication().getApplicationContext()).upgrade(visita);

                Toast.makeText(getActivity(), "Visita salva na data: " + visita.getData_atendimento(), Toast.LENGTH_SHORT).show();


                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new ListaAgendamentoFragment())
                        .commit();
            }
        });
        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new ListaAgendamentoFragment())
                        .commit();
            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getArguments() != null) {

            Visita v = new VisitaProvider(getActivity()).get(mId);

            etManutenido.setText(v.getManutenido());
            etObs.setText(v.getObs());
        }


    }

}
