package com.example.gatanhoto.bdevento_projeto.model;

import java.io.Serializable;

/**
 * Created by gatanhoto on 10/17/17.
 */

public class Evento implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long _id;
    public String nome;
    public String local;
    public String cidade;


    @Override
    public String toString() {
        return "Evento{" +
                "_id=" + _id +
                ", nome='" + nome + '\'' +
                ", local='" + local + '\'' +
                ", cidade='" + cidade + '\'' +
                ", idade='" + idade + '\'' +
                ", data='" + data + '\'' +
                ", mes='" + mes + '\'' +
                ", ano='" + ano + '\'' +
                ", hora='" + hora + '\'' +
                ", descricao='" + descricao + '\'' +
                ", produtor='" + produtor + '\'' +
                ", url_foto='" + url_foto + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }

    public String idade;
    public String data;
    public String mes;
    public String ano;
    public String hora;
    public String descricao;
    public String produtor;
    public String url_foto;
    public String tipo;


}
