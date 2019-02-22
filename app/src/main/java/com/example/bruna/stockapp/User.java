package com.example.bruna.stockapp;

public class User {

    String email;
    String nome;
    String password;
    String username;


    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User( String email, String nome, String password, String username) {
        this.email = email;
        this.nome = nome;
        this.password = password;
        this.username = username;
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
