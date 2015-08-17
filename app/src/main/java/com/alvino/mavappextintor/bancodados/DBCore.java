package com.alvino.mavappextintor.bancodados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.alvino.mavappextintor.bancodados.entity.AgendamentoEntity;
import com.alvino.mavappextintor.bancodados.entity.ClienteEntity;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBCore extends SQLiteOpenHelper {

    public static final String TABELA_CLIENTE = "cliente";
    public static final String TABELA_AGENDAMENTO = "agendamento";


    private static final String NOME_BD = "teste";
    private static final int VERSAO_BD = 7;
    SimpleDateFormat sd = null;

    private ArrayList<ClienteEntity> clienteList = null;
    private ArrayList<AgendamentoEntity> agendamentoList = null;

    public DBCore(Context ctx) {
        super(ctx, NOME_BD, null, VERSAO_BD);
        sd = new SimpleDateFormat("yyyy-M-dd");
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("create table " + TABELA_CLIENTE + " ("
                + "_id integer primary key autoincrement, "
                + "nome text not null, "
                + "email text not null, "
                + "telefone text not null, "
                + "endereco text not null);");

        bd.execSQL("create table " + TABELA_AGENDAMENTO + " ("
                + "_id integer primary key autoincrement, "
                + "data date not null, "
                + "visitado INTEGER,"
                + "cliente INTEGER,"
                + "FOREIGN KEY(cliente) REFERENCES " + TABELA_CLIENTE + "(_id));");

        // restaura o backup da tabela cliente
        if (clienteList != null) {
            for (ClienteEntity cliente : clienteList) {

                ContentValues valores = new ContentValues();
                valores.put("_id", cliente.getId());
                valores.put("nome", cliente.getNome());
                valores.put("email", cliente.getEmail());
                valores.put("telefone", cliente.getTelefone());
                valores.put("endereco", cliente.getEndereco());

                bd.insert(DBCore.TABELA_CLIENTE, null, valores);
            }
        }

        if (agendamentoList != null) {
            for (AgendamentoEntity agendamento : agendamentoList) {

                ContentValues valores = new ContentValues();
                valores.put("_id", agendamento.getId());
                valores.put("data", formatDateSQLite(agendamento.getData()));
                valores.put("visitado", agendamento.getVisitado());
                valores.put("cliente", agendamento.getClienteId());

                bd.insert(DBCore.TABELA_AGENDAMENTO, null, valores);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int arg1, int arg2) {

        //backup tabela cliente
        String[] columnsCliente = new String[]{"_id", "nome", "email", "telefone", "endereco"};
        Cursor queryCliente = bd.query(TABELA_CLIENTE, columnsCliente, null, null, null, null, null);
        clienteList = new ArrayList<ClienteEntity>();
        if (queryCliente.getCount() > 0) {
            queryCliente.moveToFirst();

            do {

                ClienteEntity c = new ClienteEntity();
                c.setId(queryCliente.getInt(0));
                c.setNome(queryCliente.getString(1));
                c.setEmail(queryCliente.getString(2));
                c.setTelefone(queryCliente.getString(3));
                c.setEndereco(queryCliente.getString(4));
                clienteList.add(c);

            } while (queryCliente.moveToNext());
        }


        //backup tabela agendamento
        String[] columnsAgendamento = new String[]{"_id", "data", "visitado", "cliente"};
        Cursor queryAgendamento = bd.query(TABELA_AGENDAMENTO, columnsAgendamento, null, null, null, null, null);
        agendamentoList = new ArrayList<AgendamentoEntity>();
        if (queryAgendamento.getCount() > 0) {
            queryAgendamento.moveToFirst();

            do {

                try {
                    AgendamentoEntity a = new AgendamentoEntity();
                    a.setId(queryAgendamento.getInt(0));
                    a.setData(formatStringDate(queryAgendamento.getString(1)));
                    a.setVisitado(queryAgendamento.getInt(2));
                    a.setClienteId(queryAgendamento.getInt(3));
                    agendamentoList.add(a);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            } while (queryAgendamento.moveToNext());
        }

        //drop
        bd.execSQL("DROP TABLE IF EXISTS " + TABELA_CLIENTE + ";");
        bd.execSQL("DROP TABLE IF EXISTS " + TABELA_AGENDAMENTO + ";");
        onCreate(bd);
    }

    private Date formatStringDate(String string) throws ParseException {
        Date data = sd.parse(string);
        return data;
    }

    private String formatDateSQLite(Date data) {
        return sd.format(data);
    }

}
