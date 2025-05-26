/*
import maps.GameMap;
import Hero.Hero;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int height = 10; // Высота игрового поля
        int weight = 10; // Ширина игрового поля
        // Массив для хранения двух героев
        Hero[] heroes = new Hero[2];

        for (int i = 0; i < heroes.length; i++) {
            heroes[i] = new Hero(100); // Инициализация каждого героя с 100 единицами золота
        }

        GameMap map = new GameMap(height, weight, heroes); // Создание карты игры с заданными размерами и героями
        Scanner scanner = new Scanner(System.in); // Создание сканера для ввода данных от пользователя


        while (true) {
            boolean playerMovesExhausted = false; // Флаг заканчивания ходов игрока

            while (!playerMovesExhausted) {
                playerMovesExhausted = true; // Предполагаем, что ходы игрока закончены

                if (heroes[0].move > 0 && canHeroMove(heroes[0], height, weight)) {
                    playerMovesExhausted = false; // Ходы игрока не закончены
                    System.out.print("Ваш ход (W,A,S,D для передвижения, Q для выхода): ");
                    String input = scanner.nextLine().toLowerCase(); // Чтение ввода игрока
                    switch (input) {
                        case "w":
                            map.moveHero(heroes[0], 0, -1); // Движение вверх
                            break;
                        case "s":
                            map.moveHero(heroes[0], 0, 1); // Движение вниз
                            break;
                        case "a":
                            map.moveHero(heroes[0], -1, 0); // Движение влево
                            break;
                        case "d":
                            map.moveHero(heroes[0], 1, 0); // Движение вправо
                            break;
                        case "q":
                            System.out.println("Выход из игры..."); // Выход из игры
                            return;
                        default:
                            System.out.println("Неверный ввод."); // Неверный ввод
                    }
                }
            }

            boolean computerMovesExhausted = false; // Флаг заканчивания ходов компьютера

            while (!computerMovesExhausted && !map.isBattleStarted && GameMap.computerIsAlive) {
                computerMovesExhausted = true; // Предположительно, ходы компьютера закончены

                if (heroes[1].move > 0 && canHeroMove(heroes[1], height, weight)) {
                    computerMovesExhausted = false; // Ходы компьютера не закончены
                    System.out.println("Ход компьютера...");
                    try {
                        map.moveHero(heroes[1], 0, 0); // Двигать компьютерного героя вправо
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e); // Обработка исключения
                    }
                }
            }

            map.endRound(heroes); // Завершение раунда
            System.out.println("Раунд завершен. Ходы восстановлены.");
        }
    }


    public static boolean canHeroMove(Hero hero, int height, int width) {
        int currentX = hero.heroX; // Текущие координаты героя X
        int currentY = hero.heroY; // Текущие координаты героя Y

        // Проверка на возможность движения героя с учетом оставшихся очков перемещения и границ карты
        return (hero.move > 0) && (currentX >= 0 && currentX < width) && (currentY >= 0 && currentY < height);
    }
}

 */

/*
import maps.GameMap;
import Hero.Hero;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int height = 10; // Высота игрового поля
        int weight = 10; // Ширина игрового поля
        Hero[] heroes = new Hero[2]; // Массив для хранения двух героев

        for (int i = 0; i < heroes.length; i++) {
            heroes[i] = new Hero(100); // Инициализация каждого героя с 100 единицами золота
        }

        GameMap map = new GameMap(height, weight, heroes); // Создание карты игры с заданными размерами и героями
        Scanner scanner = new Scanner(System.in); // Создание сканера для ввода данных от пользователя


        while (true) {
            boolean playerMovesExhausted = false; // Флаг заканчивания ходов игрока

            while (!playerMovesExhausted) {
                playerMovesExhausted = true; // Предполагаем, что ходы игрока закончены

                if (heroes[0].move > 0 && canHeroMove(heroes[0], height, weight)) {
                    playerMovesExhausted = false; // Ходы игрока не закончены
                    System.out.print("Ваш ход W,A,S,D для передвижения; Q для выхода: ");
                    String input = scanner.nextLine().toLowerCase(); // Чтение ввода игрока
                    switch (input) {
                        case "w":
                            map.moveHero(heroes[0], 0, -1); // Движение вверх
                            break;
                        case "s":
                            map.moveHero(heroes[0], 0, 1); // Движение вниз
                            break;
                        case "a":
                            map.moveHero(heroes[0], -1, 0); // Движение влево
                            break;
                        case "d":
                            map.moveHero(heroes[0], 1, 0); // Движение вправо
                            break;
                        case "q":
                            System.out.println("Выход из игры..."); // Выход из игры
                            return;
                        default:
                            System.out.println("Неверный ввод."); // Неверный ввод
                    }
                }
            }

            boolean computerMovesExhausted = false; // Флаг заканчивания ходов компьютера

            while (!computerMovesExhausted && !map.isBattleStarted && GameMap.computerIsAlive) {
                computerMovesExhausted = true; // Предположительно, ходы компьютера закончены

                if (heroes[1].move > 0 && canHeroMove(heroes[1], height, weight)) {
                    computerMovesExhausted = false; // Ходы компьютера не закончены
                    System.out.println("Ход компьютера...");
                    try {
                        map.moveHero(heroes[1], 1, 0); // Двигать компьютерного героя вправо
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e); // Обработка исключения
                    }
                }
            }

            map.endRound(heroes); // Завершение раунда
            System.out.println("Раунд завершен. Ходы восстановлены.");
        }
    }

    private static boolean canHeroMove(Hero hero, int height, int width) {
        int currentX = hero.heroX; // Текущие координаты героя X
        int currentY = hero.heroY; // Текущие координаты героя Y

        // Проверка на возможность движения героя с учетом оставшихся очков перемещения и границ карты
        return (hero.move > 0) && (currentX >= 0 && currentX < width) && (currentY >= 0 && currentY < height);
    }
}


 */