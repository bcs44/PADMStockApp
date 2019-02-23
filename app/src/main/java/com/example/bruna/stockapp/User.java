package com.example.bruna.stockapp;

public class User {

    String email;
    String nome;
    String password;
    String username;
    String imgURL;
    String filial;

    public String getFilial() {
        return filial;
    }

    public void setFilial(String filial) {
        this.filial = filial;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User( String email, String nome, String password, String username,  String imgURL) {
        this.email = email;
        this.nome = nome;
        this.password = password;
        this.username = username;
        this.imgURL = imgURL;
    }

    public User() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
