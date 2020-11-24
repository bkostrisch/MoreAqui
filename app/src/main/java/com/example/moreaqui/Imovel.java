package com.example.moreaqui;

public class Imovel {

    int id;
    String phone;
    String type, size;
    String building;

    public Imovel(){


    }

    public Imovel(int _id, String _phone, String _type, String _size, String _building){

        this.id = _id;
        this.phone = _phone;
        this.type = _type;
        this.size = _size;
        this.building = _building;


    }

    public Imovel(String _phone, String _type, String _size, String _building){

        this.phone = _phone;
        this.type = _type;
        this.size = _size;
        this.building = _building;


    }

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
}
