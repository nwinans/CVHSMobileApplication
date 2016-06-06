package com.benrcarvergmail.cvhsmobileapplication;

/**
 * Created by 3Robotics on 5/31/2016.
 */
public class Teacher {
    private String name;
    private String email;
    private String department;
    public Teacher(String a){
        name = a;
    }
    public Teacher(String a, String b){
        name = a;
        email = b;
    }

    public Teacher(String a, String b, String d){
        name = a;
        email = b;
        department = d;
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }

    public String getDepartment(){
        return department;
    }
    public String toString(){
        String a = name;
        return a;
    }
}
