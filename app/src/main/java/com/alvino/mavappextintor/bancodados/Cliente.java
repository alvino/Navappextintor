package com.alvino.mavappextintor.bancodados;

import android.content.ContentValues;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alvino on 21/08/15.
 */
public class Cliente {

    private Long id;
    private String nome_fantazia;
    private String proprietario;
    private String responsavel;
    private String fone;
    private String email;
    private String endereco;

    public Cliente() {
    }

    public Cliente(Long id, String nome_fantazia, String proprietario, String responsavel, String fone, String email, String endereco) {
        this.id = id;
        this.nome_fantazia = nome_fantazia;
        this.proprietario = proprietario;
        this.responsavel = responsavel;
        this.fone = fone;
        this.email = email;
        this.endereco = endereco;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome_fantazia() {
        return nome_fantazia;
    }

    public void setNome_fantazia(String nome_fantazia) {
        this.nome_fantazia = nome_fantazia;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public ContentValues getContentValue() {
        ContentValues valores = new ContentValues();
        if((id != 0)||(id != null))
            valores.put("id",this.getId());
        valores.put("nome_fantazia", this.getNome_fantazia());
        valores.put("proprietario", this.getProprietario());
        valores.put("responsavel", this.getResponsavel());
        valores.put("fone", this.getFone());
        valores.put("email", this.getEmail());
        valores.put("endereco", this.getEndereco());
        return valores;
    }
}
