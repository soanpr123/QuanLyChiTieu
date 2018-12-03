package com.soanlv.duan1android.model;

public class NguoiDung {
    private int ID;
    private String Name,UserName,Pass;

    public NguoiDung(int ID, String name, String userName, String pass) {
        this.ID = ID;
        Name = name;
        UserName = userName;
        Pass = pass;
    }

    public NguoiDung(String name, String userName, String pass) {
        Name = name;
        UserName = userName;
        Pass = pass;
    }

    public NguoiDung() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }
}
