package com.forksystem.constituicao.model;





public class Artigo {


    private String titulo;
    private int artigo;


  public Artigo(){}

    public Artigo(int artigo,String titulo) {
        this.titulo = titulo;
        this.artigo = artigo;

    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getArtigo() {
        return artigo;
    }

    public void setArtigo(Integer artigo) {
        this.artigo = artigo;
    }


    @Override
    public String toString() {
        return "Artigo{" +
                "titulo='" + titulo + '\'' +
                ", artigo=" + artigo +
                '}';
    }
}
