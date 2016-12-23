package com.forksystem.constituicao.model;

import java.io.Serializable;

/**
 * Created by sam on 06/12/16.
 */

public class Favorito implements Serializable {

    private int id;
    private String conteudo;

    public Favorito(){}


    public Favorito(int id,String conteudo) {
        this.id = id;
         this.conteudo = conteudo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    @Override
    public String toString() {
        return "Epigrafo{" +
                "id=" + id +
                ", conteudo='" + conteudo + '\'' +
                '}';
    }
}
