package com.forksystem.constituicao.model;

import java.io.Serializable;

/**
 * Created by sam on 06/12/16.
 */

public class Epigrafo implements Serializable {

    private int id;
    private int artigo;
    private String conteudo;

    public Epigrafo(){}


    public Epigrafo(String conteudo, int artigo) {
        this.id = id;
        this.artigo = artigo;
        this.conteudo = conteudo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArtigo() {
        return artigo;
    }

    public void setArtigo(int artigo) {
        this.artigo = artigo;
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
                ", artigo=" + artigo +
                ", conteudo='" + conteudo + '\'' +
                '}';
    }
}
