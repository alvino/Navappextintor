package com.alvino.mavappextintor.bancodados;

import android.content.ContentValues;

import com.alvino.mavappextintor.core.SimplesDataFormatada;

import java.util.Date;

/**
 * Created by alvino on 21/08/15.
 */
public class Visita {


    private Long id;
    private Long cliente;
    private Date data_agendada;
    private Date data_criacao;
    private Date data_atendimento;
    private String atendido;
    private String manutenido;
    private String obs;

    public Visita(Long id, Long cliente, Date data_agendada, Date data_criacao, Date data_atendimento, String atendido, String manutenido, String obs) {
        this.id = id;
        this.cliente = cliente;
        this.data_agendada = data_agendada;
        this.data_criacao = data_criacao;
        this.data_atendimento = data_atendimento;
        this.atendido = atendido;
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

    public Date getData_agendada() {
        return data_agendada;
    }

    public void setData_agendada(Date data_agendada) {
        this.data_agendada = data_agendada;
    }

    public Date getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(Date data_criacao) {
        this.data_criacao = data_criacao;
    }

    public Date getData_atendimento() {
        return data_atendimento;
    }

    public void setData_atendimento(Date data_atendimento) {
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

    public String getAtendido() {
        return atendido;
    }

    public void setAtendido(String atendido) {
        this.atendido = atendido;
    }

    public ContentValues getContentValue() {
        ContentValues valores = new ContentValues();

        valores.put("id", this.getId());
        valores.put("cliente", this.getCliente());
        valores.put("data_agendada", SimplesDataFormatada.formatar(this.getData_agendada(),SimplesDataFormatada.YYYYMDD));
        valores.put("data_criacao", SimplesDataFormatada.formatar(this.getData_criacao(), SimplesDataFormatada.YYYYMDD));
        valores.put("data_atendimento", SimplesDataFormatada.formatar(this.getData_atendimento(), SimplesDataFormatada.YYYYMDD));
        valores.put("atendido",this.getAtendido());
        valores.put("manutenido", this.getManutenido());
        valores.put("obs", this.getObs());
        return valores;
    }
}
