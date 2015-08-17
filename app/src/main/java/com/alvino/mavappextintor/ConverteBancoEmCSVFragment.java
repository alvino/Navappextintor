package com.alvino.mavappextintor;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alvino.mavappextintor.bancodados.BDAgendamento;
import com.alvino.mavappextintor.bancodados.BDCliente;
import com.alvino.mavappextintor.bancodados.entity.AgendamentoEntity;
import com.alvino.mavappextintor.bancodados.entity.ClienteEntity;
import com.alvino.mavappextintor.core.ManageFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;


public class ConverteBancoEmCSVFragment extends Fragment {

    private ProgressBar mProgress;
    private TextView mTexto;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle(getResources().getString(R.string.title_actionbar_converte_dados_para_csv));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View raiz = inflater.inflate(R.layout.fragment_converte_banco_em_csv, container, false);

        Button button = (Button) raiz.findViewById(R.id.btn_cria_cvs);
        mTexto = (TextView) raiz.findViewById(R.id.txt_saida);
        mProgress = (ProgressBar) raiz.findViewById(R.id.pb);
        mProgress.setProgress(0);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cliqueButton();
            }
        });

        return raiz;
    }

    private void cliqueButton() {


        mProgress.setProgress(0);
        mTexto.setText("");


        SalvarArquivoThread thread = new SalvarArquivoThread(getActivity().getApplicationContext(),mProgress,mTexto);

        if ( !thread.isAlive() ) {
            thread.start();
        }
    }


}

class SalvarArquivoThread extends Thread{

    private final ProgressBar mProgress;
    private final TextView mTexto;
    private Handler mHandler = new Handler();
    private Context context;
    private int mProgressStatus;

    public SalvarArquivoThread(Context context,ProgressBar progressBar, TextView textView) {
        super();
        this.context = context;
        this.mProgress = progressBar;
        this.mTexto = textView;
    }

    public void run() {

        float count = 0;
        float contador = 0;

        StringBuffer txt_arquivo = new StringBuffer();

        BDAgendamento agendamentoList = new BDAgendamento(context);
        List<AgendamentoEntity> agendamentos = agendamentoList.buscarTodos();

        count = agendamentos.size();

        BDCliente clienteEntity = new BDCliente(context);

        //txt_arquivo.append("id cliente, nome, email, telefone, endereco, id agendamento, data, visitado\n");

        for (AgendamentoEntity agendamento : agendamentos) {

            contador += 1;

            ClienteEntity cliente = clienteEntity.buscarPorId(agendamento.getClienteId());

            StringBuffer sb = parseStringBuffer(cliente, agendamento);


            txt_arquivo.append(sb.toString());

            mProgressStatus = (int) ((contador / count) * 100.0);
            final StringBuffer finalSb = sb;
            mHandler.post(new Display(mProgressStatus,sb.toString()));
        }

        salvarArquivo(txt_arquivo);

    }

    private void salvarArquivo(StringBuffer txt_arquivo) {

        ManageFile managefile = new ManageFile(context);
        managefile.getStateSDcard();
        String texto;
        if (managefile.isSdCardAvailable() && managefile.isSdCardWritableReadable()) {

            if (managefile.WriteFile(txt_arquivo.toString()) == true) {
                File file = managefile.getFile();
                texto = new String("Dados salvo com sucesso em:  " + file.getPath());
            } else {
                texto = new String("Não foi possível escrever o texto.");
            }
        } else {
            texto = new String("O cartão SD não está disponível, ou não permite escrita.");
        }
        mHandler.post(new Display(mProgressStatus,texto));
    }

    private StringBuffer parseStringBuffer(ClienteEntity cliente,AgendamentoEntity agendamento) {
        StringBuffer sb = new StringBuffer();
        sb.append(cliente.getId() + ",");
        sb.append(cliente.getNome() + ",");
        sb.append(cliente.getEmail() + ",");
        sb.append(cliente.getTelefone() + ",");
        sb.append(cliente.getEndereco() + ",");
        sb.append(agendamento.getId() + ",");
        sb.append(new SimpleDateFormat("dd/MM/yyyy").format(agendamento.getData()) + ",");
        sb.append(agendamento.getVisitado() + "");
        sb.append("\n");
        return sb;
    }

    class Display implements Runnable {

        private String texto;
        private int status;

        Display(int status,String texto){
            this.status = status;
            this.texto = texto;
        }

        @Override
        public void run() {
            mProgress.setProgress(status);
            mTexto.setText(texto);
        }

    }

}
