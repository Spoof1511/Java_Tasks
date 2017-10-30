package com.company;


public class Seller extends Thread {
    ;
    Inspector inspector;
    Integer products;
    float T2;
    ProductsQuantity eggs;

    public Seller(float T2, Inspector inspector) {
        this.products = rnd(10, 100);
        this.T2 = T2;
        this.inspector = inspector;
        eggs = new ProductsQuantity(products);
    }

    public void run() {
        while (true) {
            eggs = new ProductsQuantity(rnd(10, 100));
            try {
                System.out.println("Продавец " + Thread.currentThread().getName() + " пришел за: " + eggs.quantity);
                inspector.queueSellers.put(eggs);
                System.out.println("Продавец идет продавать" + Thread.currentThread().getName() + ": " + eggs.quantity);
                Thread.sleep((long) T2);

            } catch (InterruptedException e) {
            }
        }
    }

    public static int rnd(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

}

