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
public class ClienteProvider implements CRUD<Cliente>{

    private final BancoDeDadosProvider dbp;
    private SQLiteDatabase db;
    private String tabela;

    public ClienteProvider(Context context) {
        dbp = new BancoDeDadosProvider(context);
        db = dbp.getWritableDatabase();
        tabela = BancoDeDadosProvider.TABELA_CLIENTE;
    }


    @Override
    public long insert(Cliente cliente) {

        ContentValues valores = cliente.getContentValue();

        return db.insert(tabela, null, valores);
    }

    @Override
    public int upgrade(Cliente cliente) {

        ContentValues valores = cliente.getContentValue();

        return db.update(tabela,valores,"id = "+cliente.getId(), null);
    }

    @Override
    public int delete(Cliente cliente) {
        return db.delete(DBCore.TABELA_CLIENTE, "_id = " + cliente.getId(), null);
    }

    @Override
    public Set<Cliente> all() {
        Set<Cliente> set = new HashSet<Cliente>();
        String[] colunas = dbp.getColunasCliente();
        Cursor cursor = db.query(tabela, colunas, null, null,null, null, "nome ASC");

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
                set.add(c);

            } while (cursor.moveToNext());
        }

        return set;
    }

    @Override
    public Cliente get(long id) {

        String[] colunas = dbp.getColunasCliente();
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
