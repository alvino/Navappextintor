package com.alvino.mavappextintor.bancodados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alvino.mavappextintor.bancodados.inteface.CRUD;
import com.alvino.mavappextintor.core.SimplesDataFormatada;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alvino on 21/08/15.
 */
public class VisitaProvider implements CRUD<Visita> {

    private SQLiteDatabase db;
    private String tabela;
    private String[] colunas;

    public VisitaProvider(Context context) {
        db = new BancoDeDadosProvider(context).getWritableDatabase();
        tabela = BancoDeDadosProvider.TABELA_VISITA;
        colunas = BancoDeDadosProvider.COLUNAS_VISITA;
    }

    @Override
    public long insert(Visita visita) {
        ContentValues values = visita.getContentValue();
        return db.insert(tabela, null, values);
    }

    @Override
    public int upgrade(Visita visita) {
        ContentValues values = visita.getContentValue();
        return db.update(tabela, values, "id = " + visita.getId(), null);
    }

    @Override
    public int delete(Visita visita) {
        return db.delete(tabela, "id = " + visita.getId(), null);
    }

    @Override
    public List<Visita> all() {
        List<Visita> list = new ArrayList<Visita>();
        Cursor cursor = db.query(tabela, colunas, null, null, null, null, colunas[2] + " ASC");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Visita v = new Visita(
                        cursor.getLong(0),
                        cursor.getLong(1),
                        SimplesDataFormatada.formatar(cursor.getString(2), SimplesDataFormatada.YYYYMDD),
                        SimplesDataFormatada.formatar(cursor.getString(3), SimplesDataFormatada.YYYYMDD),
                        SimplesDataFormatada.formatar(cursor.getString(4), SimplesDataFormatada.YYYYMDD),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)
                );
                list.add(v);

            } while (cursor.moveToNext());
        }

        return list;
    }

    public List<Visita> allCliente(long id) {
        List<Visita> list = new ArrayList<Visita>();
        Cursor cursor = db.query(tabela, colunas, "cliente = " + id, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Visita v = new Visita(
                        cursor.getLong(0),
                        cursor.getLong(1),
                        SimplesDataFormatada.formatar(cursor.getString(2), SimplesDataFormatada.YYYYMDD),
                        SimplesDataFormatada.formatar(cursor.getString(3), SimplesDataFormatada.YYYYMDD),
                        SimplesDataFormatada.formatar(cursor.getString(4), SimplesDataFormatada.YYYYMDD),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)
                );
                list.add(v);

            } while (cursor.moveToNext());
        }

        return list;
    }

    @Override
    public Visita get(long id) {
        Cursor cursor = db.query(tabela, colunas, "id = " + id, null, null, null, null);

        Visita v = null;

        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            v = new Visita(
                    cursor.getLong(0),
                    cursor.getLong(1),
                    SimplesDataFormatada.formatar(cursor.getString(2), SimplesDataFormatada.YYYYMDD),
                    SimplesDataFormatada.formatar(cursor.getString(3), SimplesDataFormatada.YYYYMDD),
                    SimplesDataFormatada.formatar(cursor.getString(4), SimplesDataFormatada.YYYYMDD),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7)
            );
        }

        return v;
    }

    public List<Visita> allNotAtendido() {
        List<Visita> list = new ArrayList<Visita>();
        Cursor cursor = db.query(tabela, colunas, "atendido like ?", new String[]{"nao"}, null, null, colunas[2] + " ASC");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Visita v = new Visita(
                        cursor.getLong(0),
                        cursor.getLong(1),
                        SimplesDataFormatada.formatar(cursor.getString(2), SimplesDataFormatada.YYYYMDD),
                        SimplesDataFormatada.formatar(cursor.getString(3), SimplesDataFormatada.YYYYMDD),
                        SimplesDataFormatada.formatar(cursor.getString(4), SimplesDataFormatada.YYYYMDD),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)
                );
                list.add(v);

            } while (cursor.moveToNext());
        }

        return list;
    }
}
