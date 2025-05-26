/*
package Saves;


import java.io.Serializable;

public class Player implements Serializable {
    public String name;
    public int score;


    public int getScore() {
        return score;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setScore(int score) {
        this.score = score;
    }


    public Player(String name){
            this.name = name;
            this.score = 0;
        }
    }


 */

package Saves;

import java.io.Serializable;

// Класс Player реализует интерфейс Serializable, что позволяет
// сериализовать объекты этого класса для сохранения в файл.
                             //Это интерфейс — идентификатор, который не имеет методов, но он указывает jvm, что объекты этого класса могут быть сериализованы
public class Player implements Serializable {
    // Поле, хранящее имя игрока.
    public String name;
    // Поле, хранящее счет игрока.
    public int score;

    // Метод для получения значения поля score (счет игрока).
    public int getScore() {
        return score;
    }

    // Метод для получения значения поля name (имя игрока).
    public String getName() {
        return name;
    }

    // Метод для установки значения поля name (имя игрока).
    public void setName(String name) {
        this.name = name;
    }

    // Метод для установки значения поля score (счет игрока).
    public void setScore(int score) {
        this.score = score;
    }

    // Конструктор класса Player, который принимает имя игрока
    // и инициализирует счет игрока равным 0.
    public Player(String name) {
        this.name = name;
        this.score = 0;
    }
}