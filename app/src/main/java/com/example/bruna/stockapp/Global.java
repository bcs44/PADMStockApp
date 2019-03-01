package com.example.bruna.stockapp;

public class Global {

    private static Global instance;

    Boolean onAdd = false;

    public Boolean getOnAdd() {
        return onAdd;
    }

    public void setOnAdd(Boolean onAdd) {
        this.onAdd = onAdd;
    }


    public Global(){}

    public static synchronized Global getInstance(){
        if(instance==null){
            instance = new Global();
        }

        return instance;
    }
}
