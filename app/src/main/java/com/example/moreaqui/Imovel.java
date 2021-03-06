package com.example.moreaqui;

public class Imovel {

    /*** Declaração das variáveis que serão utilizadas para acessar os valores no banco ao longo do código. */
    int id;
    String phone;
    String type, size;
    String building;
    String occupy;
    Double latitude;
    Double longitude;

    public Imovel(){


    }

    /*** Concatenação dos valores com as variáveis */
    public Imovel(int _id, String _phone, String _type, String _size, String _building, String _occupy, Double _latitude, Double _longitude){

        this.id = _id;
        this.phone = _phone;
        this.type = _type;
        this.size = _size;
        this.building = _building;
        this.occupy = _occupy;
        this.latitude = _latitude;
        this.longitude = _longitude;

    }

    /*** Getters and Setters para acessar os valores de cada variável no banco. */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getOccupy() { return occupy; }

    public void setOccupy(String occupy) { this.occupy = occupy; }

    public Double getLatitude() { return latitude; }

    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }

    public void setLongitude(Double longitude) { this.longitude = longitude; }
}
