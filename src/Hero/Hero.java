/*
package Hero;
import buildings.*;
import maps.*;
import units.*;

import java.util.HashMap;
import java.util.Map;

import java.io.IOException;
import java.util.logging.*;

public class Hero extends Path{
    public int health;
    public int gold;
    public int heroX;
    public int heroY;
    public Map<String, Integer> army;
    public boolean isStable = false;
    public boolean isGamerHero = false;
    public boolean isAvailable = false;
    public int move = 40;

    // Логгер для записи событий
    private static final Logger logger = Logger.getLogger(Hero.class.getName());

    // Настройка логгера
    static {
        try {
            // Устанавливаем обработчик для записи в файл
            FileHandler fileHandler = new FileHandler("hero_logging.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL); // Уровень логирования - все события
        } catch (IOException e) {
            System.err.println("Возникла ошибка при настройке логгера: " + e.getMessage());
        }
    }

    public Hero(int gold){
        this.gold = gold;
        health = 100;
        army = new HashMap<>();
        //this.design = "\uD83E\uDDD4";
        this.design = "И";

        // Логируем создание героя
        logger.info("Создан герой с " + this.gold + " золота и " + this.health + " здоровья.");
    }

    public void resetMoves() {

        logger.info("Герой восстанавливает свои ходы.");
        System.out.println("Восстановили 40 ходов");
        this.move = 40;
    }

    public void takeDamage(int damage) {
        health -= damage;
        logger.warning("Герой получил " + damage + " урона! Осталось здоровья: " + health);
        System.out.println("Герой получил " + damage + " урона! Текущее здоровье: " + health);

        if (health <= 0) {
            logger.severe("Герой погиб. Игра завершена!");
            System.out.println("Ваш герой уничтожен! Игра завершена.");
            endGame();
        }
    }
    public void endGame() {
        logger.severe("Игра завершена! Герой не выдержал испытаний.");
        System.out.println("Спасибо за игру! Надеемся, вы вернётесь.");
        System.exit(0);
    }


    public void handleUnitPurchase(Building house, String choice, int gold) {
        switch (choice) {
            case "1":
                BuySpearMan(gold, house);
                break;
            case "2":
                BuyCrossBowMan(gold, house);
                break;
            case "3":
                BuySwordsMan(gold, house);
                break;
            case "4":
                BuyCavalryman(gold, house);
                break;
            case "5":
                BuyPaladin(gold, house);
                break;
            default:
                System.out.println("Неверный выбор.");
                break;
        }
    }

    public void BuySpearMan(int gold, Building house){
        if(house.guardPost) {
            SpearMan guy = new SpearMan(0);

            int maxUnits = gold / guy.price;
            if (maxUnits > 0) {

                this.gold -= maxUnits * guy.price;
                String varior = "SpearMan";
                this.army.put(varior, this.army.getOrDefault(varior, 0) + maxUnits);

                System.out.println("Куплено " + maxUnits + " копейщиков.");
            }else{
                System.out.println("Недостаточно золота для покупки копейщиков.");
            }
        }
    }

    public void BuyCrossBowMan(int gold, Building house){
        if(house.towerOfCrossBowMen) {
            CrossBowMan guy = new CrossBowMan(0);

            int maxUnits = gold / guy.price;
            if (maxUnits > 0) {

                this.gold -= maxUnits * guy.price;
                String varior = "CrossBowMan";
                this.army.put(varior, army.getOrDefault(varior, 0) + maxUnits);

                System.out.println("Куплено " + maxUnits + " арбалетчиков.");
            }else{
                System.out.println("Недостаточно золота для покупки арбалетчиков.");
            }
        }
    }

    public void BuySwordsMan(int gold, Building house){
        if(house.armory) {
            SwordsMan guy = new SwordsMan(0);

            int maxUnits = gold / guy.price;
            if (maxUnits > 0) {

                this.gold -= maxUnits * guy.price;
                String varior = "SwordsMan";
                this.army.put(varior, army.getOrDefault(varior, 0) + maxUnits);

                System.out.println("Куплено " + maxUnits + " мечников.");
            }else{
                System.out.println("Недостаточно золота для покупки мечников.");
            }
        }
    }

    public void BuyCavalryman(int gold, Building house){
        if(house.arena){
            CavalryMan guy = new CavalryMan(0);

            int maxUnits = gold / guy.price;
            if (maxUnits > 0) {

                this.gold -= maxUnits * guy.price;
                String varior = "Cavalryman";
                this.army.put(varior, army.getOrDefault(varior, 0) + maxUnits);

                System.out.println("Куплено " + maxUnits + " кавалеристов.");
            }else{
                System.out.println("Недостаточно золота для покупки кавалеристов.");
            }

        }
    }

    public void BuyPaladin(int gold, Building house) {
        if (house.cathedral) {
            Paladin guy = new Paladin(0);

            int maxUnits = gold / guy.price;
            if (maxUnits > 0) {

                this.gold -= maxUnits * guy.price;
                String varior = "Paladin";
                this.army.put(varior, army.getOrDefault(varior, 0) + maxUnits);

                System.out.println("Куплено " + maxUnits + " паладинов.");
            } else {
                System.out.println("Недостаточно золота для покупки паладинов.");
            }
        }
    }
}

 */



/*

public class Hero {
    public int health;
    public int gold;
    public int heroX;
    public int heroY;
    public Map<String, Integer> army;
    public boolean isStable = false;
    public boolean isGamerHero = false;
    public boolean isAvailable = false;
    public int move = 40;

    // Логгер для записи событий
    private static final Logger logger = Logger.getLogger(Hero.class.getName());

    // Настройка логгера
    static {
        try {
            // Устанавливаем обработчик для записи в файл
            FileHandler fileHandler = new FileHandler("hero_logging.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL); // Уровень логирования - все события
        } catch (IOException e) {
            System.err.println("Возникла ошибка при настройке логгера: " + e.getMessage());
        }
    }

    public Hero(int gold) {
        this.gold = gold;
        health = 100;
        army = new HashMap<>();
        this.design = "И";

        // Логируем создание героя
        logger.info("Создан герой с " + this.gold + " золота и " + this.health + " здоровья.");
    }

    public void resetMoves() {
        logger.info("Герой восстанавливает свои ходы.");
        System.out.println("Восстановили 40 ходов");
        this.move = 40;
    }

    public void takeDamage(int damage) {
        health -= damage;
        logger.warning("Герой получил " + damage + " урона! Осталось здоровья: " + health);
        System.out.println("Герой получил " + damage + " урона! Текущее здоровье: " + health);

        if (health <= 0) {
            logger.severe("Герой погиб. Игра завершена!");
            System.out.println("Ваш герой уничтожен! Игра завершена.");
            endGame();
        }
    }

    public void endGame() {
        logger.severe("Игра завершена! Герой не выдержал испытаний.");
        System.out.println("Спасибо за игру! Надеемся, вы вернётесь.");
        System.exit(0);
    }

    public void moveHero(int x, int y) {
        logger.info("Герой делает шаг к координатам (" + x + ", " + y + ")");
        heroX = x;
        heroY = y;
    }

    public void spendGold(int amount) {
        if (gold >= amount) {
            gold -= amount;
            logger.info("Герой потратил " + amount + " золота. Остаток: " + gold);
        } else {
            logger.warning("Недостаточно золота для выполнения операции.");
        }
    }

    public void buyUnit(String unitType, int cost) {
        if (gold >= cost) {
            army.put(unitType, army.getOrDefault(unitType, 0) + 1);
            gold -= cost;
            logger.info("Герой купил юнита: " + unitType + ". Золото после покупки: " + gold);
        } else {
            logger.warning("Недостаточно золота для покупки юнита: " + unitType);
        }
    }
}

 */




package Hero;
import buildings.*;
import maps.*;
import units.*;
import UI.UI;

import java.util.HashMap;
import java.util.Map;

import static buildings.Tower.logger;


public class Hero extends Path{
    public int health;
    public int gold;
    public int heroX;
    public int heroY;
    public Map<String, Integer> army;
    public boolean isStable = false;
    public boolean isGamerHero = false;
    public boolean isAvailable = false;
    public int move = 40;
    private int winCount; // Подсчет побед
    public UI ui = new UI();

    public boolean isCafeUp = false;
    public boolean isSalonUp = false;
    public boolean isHotelUp = false;

    public boolean isDamageUp = false;

    public Hero(int gold){
        this.gold = gold;
        health = 100;
        army = new HashMap<>();
        //this.design = "\uD83E\uDDD4";
        this.design = "И";
        this.winCount = 0; // Инициализируем количество побед
    }

    public Hero(String playerName, int i, int i1, boolean b) {
        super();
    }

    public void incrementWinCount() {
        this.winCount++; // Увеличение количества побед
    }

    public int getWinCount() {
        return this.winCount; // Метод для получения количества побед
    }

    public void resetMoves() {
        System.out.println("Восстановили 40 ходов");
        this.move = 40;
        ui.printResetMoves(this);
    }

    public void takeDamage(int damage) {
        // Уменьшаем здоровье героя
        health -= damage;
        System.out.println("Герой получил " + damage + " урона! Текущее здоровье: " + health);
        // Проверка на здоровье
        if (health <= 0) {
            System.out.println("Ваш герой уничтожен! Игра завершена.");
            logger.severe("Ваш герой уничтожен! Игра завершена.");
            endGame(); // Вызываем метод завершения игры
        }
    }
    public void endGame() {
        logger.severe("Игра завершена.");
        // Логика завершения игры, например:
        System.out.println("Спасибо за игру! Надеемся, вы вернётесь.");
        System.exit(0); // Завершаем приложение
    }


    public void handleUnitPurchase(Building house, String choice, int gold) {
        logger.info("Попытка покупки юнита: " + choice + " стоимость: " + gold);

        switch (choice) {
            case "1":
                BuySpearMan(gold, house);
                break;
            case "2":
                BuyCrossBowMan(gold, house);
                break;
            case "3":
                BuySwordsMan(gold, house);
                break;
            case "4":
                BuyCavalryman(gold, house);
                break;
            case "5":
                BuyPaladin(gold, house);
                break;
            default:
                //System.out.println("Неверный выбор.");
                ui.invalidInput();
                break;
        }
    }

    public void BuySpearMan(int gold, Building house){
        if(house.guardPost) {
            SpearMan guy = new SpearMan(0, this.isDamageUp, this.isSalonUp, this.isHotelUp);

            int maxUnits = gold / guy.price;
            if (maxUnits > 0) {

                this.gold -= maxUnits * guy.price;
                String varior = "SpearMan";
                this.army.put(varior, this.army.getOrDefault(varior, 0) + maxUnits);

                //System.out.println("Куплено " + maxUnits + " копейщиков.");
                ui.printBuyingUnits(maxUnits);
            }else{
                //System.out.println("Недостаточно золота для покупки копейщиков.");
                ui.needMoreMoney();
            }
        }
    }

    public void BuyCrossBowMan(int gold, Building house){
        if(house.towerOfCrossBowMen) {
            CrossBowMan guy = new CrossBowMan(0, this.isDamageUp, this.isSalonUp, this.isHotelUp);

            int maxUnits = gold / guy.price;
            if (maxUnits > 0) {

                this.gold -= maxUnits * guy.price;
                String varior = "CrossBowMan";
                this.army.put(varior, army.getOrDefault(varior, 0) + maxUnits);

                //System.out.println("Куплено " + maxUnits + " арбалетчиков.");
                ui.printBuyingUnits(maxUnits);
            }else{
                //System.out.println("Недостаточно золота для покупки арбалетчиков.");
                ui.needMoreMoney();
            }
        }
    }

    public void BuySwordsMan(int gold, Building house){
        if(house.armory) {
            SwordsMan guy = new SwordsMan(0, this.isDamageUp, this.isSalonUp, this.isHotelUp);

            int maxUnits = gold / guy.price;
            if (maxUnits > 0) {

                this.gold -= maxUnits * guy.price;
                String varior = "SwordsMan";
                this.army.put(varior, army.getOrDefault(varior, 0) + maxUnits);

                //System.out.println("Куплено " + maxUnits + " мечников.");
                ui.printBuyingUnits(maxUnits);
            }else{
                //System.out.println("Недостаточно золота для покупки мечников.");
                ui.needMoreMoney();
            }
        }
    }

    public void BuyCavalryman(int gold, Building house){
        if(house.arena){
            CavalryMan guy = new CavalryMan(0, this.isDamageUp, this.isSalonUp, this.isHotelUp);

            int maxUnits = gold / guy.price;
            if (maxUnits > 0) {

                this.gold -= maxUnits * guy.price;
                String varior = "Cavalryman";
                this.army.put(varior, army.getOrDefault(varior, 0) + maxUnits);

                //System.out.println("Куплено " + maxUnits + " кавалеристов.");
                ui.printBuyingUnits(maxUnits);
            }else{
                //System.out.println("Недостаточно золота для покупки кавалеристов.");
                ui.needMoreMoney();
            }

        }
    }

    public void BuyPaladin(int gold, Building house) {
        if (house.cathedral) {
            Paladin guy = new Paladin(0, this.isDamageUp, this.isSalonUp, this.isHotelUp);

            int maxUnits = gold / guy.price;
            if (maxUnits > 0) {

                this.gold -= maxUnits * guy.price;
                String varior = "Paladin";
                this.army.put(varior, army.getOrDefault(varior, 0) + maxUnits);

                //System.out.println("Куплено " + maxUnits + " паладинов.");
                ui.printBuyingUnits(maxUnits);
            } else {
                //System.out.println("Недостаточно золота для покупки паладинов.");
                ui.needMoreMoney();
            }
        }
    }
}
