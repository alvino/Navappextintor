package com.alvino.mavappextintor.bancodados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alvino.mavappextintor.bancodados.inteface.CRUD;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by alvino on 21/08/15.
 */
public class VisitaProvider implements CRUD<Visita> {

    private final BancoDeDadosProvider dbp;
    private SQLiteDatabase db;
    private String tabela;

    public VisitaProvider(Context context) {
        dbp = new BancoDeDadosProvider(context);
        db = dbp.getWritableDatabase();
        tabela = BancoDeDadosProvider.TABELA_VISITA;
    }

    @Override
    public long insert(Visita visita) {
        ContentValues values = visita.getContentValue();
        return db.insert(tabela,null,values);
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
    public Set<Visita> all() {
        Set<Visita> set = new HashSet<Visita>();
        String[] colunas = dbp.getColunasExtintor();
        Cursor cursor = db.query(tabela, colunas, null, null,null, null, "nome ASC");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Visita v = new Visita(
                        cursor.getLong(0),
                        cursor.getLong(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                );
                set.add(v);

            } while (cursor.moveToNext());
        }

        return set;
    }

    public Set<Visita> allCliente(long id) {
        Set<Visita> set = new HashSet<Visita>();
        String[] colunas = dbp.getColunasExtintor();
        Cursor cursor = db.query(tabela, colunas, "cliente = "+id, null,null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Visita v = new Visita(
                        cursor.getLong(0),
                        cursor.getLong(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                );
                set.add(v);

            } while (cursor.moveToNext());
        }

        return set;
    }

    @Override
    public Visita get(long id) {
        String[] colunas = dbp.getColunasExtintor();
        Cursor cursor = db.query(tabela, colunas, "id = " + id, null, null, null, null);

        Visita v = null;

        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            v = new Visita(
                    cursor.getLong(0),
                    cursor.getLong(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
            );
        }

        return v;
    }

    public Set<Visita> allNotAtendido() {
        Set<Visita> set = new HashSet<Visita>();
        String[] colunas = dbp.getColunasExtintor();
        Cursor cursor = db.query(tabela, colunas, "data_atendimento = null", null,null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Visita v = new Visita(
                        cursor.getLong(0),
                        cursor.getLong(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                );
                set.add(v);

            } while (cursor.moveToNext());
        }

        return set;
    }
}
