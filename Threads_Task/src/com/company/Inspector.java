package com.company;

import java.util.concurrent.BlockingQueue;

public class Inspector extends Thread {
    int p;
    float t1;
    float t2;
    int storage;
    BlockingQueue<ProductsQuantity> queueFarmers;
    BlockingQueue<ProductsQuantity> queueSellers;

    public Inspector(int p, float t1, float t2, BlockingQueue<ProductsQuantity> queue1, BlockingQueue<ProductsQuantity> queue2) {
        this.p = p;
        this.t1 = t1;
        this.t2 = t2;
        this.queueFarmers = queue1;
        this.queueSellers = queue2;
    }

    private void addProducts(ProductsQuantity n) {
        synchronized (n) {
            try {
                Thread.sleep(n.quantity * (long) t1);
                storage += (int) (n.quantity * (1 - (double) p / 100));
                System.out.println("Инспектор проверил: " + n.quantity + " ,а положил: " + (int) (n.quantity * (1 - (double) p / 100)));
            } catch (InterruptedException e) {
            }
        }
    }

    public void takeProducts(ProductsQuantity m) {
        System.out.println("На складе: " + storage + " товаров");
        synchronized (m) {
            try {
                if (m.quantity > storage) {
                    System.out.println("Нехватка товара на складе!");
                    queueSellers.remove(m);
                } else {
                    storage -= m.quantity;
                    Thread.sleep(m.quantity * (long) t2);
                    System.out.println("Со склада забрали: " + m.quantity);
                    queueSellers.remove(m);
                }
            } catch (InterruptedException e) {
            }
        }
    }

    public void run() {
        System.out.println("Я родился!!!(с)" + Thread.currentThread().getName());
        while (true) {
            while (!queueFarmers.isEmpty()) {
                try {
                    addProducts(queueFarmers.take());
                } catch (InterruptedException e) {
                }
            }
            while (!queueSellers.isEmpty()) {
                for (ProductsQuantity a : queueSellers) {
                    takeProducts(a);
                }
            }
        }
    }
}
