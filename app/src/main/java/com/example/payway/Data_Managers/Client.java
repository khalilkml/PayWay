package com.example.payway.Data_Managers;

public class Client {
    String username;
    String number;
    String id;
    public Client(String username,String number,String id){
        this.username=username;
        this.number=number;
        this.id=id;
    }

    public String getUsername() {
        return username;
    }

    public String getNumber() {
        return number;
    }

    public String getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setId(String id) {
        this.id = id;
    }
}
