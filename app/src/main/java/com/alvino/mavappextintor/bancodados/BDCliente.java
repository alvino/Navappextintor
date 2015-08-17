package com.alvino.mavappextintor.bancodados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alvino.mavappextintor.bancodados.entity.ClienteEntity;

import java.util.ArrayList;
import java.util.List;

public class BDCliente {

    private SQLiteDatabase bd;

    public BDCliente(Context context) {
        DBCore auxBd = new DBCore(context);
        bd = auxBd.getWritableDatabase();
    }

    public void inserir(ClienteEntity cliente) {

        ContentValues valores = new ContentValues();
        valores.put("nome", cliente.getNome());
        valores.put("email", cliente.getEmail());
        valores.put("telefone", cliente.getTelefone());
        valores.put("endereco", cliente.getEndereco());

        bd.insert(DBCore.TABELA_CLIENTE, null, valores);
    }

    public void atualizar(ClienteEntity cliente) {
        ContentValues valores = new ContentValues();
        valores.put("nome", cliente.getNome());
        valores.put("email", cliente.getEmail());
        valores.put("telefone", cliente.getTelefone());
        valores.put("endereco", cliente.getEndereco());

        bd.update(DBCore.TABELA_CLIENTE, valores, "_id = ?", new String[]{""
                + cliente.getId()});
    }

    public void deletar(ClienteEntity cliente) {
        bd.delete(DBCore.TABELA_CLIENTE, "_id = " + cliente.getId(), null);
    }

    public List<ClienteEntity> buscarTodos() {
        List<ClienteEntity> list = new ArrayList<ClienteEntity>();
        String[] colunas = new String[]{"_id", "nome", "email", "telefone",
                "endereco"};

        Cursor cursor = bd.query(DBCore.TABELA_CLIENTE, colunas, null, null,
                null, null, "nome ASC");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                ClienteEntity c = new ClienteEntity();
                c.setId(cursor.getInt(0));
                c.setNome(cursor.getString(1));
                c.setEmail(cursor.getString(2));
                c.setTelefone(cursor.getString(3));
                c.setEndereco(cursor.getString(4));
                list.add(c);

            } while (cursor.moveToNext());
        }

        return list;
    }

    public ClienteEntity buscarPorId(int clienteId) {
        ClienteEntity c = new ClienteEntity();
        String[] colunas = new String[]{"_id", "nome", "email", "telefone",
                "endereco"};

        Cursor cursor = bd.query(DBCore.TABELA_CLIENTE, colunas, "_id = " + clienteId, null, null, null, "nome ASC");

        if (cursor.getCount() == 1) {
            cursor.moveToFirst();

            c.setId(cursor.getInt(0));
            c.setNome(cursor.getString(1));
            c.setEmail(cursor.getString(2));
            c.setTelefone(cursor.getString(3));
            c.setEndereco(cursor.getString(4));

        }

        return (c);
    }
}
