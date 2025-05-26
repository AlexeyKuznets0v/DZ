
package maps;

import java.io.*;
import java.util.*;

import Castle.ComputerHouse;
import Castle.GamerHouse;
import Hero.Hero;
import Que.AddQue;
import Que.RemoveQue;
import Que.SimpleQue;
import battlef.BattleField;
import buildings.Building;
import buildings.Tower;
import UI.*;
import tests.Test1;
import java.io.IOException;
import static buildings.Tower.logger;

public class GameMap implements Serializable {

    private static final long serialVersionUID = 1L; // Рекомендуется для управления версиями

    public UI ui; // Объект для управления пользовательским интерфейсом
    public String saveName; // Имя игрока или сохраненной игры
    public String mapType; // Тип карты

    // Статический флаг для проверки, жив ли компьютер
    public static boolean computerIsAlive = true;
    public boolean isGamerWinner = false; // Флаг победы игрока
    public boolean inBuilding = false; // Флаг, указывающий, находится ли герой в здании
    public boolean basedGame; // Флаг, указывающий, началась ли основанная игра
    public boolean isGAmeOver = false; // Флаг, указывающий, окончена ли игра

    // Размеры карты: высота и ширина
    public final int height; // Высота карты
    public final int weight; // Ширина карты

    // Двумерный массив, представляющий игровую карту
    public Path[][] world; // Игровая карта

    // Флаг, указывающий, началось ли сражение
    public static boolean isBattleStarted = false;
    public Hero[] heroes; // Массив героев

    public Cafe cafe = new Cafe(); // Кафе
    public Salon salon = new Salon(); // Салон
    public Hotel hotel = new Hotel(); // Гостиница

    List<String> list = Collections.synchronizedList(new ArrayList<>()); // Синхронизированный список для управления потоками
    List<String> names = new ArrayList<>(); // Список имен для использования в очереди

    // Метод для создания героев
    public void createHeroes(String mapType) {
        heroes = new Hero[2]; // Создаем массив с двумя героями

        // Инициализация и добавление характеристик к героям
        for (int i = 0; i < heroes.length; i++) {
            heroes[i] = new Hero(100); // Установка начального здоровья
        }

        // Настройка первого героя (игрока)
        heroes[0].isGamerHero = true;
        heroes[0].isAvailable = true;
        heroes[0].army.put("SpearMan", 1); // Добавление юнитов
        heroes[0].army.put("CrossBowMan", 10);

        // Настройка второго героя (компьютера)
        heroes[1].isGamerHero = false;
        heroes[1].isAvailable = true;
        heroes[1].army.put("SpearMan", 6);
        heroes[1].army.put("CrossBowMan", 3);

        // Установка начальных координат героев на карте в зависимости от типа карты
        if (mapType.equals(Test1.CROSS_MAP)) {
            heroes[0].heroY = height / 2;
            heroes[0].heroX = 1;
            heroes[1].heroY = height / 2;
            heroes[1].heroX = weight - 2;
        } else {
            heroes[0].heroY = 1;
            heroes[0].heroX = 1;
            heroes[1].heroY = height - 2;
            heroes[1].heroX = weight - 2;
        }

        // Помещаем героев на карту
        world[heroes[0].heroY][heroes[0].heroX] = heroes[0];
        world[heroes[1].heroY][heroes[1].heroX] = heroes[1];
    }

    // Метод для создания карты с перекрестным типом
    public void createCrossMap() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.weight; j++) {
                // Определяем типы клеток на карте
                if (i == height / 2 && j == 0) {
                    world[i][j] = new GamerHouse(); // Дома игрока
                } else if ((i == height / 2 && j == weight - 1 || (j == weight / 2 && (i == height - 1 || i == 0)))) {
                    world[i][j] = new ComputerHouse(); // Дома компьютера
                } else if (i == height / 2 || j == weight / 2) {
                    world[i][j] = new Let(); // Проходы
                } else {
                    world[i][j] = new Field(); // Поля
                }
            }
        }
    }

    // Метод для создания диагональной карты
    public void createDiagonalMap() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.weight; j++) {
                // Определяем типы клеток на диагональной карте
                if (i == 0 && j == 0) {
                    world[i][j] = new GamerHouse(); // Дома игрока
                } else if (i == 0 && j == weight - 1 || i == height - 1 && (j == 0 || j == weight - 1)) {
                    world[i][j] = new ComputerHouse(); // Дома компьютера
                } else if (i == j || j == height - i - 1) {
                    world[i][j] = new Let(); // Проходы
                } else {
                    world[i][j] = new Field(); // Поля
                }
            }
        }
    }

    // Конструктор класса GameMap, инициализирующий основные параметры карты
    public GameMap(int height, int weight, String name, String mapType, boolean basedGame, boolean moded) throws InterruptedException {
        this.height = height; // Установка высоты карты
        this.weight = weight; // Установка ширины карты
        this.saveName = name; // Установка имени игрока
        this.mapType = mapType; // Установка типа карты
        this.basedGame = basedGame; // Установка флага для основанной игры
        this.ui = new UI(); // Инициализация пользовательского интерфейса
        this.world = new Path[this.height][this.weight]; // Создание карты
        this.names.add("Вася"); // Добавление имен в список
        this.names.add("Толя");
        this.names.add("Леша");

        // Если игра основана на предыдущем состоянии
        if (basedGame) {
            Random random = new Random(); // Создание объекта для генерации случайных чисел
            int letX; // Координата по X для случайных объектов
            int letY; // Координата по Y для случайных объектов

            // Создание карты в зависимости от типа
            if (mapType.equals(Test1.CROSS_MAP)) {
                createCrossMap(); // Создание перекрестной карты
            } else {
                createDiagonalMap(); // Создание диагональной карты
            }

            createHeroes(mapType); // Создание героев на карте

            // Генерация препятствий
            if (moded) {
                Scanner scanner = new Scanner(System.in); // Подготовка для ввода координат от игрока
                for (int i = 0; i < 5; ) { // Генерация 5 препятствий
                    ui.chooseXY(); // Запрос координат у игрока
                    letX = scanner.nextInt();
                    letY = scanner.nextInt();
                    if (checkOutOfBounds(letX, letY)) { // Проверка правильности координат
                        if (world[letY][letX] instanceof Field) {
                            world[letY][letX] = new Let(); // Установка препятствия
                            i++; // Увеличение счетчика
                        } else {
                            ui.printLetIsWrong(); // Сообщение об ошибке
                        }
                    } else {
                        ui.printLetOutOfBounds(); // Сообщение о выходе за границы
                    }
                    updateDisplay(); // Обновление отображения карты
                }
            } else {
                for (int i = 0; i < 10; ) { // Генерация 10 препятствий случайным образом
                    letY = Math.abs(random.nextInt()) % this.height; // Генерация координаты Y
                    letX = Math.abs(random.nextInt()) % this.weight; // Генерация координаты X
                    if (world[letY][letX] instanceof Field) { // Проверка, является ли клетка полем
                        world[letY][letX] = new Let(); // Установка препятствия
                        i++; // Увеличение счетчика
                    }
                }
            }

            // Размещение объектов на карте (кафе, салон, гостиница)
            while (true) {
                letY = Math.abs(random.nextInt()) % this.height;
                letX = Math.abs(random.nextInt()) % this.weight;
                if (world[letY][letX] instanceof Field) {
                    world[letY][letX] = cafe; // Размещение кафе
                    break;
                }
            }

            while (true) {
                letY = Math.abs(random.nextInt()) % this.height;
                letX = Math.abs(random.nextInt()) % this.weight;
                if (world[letY][letX] instanceof Field) {
                    world[letY][letX] = salon; // Размещение салона
                    break;
                }
            }

            while (true) {
                letY = Math.abs(random.nextInt()) % this.height;
                letX = Math.abs(random.nextInt()) % this.weight;
                if (world[letY][letX] instanceof Field) {
                    world[letY][letX] = hotel; // Размещение гостиницы
                    break;
                }
            }
        }
    }

    // Метод для обновления отображения карты
    public void updateDisplay() throws InterruptedException {
        ui.newMap(world, height, weight); // Обновление интерфейса
        endGame(); // Проверка состояния игры
    }

    // Проверка границ карты
    public boolean checkOutOfBounds(int newX, int newY) {
        return newX >= 0 && newX < this.weight && newY >= 0 && newY < this.height; // Убедитесь, что координаты находятся в допустимых пределах
    }

    // Метод перемещения героя
    public void moveHero(Hero hero, int dx, int dy) throws InterruptedException {
        int newX = hero.heroX + dx; // Новая координата X героя
        int newY = hero.heroY + dy; // Новая координата Y героя
        if (checkOutOfBounds(newX, newY)) { // Проверка границ карты
            if (world[newY][newX] instanceof Building) { // Если клетка - здание
                Building building = (Building) world[newY][newX]; // Приведение к типу Building
                Scanner scanner = new Scanner(System.in); // Создание объекта для считывания пользовательского ввода
                if (building.isGamerTower && hero.isGamerHero) { // Если здание - башня игрока
                    inBuilding = true; // Устанавливаем флаг нахождения в здании
                    if (basedGame) {
                        while (inBuilding && hero.gold > 0) { // Обработка нахождения в здании
                            ui.printHeroGold(hero); // Отображение золота героя
                            ui.printBuildings(building); // Отображение информации о здании

                            String choice = scanner.nextLine().toUpperCase(); // Считывание выбора игрока

                            if (choice.equals("Q")) { // Если игрок хочет выйти из магазина
                                inBuilding = false;
                            } else {
                                building.handleBuildingPurchase(hero, choice); // Обработка покупки в здании
                            }


                            if (hero.gold > 0) {
                                ui.chooseInShop();

                                String buyUnitChoice = scanner.nextLine().toUpperCase();

                                if (buyUnitChoice.equals("Y")) {

                                    ui.printBuyingUnits();
                                    String unitChoice = scanner.nextLine().toUpperCase();

                                    ui.chooseGold();
                                    int gold = scanner.nextInt();
                                    scanner.nextLine(); // Очистка буфера после nextInt()
                                    if (gold > 0 && gold <= hero.gold) {
                                        hero.handleUnitPurchase(building, unitChoice, gold);
                                    } else {
                                        ui.needMoreMoney();
                                    }
                                }

/*
                                    // Проверка, достаточно ли золота для покупки
                            if (hero.gold > 0) {
                                ui.chooseInShop(); // Запрос на выбор в магазине

                                String buyUnitChoice = scanner.nextLine().toUpperCase(); // Считывание выбора покупки юнита

                                ui.chooseGold(); // Запрос на выбор золота
                                int gold = scanner.nextInt(); // Считывание введенной суммы
                                scanner.nextLine(); // Очистка буфера после nextInt()
                                if (gold > 0 && gold <= hero.gold) {
                                    hero.handleUnitPurchase(building, unitChoice, gold); // Обработка покупки юнита
                                } else {
                                    ui.needMoreMoney(); // Сообщение о нехватке средств
                                }

 */
                            } else {
                                break; // Если нет золота, выходим из цикла
                            }
                        }
                    } else {
                        ui.emulateShoping(); // Эмуляция магазина для неосновной игры
                    }
                } else {
                    isBattleStarted = true; // Устанавливаем флаг начала боя
                    BattleField btf = new BattleField(hero, building.defence, this, ui); // Создаем поле битвы
                    btf.startBattle("Building"); // Начало боя
                    isBattleStarted = false; // Сбрасываем флаг начала боя
                    world = endBattle(hero, newY, newX); // Обработка конца боя
                }
            } else if (world[newY][newX] instanceof Cafe) { // Если герой попадает в кафе
                if (cafe.isVisited) {
                    ui.coffeeWarning(); // Сообщение, что кафе уже посещено
                } else if (hero.gold < 10) { // Проверка достаточности золота
                    ui.goOut(10); // Сообщение о недостатке средств для посещения
                } else {
                    cafe.isVisited = true; // Помечаем кафе как посещенное
                    hero.gold -= 10; // Уменьшаем золото героя

                    ui.waitQue(); // Ожидание

                    list.clear(); // Очистка списка

                    // Создание и запуск потоков для очереди
                    AddQue addQue = new AddQue(list, names);
                    RemoveQue removeQue = new RemoveQue(list, this);

                    addQue.start();
                    removeQue.start();

                    addQue.join();
                    removeQue.join(); // Ожидание завершения потоков

                    hero.isCafeUp = true; // Устанавливаем флаг улучшения кафе
                    updateDisplay(); // Обновление отображения
                }
            } else if (world[newY][newX] instanceof Salon) { // Если герой попадает в салон
                if (salon.isVisited) {
                    ui.salonWarning(); // Сообщение, что салон уже посещен
                } else if (hero.gold < 12) { // Проверка достаточности золота
                    ui.goOut(12); // Сообщение о недостатке средств для посещения
                } else {
                    salon.isVisited = true; // Помечаем салон как посещенный
                    hero.gold -= 12; // Уменьшаем золото героя

                    ui.waitQue(); // Ожидание

                    list.clear(); // Очистка списка

                    // Создание и запуск потоков для очереди
                    AddQue addQue = new AddQue(list, names);
                    RemoveQue removeQue = new RemoveQue(list, this);

                    addQue.start();
                    removeQue.start();

                    addQue.join();
                    removeQue.join(); // Ожидание завершения потоков

                    hero.isSalonUp = true; // Устанавливаем флаг улучшения салона
                    updateDisplay(); // Обновление отображения
                }
            } else if (world[newY][newX] instanceof Hotel) { // Если герой попадает в гостиницу
                if (hotel.isVisited) {
                    ui.hotelWarning(); // Сообщение, что гостиница уже посещена
                } else if (hero.gold < 15) { // Проверка достаточности золота
                    ui.goOutFromHotel(); // Сообщение о недостатке средств для посещения
                } else {
                    hotel.isVisited = true; // Помечаем гостиницу как посещенную
                    hero.gold -= 15; // Уменьшаем золото героя
                    hero.isHotelUp = true; // Устанавливаем флаг улучшения гостиницы
                    ui.startSleeping(); // Сообщение о начале сна
                    SimpleQue simpleQue = new SimpleQue(); // Создание потока для ожидания

                    simpleQue.start();
                    simpleQue.join(); // Ожидание завершения сна

                    ui.isGoodNight(); // Сообщение о хорошем сне
                    Thread.sleep(1000); // Пауза на 1 секунду

                    hero.move = 0; // Сбрасываем перемещение героя
                }
            } else if (world[newY][newX] instanceof Road) { // Если герой попадает на дорогу
                // Проверка возможности перемещения по дороге или полю
                if ((world[newY][newX] instanceof Let && hero.move > 0) || (world[newY][newX] instanceof Field && hero.move > 1)) {
                    if (world[newY][newX] instanceof Let) {
                        hero.move -= new Let().path; // Уменьшаем перемещение на значение пути
                        ui.printPath(new Let().path); // Отображаем путь
                    } else if (world[newY][newX] instanceof Field) {
                        hero.move -= new Field().path; // Уменьшаем перемещение на значение пути
                        ui.printPath(new Field().path); // Отображаем путь
                    }

                    ui.movesRemaining(hero); // Отображаем оставшееся перемещение

                    // Обновление клетки героя на карте
                    if (mapType.equals(Test1.CROSS_MAP)) {
                        if (hero.heroY == height / 2 || hero.heroX == weight / 2) {
                            world[hero.heroY][hero.heroX] = new Let(); // Устанавливаем проход
                        } else {
                            world[hero.heroY][hero.heroX] = new Field(); // Устанавливаем поле
                        }
                    } else {
                        if (hero.heroY == hero.heroX || hero.heroX == height - hero.heroY - 1) {
                            world[hero.heroY][hero.heroX] = new Let(); // Устанавливаем проход
                        } else {
                            world[hero.heroY][hero.heroX] = new Field(); // Устанавливаем поле
                        }
                    }

                    hero.heroX = newX; // Обновление координаты X героя
                    hero.heroY = newY; // Обновление координаты Y героя
                    world[hero.heroY][hero.heroX] = hero; // Установка героя в новую позицию
                    updateDisplay(); // Обновление отображения
                } else {
                    ui.needMoreMoves(); // Сообщение о недостаточном количестве перемещений
                }
            } else if (world[newY][newX] instanceof Let) {
                ui.letIsHere(); // Сообщение о том, что герой уже находится на проходе
            }
        } else {
            ui.letIsHere(); // Сообщение о выходе за границы карты
        }
    }

    // Метод обработки результата боя при атаке здания
    public Path[][] endBattle(Hero playerHero, int newY, int newX) throws InterruptedException {
        int reward = ((Building) world[newY][newX]).defence.gold; // Получение награды за защиту

        if (isGamerWinner) { // Если игрок победил
            if (playerHero.isGamerHero) { // Если это герой игрока
                world[newY][newX] = new GamerHouse(); // Заменяем здание на дом игрока
                playerHero.gold += reward; // Добавляем награду к золоту игрока
                ui.printReward(reward); // Сообщаем о полученной награде
            } else {
                ui.defSuccess(); // Сообщение о успешной защите
                world[playerHero.heroY][playerHero.heroX] = new Let(); // Установка прохода на месте героя
                playerHero.isAvailable = false; // Герой больше не доступен для перемещения
            }
        } else { // Если защитник победил
            if (playerHero.isGamerHero) { // Если это герой игрока
                isGAmeOver = true; // Игра окончена
                ui.printYouLose(); // Сообщение о поражении
            } else {
                world[newY][newX] = new ComputerHouse(); // Заменяем здание на дом компьютера
                ui.enemyOnYourBase(); // Сообщение о врагах на базе игрока
            }
        }

        // Восстанавливаем позицию героя
        if (playerHero.isGamerHero) {
            if (isGamerWinner || !isGAmeOver) { // Если игрок победил или игра не окончена
                world[playerHero.heroY][playerHero.heroX] = playerHero; // Устанавливаем героя на прежнее место
            } else {
                world[playerHero.heroY][playerHero.heroX] = new Let(); // Устанавливаем проход на месте героя
            }
        } else {
            if (!isGamerWinner) { // Если компьютерный герой победил
                world[newY][newX] = new ComputerHouse(); // Оставляем компьютерного героя в захваченном здании
                computerIsAlive = false; // Устанавливаем флаг, что компьютер мертв
            } else {
                world[playerHero.heroY][playerHero.heroX] = new Let(); // Убираем компьютерного героя
                computerIsAlive = false; // Устанавливаем флаг, что компьютер мертв
            }
        }

        updateDisplay(); // Обновление отображения карты
        isBattleStarted = false; // Сбрасываем флаг начала боя
        return world; // Возвращаем обновленную карту
    }

    // Метод обработки конца боя между двумя героями
    public Path[][] endBattle(Hero playerHero, Hero computerHero) {
        if (isGamerWinner) { // Если игрок победил
            ui.printCompGold(computerHero); // Отображение золота компьютера
            playerHero.gold += computerHero.gold; // Добавление золота компьютера к золоту игрока
            ui.printHeroGold(playerHero); // Отображение золота игрока
            computerHero.gold = 0; // Обнуление золота компьютера
            computerHero.isAvailable = false; // Сделать компьютерного героя недоступным
            this.world[computerHero.heroY][computerHero.heroX] = new Let(); // Установка прохода на месте компьютерного героя
            this.world[playerHero.heroY][playerHero.heroX] = playerHero; // Установка игрока на его позицию
            isBattleStarted = false; // Сбрасываем флаг начала боя
        } else {
            this.world[computerHero.heroY][computerHero.heroX] = computerHero; // Устанавливаем компьютерного героя на карте
            this.world[playerHero.heroY][playerHero.heroX] = new Let(); // Убираем игрока с карты
            isGAmeOver = true; // Игра окончена
        }
        return this.world; // Возвращаем обновленную карту
    }

    // Метод для окончания раунда
    public void endRound(Hero[] heroes) {
        for (int i = 0; i < heroes.length; i++) {
            if (heroes[i].isAvailable && !isBattleStarted) { // Если герой доступен и битва не начата
                heroes[i].resetMoves(); // Сброс количества перемещений у героя
            }
        }
    }

    // Метод проверки победы игрока
    public boolean isGamerWin() {
        if (mapType.equals(Test1.CROSS_MAP)) { // Проверка для перекрестной карты
            return world[height / 2][0] instanceof GamerHouse && world[height / 2][weight - 1] instanceof GamerHouse
                    && world[height - 1][weight / 2] instanceof GamerHouse
                    && world[0][weight / 2] instanceof GamerHouse;
        } else { // Проверка для диагональной карты
            return world[0][0] instanceof GamerHouse && world[height - 1][weight - 1] instanceof GamerHouse
                    && world[height - 1][0] instanceof GamerHouse
                    && world[0][weight - 1] instanceof GamerHouse;
        }
    }

    // Метод проверки победы компьютера
    public boolean isCompWin() {
        if (mapType.equals(Test1.CROSS_MAP)) { // Проверка для перекрестной карты
            return world[height / 2][0] instanceof ComputerHouse && world[height / 2][weight - 1] instanceof ComputerHouse
                    && world[height - 1][weight / 2] instanceof ComputerHouse
                    && world[0][weight / 2] instanceof ComputerHouse;
        } else { // Проверка для диагональной карты
            return world[0][0] instanceof ComputerHouse && world[height - 1][weight - 1] instanceof ComputerHouse
                    && world[height - 1][0] instanceof ComputerHouse
                    && world[0][weight - 1] instanceof ComputerHouse;
        }
    }

    // Метод проверки окончания игры
    public void endGame() {
        if (isGamerWin()) { // Если игрок выиграл
            isGAmeOver = true;
            ui.printYouWin(); // Сообщение о победе
            clearSaveFile(); // Очистка файла сохранения
        }
        if (isCompWin()) { // Если компьютер выиграл
            isGAmeOver = true;
            ui.printYouLose(); // Сообщение о поражении
            clearSaveFile(); // Очистка файла сохранения
        }
    }

    // Метод для очистки файла сохранения
    public void clearSaveFile() {
        try {
            // Если сохранённое имя - "Игрок1", очищаем соответствующий файл
            if (Objects.equals(saveName, "Игрок1")) {
                new FileOutputStream(Test1.PLAYER1_SAVE).close(); // Очистка файла
            } else if (Objects.equals(saveName, "Игрок2")) { // Если сохранённое имя - "Игрок2"
                new FileOutputStream(Test1.PLAYER2_SAVE).close(); // Очистка файла
            } else { // Для других случаев
                new FileOutputStream(Test1.TEST_SAVE).close(); // Очистка файла
            }
            ui.saveClear(); // Сообщение об успешной очистке
        } catch (IOException e) { // Обработка исключений при вводе-выводе
            ui.saveClearError(); // Сообщение об ошибке очистки файла
        }
    }
}

/*
package maps;
import java.io.*;
import java.util.*;

import Castle.ComputerHouse;
import Castle.GamerHouse;
import Hero.Hero;
import Que.AddQue;
import Que.RemoveQue;
import Que.SimpleQue;
import battlef.BattleField;
import buildings.Building;
import buildings.Tower;
import UI.*;
import tests.Test1;
import java.io.IOException;
import static buildings.Tower.logger;

public class GameMap implements Serializable {

    private static final long serialVersionUID = 1L; // Рекомендуется для управления версиями {

    public UI ui;
    public String saveName;
    public String mapType;

    // Статический флаг для проверки, жив ли компьютер
    public static boolean computerIsAlive = true;
    public boolean isGamerWinner = false;
    public boolean inBuilding = false;
    public boolean basedGame;
    public boolean isGAmeOver = false;

    // Размеры карты: высота и ширина
    public final int height;
    public final int weight;

    // Двумерный массив, представляющий игровую карту
    public Path[][] world;
//    private Object[][] world;


    // Флаг, указывающий, началось ли сражение
    public static boolean isBattleStarted = false;
    public Hero[] heroes;
    //public Path[][] world;

    public Cafe cafe = new Cafe();
    public Salon salon = new Salon();
    public Hotel hotel = new Hotel();

    List<String> list = Collections.synchronizedList(new ArrayList<>());
    List<String> names = new ArrayList<>();


    public void createHeroes(String mapType) {
        heroes = new Hero[2];

        for (int i = 0; i < heroes.length; i++) {
            heroes[i] = new Hero(100);
        }

        heroes[0].isGamerHero = true;
        heroes[0].isAvailable = true;

        heroes[0].army.put("SpearMan", 1);
        heroes[0].army.put("CrossBowMan", 10);

        heroes[1].isGamerHero = false;
        heroes[1].isAvailable = true;

        heroes[1].army.put("SpearMan", 6);
        heroes[1].army.put("CrossBowMan", 3);

        if (mapType.equals(Test1.CROSS_MAP)) {
            heroes[0].heroY = height / 2;
            heroes[0].heroX = 1;
            heroes[1].heroY = height / 2;
            heroes[1].heroX = weight - 2;
        } else {
            heroes[0].heroY = 1;
            heroes[0].heroX = 1;
            heroes[1].heroY = height - 2;
            heroes[1].heroX = weight - 2;
        }

        world[heroes[0].heroY][heroes[0].heroX] = heroes[0];
        world[heroes[1].heroY][heroes[1].heroX] = heroes[1];
    }

    public void createCrossMap() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.weight; j++) {
                if (i == height / 2 && j == 0) {
                    world[i][j] = new GamerHouse();
                } else if ((i == height / 2 && j == weight - 1 || (j == weight / 2 && (i == height - 1 || i == 0)))) {
                    world[i][j] = new ComputerHouse();
                } else if (i == height / 2 || j == weight / 2) {
                    world[i][j] = new Let();
                } else {
                    world[i][j] = new Field();
                }
            }
        }
    }

    public void createDiagonalMap() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.weight; j++) {
                if (i == 0 && j == 0) {
                    world[i][j] = new GamerHouse();
                } else if (i == 0 && j == weight - 1 || i == height - 1 && (j == 0 || j == weight - 1)) {
                    world[i][j] = new ComputerHouse();
                } else if (i == j || j == height - i - 1) {
                    world[i][j] = new Let();
                } else {
                    world[i][j] = new Field();
                }
            }
        }
    }


    public GameMap(int height, int weight, String name, String mapType, boolean basedGame, boolean moded) throws InterruptedException {
        this.height = height;
        this.weight = weight;
        this.saveName = name;
        this.mapType = mapType;
        this.basedGame = basedGame;
        this.ui = new UI();
        this.world = new Path[this.height][this.weight];
        this.names.add("Вася");
        this.names.add("Толя");
        this.names.add("Леша");

        if (basedGame) {
            Random random = new Random();
            int letX;
            int letY;
            if (mapType.equals(Test1.CROSS_MAP)) {
                createCrossMap();
            } else {
                createDiagonalMap();
            }

            createHeroes(mapType);

            //генерация препятствий
            if (moded) {
                Scanner scanner = new Scanner(System.in);
                for (int i = 0; i < 5; ) {
                    ui.chooseXY();
                    letX = scanner.nextInt();
                    letY = scanner.nextInt();
                    if (checkOutOfBounds(letX, letY)) {
                        if (world[letY][letX] instanceof Field) {
                            world[letY][letX] = new Let();
                            i++;
                        } else {
                            ui.printLetIsWrong();
                        }
                    } else {
                        ui.printLetOutOfBounds();
                    }
                    updateDisplay();
                }
            } else {
                for (int i = 0; i < 10; ) {
                    letY = Math.abs(random.nextInt()) % this.height;
                    letX = Math.abs(random.nextInt()) % this.weight;
                    if (world[letY][letX] instanceof Field) {
                        world[letY][letX] = new Let();
                        i++;
                    }
                }
            }


            while (true) {
                letY = Math.abs(random.nextInt()) % this.height;
                letX = Math.abs(random.nextInt()) % this.weight;
                if (world[letY][letX] instanceof Field) {
                    world[letY][letX] = cafe;
                    break;
                }
            }

            while (true) {
                letY = Math.abs(random.nextInt()) % this.height;
                letX = Math.abs(random.nextInt()) % this.weight;
                if (world[letY][letX] instanceof Field) {
                    world[letY][letX] = salon;
                    break;
                }
            }

            while (true) {
                letY = Math.abs(random.nextInt()) % this.height;
                letX = Math.abs(random.nextInt()) % this.weight;
                if (world[letY][letX] instanceof Field) {
                    world[letY][letX] = hotel;
                    break;
                }
            }
        }
    }

    public void updateDisplay() throws InterruptedException {
        ui.newMap(world, height, weight);
        endGame();
    }

    // Проверка границ карты
    public boolean checkOutOfBounds(int newX, int newY) {
        return newX >= 0 && newX < this.weight && newY >= 0 && newY < this.height;
    }


    public void moveHero(Hero hero, int dx, int dy) throws InterruptedException {
        int newX = hero.heroX + dx;
        int newY = hero.heroY + dy;
        //if (world == null) {
        //   throw new IllegalStateException("GameMap world is not initialized!");
        // }
        if (checkOutOfBounds(newX, newY)) {
            if (world[newY][newX] instanceof Building) {
                Building building = (Building) world[newY][newX];
                Scanner scanner = new Scanner(System.in);
                if (building.isGamerTower && hero.isGamerHero) {
                    inBuilding = true;
                    if (basedGame) {
                        while (inBuilding && hero.gold > 0) {
                            ui.printHeroGold(hero);
                            ui.printBuildings(building);

                            String choice = scanner.nextLine().toUpperCase();

                            if (choice.equals("Q")) {
                                inBuilding = false;
                            } else {
                                building.handleBuildingPurchase(hero, choice);
                            }

                            if (hero.gold > 0) {
                                ui.chooseInShop();

                                String buyUnitChoice = scanner.nextLine().toUpperCase();

                                if (buyUnitChoice.equals("Y")) {

                                    ui.printBuyingUnits();
                                    String unitChoice = scanner.nextLine().toUpperCase();

                                    ui.chooseGold();
                                    int gold = scanner.nextInt();
                                    scanner.nextLine(); // Очистка буфера после nextInt()
                                    if (gold > 0 && gold <= hero.gold) {
                                        hero.handleUnitPurchase(building, unitChoice, gold);
                                    } else {
                                        ui.needMoreMoney();
                                    }


                                } else {
                                    break;
                                }
                            }
                        }
                    } else {
                        ui.emulateShoping();
                    }
                } else {
                    isBattleStarted = true;
                    BattleField btf = new BattleField(hero, building.defence, this, ui);
                    btf.startBattle("Building");
                    isBattleStarted = false;
                    world = endBattle(hero, newY, newX);
                }
            } else if (world[newY][newX] instanceof Cafe) {
                if (cafe.isVisited) {
                    ui.coffeeWarning();
                } else if (hero.gold < 10) {
                    ui.goOut(10);
                } else {
                    cafe.isVisited = true;
                    hero.gold -= 10;

                    ui.waitQue();

                    list.clear();

                    AddQue addQue = new AddQue(list, names);
                    RemoveQue removeQue = new RemoveQue(list, this);

                    addQue.start();
                    removeQue.start();

                    addQue.join();
                    removeQue.join();

                    hero.isCafeUp = true;
                    updateDisplay();
                }
            } else if (world[newY][newX] instanceof Salon) {
                if (salon.isVisited) {
                    ui.salonWarning();
                } else if (hero.gold < 12) {
                    ui.goOut(12);
                } else {
                    salon.isVisited = true;
                    hero.gold -= 12;

                    ui.waitQue();

                    list.clear();

                    AddQue addQue = new AddQue(list, names);
                    RemoveQue removeQue = new RemoveQue(list, this);

                    addQue.start();
                    removeQue.start();

                    addQue.join();
                    removeQue.join();

                    hero.isSalonUp = true;
                    updateDisplay();
                }
            } else if (world[newY][newX] instanceof Hotel) {
                if (hotel.isVisited) {
                    ui.hotelWarning();
                } else if (hero.gold < 15) {
                    ui.goOutFromHotel();
                } else {
                    hotel.isVisited = true;
                    hero.gold -= 15;
                    hero.isHotelUp = true;
                    ui.startSleeping();
                    SimpleQue simpleQue = new SimpleQue();

                    simpleQue.start();
                    simpleQue.join();

                    ui.isGoodNight();
                    Thread.sleep(1000);

                    hero.move = 0;
                }
            } else if (world[newY][newX] instanceof Road) {
                if ((world[newY][newX] instanceof Let && hero.move > 0) || (world[newY][newX] instanceof Field && hero.move > 1)) {
                    if (world[newY][newX] instanceof Let) {
                        hero.move -= new Let().path;

                        ui.printPath(new Let().path);

                    } else if (world[newY][newX] instanceof Field) {
                        hero.move -= new Field().path;

                        ui.printPath(new Field().path);
                    }

                    ui.movesRemaining(hero);

                    if (mapType.equals(Test1.CROSS_MAP)) {
                        if (hero.heroY == height / 2 || hero.heroX == weight / 2) {
                            world[hero.heroY][hero.heroX] = new Let();
                        } else {
                            world[hero.heroY][hero.heroX] = new Field();
                        }
                    } else {
                        if (hero.heroY == hero.heroX || hero.heroX == height - hero.heroY - 1) {
                            world[hero.heroY][hero.heroX] = new Let();
                        } else {
                            world[hero.heroY][hero.heroX] = new Field();
                        }
                    }

                    hero.heroX = newX;
                    hero.heroY = newY;
                    world[hero.heroY][hero.heroX] = hero;

                    updateDisplay();
                } else {
                    ui.needMoreMoves();
                }
            } else if (world[newY][newX] instanceof Let) {
                ui.letIsHere();
            }
        } else {
            ui.letIsHere();
        }
    }

    public Path[][] endBattle(Hero playerHero, int newY, int newX) throws InterruptedException {
        int reward = ((Building) world[newY][newX]).defence.gold;

        if (isGamerWinner) {
            // Победил атакующий
            if (playerHero.isGamerHero) {
                // Игрок захватил здание компьютера
                world[newY][newX] = new GamerHouse();
                playerHero.gold += reward;

                ui.printReward(reward);
            } else {
                // Компьютер проиграл при атаке здания игрока
                ui.defSuccess();
                world[playerHero.heroY][playerHero.heroX] = new Let();
                playerHero.isAvailable = false;
            }
        } else {
            // Победил защитник
            if (playerHero.isGamerHero) {
                // Игрок проиграл при атаке здания компьютера
                isGAmeOver = true;
                ui.printYouLose();
            } else {
                world[newY][newX] = new ComputerHouse();
                ui.enemyOnYourBase();
            }
        }

        // Восстанавливаем позицию героя
        if (playerHero.isGamerHero) {
            if (isGamerWinner || !isGAmeOver) {
                world[playerHero.heroY][playerHero.heroX] = playerHero;
            } else {
                world[playerHero.heroY][playerHero.heroX] = new Let();
            }
        } else {
            if (!isGamerWinner) {
                // Компьютерный герой победил и остается в захваченном здании
                world[newY][newX] = new ComputerHouse();
                computerIsAlive = false;
            } else {
                // Компьютерный герой проиграл и исчезает
                world[playerHero.heroY][playerHero.heroX] = new Let();
                computerIsAlive = false;
            }
        }

        updateDisplay();
        isBattleStarted = false;
        return world;
    }

    public Path[][] endBattle(Hero playerHero, Hero computerHero) {
        if (isGamerWinner) {
            ui.printCompGold(computerHero);

            playerHero.gold += computerHero.gold;

            ui.printHeroGold(playerHero);

            computerHero.gold = 0;
            computerHero.isAvailable = false;

            this.world[computerHero.heroY][computerHero.heroX] = new Let();

            this.world[playerHero.heroY][playerHero.heroX] = playerHero;

            // Сбрасываем флаг начала боя
            isBattleStarted = false;
        } else {
            this.world[computerHero.heroY][computerHero.heroX] = computerHero;

            this.world[playerHero.heroY][playerHero.heroX] = new Let();
            isGAmeOver = true;
        }
        return this.world;
    }

    public void endRound(Hero[] heroes) {
        for (int i = 0; i < heroes.length; i++) {
            if (heroes[i].isAvailable && !isBattleStarted) {
                heroes[i].resetMoves();
            }
        }
    }

    public boolean isGamerWin() {
        if (mapType.equals(Test1.CROSS_MAP)) {
            return world[height / 2][0] instanceof GamerHouse && world[height / 2][weight - 1] instanceof GamerHouse
                    && world[height - 1][weight / 2] instanceof GamerHouse
                    && world[0][weight / 2] instanceof GamerHouse;
        } else {
            return world[0][0] instanceof GamerHouse && world[height - 1][weight - 1] instanceof GamerHouse
                    && world[height - 1][0] instanceof GamerHouse
                    && world[0][weight - 1] instanceof GamerHouse;
        }
    }

    public boolean isCompWin() {
        if (mapType.equals(Test1.CROSS_MAP)) {
            return world[height / 2][0] instanceof ComputerHouse && world[height / 2][weight - 1] instanceof ComputerHouse
                    && world[height - 1][weight / 2] instanceof ComputerHouse
                    && world[0][weight / 2] instanceof ComputerHouse;
        } else {
            return world[0][0] instanceof ComputerHouse && world[height - 1][weight - 1] instanceof ComputerHouse
                    && world[height - 1][0] instanceof ComputerHouse
                    && world[0][weight - 1] instanceof ComputerHouse;
        }
    }

    public void endGame() {
        if (isGamerWin()) {
            isGAmeOver = true;
            ui.printYouWin();
            clearSaveFile();
        }
        if (isCompWin()) {
            isGAmeOver = true;
            ui.printYouLose();
            clearSaveFile();
        }
    }

    public void clearSaveFile() {
        try {
            if (Objects.equals(saveName, "Игрок1")) {
                new FileOutputStream(Test1.PLAYER1_SAVE).close(); // Очистка файла
            } else if (Objects.equals(saveName, "Игрок2")) {
                new FileOutputStream(Test1.PLAYER2_SAVE).close();
            } else {
                new FileOutputStream(Test1.TEST_SAVE).close();
            }
            ui.saveClear();
        } catch (IOException e) {
            ui.saveClearError();
        }
    }
}

 */





    /*
    //
    // Конструктор для создания игрового поля.
    // Инициализирует карту, героев, здания и препятствия.
    public GameMap(int height, int weight, Hero[] heroes) {
        Random random = new Random();
        int letX; // Координата по X для препятствий
        int letY; // Координата по Y для препятствий

        // Установить параметры первого героя (игрока)
        heroes[0].isGamerHero = true;
        heroes[0].isAvailable = true;
        heroes[0].heroY = height / 2; // Начальная позиция по Y
        heroes[0].heroX = 1; // Начальная позиция по X
        heroes[0].army.put("SpearMan", 1); // Начальная армия
        heroes[0].army.put("CrossBowMan", 10);

        // Установить параметры второго героя (компьютера)
        heroes[1].isGamerHero = false;
        heroes[1].isAvailable = true;
        heroes[1].heroY = height / 2;
        heroes[1].heroX = weight - 2;
        heroes[1].army.put("SpearMan", 6);
        heroes[1].army.put("CrossBowMan", 3);

        this.height = height;
        this.weight = weight;

        // Создаем пустую карту
        this.world = new Path[this.height][this.weight];

        // Генерация карты
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.weight; j++) {
                // Размещение зданий игрока и компьютера
                if (i == height / 2 && j == 0) {
                    world[i][j] = (Path) new GamerHouse(); // Дом игрока
                    //world[i][j] = new GamerHouse(); // Дом игрока

                } else if ((i == height / 2 && j == weight - 1 || (j == weight / 2 && (i == height - 1 || i == 0)))) {
                    //world[i][j] = new ComputerHouse(); // Дом компьютера
                    world[i][j] = (Path) new ComputerHouse(); // Дом компьютера

                }
                // Дороги
                else if (i == height / 2 || j == weight / 2) {
                    //world[i][j] = new Let();
                    world[i][j] = (Path) new Let();

                }
                // Поле
                else {
                    //world[i][j] = new Field();
                    world[i][j] = (Path) new Field();

                }
            }
        }

        // Позиции героев на карте
        world[heroes[0].heroY][heroes[0].heroX] = (Path) heroes[0];
        world[heroes[1].heroY][heroes[1].heroX] = (Path) heroes[1];
        //world[heroes[0].heroY][heroes[0].heroX] = heroes[0];
        //world[heroes[1].heroY][heroes[1].heroX] = heroes[1];

        // Генерация препятствий (10 штук)
        for (int i = 0; i < 10; ) {
            letY = Math.abs(random.nextInt()) % this.height;
            letX = Math.abs(random.nextInt()) % this.weight;
            if (world[letY][letX] instanceof Field) { // Проверяем, чтобы препятствие было на поле
                //world[letY][letX] = new Gam(); // Размещаем препятствие
                world[letY][letX] = (Path) new Gam(); // Размещаем препятствие
                i++;
            }
        }
        for (int i = 0; i < 10; i++) { // Допустим, мы создаем 8 башен
            letY = random.nextInt(this.height);
            letX = random.nextInt(this.weight);
            if (world[letY][letX] instanceof Field) { // Проверяем, чтобы размещение было на поле
                //world[letY][letX] = new Tower(); // Создаем и размещаем башню
                world[letY][letX] = (Path) new Tower(); // Создаем и размещаем башню

            }
        }
    }


    // Метод очистки консоли (визуальное обновление).
    // Также добавляется задержка для удобства игрового процесса.

    public void clearConsole() throws InterruptedException {
        Thread.sleep(500); // Задержка
        for (int i = 0; i < 40; i++) {
            System.out.println(); // Печатает пустые строки для очистки
        }
    }

    // Метод обновления отображения карты на экране.
    // Вызывает метод очистки консоли и выводит текущую карту.

    public void updateDisplay() throws InterruptedException {
        clearConsole();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < weight; j++) {
                System.out.print(world[i][j] + " "); // Вывод карты в консоли
            }
            System.out.println();
        }
        endGame(); // Проверка условий окончания игры
    }


    // Перемещает героя.
    // Проверяет правила перемещения, вызов боя, работа с зданиями.

    public void moveHero(Hero hero, int dx, int dy) throws InterruptedException {
        int newX = hero.heroX + dx; // Новая координата X
        int newY = hero.heroY + dy; // Новая координата Y

        // Проверяем границы карты
        if (newX >= 0 && newX < this.weight && newY >= 0 && newY < height) {
            // Если попадаем на здание
            if (world[newY][newX] instanceof Building) {
                Building building = (Building) world[newY][newX];
                Scanner scanner = new Scanner(System.in);

                // Если здание игрока
                if (building.isGamerTower ) {
                    boolean inBuilding = true;

                    // Работа внутри здания (покупка, управление)
                    while (inBuilding && hero.gold > 0) {
                        System.out.println("Ваше золото: " + hero.gold);
                        building.printBuildings(); // Вывод доступных зданий

                        String choice = scanner.nextLine().toUpperCase();

                        if (choice.equals("Q")) { // Выход из здания
                            inBuilding = false;
                        } else {
                            building.handleBuildingPurchase(hero, choice); // Совершаем покупку
                        }

                        // После покупки спрашиваем об армии
                        if (hero.gold > 0) {
                            System.out.println("Хотите купить юнитов? (Y/N)");
                            String buyUnitChoice = scanner.nextLine().toUpperCase();

                            // Обработка покупки армии
                            if (buyUnitChoice.equals("Y")) {
                                System.out.println("Выберите юнита для покупки (1 - Копейщик, 2 - Арбалетчик, 3 - Мечник, 4 - Кавалерист, 5 - Паладин):");
                                String unitChoice = scanner.nextLine().toUpperCase();

                                System.out.println("Введите количество золота для покупки:");
                                int gold = scanner.nextInt();
                                scanner.nextLine(); // Очищаем буфер

                                if (gold > 0 && gold <= hero.gold) {
                                    hero.handleUnitPurchase(building, unitChoice, gold);
                                } else {
                                    System.out.println("Недостаточно золота или неверное количество.");
                                }
                            } else {
                                break;
                            }
                        }
                    }
                } else {
                    // Если здание врага — начинается битва
                    isBattleStarted = true;
                    BattleField btf = new BattleField(hero, building.defence);
                    btf.startBattle(hero, building.defence);
                    isBattleStarted = false;
                    world = endBattle(hero, newY, newX); // Завершение боя
                }
            }
            // Если попадаем на дорогу
            else if (world[newY][newX] instanceof Road) {
                if ((world[newY][newX] instanceof Let && hero.move > 0) || (world[newY][newX] instanceof Field && hero.move > 1)) {
                    if (world[newY][newX] instanceof Let) {
                        hero.move -= new Let().path; // Уменьшаем очки хода
                    } else if (world[newY][newX] instanceof Field) {
                        hero.move -= new Field().path; // Больше затрат на передвижение по полю
                    }

                    // Обновление карты после движения
                    if (hero.heroY == height / 2 || hero.heroX == weight / 2) {
                        //world[hero.heroY][hero.heroX] = new Let();
                        world[hero.heroY][hero.heroX] = (Path) new Let();
                    } else {
                        //world[hero.heroY][hero.heroX] = new Field();
                        world[hero.heroY][hero.heroX] = (Path) new Field();

                    }

                    hero.heroX = newX; // Устанавливаем новые координаты героя
                    hero.heroY = newY;
                    //world[hero.heroY][hero.heroX] = hero;
                    world[hero.heroY][hero.heroX] = (Path) hero;

                    updateDisplay();
                } else {
                    System.out.println("Недостаточно очков перемещения");
                }
            }
            // Если попадаем на другого героя (битва)
            else if (world[newY][newX] instanceof Hero) {
                Hero otherHero = (Hero) world[newY][newX];
                isBattleStarted = true;
                BattleField btf = new BattleField(hero, otherHero);
                btf.startBattle(hero, otherHero);
                isBattleStarted = false;
                world = endBattle(hero, otherHero); // Завершение боя
                computerIsAlive = false;
            }
        } else {
            System.out.println("Вы врезались в препятствие");
            logger.warning("Вы врезались в препятствие");
        }
        if (world[newY][newX] instanceof Tower) {
            Tower tower = (Tower) world[newY][newX];
            tower.effect(hero); // Применяем эффект башни к герою
        }
    }


    // Метод завершения битвы с зданием.
    public Path[][] endBattle(Hero playerHero, int newY, int newX) throws InterruptedException {
        // Если враг жив
        if (computerIsAlive) {
            //world[newY][newX] = new ComputerHouse();
            //world[playerHero.heroY][playerHero.heroX] = playerHero;

            world[newY][newX] = (Path) new ComputerHouse();
            world[playerHero.heroY][playerHero.heroX] = (Path) playerHero;
        } else {
            int reward = ((Building) world[newY][newX]).defence.gold;
            //world[newY][newX] = new GamerHouse();
            world[newY][newX] = (Path) new GamerHouse();

            if (playerHero.isGamerHero) {
                playerHero.gold += reward;
            }
        }
        updateDisplay();
        return world;
    }


    // Метод завершения битвы между героями.
    public static Path[][] endBattle(Hero playerHero, Hero computerHero) throws InterruptedException {
        System.out.println("Вы получили " + computerHero.gold + " золота от врага!");
        playerHero.gold += computerHero.gold;
        computerHero.gold = 0;
        computerHero.isAvailable = false;

        //world[computerHero.heroY][computerHero.heroX] = new Let();
        //world[playerHero.heroY][playerHero.heroX] = playerHero;

        world[computerHero.heroY][computerHero.heroX] = (Path) new Let();
        world[playerHero.heroY][playerHero.heroX] = (Path) playerHero;

        return world;
    }


    // Сбрасываем очки хода героев.
    public void endRound(Hero[] heroes) {
        for (Hero hero : heroes) {
            if (hero.isAvailable && !isBattleStarted) {
                hero.resetMoves();
            }
        }
    }


    // Проверяем условия окончания игры.
    public void endGame() {
        if (world[height / 2][0] instanceof GamerHouse && world[height / 2][weight - 1] instanceof GamerHouse &&
                world[height - 1][weight / 2] instanceof GamerHouse &&
                world[0][weight / 2] instanceof GamerHouse) {
            System.out.println("Вы победили!");
            System.exit(0);
        }
        if (world[height / 2][0] instanceof ComputerHouse && world[height / 2][weight - 1] instanceof ComputerHouse &&
                world[height - 1][weight / 2] instanceof ComputerHouse &&
                world[0][weight / 2] instanceof ComputerHouse) {
            System.out.println("Вы проиграли!");
            System.exit(0);
        }
    }

    // Метод редактирования карты
    public void editMap() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите координаты (x, y) для редактирования, и тип элемента (Let, Tower): ");
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        String elementType = scanner.next();

        // Проверка границ координат
        if (x >= 0 && x < weight && y >= 0 && y < height) {
            switch (elementType) {
                case "Let":
                    world[y][x] = new Let();
                    break;
                case "Tower":
                    world[y][x] = new Tower();
                    break;
                default:
                    System.out.println("Неизвестный тип элемента!");
                    return;
            }
            System.out.println("Карта обновлена.");
        } else {
            System.out.println("Координаты вне пределов карты!");
        }
    }

    // Метод для сохранения состояния игры
    public void saveGame(Hero[] heroes) {
        try (FileOutputStream fileOut = new FileOutputStream("game_save.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(heroes); // Сохранение состояния героев
            out.writeObject(world); // Сохранение состояния поля
            System.out.println("Игра сохранена.");
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении игры: " + e.getMessage());
        }
    }

    // Метод для загрузки состояния игры
    public void loadGame(Hero[] heroes) {
        try (FileInputStream fileIn = new FileInputStream("game_save.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            Hero[] loadedHeroes = (Hero[]) in.readObject(); // Загрузка состояния героев
            world = (Path[][]) in.readObject(); // Загрузка состояния поля

            // Обновление текущих героев до загруженных
            for (int i = 0; i < heroes.length; i++) {
                heroes[i] = loadedHeroes[i];
            }
            System.out.println("Игра загружена.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка при загрузке игры: " + e.getMessage());
        }
    }

     */





/*
package maps;
import java.util.Random;
import java.util.Scanner;

import Castle.ComputerHouse;
import Castle.GamerHouse;
import Hero.Hero;
import battlef.BattleField;
import buildings.Building;
import buildings.Tower;


import java.util.Random; // Импортируем класс Random для генерации случайных чисел
import java.util.Scanner; // Импортируем класс Scanner для считывания пользовательского ввода

public class GameMap {
    // Статический флаг для проверки, жив ли компьютер (противник)
    public static boolean computerIsAlive = true;

    // Размеры карты: высота и ширина
    private final int height; // Высота карты
    private final int weight;  // Ширина карты

    // Двумерный массив, представляющий игровую карту
    private static Path[][] world; // Является основной игровой картой

    // Флаг, указывающий, началось ли сражение
    public static boolean isBattleStarted = false;

    // Конструктор для создания игрового поля.
    // Инициализирует карту, героев, здания и препятствия.
    public GameMap(int height, int weight, Hero[] heroes) {
        Random random = new Random(); // Создаем объект Random для генерации случайных чисел
        int letX; // Координата по X для препятствий
        int letY; // Координата по Y для препятствий

        // Установить параметры первого героя (игрока)
        heroes[0].isGamerHero = true; // Устанавливаем флаг, что это герой игрока
        heroes[0].isAvailable = true; // Герой доступен для игры
        heroes[0].heroY = height / 2; // Начальная позиция по Y
        heroes[0].heroX = 1; // Начальная позиция по X
        heroes[0].army.put("SpearMan", 1); // Начальная армия
        heroes[0].army.put("CrossBowMan", 10); // Начальное количество воинов

        // Установить параметры второго героя (компьютера)
        heroes[1].isGamerHero = false; // Устанавливаем флаг, что это герой компьютера
        heroes[1].isAvailable = true; // Герой доступен
        heroes[1].heroY = height / 2; // Позиция по Y
        heroes[1].heroX = weight - 2; // Позиция по X
        heroes[1].army.put("SpearMan", 6); // Начальная армия компьютера
        heroes[1].army.put("CrossBowMan", 3); // Начальное количество воинов компьютера

        this.height = height; // Инициализация высоты игрового поля
        this.weight = weight; // Инициализация ширины игрового поля

        // Создаем пустую карту
        this.world = new Path[this.height][this.weight]; // Инициализируем двумерный массив для карты

        // Генерация карты
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.weight; j++) {
                // Размещение зданий игрока и компьютера
                if (i == height / 2 && j == 0) {
                    world[i][j] = new GamerHouse(); // Дом игрока
                } else if ((i == height / 2 && j == weight - 1 || (j == weight / 2 && (i == height - 1 || i == 0)))) {
                    world[i][j] = new ComputerHouse(); // Дом компьютера
                }
                // Дороги
                else if (i == height / 2 || j == weight / 2) {
                    world[i][j] = new Let(); // Проходимая территория
                }
                // Поле
                else {
                    world[i][j] = new Field(); // Обычное поле
                }
            }
        }

        // Позиции героев на карте
        world[heroes[0].heroY][heroes[0].heroX] = heroes[0]; // Устанавливаем игрока на карту
        world[heroes[1].heroY][heroes[1].heroX] = heroes[1]; // Устанавливаем компьютера на карту

        // Генерация препятствий (10 штук)
        for (int i = 0; i < 10; ) {
            letY = Math.abs(random.nextInt()) % this.height; // Генерация случайной координаты Y
            letX = Math.abs(random.nextInt()) % this.weight; // Генерация случайной координаты X
            if (world[letY][letX] instanceof Field) { // Проверяем, чтобы препятствие было на поле
                world[letY][letX] = new Gam(); // Размещаем препятствие
                i++; // Увеличиваем счетчик препятствий
            }
        }
    }

    // Метод очистки консоли (визуальное обновление).
    // Также добавляется задержка для удобства игрового процесса.
    private void clearConsole() throws InterruptedException {
        Thread.sleep(500); // Задержка перед очисткой
        for (int i = 0; i < 40; i++) {
            System.out.println(); // Печатает пустые строки для очистки консоли
        }
    }

    // Метод обновления отображения карты на экране.
    // Вызывает метод очистки консоли и выводит текущую карту.
    private void updateDisplay() throws InterruptedException {
        clearConsole(); // Очищаем консоль
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < weight; j++) {
                System.out.print(world[i][j] + " "); // Вывод карты в консоли
            }
            System.out.println(); // Переход на следующую строку
        }
        endGame(); // Проверка условий окончания игры
    }

    // Перемещает героя.
    // Проверяет правила перемещения, вызов боя, работа с зданиями.
    /*
    public void moveHero(Hero hero, int dx, int dy) throws InterruptedException {
        int newX = hero.heroX + dx; // Новая координата X после движения
        int newY = hero.heroY + dy; // Новая координата Y после движения

        // Проверяем границы карты
        if (newX >= 0 && newX < this.weight && newY >= 0 && newY < height) {
            // Если попадаем на здание
            if (world[newY][newX] instanceof Building) {
                Building building = (Building) world[newY][newX]; // Приводим объект к типу Building
                Scanner scanner = new Scanner(System.in); // Создаем сканер для ввода

                // Если здание игрока
                if (building.isGamerTower) {
                    boolean inBuilding = true; // Флаг для проверки, внутри здания ли герой

                    // Работа внутри здания (покупка, управление)
                    while (inBuilding && hero.gold > 0) {
                        System.out.println("Ваше золото: " + hero.gold); // Показываем текущее количество золота
                        building.printBuildings(); // Вывод доступных зданий

                        String choice = scanner.nextLine().toUpperCase(); // Считываем выбор

                        if (choice.equals("Q")) { // Выход из здания
                            inBuilding = false; // Устанавливаем флаг для выхода
                        } else {
                            building.handleBuildingPurchase(hero, choice); // Совершаем покупку
                        }

                        // После покупки спрашиваем об армии
                        if (hero.gold > 0) {
                            System.out.println("Хотите купить юнитов? (Y/N)");
                            String buyUnitChoice = scanner.nextLine().toUpperCase(); // Считываем выбор на покупку юнитов

                            // Обработка покупки армии
                            if (buyUnitChoice.equals("Y")) {
                                System.out.println("Выберите юнита для покупки (1 - Копейщик, 2 - Арбалетчик, 3 - Мечник, 4 - Кавалерист, 5 - Паладин):");
                                String unitChoice = scanner.nextLine().toUpperCase(); // Выбор юнита

                                System.out.println("Введите количество золота для покупки:");
                                int gold = scanner.nextInt(); // Ввод количества золота для покупки
                                scanner.nextLine(); // Очищаем буфер после ввода числа

                                // Проверка на достаточное количество золота
                                if (gold > 0 && gold <= hero.gold) {
                                    hero.handleUnitPurchase(building, unitChoice, gold); // Покупка юнита
                                } else {
                                    System.out.println("Недостаточно золота или неверное количество."); // Ошибка
                                }
                            } else {
                                break; // Выход из цикла, если выбор "N"
                            }
                        }
                    }
                } else {
                    // Если здание врага — начинается битва
                    isBattleStarted = true; // Устанавливаем флаг, что битва началась
                    BattleField btf = new BattleField(hero, building.defence); // Инициализируем поле боя
                    btf.startBattle(hero, building.defence); // Начинаем бой
                    isBattleStarted = false; // Сбрасываем флаг после боя
                    world = endBattle(hero, newY, newX); // Завершение боя
                }
            }
            // Если попадаем на дорогу
            else if (world[newY][newX] instanceof Road) {
                // Проверка, достаточно ли очков для перемещения
                if ((world[newY][newX] instanceof Let && hero.move > 0) || (world[newY][newX] instanceof Field && hero.move > 1)) {
                    if (world[newY][newX] instanceof Let) {
                        hero.move -= new Let().path; // Уменьшаем очки хода для проходимой территории
                    } else if (world[newY][newX] instanceof Field) {
                        hero.move -= new Field().path; // Уменьшаем очки хода для обычного поля
                    }

                    // Обновление карты после движения
                    if (hero.heroY == height / 2 || hero.heroX == weight / 2) {
                        world[hero.heroY][hero.heroX] = new Let(); // Заменяем старую позицию героя на проходимую территорию
                    } else {
                        world[hero.heroY][hero.heroX] = new Field(); // Заменяем старую позицию на обычное поле
                    }

                    hero.heroX = newX; // Устанавливаем новые координаты героя
                    hero.heroY = newY;
                    world[hero.heroY][hero.heroX] = hero; // Устанавливаем героя на новое место

                    updateDisplay(); // Обновляем отображение карты
                } else {
                    System.out.println("Недостаточно очков перемещения"); // Сообщаем об ошибке недостатка очков
                }
            }
            // Если попадаем на другого героя (битва)
            else if (world[newY][newX] instanceof Hero) {
                Hero otherHero = (Hero) world[newY][newX]; // Получаем ссылку на другого героя
                isBattleStarted = true; // Устанавливаем флаг битвы
                BattleField btf = new BattleField(hero, otherHero); // Создаем поле боя
                btf.startBattle(hero, otherHero); // Начинаем бой
                isBattleStarted = false; // Сбрасываем флаг после боя
                world = endBattle(hero, otherHero); // Завершение боя
                computerIsAlive = false; // Устанавливаем флаг, что компьютер мертв
            }
        } else {
            System.out.println("Вы врезались в препятствие"); // Сообщаем о столкновении с границей карты
        }
    }


    public void moveHero(Hero hero, int dx, int dy) throws InterruptedException {
        int newX = hero.heroX + dx;
        int newY = hero.heroY + dy;
        // Проверка границ карты
        if (newX >= 0 && newX < this.weight && newY >= 0 && newY < height) {
            if (world[newY][newX] instanceof Building) {
                Building building = (Building) world[newY][newX];
                Scanner scanner = new Scanner(System.in);
                if(building.isGamerTower){
                    //if(building.isGamerTower && hero.isGamerHero){
                    boolean inBuilding = true;

                    while (inBuilding && hero.gold > 0) {
                        System.out.println("Ваше золото: " + hero.gold);
                        building.printBuildings();

                        String choice = scanner.nextLine().toUpperCase();

                        if(choice.equals("Q")){
                            inBuilding = false;
                        }else{
                            building.handleBuildingPurchase(hero, choice);
                        }

                        if(hero.gold > 0){

                            System.out.println("Хотите купить юнитов? (Y/N)");
                            String buyUnitChoice = scanner.nextLine().toUpperCase();

                            if (buyUnitChoice.equals("Y")){

                                System.out.println("Выберите юнита для покупки (1 - Копейщик, 2 - Арбалетчик, 3 - Мечник, 4 - Кавалерист, 5 - Паладин):");
                                String unitChoice = scanner.nextLine().toUpperCase();

                                System.out.println("Введите количество золота для покупки:");
                                int gold = scanner.nextInt();
                                scanner.nextLine(); // Очистка буфера после nextInt()
                                if(gold > 0 && gold <= hero.gold){
                                    hero.handleUnitPurchase(building, unitChoice, gold);
                                }else{
                                    System.out.println("Недостаточно золота или неверное количество.");
                                }


                            }else {
                                break;
                            }
                        }
                    }
                }else{
                    isBattleStarted = true;
                    BattleField btf = new BattleField(hero, building.defence);
                    btf.startBattle(hero, building.defence);
                    isBattleStarted = false;
                    world = endBattle(hero, newY, newX);
                }
            } else if (world[newY][newX] instanceof Road) {
                if ((world[newY][newX] instanceof Let && hero.move > 0) || (world[newY][newX] instanceof Field && hero.move > 1)) {
                    if (world[newY][newX] instanceof Let) {
                        hero.move -= new Let().path;
                        System.out.println("Поле отняло 1 очко");
                    } else if (world[newY][newX] instanceof Field) {
                        hero.move -= new Field().path;
                        System.out.println("Поле отняло 2 очко");
                    }
                    System.out.println("У героя осталось " +hero.move+" ходов");

                    if (hero.heroY == height / 2 || hero.heroX == weight / 2) {
                        world[hero.heroY][hero.heroX] = new Let();
                    } else {
                        world[hero.heroY][hero.heroX] = new Field();
                    }

                    hero.heroX = newX;
                    hero.heroY = newY;
                    world[hero.heroY][hero.heroX] = hero;

                    updateDisplay();
                } else {
                    System.out.println("Недостаточно очков перемещения");
                }
            }
            else if (world[newY][newX] instanceof Hero) {
                Hero otherHero = (Hero) world[newY][newX];
                if (!hero.isGamerHero){
                    hero.move = 0;
                }else {
                    otherHero.move = 0;
                }
                isBattleStarted = true;
                BattleField btf = new BattleField(hero, otherHero);
                btf.startBattle(hero, otherHero);
                isBattleStarted = false;
                world = endBattle(hero, otherHero);
                computerIsAlive = false;
                world[newY][newX] = new Field();
                updateDisplay();
            }
        } else {
            System.out.println("Вы врезались в препятствие");
        }
    }

    // Метод завершения битвы с зданием.
    public Path[][] endBattle(Hero playerHero, int newY, int newX) throws InterruptedException {
        // Если враг жив
        if (computerIsAlive) {
            world[newY][newX] = new ComputerHouse(); // Заменяем здание врага на новое
            world[playerHero.heroY][playerHero.heroX] = playerHero; // Устанавливаем позицию игрока
        } else {
            int reward = ((Building) world[newY][newX]).defence.gold; // Получаем награду после победы над зданием
            world[newY][newX] = new GamerHouse(); // Заменяем здание на дом игрока
            if (playerHero.isGamerHero) {
                playerHero.gold += reward; // Добавляем золото игроку
            }
        }
        updateDisplay(); // Обновляем вывод карты
        return world; // Возвращаем обновленную карту
    }


    // Метод завершения битвы между героями.
    public static Path[][] endBattle(Hero playerHero, Hero computerHero) throws InterruptedException {
        System.out.println("Вы получили " + computerHero.gold + " золота от врага!"); // Уведомляем игрока о награде
        playerHero.gold += computerHero.gold; // Добавляем золото к награде
        computerHero.gold = 0; // Устанавливаем золото компьютера в 0
        computerHero.isAvailable = false; // Компьютер больше не доступен

        world[computerHero.heroY][computerHero.heroX] = new Let(); // Заменяем позицию компьютера на проходимую территорию
        world[playerHero.heroY][playerHero.heroX] = playerHero; // Устанавливаем игрока на карте

        return world; // Возвращаем обновленную карту
    }



    // Сбрасываем очки хода героев.
    public void endRound(Hero[] heroes) {
        for (Hero hero : heroes) {
            if (hero.isAvailable && !isBattleStarted) {
                hero.resetMoves(); // Сбрасываем очки хода для доступных героев
            }
        }
    }

    // Проверяем условия окончания игры.
    public void endGame() {
        // Условие победы игрока
        if (world[height / 2][0] instanceof GamerHouse && world[height / 2][weight - 1] instanceof GamerHouse &&
                world[height - 1][weight / 2] instanceof GamerHouse &&
                world[0][weight / 2] instanceof GamerHouse) {
            // Сообщаем о победе и завершаем игру
            System.out.println("Вы победили!");
            System.exit(0); // Выход из программы
        }
        // Условие поражения игрока
        if (world[height / 2][0] instanceof ComputerHouse && world[height / 2][weight - 1] instanceof ComputerHouse &&
                world[height - 1][weight / 2] instanceof ComputerHouse &&
                world[0][weight / 2] instanceof ComputerHouse) {
            // Сообщаем о поражении и завершаем игру
            System.out.println("Вы проиграли!");
            System.exit(0); // Выход из программы
        }
    }
}

 */

