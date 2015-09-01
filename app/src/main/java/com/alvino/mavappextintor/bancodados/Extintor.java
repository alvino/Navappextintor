package com.alvino.mavappextintor.bancodados;

import android.content.ContentValues;

import com.alvino.mavappextintor.core.SimplesDataFormatada;

import java.util.Date;

/**
 * Created by alvino on 21/08/15.
 */
public class Extintor {

    private Long id;
    private Long cliente;
    private String tipo;
    private Date data_validade;

    public Extintor() {
    }

    public Extintor(Long id, Long cliente, String tipo, Date data_validade) {
        this.id = id;
        this.cliente = cliente;
        this.tipo = tipo;
        this.data_validade = data_validade;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Date getData_validade() {
        return data_validade;
    }

    public void setData_validade(Date data_validade) {
        this.data_validade = data_validade;
    }


    public ContentValues getContentValue() {
        String[] colunas = BancoDeDadosProvider.COLUNAS_EXTINTOR;
        ContentValues valores = new ContentValues();
        valores.put(colunas[0], this.getId());
        valores.put(colunas[1], this.getCliente());
        valores.put(colunas[2], this.getTipo());
        valores.put(colunas[3], SimplesDataFormatada.formatar(this.getData_validade(), SimplesDataFormatada.YYYYMDD));
        return valores;
    }
}

