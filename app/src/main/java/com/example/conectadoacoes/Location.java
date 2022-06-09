package com.example.conectadoacoes;

public class Location {

    private int id;
    private String equipamento_publico_disponivel;
    private String tipo;
    private String municipal_estadual;
    private String endereco;
    private String bairro;
    private double latitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEquipamento_publico_disponivel() {
        return equipamento_publico_disponivel;
    }

    public void setEquipamento_publico_disponivel(String equipamento_publico_disponivel) {
        this.equipamento_publico_disponivel = equipamento_publico_disponivel;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMunicipal_estadual() {
        return municipal_estadual;
    }

    public void setMunicipal_estadual(String municipal_estadual) {
        this.municipal_estadual = municipal_estadual;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    private double longitude;

    public Location(int id, String equipamento_publico_disponivel, String tipo, String municipal_estadual, String endereco, String bairro, double latitude, double longitude) {
        this.id = id;
        this.equipamento_publico_disponivel = equipamento_publico_disponivel;
        this.tipo = tipo;
        this.municipal_estadual = municipal_estadual;
        this.endereco = endereco;
        this.bairro = bairro;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(){}
}


