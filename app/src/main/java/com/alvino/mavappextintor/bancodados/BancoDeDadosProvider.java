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
        map.put(COLUNAS_CLIENTE[0], "integer primary key autoincrement");
        map.put(COLUNAS_CLIENTE[1], "text default \"\"");
        map.put(COLUNAS_CLIENTE[2], "text default \"\"");
        map.put(COLUNAS_CLIENTE[3], " text default \"\"");
        map.put(COLUNAS_CLIENTE[4], "text default \"\"");
        map.put(COLUNAS_CLIENTE[5], "text default \"\"");
        map.put(COLUNAS_CLIENTE[6], "text default \"\"");
        return map;
    }


    private Map<String, String> colummExtintorMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put(COLUNAS_EXTINTOR[0], "integer primary key autoincrement");
        map.put(COLUNAS_EXTINTOR[1], "INTEGER");
        map.put(COLUNAS_EXTINTOR[2], "text default \"\"");
        map.put(COLUNAS_EXTINTOR[3], "date");
        map.put(" ", "FOREIGN KEY(cliente) REFERENCES "+TABELA_CLIENTE+"(id)");
        return map;
    }

    private Map<String, String> colummVisitaMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put(COLUNAS_VISITA[0], "integer primary key autoincrement");
        map.put(COLUNAS_VISITA[1], "INTEGER");
        map.put(COLUNAS_VISITA[2], "date");
        map.put(COLUNAS_VISITA[3], "date");
        map.put(COLUNAS_VISITA[4], "date");
        map.put(COLUNAS_VISITA[5], "text default nao");
        map.put(COLUNAS_VISITA[6], "text default \"\"");
        map.put(COLUNAS_VISITA[7], "text default \"\"");
        map.put(" ", "FOREIGN KEY(cliente) REFERENCES "+TABELA_CLIENTE+"(id)");
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
