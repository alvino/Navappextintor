package com.alvino.mavappextintor.bancodados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alvino.mavappextintor.core.SimplesDataFormatada;
import com.alvino.mavappextintor.domain.Extintor;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by alvino on 21/08/15.
 */
public class ExtintorProvider implements CRUD<Extintor> {

    private final String[] colunas;
    private SQLiteDatabase db;
    private String tabela;

    private static String tag = "ExtintorProvider";

    public ExtintorProvider(Context context) {
        db = new BancoDeDadosProvider(context).getWritableDatabase();
        tabela = BancoDeDadosProvider.TABELA_EXTINTOR;
        colunas = BancoDeDadosProvider.COLUNAS_EXTINTOR;
    }

    @Override
    public long insert(Extintor extintor) {
        ContentValues values = extintor.getContentValue();
        Log.i(tag+"insert", values.toString());
        return db.insert(tabela,null,values);
    }

    @Override
    public int upgrade(Extintor extintor) {
        ContentValues values = extintor.getContentValue();
        Log.i(tag+"upgrade", values.toString());
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

        Log.i(tag + "all", "quantidade: "+cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Extintor e = new Extintor(
                        cursor.getLong(0),
                        cursor.getLong(1),
                        cursor.getString(2),
                        SimplesDataFormatada.formatar(cursor.getString(3), SimplesDataFormatada.YYYYMDD)
                        );
                List.add(e);
                Log.i(tag + "all", e.getContentValue().toString());
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
                    SimplesDataFormatada.formatar(cursor.getString(3), SimplesDataFormatada.YYYYMDD)
            );
        }

        return e;
    }

    public List<Extintor> allCliente(Long mCliente) {
        List<Extintor> List = new ArrayList<Extintor>();
        Cursor cursor = db.query(tabela, colunas, colunas[1]+" = ?",new String[]{String.valueOf(mCliente)},null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Extintor e = new Extintor(
                        cursor.getLong(0),
                        cursor.getLong(1),
                        cursor.getString(2),
                        SimplesDataFormatada.formatar(cursor.getString(3), SimplesDataFormatada.YYYYMDD)
                );
                List.add(e);
                Log.i(tag + "allCliente", e.getContentValue().toString());
            } while (cursor.moveToNext());
        }

        return List;
    }
}
