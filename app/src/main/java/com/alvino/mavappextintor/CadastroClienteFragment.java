package com.alvino.mavappextintor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alvino.mavappextintor.bancodados.Cliente;
import com.alvino.mavappextintor.bancodados.ClienteProvider;

public class CadastroClienteFragment extends Fragment {

    private static final String ID = "id";
    private static final String NOME_FANTASIA = "nome_fantasia";
    private static final String PROPRIETARIO = "proprietario";
    private static final String RESPONSAVEL = "responsavel";
    private static final String FONE = "fone";
    private static final String EMAIL = "email";
    private static final String ENDERECO = "endereco";

    private Long mId;
    private String mNome_fantasia;
    private String mProprietario;
    private String mResponsavel;
    private String mFone;
    private String mEmail;
    private String mEndereco;

    private EditText txtEmail;
    private EditText txtEndereco;
    private EditText txtFone;
    private EditText txtResponsavel;
    private EditText txtProprietario;
    private EditText txtNome_fantasia;
    private Button cancelar = null;
    private Button salvar = null;

    private boolean flagAtualizar = false;


    public static CadastroClienteFragment newInstance(Long id, String nome_fantasia,String proprietario, String responsavel, String fone, String email, String endereco) {
        CadastroClienteFragment fragment = new CadastroClienteFragment();
        Bundle args = new Bundle();
        args.putLong(ID, id);
        args.putString(NOME_FANTASIA, nome_fantasia);
        args.putString(PROPRIETARIO, proprietario);
        args.putString(RESPONSAVEL,responsavel);
        args.putString(FONE,fone);
        args.putString(EMAIL, email);
        args.putString(ENDERECO, endereco);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mId = getArguments().getLong(ID);
            mNome_fantasia = getArguments().getString(NOME_FANTASIA);
            mProprietario = getArguments().getString(PROPRIETARIO);
            mResponsavel = getArguments().getString(RESPONSAVEL);
            mFone = getArguments().getString(FONE);
            mEmail = getArguments().getString(EMAIL);
            mEndereco = getArguments().getString(ENDERECO);

            flagAtualizar = true;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View raiz = inflater.inflate(R.layout.fragment_cadastro_cliente, container, false);

        txtNome_fantasia = (EditText) raiz.findViewById(R.id.txtNome_fantasia_cadastro_cliente);
        txtProprietario = (EditText) raiz.findViewById(R.id.txtProprietario_cadastro_cliente);
        txtResponsavel = (EditText) raiz.findViewById(R.id.txtResponsavel_cadastro_cliente);
        txtFone = (EditText) raiz.findViewById(R.id.txtPhone_cadastro_cliente);
        txtEmail = (EditText) raiz.findViewById(R.id.txtEmail_cadastro_cliente);
        txtEndereco = (EditText) raiz.findViewById(R.id.txtEndereco_cadastro_cliente);

        cancelar = (Button) raiz.findViewById(R.id.cancelar_cadastro_cliente);
        cancelar.setOnClickListener(new EventCancelar());
        salvar = (Button) raiz.findViewById(R.id.salvar_cadastro_cliente);
        salvar.setOnClickListener(new EventSalvar());
        return raiz;
    }

    @Override
    public void onStart() {
        super.onStart();

        getActivity().setTitle(R.string.title_actionbar_cadastro_de_usuario);

        if (getArguments() != null) {
            txtNome_fantasia.setText(mNome_fantasia);
            txtProprietario.setText(mProprietario);
            txtResponsavel.setText(mResponsavel);
            txtFone.setText(mFone);
            txtEmail.setText(mEmail);
            txtEndereco.setText(mEndereco);
        }
    }

    private void navegarListaClienteFrament() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment fragment = new ListaClientesFragment();
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
            navegarListaClienteFrament();
        }
    }

    class EventSalvar implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            String string1 = txtNome_fantasia.getText().toString().toUpperCase();
            String string2 = txtProprietario.getText().toString().toUpperCase();
            String string3 = txtResponsavel.getText().toString().toUpperCase();
            String string4 = txtFone.getText().toString().toUpperCase();
            String string5 = txtEmail.getText().toString().toUpperCase();
            String string6 = txtEndereco.getText().toString().toUpperCase();

            Cliente c = new Cliente();

            c.setNome_fantazia(string1);
            c.setProprietario(string2);
            c.setResponsavel(string3);
            c.setFone(string4);
            c.setEmail(string5);
            c.setEndereco(string6);

            String text = "";

            ClienteProvider cp = new ClienteProvider(getActivity());
            if (!flagAtualizar) {
                cp.insert(c);

            } else {
                c.setId(mId);
                cp.upgrade(c);
                flagAtualizar = false;
            }

            text = "Salvor com sucesso:\n" + c.getNome_fantazia();
            Toast to = Toast.makeText(getActivity().getApplicationContext(), text,
                    Toast.LENGTH_SHORT);
            to.show();

            navegarListaClienteFrament();
        }
    }


}
