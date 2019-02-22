package com.example.bruna.stockapp;

public class Product {
    private int img;
    private String nome;
    private String preco;
    private String imgURL;
    private String desc;
    private String QRCode;
    private String Qtd;

    public Product() {

    }

    public Product(int img, String nome, String preco, String imgURL, String desc, String Qtd) {
        this.img = img;
        this.nome = nome;
        this.preco = preco;
        this.imgURL = imgURL;
        this.desc = desc;
        this.Qtd = Qtd;
    }

    public String getQtd() {
        return Qtd;
    }

    public void setQtd(String qtd) {
        Qtd = qtd;
    }

    public String getQRCode() {
        return QRCode;
    }

    public void setQRCode(String QRCode) {
        this.QRCode = QRCode;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
