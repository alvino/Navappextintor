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
public class ExtintorProvider implements CRUD<Extintor> {

    private final BancoDeDadosProvider dbp;
    private SQLiteDatabase db;
    private String tabela;

    public ExtintorProvider(Context context) {
        dbp = new BancoDeDadosProvider(context);
        db = dbp.getWritableDatabase();
        tabela = BancoDeDadosProvider.TABELA_EXTINTOR;
    }

    @Override
    public long insert(Extintor extintor) {
        ContentValues values = extintor.getContentValue();
        return db.insert(tabela,null,values);
    }

    @Override
    public int upgrade(Extintor extintor) {
        ContentValues values = extintor.getContentValue();
        return db.update(tabela, values, "id = " + extintor.getId(), null);
    }

    @Override
    public int delete(Extintor extintor) {
        return db.delete(tabela, "id = " + extintor.getId(), null);
    }

    @Override
    public Set<Extintor> all() {
        Set<Extintor> set = new HashSet<Extintor>();
        String[] colunas = dbp.getColunasExtintor();
        Cursor cursor = db.query(tabela, colunas, null, null,null, null, "nome ASC");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Extintor e = new Extintor(
                        cursor.getLong(0),
                        cursor.getLong(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );
                set.add(e);

            } while (cursor.moveToNext());
        }

        return set;
    }

    @Override
    public Extintor get(long id) {
        String[] colunas = dbp.getColunasExtintor();
        Cursor cursor = db.query(tabela, colunas, "id = " + id, null, null, null, null);

        Extintor e = null;

        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            e = new Extintor(
                    cursor.getLong(0),
                    cursor.getLong(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
        }

        return e;
    }

    public Set<Extintor> allCliente(Long mId) {
        Set<Extintor> set = new HashSet<Extintor>();
        String[] colunas = dbp.getColunasExtintor();
        Cursor cursor = db.query(tabela, colunas, "cliente = "+mId, null,null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Extintor e = new Extintor(
                        cursor.getLong(0),
                        cursor.getLong(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );
                set.add(e);

            } while (cursor.moveToNext());
        }

        return set;
    }
}
