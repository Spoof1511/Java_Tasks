package com.company;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    public static void main(String[] args) {
        Scanner scr=new Scanner(System.in);
        int N,M;
        float t1,t2,T1,T2;
        int p;
        System.out.println("Введите количество фермеров:");
        N=scr.nextInt();
        System.out.println("Введите количество продавцов:");
        M=scr.nextInt();
        System.out.println("Введите вероятность порчи продукта:");
        p=scr.nextInt();
        System.out.println("Введите время проверки одного товара:");
        t1=scr.nextFloat();
        System.out.println("Введите время погрузки одного товара:");
        t2=scr.nextFloat();
        System.out.println("Введите время производства товаров:");
        T1=scr.nextFloat();
        System.out.println("Введите время продажы товаров:");
        T2=scr.nextFloat();
        BlockingQueue<ProductsQuantity> farmers=new ArrayBlockingQueue(N);
        BlockingQueue<ProductsQuantity> sellers=new ArrayBlockingQueue(M);
        Inspector mrInspector = new Inspector(p,t1,t2,farmers,sellers);
        Thread inspectorThread = mrInspector;
        inspectorThread.setName("Mr.Gadjet");
        inspectorThread.start();
        for (int i=0;i<N;i++){
            Thread temp=new Farmer(T1,mrInspector);
            temp.setName("F_"+(i+1));
            temp.start();
        }
        for (int i=0;i<M;i++){
            Thread temp=new Seller(T2,mrInspector);
            temp.setName("S_"+(i+1));
            temp.start();
        }

    }
}
