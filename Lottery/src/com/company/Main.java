package com.company;

import org.omg.CORBA.INTERNAL;

import java.util.ArrayList;
import java.util.List;
import  java.util.Scanner;
import  java.util. Random;

public class Main {

    public static void main(String[] args) {
        System.out.print("Количество участников=");
        int N;
        Scanner sc=new Scanner(System.in);
        if (sc.hasNextInt()){
         N=sc.nextInt();
            LoxLoTo users = new LoxLoTo(N);}
        else {
            System.out.print("Вы ввели не число!!!");
        }


    }
}



