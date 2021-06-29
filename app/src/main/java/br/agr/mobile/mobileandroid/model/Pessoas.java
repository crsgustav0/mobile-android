package br.agr.mobile.mobileandroid.model;

public class Pessoas {

    private int id;
    private String nome;
    private String telefone;
    private String email;

    public Pessoas() {
        this.id = -1;
        this.nome = "";
        this.telefone = "";
        this.email = "";
    }

    public Pessoas(int id, String nome, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}