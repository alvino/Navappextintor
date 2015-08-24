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
import com.alvino.mavappextintor.dialog.DateDialog;

import java.util.Date;

public class CadastroExtintorFragment extends Fragment {

    private static final String ID = "id";
    private static final String CLIENTE = "cliente";
    private static final String TIPO = "tipo";
    private static final String DATA_VALIDADE = "data_validade";

    private Long mId;
    private Long mCliente;
    private String mTipo;
    private String mData_validade;



    private EditText txtData_validade;
    private EditText txtTipo;
    private ImageButton ibCalendario;

    private Button cancelar = null;
    private Button salvar = null;

    private boolean flagAtualizar = false;
    private Date data;


    public static CadastroExtintorFragment newInstance(Long id, Long cliente ,String tipo, String data_validade) {
        CadastroExtintorFragment fragment = new CadastroExtintorFragment();
        Bundle args = new Bundle();
        if(id != null) args.putLong(ID, id);
        args.putLong(CLIENTE, cliente);
        if(tipo != null) args.putString(TIPO, tipo);
        if(data_validade != null) args.putString(DATA_VALIDADE,data_validade);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mId = getArguments().getLong(ID);
            mCliente = getArguments().getLong(CLIENTE);
            mTipo = getArguments().getString(TIPO);
            mData_validade = getArguments().getString(DATA_VALIDADE);
            flagAtualizar = true;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cadastro_extintor, container, false);

        txtTipo = (EditText) v.findViewById(R.id.txtTipo_cadastro_extintor);
        txtData_validade = (EditText) v.findViewById(R.id.txtData_validade_cadastro_extintor);
        ibCalendario = (ImageButton) v.findViewById(R.id.ib_calendario_cadastro_extintor);


        ibCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener setListener = new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        txtData_validade.setText("" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                };

                DateDialog dateDialog = new DateDialog(setListener);
                dateDialog.show(getFragmentManager(), "DATEDIALOGVALIDADE");

            }
        });

        cancelar = (Button) v.findViewById(R.id.cancelar_cadastro_extintor);
        cancelar.setOnClickListener(new EventCancelar());
        salvar = (Button) v.findViewById(R.id.salvar_cadastro_extintor);
        salvar.setOnClickListener(new EventSalvar());
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        getActivity().setTitle(R.string.title_actionbar_cadastro_de_usuario);

        if (getArguments() != null) {
            txtTipo.setText(getArguments().getString(TIPO));
            txtData_validade.setText(getArguments().getString(DATA_VALIDADE));

        }
    }

    private void navegarListaExtintorFrament() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment fragment = new ListaExtintorFragment().newInstance(mCliente);
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    class EventCancelar implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            navegarListaExtintorFrament();
        }
    }

    class EventSalvar implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            String string1 = txtTipo.getText().toString().toUpperCase();
            String string2 = txtData_validade.getText().toString().toUpperCase();


            Extintor e = new Extintor();

            e.setCliente(mCliente);
            e.setTipo(string1);
            e.setData_validade(string2);

            String text = "";

            ExtintorProvider ep = new ExtintorProvider(getActivity());
            if (!flagAtualizar) {
                ep.insert(e);

            } else {
                e.setId(mId);
                ep.upgrade(e);
                flagAtualizar = false;
            }

            text = "Salvor com sucesso:\n" + e.getTipo();
            Toast to = Toast.makeText(getActivity().getApplicationContext(), text,
                    Toast.LENGTH_SHORT);
            to.show();

            navegarListaExtintorFrament();
        }
    }


}
