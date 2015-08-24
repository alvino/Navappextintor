package com.alvino.mavappextintor.bancodados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BancoDeDadosProvider extends SQLiteOpenHelper {

    private static final String NOME_BD = "AppExtintor";
    private static final int VERSAO_BD = 1;

    public static final String TABELA_CLIENTE = "Cliente";
    public static final String TABELA_EXTINTOR = "Extintor";
    public static final String TABELA_VISITA = "Visita";


    public BancoDeDadosProvider(Context ctx) {
        super(ctx, NOME_BD, null, VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL(sqlCreateTable(TABELA_CLIENTE, colummClienteMap()));
        bd.execSQL(sqlCreateTable(TABELA_EXTINTOR,colummExtintorMap()));
        bd.execSQL(sqlCreateTable(TABELA_VISITA, colummVisitaMap()));
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
        map.put("nome_fantazia", "text");
        map.put("proprietario", "text");
        map.put("responsavel", " text");
        map.put("fone", "text");
        map.put("email", "text");
        map.put("endereco", "text");
        return map;
    }



    private Map<String, String> colummExtintorMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id","integer primary key autoincrement");
        map.put("cliente","INTEGER");
        map.put("tipo","text");
        map.put("data_validade","text");
        map.put(" ","FOREIGN KEY(cliente) REFERENCES cliente(id)");
        return map;
    }

    private Map<String, String> colummVisitaMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id","integer primary key autoincrement");
        map.put("cliente","INTEGER");
        map.put("data_agendada","text");
        map.put("data_criacao","text");
        map.put("data_atendimento","text");
        map.put("manutenido","text");
        map.put("obs","text");
        map.put(" ","FOREIGN KEY(cliente) REFERENCES cliente(id)");
        return map;
    }


    private String sqlCreateTable(String name, Map<String, String> map) {
        StringBuffer sb = new StringBuffer();
        sb.append("CREATE TABLE IF EXISTS " + name + "( \n");
        Set<String> set = map.keySet();
        for (String str : set) {
            sb.append(" " + map.containsKey(str) + " " + map.containsValue(str) + ", \n");
        }
        sb.append(");");
        return (sb.toString());
    }

    private String sqlDropTable(String name) {
        return "DROP TABLE IF EXISTS  " + name + ";";
    }


    public String[] getColunasCliente() {
        Set<String> set = colummClienteMap().keySet();
        return (String[]) set.toArray();
    }

    public String[] getColunasExtintor() {
        Set<String> set = colummExtintorMap().keySet();
        return (String[]) set.toArray();
    }
}
