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

import com.alvino.mavappextintor.bancodados.BDCliente;
import com.alvino.mavappextintor.bancodados.entity.ClienteEntity;

public class CadastroClienteFragment extends Fragment {

    private static final String ID = "_id";
    private static final String NOME = "nome";
    private static final String EMAIL = "email";
    private static final String TELEFONE = "telefone";
    private static final String ENDERECO = "endereco";

    private int mId;
    private String mNome;
    private String mEmail;
    private String mTelefone;
    private String mEndereco;

    private EditText txtNome = null;
    private EditText txtEmail = null;
    private EditText txtEndereco = null;
    private EditText txtTelefone = null;
    private Button cancelar = null;
    private Button salvar = null;

    private boolean flagAtualizar = false;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param nome     Nome.
     * @param email    Email.
     * @param telefone Telefone.
     * @param endereco Endereco.
     * @return A new instance of fragment CadastroClienteFragment.
     */

    public static CadastroClienteFragment newInstance(int id, String nome, String email, String telefone, String endereco) {
        CadastroClienteFragment fragment = new CadastroClienteFragment();
        Bundle args = new Bundle();
        args.putInt(ID, id);
        args.putString(NOME, nome);
        args.putString(EMAIL, email);
        args.putString(TELEFONE, telefone);
        args.putString(ENDERECO, endereco);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mId = getArguments().getInt(ID);
            mNome = getArguments().getString(NOME);
            mEmail = getArguments().getString(EMAIL);
            mTelefone = getArguments().getString(TELEFONE);
            mEndereco = getArguments().getString(ENDERECO);

            flagAtualizar = true;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View raiz = inflater.inflate(R.layout.fragment_cadastro_cliente, container, false);

        txtNome = (EditText) raiz.findViewById(R.id.txtNome);
        txtEmail = (EditText) raiz.findViewById(R.id.txtEmail);
        txtEndereco = (EditText) raiz.findViewById(R.id.txtEndereco);
        txtTelefone = (EditText) raiz.findViewById(R.id.txtTelefone);

        cancelar = (Button) raiz.findViewById(R.id.cancelar);
        cancelar.setOnClickListener(new EventCancelar());
        salvar = (Button) raiz.findViewById(R.id.salvar);
        salvar.setOnClickListener(new EventSalvar());
        return raiz;
    }

    @Override
    public void onStart() {
        super.onStart();

        getActivity().setTitle(R.string.title_actionbar_cadastro_de_usuario);

        if (getArguments() != null) {
            txtNome.setText(mNome);
            txtEmail.setText(mEmail);
            txtTelefone.setText(mTelefone);
            txtEndereco.setText(mEndereco);
        }
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

            String string1 = txtNome.getText().toString().toUpperCase();
            String string2 = txtEmail.getText().toString().toUpperCase();
            String string3 = txtTelefone.getText().toString().toUpperCase();
            String string4 = txtEndereco.getText().toString().toUpperCase();

            ClienteEntity clienteEntity = new ClienteEntity();

            clienteEntity.setNome(string1);
            clienteEntity.setEmail(string2);
            clienteEntity.setTelefone(string3);
            clienteEntity.setEndereco(string4);

            String text = "";

            BDCliente db = new BDCliente(getActivity().getApplicationContext());
            if (!flagAtualizar) {
                db.inserir(clienteEntity);

            } else {
                clienteEntity.setId(mId);
                db.atualizar(clienteEntity);
                flagAtualizar = false;
            }

            text = "Salvor com sucesso:\n" + clienteEntity.getNome();
            Toast to = Toast.makeText(getActivity().getApplicationContext(), text,
                    Toast.LENGTH_SHORT);
            to.show();

            navegarListaClienteFrament();
        }
    }


}
