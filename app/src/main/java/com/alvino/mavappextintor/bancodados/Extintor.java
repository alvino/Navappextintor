package com.alvino.mavappextintor.bancodados;

import android.content.ContentValues;

/**
 * Created by alvino on 21/08/15.
 */
public class Extintor {

    private Long id;
    private Long cliente;
    private String tipo;
    private String data_validade;

    public Extintor() {
    }

    public Extintor(Long id, Long cliente, String tipo, String data_validade) {
        this.id = id;
        this.cliente = cliente;
        this.tipo = tipo;
        this.data_validade = data_validade;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCliente() {
        return cliente;
    }

    public void setCliente(long cliente) {
        this.cliente = cliente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getData_validade() {
        return data_validade;
    }

    public void setData_validade(String data_validade) {
        this.data_validade = data_validade;
    }


    public ContentValues getContentValue() {
        ContentValues valores = new ContentValues();
        if ((id != 0) || (id != null))
            valores.put("id", this.getId());
        valores.put("cliente", this.getCliente());
        valores.put("tipo", this.getTipo());
        valores.put("data_validade", this.getData_validade());
        return valores;
    }
}

