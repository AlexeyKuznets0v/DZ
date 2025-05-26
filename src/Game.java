/*
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private Map gameMap;
    private Player player;
    private ArrayList<Record> records;

    public Game() {
        this.gameMap = new Map();
        this.player = new Player("Первый игрок");
        this.records = new ArrayList<>();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Добро пожаловать в игру! Выберите действие: ");

        while (true) {
            System.out.println("1. Играть\n2. Редактировать карту\n3. Сохранить игру\n4. Загрузить игру\n5. Выход");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    play();
                    break;
                case 2:
                    editMap(scanner);
                    break;
                case 3:
                    saveGame();
                    break;
                case 4:
                    loadGame();
                    break;
                case 5:
                    System.out.println("До свидания!");
                    return;
                default:
                    System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }

    private void play() {
        // Здесь происходит игровой процесс
        System.out.println("Игра началась на карте:");
        gameMap.displayMap();

        // Здесь могла бы быть логика битвы...

        // После завершения битвы:
        player.incrementWins();
        records.add(new Record(player.getName(), 10, "МояКарта")); // Пример добавления
    }

    private void editMap(Scanner scanner) {
        System.out.println("Введите координаты и тип элемента (x y type):");
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        String type = scanner.next();

        gameMap.editCell(x, y, type);
        System.out.println("Карта изменена.");
    }

    private void saveGame() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("game_save.bin"))) {
            out.writeObject(gameMap);
            out.writeObject(player);
            out.writeObject(records);
            System.out.println("Игра сохранена.");
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении игры: " + e.getMessage());
        }
    }

    private void loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("game_save.bin"))) {
            gameMap = (Map) in.readObject();
            player = (Player) in.readObject();
            records = (ArrayList<Record>) in.readObject();
            System.out.println("Игра загружена.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка при загрузке игры: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}

class Player implements Serializable {
    private String name;
    private int wins;

    public Player(String name) {
        this.name = name;
        this.wins = 0;
    }

    public void incrementWins() {
        this.wins++;
    }

    public String getName() {
        return name;
    }

    public int getWins() {
        return wins;
    }
}

class Map implements Serializable {
    private String[][] grid;

    public Map() {
        grid = new String[5][5]; // Пример карты 5x5
        initializeMap();
    }

    private void initializeMap() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = ".";
            }
        }
    }

    public void displayMap() {
        for (String[] row : grid) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    public void editCell(int x, int y, String type) {
        if (x >= 0 && x < grid.length && y >= 0 && y < grid[x].length) {
            grid[x][y] = type;
        } else {
            System.out.println("Неверные координаты.");
        }
    }
}

class Record implements Serializable {
    private String playerName;
    private int score;
    private String mapName;

    public Record(String playerName, int score, String mapName) {
        this.playerName = playerName;
        this.score = score;
        this.mapName = mapName;
    }

    @Override
    public String toString() {
        return "Игрок: " + playerName + ", Очки: " + score + ", Карта: " + mapName;
    }
}

 */
/*
import battlef.BattleField;

import Hero.Hero;
import battlef.BattleField;
import java.util.Scanner;

public class Game {
    public BattleField battleField;

    public void start() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя вашего героя:");
        String playerName = scanner.nextLine();
        Hero playerHero = new Hero(playerName, 100, 20, true); // Игрок
        Hero enemyHero = new Hero("Враг", 80, 15, false); // Противник

        battleField = new BattleField(playerHero, enemyHero);
        //battleField.startBattle(Hero Hero); // Начать бой

        displayStats(playerHero);
    }

    private void displayStats(Hero hero) {
        System.out.println("Количество побед " + hero.getWinCount());
    }

    public static void main(String[] args) throws InterruptedException {
        Game game = new Game();
        game.start();
    }
}


 */