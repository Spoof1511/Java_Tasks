package com.company;

public class Farmer extends Thread {
    Inspector inspector;
    Integer products;
    float T1;
    ProductsQuantity eggs;

    public Farmer(float T1, Inspector inspector) {
        this.T1 = T1;
        products = rnd(100, 1000);
        this.inspector = inspector;
        eggs = new ProductsQuantity(products);
    }

    public void run() {
        while (true) {
            eggs = new ProductsQuantity(rnd(100, 1000));
            try {
                System.out.println("Фермер " + Thread.currentThread().getName() + " принес " + eggs.quantity + " товаров!");
                inspector.queueFarmers.put(eggs);
                System.out.println("Фермер " + Thread.currentThread().getName() + " уходит на производство!!");
                Thread.sleep((long) T1);

            } catch (InterruptedException e) {
            }
        }
    }

    public static int rnd(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

}


