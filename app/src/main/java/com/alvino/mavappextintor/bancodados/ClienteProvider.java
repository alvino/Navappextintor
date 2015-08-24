package com.alvino.mavappextintor.bancodados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alvino.mavappextintor.bancodados.inteface.CRUD;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by alvino on 21/08/15.
 */
public class ClienteProvider implements CRUD<Cliente>{

    private final String[] colunas;
    private SQLiteDatabase db;
    private String tabela;

    public ClienteProvider(Context context) {
        db = new BancoDeDadosProvider(context).getWritableDatabase();
        tabela = BancoDeDadosProvider.TABELA_CLIENTE;
        colunas = BancoDeDadosProvider.COLUNAS_CLIENTE;
    }


    @Override
    public long insert(Cliente cliente) {

        ContentValues valores = cliente.getContentValue();
        Log.i("Cliente Provider insert",valores.toString());
        return db.insert(tabela, null, valores);
    }

    @Override
    public int upgrade(Cliente cliente) {

        ContentValues valores = cliente.getContentValue();

        return db.update(tabela,valores,"id = "+cliente.getId(), null);
    }

    @Override
    public int delete(Cliente cliente) {
        return db.delete(tabela, "id = " + cliente.getId(), null);
    }

    @Override
    public List<Cliente> all() {
        List<Cliente> list = new ArrayList<Cliente>();
        Cursor cursor = db.query(tabela, colunas, null, null,null, null, colunas[1]+" ASC");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Cliente c = new Cliente(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                        );
                list.add(c);

            } while (cursor.moveToNext());
        }

        return list;
    }

    @Override
    public Cliente get(long id) {

        Cursor cursor = db.query(tabela, colunas, "id = "+id, null,null, null, null);

        Cliente c = null;

        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
                    c = new Cliente(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                );

        }

        return c;
    }
}
