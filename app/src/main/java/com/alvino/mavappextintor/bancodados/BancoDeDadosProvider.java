package com.alvino.mavappextintor.bancodados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BancoDeDadosProvider extends SQLiteOpenHelper {

    private static final String NOME_BD = "AppExtintor";
    private static final int VERSAO_BD = 1;

    public static final String TABELA_CLIENTE = "Cliente";
    public static final String[] COLUNAS_CLIENTE = new String[]{"id", "nome_fantazia", "proprietario", "responsavel", "fone", "email", "endereco"};
    public static final String TABELA_EXTINTOR = "Extintor";
    public static final String[] COLUNAS_EXTINTOR = new String[]{"id", "cliente", "tipo", "data_validade"};
    public static final String TABELA_VISITA = "Visita";
    public static final String[] COLUNAS_VISITA = new String[]{"id", "cliente", "data_agendada", "data_criacao", "data_atendimento", "atendido", "manutenido", "obs"};

    public BancoDeDadosProvider(Context ctx) {
        super(ctx, NOME_BD, null, VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL(sqlCreateTable(TABELA_CLIENTE, COLUNAS_CLIENTE, colummClienteMap(),false) );
        bd.execSQL(sqlCreateTable(TABELA_EXTINTOR, COLUNAS_EXTINTOR, colummExtintorMap(),true) );
        bd.execSQL(sqlCreateTable(TABELA_VISITA, COLUNAS_VISITA, colummVisitaMap(),true) );
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int arg1, int arg2) {
        bd.execSQL(sqlDropTable(TABELA_CLIENTE));
        bd.execSQL(sqlDropTable(TABELA_EXTINTOR));
        bd.execSQL(sqlDropTable(TABELA_VISITA));
    }

    private Map<String, String> colummClienteMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "integer primary key autoincrement");
        map.put("nome_fantazia", "text default \"\"");
        map.put("proprietario", "text default \"\"");
        map.put("responsavel", " text default \"\"");
        map.put("fone", "text default \"\"");
        map.put("email", "text default \"\"");
        map.put("endereco", "text default \"\"");
        return map;
    }


    private Map<String, String> colummExtintorMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "integer primary key autoincrement");
        map.put("cliente", "INTEGER");
        map.put("tipo", "text default \"\"");
        map.put("data_validade", "text default \"\"");
        map.put(" ", "FOREIGN KEY(cliente) REFERENCES cliente(id)");
        return map;
    }

    private Map<String, String> colummVisitaMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "integer primary key autoincrement");
        map.put("cliente", "INTEGER");
        map.put("data_agendada", "date");
        map.put("data_criacao", "date");
        map.put("data_atendimento", "date");
        map.put("atendido", "text default nao");
        map.put("manutenido", "text default \"\"");
        map.put("obs", "text default \"\"");
        map.put(" ", "FOREIGN KEY(cliente) REFERENCES cliente(id)");
        return map;
    }


    private String sqlCreateTable(String name, String[] colunas, Map<String, String> map,boolean forekey) {
        StringBuffer sb = new StringBuffer();
        sb.append("CREATE TABLE IF NOT EXISTS " + name + " ( \n");
        Set<String> set = map.keySet();
        int size = colunas.length;
        int i = 1;
        for (String str : colunas) {

            if((i == size) && (!forekey) ){
                sb.append(" " + str + " " + map.get(str) + " \n");
            } else {
                sb.append(" " + str + " " + map.get(str) + ", \n");
            }

            i++;
        }
        if (forekey) {
            sb.append(" " + map.get(" ") + " \n");
        }
        sb.append(");");
        Log.i("BancoDeDadosProvider", sb.toString());
        return (sb.toString());
    }

    private String sqlDropTable(String name) {
        return "DROP TABLE IF EXISTS  " + name + ";";
    }


    public String[] getColunasCliente() {
        return COLUNAS_CLIENTE;
    }

    public String[] getColunasExtintor() {
        return COLUNAS_EXTINTOR;
    }

    public String[] getColunasVisita() {
        return COLUNAS_VISITA;
    }
}
