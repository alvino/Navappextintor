package com.alvino.mavappextintor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alvino.mavappextintor.domain.Visita;
import com.alvino.mavappextintor.bancodados.VisitaProvider;

import java.util.Date;

public class CadastroVisitaFragment extends Fragment {

    private static final String ID = "id";
    private static final String LAYOUT = "layout";

    private Long mId = null;
    private Integer  mLayout = null;


    private EditText etManutenido;
    private EditText etObs;
    private Button btCancelar;
    private Button btSalvar;

    private View.OnClickListener clickeListener;


    public static CadastroVisitaFragment newInstance(Long id, int layout) {
        CadastroVisitaFragment fragment = new CadastroVisitaFragment();
        Bundle args = new Bundle();
        if (id != null) args.putLong(ID, id);
        args.putInt(LAYOUT,layout);
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
            mLayout = getArguments().getInt(LAYOUT);
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


                chagerFragment();
            }
        });
        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chagerFragment();
            }
        });
        return v;
    }

    void chagerFragment(){
        Fragment fragment = null;
        switch (mLayout){
            case R.layout.modelo_lista_recyclerview_todos_agendamento:
                fragment = new ListaTodosAgendadosFragment();
                break;
            case R.layout.modelo_lista_recyclerview_agendamento:
                fragment = new ListaAgendamentoFragment();
                break;
        }
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment)
                .commit();
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
