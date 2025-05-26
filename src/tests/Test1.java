/*
package tests;

import maps.*;
import UI.UI;
import Saves.*;
import Hero.*;

import java.io.*;
import java.util.Scanner;

public class Test1 {
    public static final String PLAYER1_SAVE = "C:"+ File.separator +"Users" + File.separator +"alex2803"+ File.separator +"IdeaProjects"+ File.separator +"untitled"+ File.separator +"src"+ File.separator +"Game"+ File.separator+ "saves"+ File.separator + "save1.bin";
    public static final String PLAYER2_SAVE = "C:"+ File.separator +"Users" + File.separator +"alex2803"+ File.separator +"IdeaProjects"+ File.separator +"untitled"+ File.separator +"src"+ File.separator +"Game"+ File.separator+ "saves"+ File.separator + "save2.bin";
    public static final String SCORES_FILE = "C:"+ File.separator +"Users" + File.separator +"alex2803"+ File.separator +"IdeaProjects"+ File.separator +"untitled"+ File.separator +"src"+ File.separator +"Game"+ File.separator+ "saves"+ File.separator + "scores.bin";
    //public static final String SCORES_FILE_JSON = "C:"+ File.separator +"Users" + File.separator +"alex2803"+ File.separator +"IdeaProjects"+ File.separator +"untitled"+ File.separator +"src"+ File.separator +"Game"+ File.separator+ "saves"+ File.separator + "test.json";
    //public static final String SCORES_FILE_XML = "C:"+ File.separator +"Users" + File.separator +"alex2803"+ File.separator +"IdeaProjects"+ File.separator +"untitled"+ File.separator +"src"+ File.separator +"Game"+ File.separator+ "saves"+ File.separator + "persons.xml";
    public static final String TEST_SCORES_FILE = "C:"+ File.separator +"Users" + File.separator +"alex2803"+ File.separator +"IdeaProjects"+ File.separator +"untitled"+ File.separator +"src"+ File.separator +"Game"+ File.separator+ "saves"+ File.separator + "Test_scores.bin";
    public static final String TEST_SAVE = "C:"+ File.separator +"Users" + File.separator +"alex2803"+ File.separator +"IdeaProjects"+ File.separator +"untitled"+ File.separator +"src"+ File.separator +"Game"+ File.separator+ "saves"+ File.separator + "testing_save.bin";


    public static final String CROSS_MAP = "CROSS";
    public static final String DIAGONAL_MAP = "DIAGONAL";

    public static void main(String[] args){
        int height = 10;
        int weight = 10;
        GameMap map;
        Scanner scanner = new Scanner(System.in);
        UI ui = new UI();

        Player player1 = new Player("Игрок1");
        Player player2 = new Player("Игрок2");

        try{
            //Savings savings = new Savings(SCORES_FILE, SCORES_FILE_JSON, SCORES_FILE_XML);
            Savings savings = new Savings(SCORES_FILE);


            savings.loadScores(player1, player2);

            ui.choosePlayer();

            int playerChoice = scanner.nextInt();
            scanner.nextLine();

            Player currentPlayer = (playerChoice == 1) ? player1 : player2;

            // Меню загрузки/новой игры
            ui.chooseLoad();

            int choice = scanner.nextInt();
            scanner.nextLine(); // очистка буфера

            if (choice == 2) {
                try {
                    map = savings.loadGame(currentPlayer == player1 ? PLAYER1_SAVE : PLAYER2_SAVE);
                    ui.saveSuccess();
                } catch (IOException | ClassNotFoundException e) {
                    ui.saveError();

                    ui.chooseMap();

                    int mapChoice = scanner.nextInt();
                    scanner.nextLine();

                    String mapType = (mapChoice == 1) ? CROSS_MAP : DIAGONAL_MAP;

                    map = new GameMap(height, weight, currentPlayer.name, mapType, true, false);
                    savings.saveGame(currentPlayer == player1 ? PLAYER1_SAVE : PLAYER2_SAVE, map);
                }
            } else {

                ui.chooseMap();
                int mapChoice = scanner.nextInt();
                scanner.nextLine();

                String mapType = (mapChoice == 1) ? CROSS_MAP : DIAGONAL_MAP;

                ui.chooseGenerateMap();

                String choose = scanner.nextLine().toLowerCase();
                if(choose.equals("mod")){
                    map = new GameMap(height, weight, currentPlayer.name, mapType, true, true);
                }else{
                    map = new GameMap(height, weight, currentPlayer.name, mapType, true, false);
                }

                savings.saveGame(currentPlayer == player1 ? PLAYER1_SAVE : PLAYER2_SAVE, map);
            }

            ui.chooseMove();

            while (true) {
                boolean playerMovesExhausted = false;

                while(!playerMovesExhausted) {
                    playerMovesExhausted = true;

                    if(map.isGAmeOver){
                        break;
                    }

                    if (map.heroes[0].move > 0 && canHeroMove(map.heroes[0], height, weight, map.mapType)) {
                        playerMovesExhausted = false;
                        ui.yourStep();
                        String input = scanner.nextLine().toLowerCase();

                        switch (input) {
                            case "w":
                                map.moveHero(map.heroes[0], 0, -1);
                                break;
                            case "wd", "dw":
                                map.moveHero(map.heroes[0], 1, -1);
                                break;
                            case "wa", "aw":
                                map.moveHero(map.heroes[0], -1, -1);
                                break;
                            case "as", "sa":
                                map.moveHero(map.heroes[0], -1, 1);
                                break;
                            case "sd", "ds":
                                map.moveHero(map.heroes[0], 1, 1);
                                break;
                            case "s":
                                map.moveHero(map.heroes[0], 0, 1);
                                break;
                            case "a":
                                map.moveHero(map.heroes[0], -1, 0);
                                break;
                            case "d":
                                map.moveHero(map.heroes[0], 1, 0);
                                break;
                            case "q":
                                ui.printExit();
                                return;
                            case "save":
                                savings.saveGame(currentPlayer == player1 ? PLAYER1_SAVE : PLAYER2_SAVE, map);
                                ui.saveGame();
                                break;
                            default:
                                ui.invalidInput();
                        }
                    }
                }

                // Ход компьютера (остаётся без изменений)
                boolean computerMovesExhausted = false;
                while (!computerMovesExhausted && !map.isBattleStarted && map.computerIsAlive) {
                    if(map.isGAmeOver) break;
                    computerMovesExhausted = true;
                    if (map.heroes[1].move > 0 && canHeroMove(map.heroes[1], height, weight, map.mapType)) {
                        computerMovesExhausted = false;
                        ui.compStep();
                        if(map.mapType.equals(CROSS_MAP)){
                            map.moveHero(map.heroes[1], -1, 0);
                        }else{
                            map.moveHero(map.heroes[1], -1, -1);
                        }
                    }
                }

                if(map.isGAmeOver) {
                    currentPlayer.score += map.isGamerWinner ? 100 : 50;

                    savings.saveScores(player1, player2);


                    ui.printScores(player1, player2);
                    map.clearSaveFile();
                    break;
                }

                map.endRound(map.heroes);
                savings.saveGame(currentPlayer == player1 ? PLAYER1_SAVE : PLAYER2_SAVE, map);
                ui.saveGame();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean canHeroMove(Hero hero, int height, int weight, String mapType) {
        int currentX = hero.heroX;
        int currentY = hero.heroY;
        if (mapType.equals(CROSS_MAP)) {
            if ((currentY == height / 2 || currentX == weight / 2) && hero.move >= 1) {
                return true;
            } else if (hero.move >= 2) {
                return true;
            }
        }else {
            if((currentX == currentY || currentX == height - currentY - 1) && hero.move >= 1){
                return true;
            } else if (hero.move >= 2) {
                return true;
            }
        }
        return false;
    }
}

 */


package tests;

// Импорт необходимых пакетов для обработки карт, пользовательского интерфейса, сохранений и классов героя
import maps.*;
import UI.UI;
import Saves.*;
import Hero.*;

import java.io.File;
import java.io.*;
import java.util.Scanner;
import java.io.IOException;

// Класс Test1 служит основным исполняемым классом для логики игры
public class Test1 {
    // Константы для путей к файлам, в которых будут храниться сохранения игр и счета

    //public static final String PLAYER1_SAVE = "C:"+ File.separator +"Пользователи" + File.separator +"Алексей"+ File.separator +"IdeaProjects"+ File.separator +"untitled"+ File.separator +"src"+ File.separator +"Game"+ File.separator+ "saves"+ File.separator + "save1.bin";
    /*
    public static final String PLAYER2_SAVE = "C:"+ File.separator +"Пользователи" + File.separator +"Алексей"+ File.separator +"IdeaProjects"+ File.separator +"untitled"+ File.separator +"src"+ File.separator +"Game"+ File.separator+ "saves"+ File.separator + "save2.bin";
    public static final String SCORES_FILE = "C:"+ File.separator +"Пользователи" + File.separator +"Алексей"+ File.separator +"IdeaProjects"+ File.separator +"untitled"+ File.separator +"src"+ File.separator +"Game"+ File.separator+ "saves"+ File.separator + "scores.bin";
    public static final String TEST_SCORES_FILE = "C:"+ File.separator +"Пользователи" + File.separator +"Алексей"+ File.separator +"IdeaProjects"+ File.separator +"untitled"+ File.separator +"src"+ File.separator +"Game"+ File.separator+ "saves"+ File.separator + "Test_scores.bin";
    public static final String TEST_SAVE = "C:"+ File.separator +"Пользователи" + File.separator +"Алексей"+ File.separator +"IdeaProjects"+ File.separator +"untitled"+ File.separator +"src"+ File.separator +"Game"+ File.separator+ "saves"+ File.separator + "testing_save.bin";


     */


    public static final String PLAYER1_SAVE = "C:\\Пользователи\\Алексей\\IdeaProjects\\untitled\\src\\Game\\saves\\save1.bin";
    //public static final String PLAYER1_SAVE = "C:/Пользователи/Алексей/IdeaProjects/untitled/src/Game/saves/save1.bin";



    public static final String PLAYER2_SAVE = "C:\\Пользователи\\Алексей\\IdeaProjects\\untitled\\src\\Game\\saves\\save2.bin";
    public static final String SCORES_FILE = "C:\\Пользователи\\Алексей\\IdeaProjects\\untitled\\src\\Game\\saves\\scores.bin";
    //public static final String TEST_SCORES_FILE = "C:\\Пользователи\\Алексей\\IdeaProjects\\untitled\\src\\Game\\saves\\Test_scores.bin";
    public static final String TEST_SAVE = "C:\\Пользователи\\Алексей\\IdeaProjects\\untitled\\src\\Game\\saves\\testing_save.bin";

    //"C:\\Users\\Алексей\\IdeaProjects\\untitled\\src\\Game\\saves\\save1.bin"

    //public static final String PLAYER1_SAVE = "C:\\Users\\Алексей\\OneDrive\\Desktop\\Технология программирования\\ЛР1\\save1.bin";

    // Константы для типов карт
    public static final String CROSS_MAP = "CROSS";
    public static final String DIAGONAL_MAP = "DIAGONAL";

    // Метод main — точка входа в программу
    public static void main(String[] args) {
        int height = 10; // Высота карты
        int weight = 10; // Ширина карты
        GameMap map; // Объект карты
        Scanner scanner = new Scanner(System.in); // Сканер для ввода данных пользователя
        UI ui = new UI(); // Объект пользовательского интерфейса

        // Создание двух игроков
        Player player1 = new Player("Игрок1");
        Player player2 = new Player("Игрок2");

        try {
            // Инициализация объекта для сохранения данных игрового процесса
            Savings savings = new Savings(SCORES_FILE);
            savings.loadScores(player1, player2); // Загрузка очков игроков

            ui.choosePlayer(); // Запрос выбора игрока
            int playerChoice = scanner.nextInt(); // Получение выбора игрока
            scanner.nextLine(); // Очистка буфера ввода

            // Определение текущего игрока
            Player currentPlayer = (playerChoice == 1) ? player1 : player2;

            // Меню загрузки или начала новой игры
            ui.chooseLoad();
            int choice = scanner.nextInt(); // Получение выбора загружать ли игру
            scanner.nextLine(); // Очистка буфера

            // Если выбор - загрузка игры
            if (choice == 2) {
                try {
                    // Загрузка состояния игры для текущего игрока
                    map = savings.loadGame(currentPlayer == player1 ? PLAYER1_SAVE : PLAYER2_SAVE);
                    ui.saveSuccess(); // Успешное извлечение данных
                } catch (IOException | ClassNotFoundException e) {
                    ui.saveError(); // Ошибка при загрузке

                    // В случае ошибки запрашиваем выбор карты
                    ui.chooseMap();
                    int mapChoice = scanner.nextInt(); // Получаем выбор типа карты
                    scanner.nextLine(); // Очистка буфера

                    // Определяем тип карты
                    String mapType = (mapChoice == 1) ? CROSS_MAP : DIAGONAL_MAP;

                    // Создаем новую карту
                    map = new GameMap(height, weight, currentPlayer.name, mapType, true, false);
                    savings.saveGame(currentPlayer == player1 ? PLAYER1_SAVE : PLAYER2_SAVE, map); // Сохраняем новую игру
                }
            } else {
                // Если выбор - новая игра
                ui.chooseMap();
                int mapChoice = scanner.nextInt(); // Получаем выбор карты
                scanner.nextLine(); // Очистка буфера

                // Определяем тип карты и способ генерации
                String mapType = (mapChoice == 1) ? CROSS_MAP : DIAGONAL_MAP;

                ui.chooseGenerateMap(); // Запрос способа генерации карты
                String choose = scanner.nextLine().toLowerCase();

                // Генерация карты в зависимости от выбора
                if (choose.equals("mod")) {
                    map = new GameMap(height, weight, currentPlayer.name, mapType, true, true);
                } else {
                    map = new GameMap(height, weight, currentPlayer.name, mapType, true, false);
                }

                // Сохраняем новую игру
                savings.saveGame(currentPlayer == player1 ? PLAYER1_SAVE : PLAYER2_SAVE, map);
            }

            ui.chooseMove(); // Запрос хода игрока

            // Главный игровой цикл
            while (true) {
                boolean playerMovesExhausted = false;

                while (!playerMovesExhausted) {
                    playerMovesExhausted = true;

                    // Проверяем, окончена ли игра
                    if (map.isGAmeOver) {
                        break;
                    }

                    // Проверяем возможность хода для первого игрока
                    if (map.heroes[0].move > 0 && canHeroMove(map.heroes[0], height, weight, map.mapType)) {
                        playerMovesExhausted = false; // Игра продолжается
                        ui.yourStep(); // Запрос хода игрока
                        String input = scanner.nextLine().toLowerCase(); // Получаем ввод от игрока

                        // Обработка ввода для движения героя
                        switch (input) {
                            case "w":
                                map.moveHero(map.heroes[0], 0, -1);
                                break;
                            case "wd":
                            case "dw":
                                map.moveHero(map.heroes[0], 1, -1);
                                break;
                            case "wa":
                            case "aw":
                                map.moveHero(map.heroes[0], -1, -1);
                                break;
                            case "as":
                            case "sa":
                                map.moveHero(map.heroes[0], -1, 1);
                                break;
                            case "sd":
                            case "ds":
                                map.moveHero(map.heroes[0], 1, 1);
                                break;
                            case "s":
                                map.moveHero(map.heroes[0], 0, 1);
                                break;
                            case "a":
                                map.moveHero(map.heroes[0], -1, 0);
                                break;
                            case "d":
                                map.moveHero(map.heroes[0], 1, 0);
                                break;
                            case "q":
                                ui.printExit(); // Завершение игры
                                return; // Выход из программы
                            case "save":
                                // Сохранение игры
                                savings.saveGame(currentPlayer == player1 ? PLAYER1_SAVE : PLAYER2_SAVE, map);
                                ui.saveGame();
                                break;
                            default:
                                // Обработка неверного ввода
                                ui.invalidInput();
                        }
                    }
                }

                // Ход компьютера
                boolean computerMovesExhausted = false;
                while (!computerMovesExhausted && !map.isBattleStarted && map.computerIsAlive) {
                    if (map.isGAmeOver) break; // Проверка конца игры
                    computerMovesExhausted = true;
                    // Проверка возможности хода для компьютера
                    if (map.heroes[1].move > 0 && canHeroMove(map.heroes[1], height, weight, map.mapType)) {
                        computerMovesExhausted = false; // Компьютер продолжает ходить
                        ui.compStep(); // Сообщение о ходе компьютера
                        if (map.mapType.equals(CROSS_MAP)) {
                            map.moveHero(map.heroes[1], -1, 0); // Ход для карты типа CROSS
                        } else {
                            map.moveHero(map.heroes[1], -1, -1); // Ход для карты типа DIAGONAL
                        }
                    }
                }

                // Проверка окончание игры после ходов
                if (map.isGAmeOver) {
                    // Обновление счета игрока
                    currentPlayer.score += map.isGamerWinner ? 100 : 50;
                    savings.saveScores(player1, player2); // Сохранение счетов обоих игроков
                    ui.printScores(player1, player2); // Вывод счетов
                    map.clearSaveFile(); // Очистка файла сохранений
                    break; // Завершение главного цикла
                }

                // Завершение раунда
                map.endRound(map.heroes);
                savings.saveGame(currentPlayer == player1 ? PLAYER1_SAVE : PLAYER2_SAVE, map); // Сохранение игры в конце раунда
                ui.saveGame();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Обработка исключений
        }
    }

    // Метод для проверки возможности передвижения героя
    public static boolean canHeroMove(Hero hero, int height, int weight, String mapType) {
        int currentX = hero.heroX; // Текущая координата X героя
        int currentY = hero.heroY; // Текущая координата Y героя
        // Проверка типа карты и условий перемещения
        if (mapType.equals(CROSS_MAP)) {
            // Условия для пересечения карты
            if ((currentY == height / 2 || currentX == weight / 2) && hero.move >= 1) {
                return true; // Перемещение возможно
            } else if (hero.move >= 2) {
                return true; // Если остались ходы
            }
        } else {
            // Условия для диагональной карты
            if ((currentX == currentY || currentX == height - currentY - 1) && hero.move >= 1) {
                return true; // Перемещение возможно
            } else if (hero.move >= 2) {
                return true; // Если остались ходы
            }
        }
        return false; // Перемещение невозможно
    }
}