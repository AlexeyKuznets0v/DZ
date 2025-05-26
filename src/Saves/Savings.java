/*
package Saves;

import UI.UI;
import maps.GameMap;
import java.io.*;

public class Savings {

    UI ui = new UI();
   // public String SCORES_FILE_JSON;
    public String SCORES_FILE;
    //public String SCORES_FILE_XML;
    public Savings(String SCORES_FILE){
    //public Savings(String SCORES_FILE, String SCORES_FILE_JSON, String SCORES_FILE_XML){
        this.SCORES_FILE = SCORES_FILE;
        //this.SCORES_FILE_JSON = SCORES_FILE_JSON;
        //this.SCORES_FILE_XML = SCORES_FILE_XML;
    }

    public void saveScores(Player p1, Player p2) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SCORES_FILE))) {
            oos.writeObject(p1);
            oos.writeObject(p2);
        }
    }


    public void loadScores(Player p1, Player p2) {
        File file = new File(SCORES_FILE);
        if (!file.exists() || file.length() == 0) {
            ui.fileNotFound();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Player savedP1 = (Player) ois.readObject();
            Player savedP2 = (Player) ois.readObject();

            p1.score = savedP1.score;
            p2.score = savedP2.score;
        } catch (EOFException e) {
            ui.fileNotFound();
        } catch (IOException | ClassNotFoundException e) {
            ui.fileNotFound();
        }
    }
    public void saveGame(String filename, GameMap game) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(game);
        }
    }

    public GameMap loadGame(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (GameMap) ois.readObject();
        }
    }
}

 */

package Saves;

// Импортируем необходимые классы: UI для пользовательского интерфейса, GameMap для карты игры, и классы для работы с файлами
import UI.UI;
import maps.GameMap;
import maps.Path;

import java.io.*;

// Класс для сохранения и загрузки данных игры
public class Savings {

    // Создаём экземпляр UI для взаимодействия с пользователем
    UI ui = new UI();

    // Путь к файлу для сохранения результатов (результаты могут быть в формате JSON, XML, и т.д.)
    public String SCORES_FILE;


    // Конструктор класса Savings, принимающий путь к файлу сохранения
    public Savings(String SCORES_FILE) {
        this.SCORES_FILE = SCORES_FILE; // Инициализируем путь к файлу
    }


    // Метод для сохранения результатов игроков в файл
    public void saveScores(Player p1, Player p2) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SCORES_FILE))) {
            // Записываем объекты игроков в файл
            oos.writeObject(p1); //ПОЛЬЗОВАТЕЛЬСКАЯ СЕРИАЛИЗАЦИЯ
            oos.writeObject(p2);

        }
    }

    // Метод для загрузки результатов игроков из файла
    public void loadScores(Player p1, Player p2) {
        // Создаём объект File для проверки существования файла
        File file = new File(SCORES_FILE);
        // Проверяем, существует ли файл и содержит ли он данные
        if (!file.exists() || file.length() == 0) {
            ui.fileNotFound(); // Если файл не найден, выводим уведомление
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            // Читаем объекты игроков из файла
            Player savedP1 = (Player) ois.readObject(); // ПОЛЬЗОВАТЕЛЬСКАЯ СЕРИАЛИЗАЦИЯ
            Player savedP2 = (Player) ois.readObject();

            // Восстанавливаем состояние игроков
            p1.score = savedP1.score;
            p2.score = savedP2.score;
        } catch (EOFException e) {
            ui.fileNotFound(); // Если достигнут конец файла, выводим уведомление
        } catch (IOException | ClassNotFoundException e) {
            ui.fileNotFound(); // При возникновении ошибок также выводим уведомление
        }
    }

    /*
    // Метод для сохранения состояния игры в файл
    public void saveGame(String filename, GameMap game) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            // Записываем объект карты игры в файл
            oos.writeObject(game);
        }
    }
     */
    public void saveGame(String filename, GameMap game) throws IOException {
        File file = new File(filename);
        file.getParentFile().mkdirs(); // Creates the directories if they don't exist
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(game);
            //oos.writeObject(map); // Необходимо убедиться, что все поля map сериализуются
        }
    }


    /*
    // Метод для загрузки состояния игры из файла
    public GameMap loadGame(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            //GameMap map = (GameMap) ois.readObject();
            // Читаем объект карты игры из файла и возвращаем его
            return (GameMap) ois.readObject();
        }
    }

     */
    public GameMap loadGame(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            GameMap map = (GameMap) ois.readObject();
            if (map.world == null) {
                map.world = new Path[map.height][map.weight]; // Инициализация поля
            }
            return map;
        }
    }
}