package com.alvino.mavappextintor.bancodados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alvino.mavappextintor.bancodados.inteface.CRUD;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by alvino on 21/08/15.
 */
public class ExtintorProvider implements CRUD<Extintor> {

    private final String[] colunas;
    private SQLiteDatabase db;
    private String tabela;

    public ExtintorProvider(Context context) {
        db = new BancoDeDadosProvider(context).getWritableDatabase();
        tabela = BancoDeDadosProvider.TABELA_EXTINTOR;
        colunas = BancoDeDadosProvider.COLUNAS_EXTINTOR;
    }

    @Override
    public long insert(Extintor extintor) {
        ContentValues values = extintor.getContentValue();
        Log.i("Extintor Provider insert", values.toString());
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
    public List<Extintor> all() {
        List<Extintor> List = new ArrayList<Extintor>();
        Cursor cursor = db.query(tabela, colunas, null, null,null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Extintor e = new Extintor(
                        cursor.getLong(0),
                        cursor.getLong(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );
                List.add(e);

            } while (cursor.moveToNext());
        }

        return List;
    }

    @Override
    public Extintor get(long id) {
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

    public List<Extintor> allCliente(Long mId) {
        List<Extintor> List = new ArrayList<Extintor>();
        Cursor cursor = db.query(tabela, colunas, "cliente = "+mId,null,null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Extintor e = new Extintor(
                        cursor.getLong(0),
                        cursor.getLong(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );
                List.add(e);

            } while (cursor.moveToNext());
        }

        return List;
    }
}
