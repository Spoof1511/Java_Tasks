package com.company;

import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;

/**
 * Created by Alan
 */
interface Player {
    void startGame();

    void acceptDigit(int digit);

    void gameOver();
}

interface Organizer {
    void register();

    void unregister(int p);
}


public class LoxLoTo implements Player, Organizer {
    class PlayerInfo {
        private String name;
        private int id;
        private ArrayList<Integer> card;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public ArrayList<Integer> getCard() {
            return card;
        }

        public PlayerInfo(String n, int i, ArrayList<Integer> c) {
            name = n;
            id = i;
            card = c;
        }
    }

    private int n;

    private ArrayList<PlayerInfo> playerslist = new ArrayList<>();

    public int generateDigit() {
        int digit;
        Random rnd = new Random();
        digit = rnd.nextInt(9);
        return digit;
    }

    public ArrayList<Integer> generateCard() {
        ArrayList<Integer> cardlist = new ArrayList<>();
        Random rnd = new Random();
        int numb;

        for (int j = 0; j < 6; j++) {
            numb = rnd.nextInt(9);
            cardlist.add(numb);
        }
        return cardlist;
    }

    public LoxLoTo(int N) {
        n = N;
        register();
        startGame();
        gameOver();

    }

    public void startGame() {

        for (int i = 0; i < 6; i++) {
            acceptDigit(generateDigit());
        }

    }


    public void acceptDigit(int digit) {
        System.out.println("Организатор:Шар с номером:" + digit);
        for (PlayerInfo p : playerslist) {
            for (int a : p.getCard()) {
                if (a == digit) {
                    p.getCard().remove((Integer) digit);
                    System.out.println(p.getName() + " :Я угадал " + (6 - p.getCard().size()) + " раз: Цифра<" + digit + ">");
                    return;
                }
            }
        }
    }


    public void gameOver() {
        int prize = n * 100;
        int max = 6;
        int count = 0;
        for (PlayerInfo p : playerslist) {
            if (p.getCard().size() < max) {
                max = p.getCard().size();
            }
        }

        Iterator<PlayerInfo> iterator = playerslist.iterator();
        while (iterator.hasNext()) {
            PlayerInfo p = iterator.next();
            if (p.getCard().size() != max)
                iterator.remove();
        }

        for (PlayerInfo pe : playerslist) {
            int getprize = prize / playerslist.size();
            System.out.println("Организатор: " + pe.getName() + " выйграл: " + getprize + " Баксов");
            unregister(getprize);
        }

        if (playerslist.size() == n) {
            System.out.println("Вы все лохи!");
        }
    }


    public void register() {
        String str;
        for (int i = 0; i < n; i++) {
            str = "player_" + i;
            playerslist.add(new PlayerInfo(str, i, generateCard()));
        }
    }


    public void unregister(int p) {

        System.out.println("Ура!!!!Я выйграл " + p + " баксов!");

    }
}
