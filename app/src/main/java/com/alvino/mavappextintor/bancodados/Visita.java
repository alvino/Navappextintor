package com.alvino.mavappextintor.bancodados;

import android.content.ContentValues;

import java.util.Date;

/**
 * Created by alvino on 21/08/15.
 */
public class Visita {

    private Long id;
    private Long cliente;
    private String data_agendada;
    private String data_criacao;
    private String data_atendimento;
    private String manutenido;
    private String obs;

    public Visita(Long id, Long cliente, String data_agendada, String data_criacao, String data_atendimento, String manutenido, String obs) {
        this.id = id;
        this.cliente = cliente;
        this.data_agendada = data_agendada;
        this.data_criacao = data_criacao;
        this.data_atendimento = data_atendimento;
        this.manutenido = manutenido;
        this.obs = obs;
    }

    public Visita() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCliente() {
        return cliente;
    }

    public void setCliente(Long cliente) {
        this.cliente = cliente;
    }

    public String getData_agendada() {
        return data_agendada;
    }

    public void setData_agendada(String data_agendada) {
        this.data_agendada = data_agendada;
    }

    public String getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(String data_criacao) {
        this.data_criacao = data_criacao;
    }

    public String getData_atendimento() {
        return data_atendimento;
    }

    public void setData_atendimento(String data_atendimento) {
        this.data_atendimento = data_atendimento;
    }

    public String getManutenido() {
        return manutenido;
    }

    public void setManutenido(String manutenido) {
        this.manutenido = manutenido;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public ContentValues getContentValue() {
        ContentValues valores = new ContentValues();
        if((id != 0)||(id != null))
            valores.put("id",this.getId());
        valores.put("cliente", this.getCliente());
        valores.put("data_agendada", this.getData_agendada());
        valores.put("data_criacao", this.getData_criacao());
        valores.put("data_atendimento", this.getData_atendimento());
        valores.put("manutenido",this.getManutenido());
        valores.put("obs",this.getObs());
        return valores;
    }
}
