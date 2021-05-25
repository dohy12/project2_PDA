package com.example.pda;

public class Member {
    private int id;
    private String name;
    private int age;
    private String phone;
    private String email;
    private String intro;

    public Member(int id, String name, int age, String phone, String email, String intro){
        this.id = id;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.intro = intro;
    }

    public int getId() {
        return id;
    }

    public String getName(){
        return this.name;
    }

    public int getAge(){
        return this.age;
    }

    public String getPhone(){
        return this.phone;
    }

    public String getEmail(){
        return this.email;
    }

    public String getIntro(){
        return this.intro;
    }
}
