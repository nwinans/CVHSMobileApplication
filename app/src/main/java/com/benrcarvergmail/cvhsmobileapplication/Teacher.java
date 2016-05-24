package com.benrcarvergmail.cvhsmobileapplication;

import java.util.ArrayList;

/**
 * Created by 3Robotics on 5/20/2016.
 */
public class Teacher{
    private String name;
    private String email;
    private ArrayList<String> course;
    public Teacher(String a){
        name = a;
    }
    public Teacher(String a, String b){
        name = a;
        email = b;
    }
    public Teacher(String a, String b, ArrayList<String> c){
        name = a;
        email = b;
        course = c;
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public ArrayList<String> getCourse(){
        return course;
    }
    public String toString(){
        String a = "Name: " + name + " Email: " + email + "Courses:";
        for(int i = 0; i < course.size(); i++){
            a += " " + course.get(i);
        }
        return a;
    }


}
