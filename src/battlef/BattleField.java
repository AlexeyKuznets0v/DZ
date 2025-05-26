package battlef;

import Hero.Hero;
import buildings.Tower;
import units.*;
import maps.*;
import UI.UI;

import java.io.*;
import java.util.*;

import static buildings.Tower.logger;

public class BattleField {
    public final int height = 5; // Высота поля
    public final int weight = 10; // Ширина поля

    public GameMap gameMap;
    public Path[][] battle = new Path[height][weight]; // Создание двумерного массива для поля боя

    public UI ui;

    //private static final List<Record> records = new ArrayList<>(); // Список рекордов
    //private final Scanner scanner = new Scanner(System.in); // Сканер для ввода

    public BattleField(Hero hero1, Hero hero2, GameMap gameMap, UI ui) throws InterruptedException {
        this.ui = ui;
        this.gameMap = gameMap;
        // Инициализация игрового поля
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < weight; j++) {
                battle[i][j] = new Let(); // Заполнение поля пустыми клетками
            }
        }

        // Установка юнитов на поле в зависимости от того, кто из героев является игроком
        if (hero1.isGamerHero) {
            placeUnits(hero1, 0); // Размещение юнитов игрока на левой стороне
            placeUnits(hero2, weight - 1); // Размещение юнитов противника на правой стороне
        } else {
            placeUnits(hero2, 0); // Размещение юнитов противника на левой стороне
            placeUnits(hero1, weight - 1); // Размещение юнитов игрока на правой стороне
        }

        updateDisplay(); // Обновление отображения поля боя
    }




    public void placeUnits(Hero hero, int startX) {
        // Размещение юнитов героя на поле
        int i = 0;
        for (Map.Entry<String, Integer> entry : hero.army.entrySet()) {
            String unit = entry.getKey(); // Получаем тип юнита
            int count = entry.getValue(); // Получаем количество юнитов

            if (count > 0) { // Если количество юнитов больше нуля
                Unit newUnit = createUnit(unit, count, hero); // Создаем нового юнита
                newUnit.unitX = startX; // Устанавливаем координату X
                newUnit.unitY = i; // Устанавливаем координату Y
                newUnit.isGamerUnit = hero.isGamerHero; // Помечаем юниты игрока
                battle[i][startX] = newUnit; // Размещаем юнита на поле
                ui.spawnUnit(unit, startX, i);
                //System.out.println("Добавлен юнит: " + unit + " в позицию (" + i + ", " + startX + ")");
                i++;
            }
        }
    }

    /*
    public void startBattle(Hero hero1, Hero hero2) throws InterruptedException {
        Scanner scanner = new Scanner(System.in); // Создаем сканер для ввода пользователя

        while (true) { // Основной цикл боя
            playerTurn(scanner); // Ход игрока
            updateDisplay(); // Обновление отображения после хода игрока

            // Проверка, завершилась ли битва
            if (isBattleOver(hero1, hero2)) {
                if (hero1.isGamerHero) {
                    hero1.incrementWinCount(); // Увеличиваем счетчик побед игрока
                    }
                    System.out.println("Бой завершен");
                    break; // Выход из цикла, если бой завершен
                }

                enemyTurn(); // Ход противника
                updateDisplay(); // Обновление отображения после хода противника

                // Проверка, завершилась ли битва
                if (isBattleOver(hero1, hero2)) {
                    System.out.println("Бой завершен");
                    break; // Выход из цикла, если бой завершен
                }
            }
        }

     */

    public void startBattle(String isComputer) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        while(true){
            playerTurn(scanner);
            updateDisplay();

            if(isBattleOver(isComputer)){
                ui.endFight();
                break;
            }

            enemyTurn();
            updateDisplay();

            if(isBattleOver(isComputer)){
                ui.endFight();
                break;
            }
        }
    }

    public void playerTurn(Scanner scanner) throws InterruptedException {
        logger.info("Начался ход игрока.");
        ui.yourStep();
        //System.out.println("Ваш ход"); // Уведомление о начале хода игрока
        resetMovedStatus(); // Сбрасываем статус перемещения юнитов
        // Проходим по полю и выполняем действия игрока
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < weight; j++) {
                if (battle[i][j] instanceof Unit && ((Unit) battle[i][j]).isGamerUnit) {
                    Unit unit = (Unit) battle[i][j]; // Получаем юнит игрока
                    if (unit.count > 0 && !unit.isMoved) { // Проверяем, что юнит жив и не движется
                        //System.out.println("Ход юнита: " + unit.design + " в позиции (" + i + ", " + j + ")");
                        //unit.act(battle, scanner); // Действие юнита
                        unit.act(battle, i, j, scanner, ui);
                        updateDisplay(); // Обновление отображения после действия юнита
                    }
                }
            }
        }
    }

    public void enemyTurn() throws InterruptedException {
        logger.info("Враг начинает действие.");
        resetMovedStatus(); // Сбрасываем статус движения у противника
        ui.compStep();
        //System.out.println("Ход врага"); // Уведомление о начале хода противника
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < weight; j++) {
                if(battle[i][j] instanceof Unit && !((Unit)battle[i][j]).isGamerUnit){
                    Unit unit = (Unit)battle[i][j];
                    ui.printUnitStats(unit, i, j);
                    enemyAct(unit);
                }
            }
        }
        /*
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < weight; j++) {
                if (battle[i][j] instanceof Unit && !((Unit) battle[i][j]).isGamerUnit) {
                    Unit unit = (Unit) battle[i][j]; // Получаем юнит противника
                    // Вывод информации о юните
                    System.out.println("Сейчас ходит юнит " + unit.design);
                    System.out.println("Здоровье одного юнита - " + unit.HP);
                    System.out.println("Урон одного юнита - " + unit.totalDamage);
                    System.out.println("Максимальное здоровье юнита " + unit.totalHP);
                    System.out.println("Максимальная дистанция атаки юнита - " + unit.distance);
                    System.out.println("Максимальная дистанция перемещения - " + unit.move);
                    System.out.println("Максимальный урон юнита - " + unit.totalDamage);
                    enemyAct(unit); // Действие противника
                }
            }
        }
         */
        updateDisplay(); // Обновление отображения после действия противника
    }

    public void resetMovedStatus() {
        // Сброс статуса движения для всех юнитов
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < weight; j++) {
                if (battle[i][j] instanceof Unit) {
                    Unit unit = (Unit) battle[i][j];
                    unit.isMoved = false; // Сбрасываем статус перемещения
                }
            }
        }
    }

    public void enemyAct(Unit unit) {
        // Действия юнита противника
        if (unit.isMoved) {
            return; // Если юнит уже двигался или атаковал, пропускаем его ход
        }

        Unit target = findPlayerUnitInRange(unit); // Находим игрока в зоне атаки

        if(target != null){
            target.takeDamage(unit.totalDamage);
            ui.printDamageLeft(target);
            if (target.totalHP <= 0){
                ui.printUnitDie(target);
                battle[target.unitY][target.unitX] = new Let();
            }
            unit.isMoved = true;
        }else{
            moveUnitTowardsPlayer(unit);
        }

        /*
        if (target != null) { // Если цель найдена
            System.out.println("Нанесено урона: " + unit.totalDamage); // Информируем об уроне
            target.takeDamage(unit.totalDamage); // Наносим урон
            System.out.println("Осталось здоровья у вашего юнита " + target.totalHP); // Информируем о здоровье
            if (target.totalHP <= 0) { // Проверка на смерть юнита
                System.out.println("Юнит " + target.design + " погиб");
                battle[target.unitY][target.unitX] = new Let(); // Удаляем юнита с поля
            }
            unit.isMoved = true; // Помечаем, что юнит двигался
        } else { // Если цели нет, двигаемся к игроку
            moveUnitTowardsPlayer(unit);
        }

         */
    }

    // Поиск ближайшего юнита игрока в радиусе атаки
    public Unit findPlayerUnitInRange(Unit unit) {
        for (int i = Math.max(0, unit.unitY - unit.distance); i <= Math.min(height - 1, unit.unitY + unit.distance); i++) {
            for (int j = Math.max(0, unit.unitX - unit.distance); j <= Math.min(weight - 1, unit.unitX + unit.distance); j++) {
                if (battle[i][j] instanceof Unit && ((Unit) battle[i][j]).isGamerUnit) { // Если нашли юнита игрока
                    ui.enemyAttackYourUnit((Unit) battle[i][j]);
                    //System.out.println("Враг атаковал вашего юнита " + ((Unit) battle[i][j]).design);
                    return (Unit) battle[i][j]; // Возвращаем цель
                }
            }
        }
        return null; // Цель не найдена
    }

    public void moveUnitTowardsPlayer(Unit unit) {
        // Перемещение юнита противника в сторону игрока
        int newX = unit.unitX;
        int newY = unit.unitY;
        int stepsRemaining = unit.move; // Количество оставшихся шагов

        while (stepsRemaining > 0) {
            int nextX = newX - 1; // Двигаемся влево (к игроку)
            int nextY = newY;

            // Проверяем, можно ли переместиться на следующую клетку
            if (nextX >= 0 && battle[nextY][nextX] instanceof Let) {
                // Освобождаем текущую клетку
                battle[newY][newX] = new Let();

                // Перемещаем юнита на следующую клетку
                newX = nextX;
                newY = nextY;
                battle[newY][newX] = unit;

                stepsRemaining--; // Уменьшаем количество оставшихся шагов
            } else {
                // Если следующая клетка недоступна, прерываем перемещение
                break;
            }
        }

        // Обновляем координаты юнита
        unit.unitX = newX;
        unit.unitY = newY;
        unit.isMoved = true; // Помечаем, что юнит двигался

        ui.printMoveUnit(newY, newX);
        //System.out.println("Враг переместился на (" + newY + ", " + newX + ")"); // Сообщаем о перемещении
    }

    public boolean isBattleOver(String isHero){
    //public boolean isBattleOver(Hero playerHero, Hero compHero)

        boolean playerHasUnits = false; // Флаг для проверки наличия юнитов игрока
        boolean compHasUnits = false; // Флаг для проверки наличия юнитов противника

        // Проверка наличия юнитов на поле
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < weight; j++) {
                if (battle[i][j] instanceof Unit) {
                    Unit unit = (Unit) battle[i][j];
                    if (unit.count > 0) { // Проверяем, что количество юнитов больше нуля
                        if (unit.isGamerUnit) {
                            playerHasUnits = true; // Игрок имеет юнитов
                        } else {
                            compHasUnits = true; // Противник имеет юнитов
                        }
                    }
                }
            }
        }

        ui.countUnits(playerHasUnits, compHasUnits);
        // Вывод информации о наличии юнитов
        //System.out.println("Игрок имеет юнитов: " + playerHasUnits);
        //System.out.println("Компьютер имеет юнитов: " + compHasUnits);

        // Проверка на окончания боя
        if (!playerHasUnits) {
            ui.printYouLose();
            gameMap.isGamerWinner = false;
            //System.out.println("Вы проиграли");
            //System.exit(0); // Завершение игры
        }
        if (!compHasUnits) {
            gameMap.isGamerWinner = true;
            if(isHero.equals("Hero")) {
                gameMap.computerIsAlive = false;
            }
            //GameMap.computerIsAlive = false; // Противник побежден
        }

        return !playerHasUnits || !compHasUnits; // Возвращает true, если игра окончена
    }


    public void clearConsole() throws InterruptedException {
        // Очистка консоли
        Thread.sleep(500);
        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }

    public void updateDisplay() throws InterruptedException {
        // Обновление отображения поля боя
        clearConsole(); // Очистка консоли перед обновлением
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < weight; j++) {
                System.out.print(battle[i][j] + " "); // Печать элемента поля
            }
            System.out.println(); // Переход на следующую строку
        }
    }

    public Unit createUnit(String unit, int count, Hero hero) {
        // Создание юнита в зависимости от типа
        switch (unit) {
            case "SpearMan":
                return new SpearMan(count, hero.isDamageUp, hero.isSalonUp, hero.isHotelUp); // Создание копейщика
            case "CrossBowMan":
                return new CrossBowMan(count,hero.isDamageUp, hero.isSalonUp, hero.isHotelUp); // Создание арбалетчика
            case "SwordsMan":
                return new SwordsMan(count,hero.isDamageUp, hero.isSalonUp, hero.isHotelUp); // Создание мечника
            case "Cavalryman":
                return new CavalryMan(count,hero.isDamageUp, hero.isSalonUp, hero.isHotelUp); // Создание кавалериста
            case "Paladin":
                return new Paladin(count,hero.isDamageUp, hero.isSalonUp, hero.isHotelUp); // Создание паладина
            default:
                throw new IllegalArgumentException("Тип неизвестен"); // Ошибка, если тип неизвестен
        }
    }

}




/*
package battlef;

import Hero.Hero;
import units.*;
import maps.*;

import java.util.*;
import java.io.*;

public class BattleField {
    public final int height = 5; // Высота поля
    public final int weight = 10; // Ширина поля

    public Path[][] battle = new Path[height][weight]; // Создание двумерного массива для поля боя

    private static int playerWins = 0; // Счетчик побед игрока
    private static final List<Record> records = new ArrayList<>(); // Список рекордов
    private final Scanner scanner = new Scanner(System.in); // Сканер для ввода

    public BattleField(Hero hero1, Hero hero2) throws InterruptedException {
        // Инициализация игрового поля
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < weight; j++) {
                battle[i][j] = new Let(); // Заполнение поля пустыми клетками
            }
        }

        // Установка юнитов на поле в зависимости от того, кто из героев является игроком
        if (hero1.isGamerHero) {
            placeUnits(hero1, 0); // Размещение юнитов игрока на левой стороне
            placeUnits(hero2, weight - 1); // Размещение юнитов противника на правой стороне
        } else {
            placeUnits(hero2, 0); // Размещение юнитов противника на левой стороне
            placeUnits(hero1, weight - 1); // Размещение юнитов игрока на правой стороне
        }

        updateDisplay(); // Обновление отображения поля боя
    }

    public void placeUnits(Hero hero, int startX) {
        // Размещение юнитов героя на поле
        int i = 0;
        for (Map.Entry<String, Integer> entry : hero.army.entrySet()) {
            String unit = entry.getKey(); // Получаем тип юнита
            int count = entry.getValue(); // Получаем количество юнитов

            if (count > 0) { // Если количество юнитов больше нуля
                Unit newUnit = createUnit(unit, count, hero); // Создаем нового юнита
                newUnit.unitX = startX; // Устанавливаем координату X
                newUnit.unitY = i; // Устанавливаем координату Y
                newUnit.isGamerUnit = hero.isGamerHero; // Помечаем юниты игрока
                battle[i][startX] = newUnit; // Размещаем юнита на поле
                System.out.println("Добавлен юнит: " + unit + " в позицию (" + i + ", " + startX + ")");
                i++;
            }
        }
    }

    public void startBattle(Hero hero1, Hero hero2) throws InterruptedException {
        Scanner scanner = new Scanner(System.in); // Создаем сканер для ввода пользователя

        while (true) { // Основной цикл боя
            playerTurn(scanner); // Ход игрока
            updateDisplay(); // Обновление отображения после хода игрока

            // Проверка, завершилась ли битва
            if (isBattleOver(hero1, hero2)) {
                System.out.println("Бой завершен");
                break; // Выход из цикла, если бой завершен
            }

            enemyTurn(); // Ход противника
            updateDisplay(); // Обновление отображения после хода противника

            // Проверка, завершилась ли битва
            if (isBattleOver(hero1, hero2)) {
                System.out.println("Бой завершен");
                break; // Выход из цикла, если бой завершен
            }
        }
    }

    public void playerTurn(Scanner scanner) throws InterruptedException {
        logger.info("Начался ход игрока.");
        System.out.println("Ваш ход"); // Уведомление о начале хода игрока
        resetMovedStatus(); // Сбрасываем статус перемещения юнитов
        // Проходим по полю и выполняем действия игрока
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < weight; j++) {
                if (battle[i][j] instanceof Unit && ((Unit) battle[i][j]).isGamerUnit) {
                    Unit unit = (Unit) battle[i][j]; // Получаем юнит игрока
                    if (unit.count > 0 && !unit.isMoved) { // Проверяем, что юнит жив и не движется
                        System.out.println("Ход юнита: " + unit.design + " в позиции (" + i + ", " + j + ")");
                        unit.act(battle, scanner); // Действие юнита
                        updateDisplay(); // Обновление отображения после действия юнита
                    }
                }
            }
        }
    }

    public void enemyTurn() throws InterruptedException {
        logger.info("Враг начинает действие.");
        resetMovedStatus(); // Сбрасываем статус движения у противника
        System.out.println("Ход врага"); // Уведомление о начале хода противника
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < weight; j++) {
                if (battle[i][j] instanceof Unit && !((Unit) battle[i][j]).isGamerUnit) {
                    Unit unit = (Unit) battle[i][j]; // Получаем юнит противника
                    // Вывод информации о юните
                    System.out.println("Сейчас ходит юнит " + unit.design);
                    System.out.println("Здоровье одного юнита - " + unit.HP);
                    System.out.println("Урон одного юнита - " + unit.totalDamage);
                    System.out.println("Максимальное здоровье юнита " + unit.totalHP);
                    System.out.println("Максимальная дистанция атаки юнита - " + unit.distance);
                    System.out.println("Максимальная дистанция перемещения - " + unit.move);
                    System.out.println("Максимальный урон юнита - " + unit.totalDamage);
                    enemyAct(unit); // Действие противника
                }
            }
        }
        updateDisplay(); // Обновление отображения после действия противника
    }

    public void resetMovedStatus() {
        // Сброс статуса движения для всех юнитов
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < weight; j++) {
                if (battle[i][j] instanceof Unit) {
                    Unit unit = (Unit) battle[i][j];
                    unit.isMoved = false; // Сбрасываем статус перемещения
                }
            }
        }
    }

    public void enemyAct(Unit unit) {
        // Действия юнита противника
        if (unit.isMoved) {
            return; // Если юнит уже двигался или атаковал, пропускаем его ход
        }

        Unit target = findPlayerUnitInRange(unit); // Находим игрока в зоне атаки

        if (target != null) { // Если цель найдена
            System.out.println("Нанесено урона: " + unit.totalDamage); // Информируем об уроне
            target.takeDamage(unit.totalDamage); // Наносим урон
            System.out.println("Осталось здоровья у вашего юнита " + target.totalHP); // Информируем о здоровье
            if (target.totalHP <= 0) { // Проверка на смерть юнита
                System.out.println("Юнит " + target.design + " погиб");
                battle[target.unitY][target.unitX] = new Let(); // Удаляем юнита с поля
            }
            unit.isMoved = true; // Помечаем, что юнит двигался
        } else { // Если цели нет, двигаемся к игроку
            moveUnitTowardsPlayer(unit);
        }
    }

    // Поиск ближайшего юнита игрока в радиусе атаки
    public Unit findPlayerUnitInRange(Unit unit) {
        for (int i = Math.max(0, unit.unitY - unit.distance); i <= Math.min(height - 1, unit.unitY + unit.distance); i++) {
            for (int j = Math.max(0, unit.unitX - unit.distance); j <= Math.min(weight - 1, unit.unitX + unit.distance); j++) {
                if (battle[i][j] instanceof Unit && ((Unit) battle[i][j]).isGamerUnit) { // Если нашли юнита игрока
                    System.out.println("Враг атаковал вашего юнита " + ((Unit) battle[i][j]).design);
                    return (Unit) battle[i][j]; // Возвращаем цель
                }
            }
        }
        return null; // Цель не найдена
    }

    public void moveUnitTowardsPlayer(Unit unit) {
        // Перемещение юнита противника в сторону игрока
        int newX = unit.unitX;
        int newY = unit.unitY;
        int stepsRemaining = unit.move; // Количество оставшихся шагов

        while (stepsRemaining > 0) {
            int nextX = newX - 1; // Двигаемся влево (к игроку)
            int nextY = newY;

            // Проверяем, можно ли переместиться на следующую клетку
            if (nextX >= 0 && battle[nextY][nextX] instanceof Let) {
                // Освобождаем текущую клетку
                battle[newY][newX] = new Let();

                // Перемещаем юнита на следующую клетку
                newX = nextX;
                newY = nextY;
                battle[newY][newX] = unit;

                stepsRemaining--; // Уменьшаем количество оставшихся шагов
            } else {
                // Если следующая клетка недоступна, прерываем перемещение
                break;
            }
        }

        // Обновляем координаты юнита
        unit.unitX = newX;
        unit.unitY = newY;
        unit.isMoved = true; // Помечаем, что юнит двигался

        System.out.println("Враг переместился на (" + newY + ", " + newX + ")"); // Сообщаем о перемещении
    }

    public boolean isBattleOver(Hero playerHero, Hero compHero) {
        boolean playerHasUnits = false; // Флаг для проверки наличия юнитов игрока
        boolean compHasUnits = false; // Флаг для проверки наличия юнитов противника

        // Проверка наличия юнитов на поле
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < weight; j++) {
                if (battle[i][j] instanceof Unit) {
                    Unit unit = (Unit) battle[i][j];
                    if (unit.count > 0) { // Проверяем, что количество юнитов больше нуля
                        if (unit.isGamerUnit) {
                            playerHasUnits = true; // Игрок имеет юнитов
                        } else {
                            compHasUnits = true; // Противник имеет юнитов
                        }
                    }
                }
            }
        }

        // Вывод информации о наличии юнитов
        System.out.println("Игрок имеет юнитов: " + playerHasUnits);
        System.out.println("Компьютер имеет юнитов: " + compHasUnits);

        // Проверка на окончания боя
        if (!playerHasUnits) {
            System.out.println("Вы проиграли");
            System.exit(0); // Завершение игры
        }
        if (!compHasUnits) {
            GameMap.computerIsAlive = false; // Противник побежден
        }

        return !playerHasUnits || !compHasUnits; // Возвращает true, если игра окончена
    }


    public void clearConsole() throws InterruptedException {
        // Очистка консоли
        Thread.sleep(500);
        for (int i = 0; i < 40; i++) {
            //System.out.println();
        }
    }

    public void updateDisplay() throws InterruptedException {
        // Обновление отображения поля боя
        clearConsole(); // Очистка консоли перед обновлением
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < weight; j++) {
                //System.out.print(battle[i][j] + " "); // Печать элемента поля
            }
            System.out.println(); // Переход на следующую строку
        }
    }

    public Unit createUnit(String unit, int count, Hero hero) {
        // Создание юнита в зависимости от типа
        switch (unit) {
            case "SpearMan":
                return new SpearMan(count); // Создание копейщика
            case "CrossBowMan":
                return new CrossBowMan(count); // Создание арбалетчика
            case "SwordsMan":
                return new SwordsMan(count); // Создание мечника
            case "Cavalryman":
                return new CavalryMan(count); // Создание кавалериста
            case "Paladin":
                return new Paladin(count); // Создание паладина
            default:
                throw new IllegalArgumentException("Тип неизвестен"); // Ошибка, если тип неизвестен
        }
    }

    // Метод для редактирования карты
    public void editMap() {
        System.out.println("Введите координаты (x, y) для редактирования, и тип элемента (Let, Tower): ");
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        String elementType = scanner.next();

        switch (elementType) {
            case "Let":
                battle[y][x] = new Let();
                break;
            case "Tower":
                battle[y][x] = new Tower();
                break;
            default:
                System.out.println("Неизвестный тип элемента!");
                break;
        }
        System.out.println("Карта обновлена.");
    }

    // Метод для сохранения состояния игры
    public void saveGame(Hero hero) {
        try (FileOutputStream fileOut = new FileOutputStream("game_save.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(hero); // Сохранение состояния героя
            out.writeObject(battle); // Сохранение состояния поля
            System.out.println("Игра сохранена.");
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении игры: " + e.getMessage());
        }
    }

    // Метод для загрузки состояния игры
    public void loadGame() {
        try (FileInputStream fileIn = new FileInputStream("game_save.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            Hero hero = (Hero) in.readObject(); // Загрузка состояния героя
            battle = (Path[][]) in.readObject(); // Загрузка состояния поля
            System.out.println("Игра загружена.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка при загрузке игры: " + e.getMessage());
        }
    }

    public void showRecords() {
        System.out.println("Топ 5 рекордов:");
        for (Record record : records) {
            System.out.println(record);
        }
    }

    // Добавление других методов...

}

// Вложенный класс для рекордов
public static class Record implements Serializable { // Реализуем Serializable для сохранения
    private String playerName;
    private int score;
    private String mapName;

    public Record(String playerName, int score, String mapName) {
        this.playerName = playerName;
        this.score = score;
        this.mapName = mapName;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Игрок: " + playerName + ", Очки: " + score + ", Карта: " + mapName;
    }
}

 */