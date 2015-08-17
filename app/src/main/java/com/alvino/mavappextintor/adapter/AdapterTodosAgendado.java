package com.alvino.mavappextintor.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alvino.mavappextintor.R;
import com.alvino.mavappextintor.bancodados.BDAgendamento;
import com.alvino.mavappextintor.bancodados.BDCliente;
import com.alvino.mavappextintor.bancodados.entity.AgendamentoEntity;
import com.alvino.mavappextintor.bancodados.entity.ClienteEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import com.alvino.mavappextintor.CadastroClienteActivity;

@SuppressLint({"ViewHolder", "InflateParams", "SimpleDateFormat"})
public class AdapterTodosAgendado extends ArrayAdapter<AgendamentoEntity> {

    private static final String TAG = "AdpterListaAgenda";
    private Context context = null;

    public AdapterTodosAgendado(Context context, int resource) {
        this(context, resource, new ArrayList<AgendamentoEntity>());
    }

    public AdapterTodosAgendado(Context context, int resource,
                                List<AgendamentoEntity> items) {
        super(context, resource, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        final AgendamentoEntity entity = getItem(position);

        Date hoje = new Date();
        long milissegundos = entity.getData().getTime() - hoje.getTime();
        long dias = milissegundos / (24 * 60 * 60 * 1000);

//		Toast toast = Toast.makeText(MainActivity.getThis(), entity.getData().toString()+" dias: "+dias,
//				Toast.LENGTH_SHORT);
//		toast.show();

//		if ( dias < 61 ) {

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.modelo_lista_agendamento, null);
        }


        TextView txtValor2 = (TextView) view.findViewById(R.id.textView1);
        TextView txtValor1 = (TextView) view.findViewById(R.id.textView2);
        ImageButton check = (ImageButton) view.findViewById(R.id.check);

        BDCliente banco = new BDCliente(context);
        ClienteEntity cliente = banco.buscarPorId(entity.getClienteId());
        txtValor1.setText(cliente.getNome());

        SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/M/yyyy");
        txtValor2.setText(simpleFormat.format(entity.getData()));

//			if ( dias < 0 ) {
//					txtValor2.setTextColor(Color.rgb(235, 27, 36));
//			}

        check.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                entity.setVisitado(1);
                new BDAgendamento(getContext()).atualizar(entity);
//					remove(entity);
                //TODO instrucao de Intent para AdapterTodosAgendado.class
                    /*
					Intent intent = new Intent(MainActivity.getThis(),
							MainActivity.class);
					MainActivity.getThis().startActivity(intent);
					*/
            }
        });

//		}
        return view;
    }
}
