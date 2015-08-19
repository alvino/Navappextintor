package com.alvino.mavappextintor.bancodados.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AgendamentoEntity {

    private int id;
    private Date data;
    private int visitado;
    private int clienteId;
    private SimpleDateFormat sd = new SimpleDateFormat("dd-M-yyyy");
    ;


    public AgendamentoEntity() {
    }

    public AgendamentoEntity(int id, Date data, int clienteId) {
        this.id = id;
        this.data = data;
        this.clienteId = clienteId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVisitado() {
        return visitado;
    }

    public void setVisitado(int visitado) {
        this.visitado = visitado;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {

        this.clienteId = clienteId;
    }

    public String getformatData() {
        return sd.format(data);
    }

    public void setFormatDate(String string) throws ParseException {
        Date data = sd.parse(string);
    }

}
