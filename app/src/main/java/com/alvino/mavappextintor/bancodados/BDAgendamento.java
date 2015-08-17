package com.alvino.mavappextintor.bancodados;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alvino.mavappextintor.bancodados.entity.AgendamentoEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressLint("SimpleDateFormat")
public class BDAgendamento {

    private SQLiteDatabase bd;

    // formata data para o padrao de sqlite %y-%m-%d
    private SimpleDateFormat sd;

    public BDAgendamento(Context context) {
        DBCore auxBd = new DBCore(context);
        bd = auxBd.getWritableDatabase();
        sd = new SimpleDateFormat("yyyy-M-dd");
    }

    private String formatDateSQLite(Date data) {
        return sd.format(data);
    }

    private Date formatStringDate(String string) throws ParseException {
        Date data = sd.parse(string);
        return data;
    }

    public void inserir(AgendamentoEntity agendamento) {

        ContentValues valores = new ContentValues();
        valores.put("cliente", agendamento.getClienteId());
        valores.put("data", formatDateSQLite(agendamento.getData()));
        valores.put("visitado", 0);

        bd.insert(DBCore.TABELA_AGENDAMENTO, null, valores);
    }

    public void atualizar(AgendamentoEntity agendamento) {

        ContentValues valores = new ContentValues();
        valores.put("cliente", agendamento.getClienteId());
        valores.put("data", formatDateSQLite(agendamento.getData()));
        valores.put("visitado", agendamento.getVisitado());

        bd.update(DBCore.TABELA_AGENDAMENTO, valores, "_id = ?", new String[]{String.valueOf(agendamento.getId())});
    }

    public void deletar(AgendamentoEntity agendamento) {
        bd.delete(DBCore.TABELA_AGENDAMENTO, "_id = " + agendamento.getId(),
                null);
    }

    public List<AgendamentoEntity> buscarTodos(int visitado) {
        List<AgendamentoEntity> list = new ArrayList<AgendamentoEntity>();
        String[] colunas = new String[]{"_id", "data", "cliente", "visitado"};

        Cursor cursor = bd.query(DBCore.TABELA_AGENDAMENTO, colunas, "visitado = ?",
                new String[]{String.valueOf(visitado)}, null, null, "data ASC");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                try {
                    AgendamentoEntity agenda = new AgendamentoEntity();
                    agenda.setId(cursor.getInt(0));
                    agenda.setData(formatStringDate(cursor.getString(1)));
                    agenda.setClienteId(cursor.getInt(2));
                    agenda.setVisitado(cursor.getInt(3));
                    list.add(agenda);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }

        return (list);
    }

    public List<AgendamentoEntity> buscarTodos() {
        List<AgendamentoEntity> list = new ArrayList<AgendamentoEntity>();
        String[] colunas = new String[]{"_id", "data", "cliente", "visitado"};

        Cursor cursor = bd.query(DBCore.TABELA_AGENDAMENTO, colunas, null,
                null, null, null, "data ASC");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                try {
                    AgendamentoEntity agenda = new AgendamentoEntity();
                    agenda.setId(cursor.getInt(0));
                    agenda.setData(formatStringDate(cursor.getString(1)));
                    agenda.setClienteId(cursor.getInt(2));
                    agenda.setVisitado(cursor.getInt(3));
                    list.add(agenda);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }

        return (list);
    }

    public AgendamentoEntity buscarPorId(int pk) {
        AgendamentoEntity agenda = new AgendamentoEntity();
        String[] colunas = new String[]{"_id", "data", "cliente", "visitado"};

        Cursor cursor = bd.query(DBCore.TABELA_AGENDAMENTO, colunas, "_id = "
                + pk, null, null, null, null);

        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            try {
                agenda.setId(cursor.getInt(0));
                agenda.setData(formatStringDate(cursor.getString(1)));
                agenda.setClienteId(cursor.getInt(2));
                agenda.setVisitado(cursor.getInt(3));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return (agenda);
    }

    public List<AgendamentoEntity> buscaPorCliente(int id) {
        List<AgendamentoEntity> list = new ArrayList<AgendamentoEntity>();
        String[] colunas = new String[]{"_id", "data", "cliente", "visitado"};

        Cursor cursor = bd.query(DBCore.TABELA_AGENDAMENTO, colunas, "cliente = ?",
                new String[]{String.valueOf(id)}, null, null, "data ASC");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                try {
                    AgendamentoEntity agenda = new AgendamentoEntity();
                    agenda.setId(cursor.getInt(0));
                    agenda.setData(formatStringDate(cursor.getString(1)));
                    agenda.setClienteId(cursor.getInt(2));
                    agenda.setVisitado(cursor.getInt(3));
                    list.add(agenda);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }

        return (list);

    }
}
