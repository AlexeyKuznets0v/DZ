/*
package UI;

import Hero.Hero;
import buildings.Building;
import maps.*;
import units.Unit;
import Saves.Player;

import java.io.Serializable;

public class UI implements Serializable {
    public void clearConsole() throws InterruptedException {
        Thread.sleep(500);
        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }

    public void newMap(Path[][] world, int height, int weight) throws InterruptedException {
        clearConsole();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < weight; j++) {
                System.out.print(world[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void printYouWin(){
        System.out.println("Вы победили!");
    }

    public void printYouLose(){
        System.out.println("Вы проиграли");
    }

    public void printHeroGold(Hero hero){
        System.out.println("Ваше золото: " + hero.gold);
    }

    public void printCompGold(Hero hero){
        System.out.println("Вы получили " + hero.gold + " золота от врага!");
    }

    public void printReward(int reward){
        System.out.println("Вы захватили здание и получили " + reward + " золота!");
    }

    public void chooseInShop(){
        System.out.println("Хотите купить юнитов? (Y/N)");
    }

    public void choosePlayer(){
        System.out.println("Выберите игрока (1 или 2):");
    }

    public void chooseMap(){
        System.out.println("Выберите тип карты:");
        System.out.println("1. Крестообразная");
        System.out.println("2. Диагональная");
    }

    public void saveSuccess(){
        System.out.println("Игра успешно загружена!");
    }

    public void saveClear(){
        System.out.println("Сохранение очищено");
    }

    public void saveClearError(){
        System.err.println("Ошибка очистки файла сохранения");
    }

    public void invalidInput(){
        System.out.println("Invalid input.");
    }

    public void yourStep(){
        System.out.print("Ваш ход: ");
    }
    public void compStep(){
        System.out.println("Ход компьютера...");
    }

    public void printScores(Player player1, Player player2){
        System.out.println("Игра окончена! Текущие очки:");
        System.out.println(player1.name + ": " + player1.score);
        System.out.println(player2.name + ": " + player2.score);
    }

    public void saveGame(){
        System.out.println("Игра сохранена!");
    }

    public void saveError(){
        System.out.println("Ошибка загрузки, начинаем новую игру");
    }

    public void chooseLoad(){
        System.out.println("1. Новая игра");
        System.out.println("2. Загрузить игру");
        System.out.print("Выберите вариант: ");
    }

    public void chooseGenerateMap(){
        System.out.println("Выберите хотите ли сами сгенерировать препятствия или рандом(mod/not)");
    }

    public void printExit(){
        System.out.println("Exiting game...");
    }

    public void chooseMove(){
        System.out.println("WASD - двигаться. Q - выйти. Save - сохранить");
    }

    public void chooseGold(){
        System.out.println("Введите количество золота для покупки:");
    }

    public void printBuyingUnits(){
        System.out.println("Выберите юнита для покупки (1 - Копейщик, 2 - Арбалетчик, 3 - Мечник, 4 - Кавалерист, 5 - Паладин):");
    }

    public void printUnitStats(Unit unit, int x, int y){
        System.out.println("\nХод юнита: " + unit.design + " в позиции (" + x + ", " + y + ")");
        System.out.println("Здоровье одного юнита - " + unit.HP);
        System.out.println("Урон одного юнита - " + unit.damage);
        System.out.println("Максимальное здоровье юнита " + unit.totalHP);
        System.out.println("Максимальная дистанция атаки юнита - " + unit.distance);
        System.out.println("Максимальная дистанция перемещения - " + unit.move);
        System.out.println("Максимальный урон юнита - " + unit.totalDamage);
    }

    public void printUnitDie(Unit target){
        System.out.println("Юнит " + target.design + " погиб");
    }




    public void chooseForAttack(){
        System.out.println("Выберите цель для атаки:");
        System.out.print("Введите координаты цели (x, y): ");
    }

    public void printTarget(Unit target, int x, int y){
        System.out.println("Цель: (" + x + ", " + y + ") - " + target.design);
    }

    public void spawnUnit(String unit, int x, int y){
        System.out.println("Добавлен юнит: " + unit + " в позицию (" + y + ", " + x + ")");
    }

    public void unitMove(){
        System.out.print("Введите направление (WASD): ");
    }

    public void endFight(){
        System.out.println("Бой завершен!");
    }

    public void countUnits(boolean heroUnits, boolean compUnits){
        System.out.println("Игрок имеет юнитов: " + heroUnits);
        System.out.println("Компьютер имеет юнитов: " + compUnits);
    }

    public void fileNotFound(){
        System.out.println("Файл сохранения очков не найден или пуст. Используются значения по умолчанию.");
    }

    public void letIsHere(){
        System.out.println("Вы врезались в препятствие");
    }


    public void printBuildings(Building building){
        System.out.println("Доступные здания:");
        //if (!building.tavern) {
          //  System.out.println("Купить таверну за 15 золота? (H)");
        //} else {
          //  System.out.println("Таверна уже куплена.");
        //}
        //if (!building.stable) {
          //  System.out.println("Купить конюшню за 20 золота? (S)");
        //} else {
          //  System.out.println("Конюшня уже куплена.");
        //}
        if (!building.guardPost) {
            System.out.println("Купить сторожевой пост за 13 золота? (G)");
        } else {
            System.out.println("Сторожевой пост уже куплен.");
        }
        if (!building.towerOfCrossBowMen) {
            System.out.println("Купить башню арбалетчиков за 16 золота? (T)");
        } else {
            System.out.println("Башня арбалетчиков уже куплена.");
        }
        if (!building.armory) {
            System.out.println("Купить оружейную за 20 золота? (A)");
        } else {
            System.out.println("Оружейная уже куплена.");
        }
        if (!building.arena) {
            System.out.println("Купить арену за 25 золота? (R)");
        } else {
            System.out.println("Арена уже куплена.");
        }
        if (!building.cathedral) {
            System.out.println("Купить собор за 30 золота? (C)");
        } else {
            System.out.println("Собор уже куплен.");
        }
        System.out.println("Введите 'Q' для выхода");
    }

    public void printPath(int x){
        System.out.println("Поле отняло " + x + " очко");
    }

    public void movesRemaining(Hero hero){
        System.out.println("У героя осталось " + hero.move +" ходов");
    }

    public void needMoreMoves(){
        System.out.println("Недостаточно очков перемещения");
    }

    public void needMoreMoney(){
        System.out.println("Недостаточно золота для покупки юнитов.");
    }

    public void printBuyingUnits(int maxUnits){
        System.out.println("Куплено " + maxUnits + " юнитов.");
    }

    public void printResetMoves(Hero hero){
        System.out.println("Восстановили "+ hero.move +" ходов");
    }

    public void printMoveUnit(int newY, int newX){
        System.out.println("Юнит переместился на (" + newY + ", " + newX + ")");
    }

    public void printAttackUnit(Unit unit,Unit target){
        System.out.println("У " + target.design + " здоровья: " + target.totalHP);
        System.out.println(unit.design + " атакует " + target.design + "!");
        System.out.println("Нанесено урона: " + unit.totalDamage);
    }

    public void printDamageLeft(Unit target){
        System.out.println("У " + target.design + " осталось здоровья: " + target.totalHP);
    }

    public void printFriendlyFire(){
        System.out.println("Нельзя атаковать своих юнитов.");
    }

    public void wrongOneOrTwo(){
        System.out.println("Неверный выбор. Введите 1 или 2.");
    }

    public void chooseXY(){
        System.out.print("Введите координаты цели (x, y): ");
    }

    public void printLetIsWrong(){
        System.out.println("Препятствие наехало на важный участок");
    }

    public void printLetOutOfBounds(){
        System.out.println("Препятствие за границей мира");
    }

    public void emulateShoping(){
        System.out.println("Закупка зданий и юнитов");
    }

    public void enemyOnYourBase(){
        System.out.println("Враг захватил ваше здание!");
    }

    public void chooseAct(boolean target){
        if(target){
            System.out.println("1. Двигаться");
            System.out.println("2. Атаковать");
        }else{
            System.out.println("1. Двигаться");
        }
        System.out.print("Выберите действие: ");
    }

    public void targetIsNotUnit(){
        System.out.println("Цель не является юнитом.");
    }

    public void targetOutOfScope(){
        System.out.println("Цель вне зоны досягаемости.");
    }

    public void defSuccess(){
        System.out.println("Ваше здание отбило атаку!");
    }

    public void enemyAttackYourUnit(Unit unit){
        System.out.println("Враг атаковал вашего юнита " + unit.design);
    }

    public void coffeeWarning(){
        System.out.println("Хватит пить кофе, сердечный приступ не за горами");
    }

    public void goOut(int moreMoney){
        System.out.println("Проваливай пока не найдешь "+ moreMoney +" золотых");
    }

    public void salonWarning(){
        System.out.println("У тебя лысая бошка, стричь нечего");
    }

    public void waitQue(){
        System.out.println("Погоди, надо отстоять свою очередь");
    }

    public void hotelWarning(){
        System.out.println("Ты сюда играть пришел или спать?");
    }

    public void goOutFromHotel(){
        System.out.println("Сходи поспи на улице пока не найдешь 15 монет");
    }

    public void startSleeping(){
        System.out.println("Самое время поспать");
    }

    public void isGoodNight(){
        System.out.println("Хорошо поспал?");
    }

    public void printNPC(String number){
        System.out.println("Вышел нпс - " + number);
    }

    public void endOfQue(){
        System.out.println("Все люди обслужены");
    }


}

 */


package UI;

import Hero.Hero; // Импорт класса Hero, описывающего героя
import buildings.Building; // Импорт класса Building, описывающего здания
import maps.*; // Импорт всех классов из пакета maps
import units.Unit; // Импорт класса Unit, описывающего юниты
import Saves.Player; // Импорт класса Player, описывающего игрока

import java.io.Serializable; // Импорт Serializable для сериализации объектов

// Класс UI отвечает за взаимодействие с пользователем, вывод информации на экран
public class UI implements Serializable {

    // Метод для очистки консоли
    public void clearConsole() throws InterruptedException {
        Thread.sleep(500); // Пауза на 500 миллисекунд для ажиотажа
        for (int i = 0; i < 40; i++) {
            System.out.println(); // Печать пустых строк для очистки экрана
        }
    }

    // Метод для отображения новой карты
    public void newMap(Path[][] world, int height, int weight) throws InterruptedException {
        clearConsole(); // Очистка консоли перед выводом новой карты
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < weight; j++) {
                System.out.print(world[i][j] + " "); // Печать каждого элемента карты
            }
            System.out.println(); // Печать новой строки после завершения строки карты
        }
    }

    // Метод вывода сообщения о победе
    public void printYouWin(){
        System.out.println("Вы победили!"); // Сообщение о победе игрока
    }

    // Метод вывода сообщения о поражении
    public void printYouLose(){
        System.out.println("Вы проиграли"); // Сообщение о поражении игрока
    }

    // Метод для отображения количества золота у героя
    public void printHeroGold(Hero hero){
        System.out.println("Ваше золото: " + hero.gold); // Вывод золота героя
    }

    // Метод для отображения золота, полученного от врага
    public void printCompGold(Hero hero){
        System.out.println("Вы получили " + hero.gold + " золота от врага!"); // Сообщение о получении золота
    }

    // Метод для отображения вознаграждения за захват здания
    public void printReward(int reward){
        System.out.println("Вы захватили здание и получили " + reward + " золота!"); // Вывод информации о вознаграждении
    }

    // Метод для запроса у игрока, хочет ли он купить юнитов
    public void chooseInShop(){
        System.out.println("Хотите купить юнитов? (Y/N)"); // Сообщение с вопросом
    }

    // Метод для выбора игрока (игрок 1 или 2)
    public void choosePlayer(){
        System.out.println("Выберите игрока (1 или 2):"); // Запрос выбора игрока
    }

    // Метод для выбора типа карты
    public void chooseMap(){
        System.out.println("Выберите тип карты:"); // Запрос выбора типа карты
        System.out.println("1. Крестообразная");
        System.out.println("2. Диагональная");
    }

    // Метод для отображения сообщения об успешном сохранении
    public void saveSuccess(){
        System.out.println("Игра успешно загружена!"); // Подтверждение успешного сохранения
    }

    // Метод для отображения сообщения об очищении сохранения
    public void saveClear(){
        System.out.println("Сохранение очищено"); // Сообщение об очищении сохранений
    }

    // Метод для вывода сообщения об ошибке очистки сохранения
    public void saveClearError(){
        System.err.println("Ошибка очистки файла сохранения"); // Сообщение об ошибке
    }

    // Метод для вывода сообщения об некорректном вводе
    public void invalidInput(){
        System.out.println("Invalid input."); // Сообщение о неправильном вводе
    }

    // Метод для запроса хода игрока
    public void yourStep(){
        System.out.print("Ваш ход: "); // Запрос ввода хода от игрока
    }

    // Метод для обозначения хода компьютера
    public void compStep(){
        System.out.println("Ход компьютера..."); // Информирование о ходе компьютера
    }

    // Метод для отображения текущих очков обеих игроков
    public void printScores(Player player1, Player player2){
        System.out.println("Игра окончена! Текущие очки:"); // Сообщение об окончании игры
        System.out.println(player1.name + ": " + player1.score); // Вывод очков первого игрока
        System.out.println(player2.name + ": " + player2.score); // Вывод очков второго игрока
    }

    // Метод для подтверждения сохранения игры
    public void saveGame(){
        System.out.println("Игра сохранена!"); // Сообщение о сохранении игры
    }

    // Метод для сообщения об ошибке загрузки игры
    public void saveError(){
        System.out.println("Ошибка загрузки, начинаем новую игру"); // Оповещение об ошибке загрузки
    }

    // Метод для выбора между новой игрой и загрузкой
    public void chooseLoad(){
        System.out.println("1. Новая игра"); // Опция для новой игры
        System.out.println("2. Загрузить игру"); // Опция для загрузки игры
        System.out.print("Выберите вариант: "); // Запрос выбора
    }

    // Метод для выбора способа генерации карты
    public void chooseGenerateMap(){
        System.out.println("Выберите хотите ли сами сгенерировать препятствия или рандом(mod/not)"); // Запрос выбора способа
    }

    // Метод для сообщения об остановке игры
    public void printExit(){
        System.out.println("Exiting game..."); // Информирование о выходе из игры
    }

    // Метод для инструкций по движению
    public void chooseMove(){
        System.out.println("WASD - двигаться. Q - выйти. Save - сохранить"); // Инструкции по управлению
    }

    // Метод для запроса количества золота для покупки
    public void chooseGold(){
        System.out.println("Введите количество золота для покупки:"); // Запрос на ввод суммы золота
    }

    // Метод для выбора юнита для покупки
    public void printBuyingUnits(){
        System.out.println("Выберите юнита для покупки (1 - Копейщик, 2 - Арбалетчик, 3 - Мечник, 4 - Кавалерист, 5 - Паладин):"); // Опции выбора юнита
    }

    // Метод для отображения характеристик юнита
    public void printUnitStats(Unit unit, int x, int y){
        System.out.println("\nХод юнита: " + unit.design + " в позиции (" + x + ", " + y + ")"); // Информация о текущем юните
        System.out.println("Здоровье одного юнита - " + unit.HP); // Текущее здоровье юнита
        System.out.println("Урон одного юнита - " + unit.damage); // Урон юнита
        System.out.println("Максимальное здоровье юнита " + unit.totalHP); // Максимальное здоровье юнита
        System.out.println("Максимальная дистанция атаки юнита - " + unit.distance); // Дистанция атаки юнита
        System.out.println("Максимальная дистанция перемещения - " + unit.move); // Дистанция перемещения
        System.out.println("Максимальный урон юнита - " + unit.totalDamage); // Максимальный урон юнита
    }

    // Метод для отображения смерти юнита
    public void printUnitDie(Unit target){
        System.out.println("Юнит " + target.design + " погиб"); // Сообщение о смерти юнита
    }

    // Метод для выбора цели атаки
    public void chooseForAttack(){
        System.out.println("Выберите цель для атаки:"); // Запрос выбора цели
        System.out.print("Введите координаты цели (x, y): "); // Запрос координат цели
    }

    // Метод для отображения информации о выбранной цели
    public void printTarget(Unit target, int x, int y){
        System.out.println("Цель: (" + x + ", " + y + ") - " + target.design); // Информация о цели атаки
    }

    // Метод для добавления юнита в заданной позиции
    public void spawnUnit(String unit, int x, int y){
        System.out.println("Добавлен юнит: " + unit + " в позицию (" + y + ", " + x + ")"); // Подтверждение добавления юнита
    }

    // Метод для запроса направления движения
    public void unitMove(){
        System.out.print("Введите направление (WASD): "); // Запрос направления движения
    }

    // Метод для окончания боя
    public void endFight(){
        System.out.println("Бой завершен!"); // Сообщение о завершении боя
    }

    // Метод для отображения количества юнитов у игроков
    public void countUnits(boolean heroUnits, boolean compUnits){
        System.out.println("Игрок имеет юнитов: " + heroUnits); // Информация о юнитах игрока
        System.out.println("Компьютер имеет юнитов: " + compUnits); // Информация о юнитах компьютера
    }

    // Метод для уведомления о проблемах с файлом сохранения
    public void fileNotFound(){
        System.out.println("Файл сохранения очков не найден или пуст. Используются значения по умолчанию."); // Сообщение о проблемах с сохранением
    }

    // Метод для уведомления о столкновении с препятствием
    public void letIsHere(){
        System.out.println("Вы врезались в препятствие"); // Уведомление о столкновении
    }

    // Метод для отображения доступных зданий
    public void printBuildings(Building building){
        System.out.println("Доступные здания:"); // Информация о доступных зданиях
        //if (!building.tavern) {
        //  System.out.println("Купить таверну за 15 золота? (H)");
        //} else {
        //  System.out.println("Таверна уже куплена.");
        //}
        //if (!building.stable) {
        //  System.out.println("Купить конюшню за 20 золота? (S)");
        //} else {
        //  System.out.println("Конюшня уже куплена.");
        //}
        if (!building.guardPost) {
            System.out.println("Купить сторожевой пост за 13 золота? (G)"); // Предложение о покупке сторожевого поста
        } else {
            System.out.println("Сторожевой пост уже куплен."); // Информация о том, что пост уже куплен
        }
        if (!building.towerOfCrossBowMen) {
            System.out.println("Купить башню арбалетчиков за 16 золота? (T)"); // Предложение о покупке башни арбалетчиков
        } else {
            System.out.println("Башня арбалетчиков уже куплена."); // Информация о покупке
        }
        if (!building.armory) {
            System.out.println("Купить оружейную за 20 золота? (A)"); // Предложение о покупке оружейной
        } else {
            System.out.println("Оружейная уже куплена."); // Информация о покупке
        }
        if (!building.arena) {
            System.out.println("Купить арену за 25 золота? (R)"); // Предложение о покупке арены
        } else {
            System.out.println("Арена уже куплена."); // Информация о покупке
        }
        if (!building.cathedral) {
            System.out.println("Купить собор за 30 золота? (C)"); // Предложение о покупке собора
        } else {
            System.out.println("Собор уже куплен."); // Информация о покупке
        }
        System.out.println("Введите 'Q' для выхода"); // Сообщение о выходе из меню
    }

    // Метод для отображения потери очков игрока на поле
    public void printPath(int x){
        System.out.println("Поле отняло " + x + " очко"); // Оповещение о потере очков
    }

    // Метод для отображения оставшихся ходов у героя
    public void movesRemaining(Hero hero){
        System.out.println("У героя осталось " + hero.move +" ходов"); // Сообщение о оставшихся ходах
    }

    // Метод для уведомления о недостаточности ходов
    public void needMoreMoves(){
        System.out.println("Недостаточно очков перемещения"); // Уведомление о недостатке очков перемещения
    }

    // Метод для уведомления о недостаточном количестве золота для покупки юнитов
    public void needMoreMoney(){
        System.out.println("Недостаточно золота для покупки юнитов."); // Уведомление о недостаточности золота
    }

    // Метод для подтверждения удачной покупки юнитов
    public void printBuyingUnits(int maxUnits){
        System.out.println("Куплено " + maxUnits + " юнитов."); // Підтвердження про покупку юнитов
    }

    // Метод для восстановления ходов у героя
    public void printResetMoves(Hero hero){
        System.out.println("Восстановили "+ hero.move +" ходов"); // Уведомление о восстановлении ходов
    }

    // Метод для отображения перемещения юнита
    public void printMoveUnit(int newY, int newX){
        System.out.println("Юнит переместился на (" + newY + ", " + newX + ")"); // Информация о новом расположении юнита
    }

    // Метод для отображения информации об атаке юнита
    public void printAttackUnit(Unit unit, Unit target){
        System.out.println("У " + target.design + " здоровья: " + target.totalHP); // Здоровье цели перед атакой
        System.out.println(unit.design + " атакует " + target.design + "!"); // Сообщение об атаке
        System.out.println("Нанесено урона: " + unit.totalDamage); // Урон, нанесенный целью
    }

    // Метод для отображения оставшегося здоровья у цели
    public void printDamageLeft(Unit target){
        System.out.println("У " + target.design + " осталось здоровья: " + target.totalHP); // Следующее здоровье цели
    }

    // Метод для уведомления о попытке атаки собственных юнитов
    public void printFriendlyFire(){
        System.out.println("Нельзя атаковать своих юнитов."); // Сообщение о запрете атаки своих юнитов
    }

    // Метод для уведомления о неверном выборе (не 1 и не 2)
    public void wrongOneOrTwo(){
        System.out.println("Неверный выбор. Введите 1 или 2."); // Уведомление о неверном вводе
    }

    // Метод для запроса координат цели
    public void chooseXY(){
        System.out.print("Введите координаты цели (x, y): "); // Запрос на ввод координат
    }

    // Метод для отображения информации о препятствиях
    public void printLetIsWrong(){
        System.out.println("Препятствие наехало на важный участок"); // Уведомление о проблеме с препятствием
    }

    // Метод для уведомления о выходе за границы карты
    public void printLetOutOfBounds(){
        System.out.println("Препятствие за границей мира"); // Сообщение о выходе за границы карты
    }

    // Метод для обозначения процесса покупки зданий и юнитов
    public void emulateShoping(){
        System.out.println("Закупка зданий и юнитов"); // Оповещение о процессе покупки
    }

    // Метод для уведомления об атаке врага на базу игрока
    public void enemyOnYourBase(){
        System.out.println("Враг захватил ваше здание!"); // Уведомление о захвате здания
    }

    // Метод для выбора действий игрока (движение или атака)
    public void chooseAct(boolean target){
        if(target){
            System.out.println("1. Двигаться"); // Опция для движения
            System.out.println("2. Атаковать"); // Опция для атаки
        }else{
            System.out.println("1. Двигаться"); // Только опция для движения
        }
        System.out.print("Выберите действие: "); // Запрос выбора действия
    }

    // Метод для уведомления о том, что цель не является юнитом
    public void targetIsNotUnit(){
        System.out.println("Цель не является юнитом."); // Уведомление о неверном выборе
    }

    // Метод для уведомления о том, что цель вне зоны досягаемости
    public void targetOutOfScope(){
        System.out.println("Цель вне зоны досягаемости."); // Уведомление об недоступной цели
    }

    // Метод для подтверждения успешной защиты здания
    public void defSuccess(){
        System.out.println("Ваше здание отбило атаку!"); // Подтверждение защиты
    }

    // Метод для уведомления о атаке врага на юнита игрока
    public void enemyAttackYourUnit(Unit unit){
        System.out.println("Враг атаковал вашего юнита " + unit.design); // Уведомление об атаке врагом
    }

    // Метод для предупреждения о чрезмерном употреблении кофе
    public void coffeeWarning(){
        System.out.println("Хватит пить кофе"); // Предупреждение о вреде
    }

    // Метод для уведомления о необходимости провалить задачу
    public void goOut(int moreMoney){
        System.out.println("Ищи "+ moreMoney +" золотых"); // Команда уйти, пока не найдено золото
    }

    // Метод для предупреждения о стрижке волос
    public void salonWarning(){
        System.out.println("У тебя лысая голова, стричь нечего"); // Представление о проблеме
    }

    // Метод для ожидания очереди
    public void waitQue(){
        System.out.println("Погоди, надо отстоять свою очередь"); // Уведомление об ожидании
    }

    // Метод для предупреждения о бесполезности посещения отеля
    public void hotelWarning(){
        System.out.println("Ты сюда играть пришел или спать?"); // Уведомление о неверном выборе
    }

    // Метод для подачи сообщения о нужде уйти из отеля
    public void goOutFromHotel(){
        System.out.println("Сходи поспи на улице пока не найдешь 15 монет"); // Советы по выходу из отеля
    }

    // Метод для уведомления о текущем времени сна
    public void startSleeping(){
        System.out.println("Самое время поспать"); // Уведомление о времени сна
    }

    // Метод для запрашивания о качестве сна
    public void isGoodNight(){
        System.out.println("Хорошо поспал?"); // Вопрос о качестве сна
    }

    // Метод для отображения информации о выходе NPC
    public void printNPC(String number){
        System.out.println("Вышел нпс - " + number); // Уведомление о выходе NPC
    }

    // Метод для объявления окончания очереди
    public void endOfQue(){
        System.out.println("Все люди обслужены"); // Уведомление о завершении обслуживания
    }
}