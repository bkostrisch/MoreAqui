package com.example.moreaqui;

public class Imovel {

    int id;
    String phone;
    String type, size;
    String building;
    String occupy;

    public Imovel(){


    }

    public Imovel(int _id, String _phone, String _type, String _size, String _building, String _occupy){

        this.id = _id;
        this.phone = _phone;
        this.type = _type;
        this.size = _size;
        this.building = _building;
        this.occupy = _occupy;


    }

    public Imovel(String _phone, String _type, String _size, String _building, String _occupy){

        this.phone = _phone;
        this.type = _type;
        this.size = _size;
        this.building = _building;
        this.occupy = _occupy;


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

    public String getOccupy() { return occupy; }

    public void setOccupy(String occupy) { this.occupy = occupy; }
}
