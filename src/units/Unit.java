package units;


import maps.Let;
import maps.Path;
import UI.*;

import java.util.Scanner;

/*
public class Unit extends Path {
    public int price;
    public int count;
    public int HP;
    public int damage;
    public int totalHP; // Суммарное здоровье группы
    public int totalDamage; // Суммарный урон группы
    public int distance;
    public int move;
    public int unitX;
    public int unitY;
    public boolean isMoved = false;
    public boolean isGamerUnit;

    public void takeDamage(int damage){
        int unitLost = damage / this.HP; // сколько юнитов точно умрут от этой атаки
        int remainDamage = damage % this.HP;// сколько хп останется у последнего юнита

        this.count = Math.max(0, this.count - unitLost);
        this.totalHP -= unitLost * this.HP;

        if (this.count > 0 && remainDamage > 0){
            this.totalHP -= remainDamage;
            if (this.totalHP <= 0) {
                this.count--; // Вся группа уничтожена
                this.totalHP = 0; // Здоровье группы равно 0
            }
        }
        // Проверяем, если здоровье <= 0, то игра должна закончиться.
        if (this.totalHP <= 0) {
            System.out.println(this.design + " уничтожен! Игра окончена.");
            System.exit(0); // завершение игры
        }

        update();
    }

    public void update(){
        this.totalDamage = this.count * this.damage;
        System.out.println();
    }


    public void act(Path[][] battlefield, Scanner scanner){
        while (true) {
            try {
                System.out.println("Здоровье одного юнита - " + this.HP);
                System.out.println("Урон одного юнита - " + this.HP);
                System.out.println("Максимальное здоровье юнита " + this.totalHP);
                System.out.println("Максимальная дистанция атаки юнита - " + this.distance);
                System.out.println("Максимальная дистанция перемещения - " + this.move);
                System.out.println("Максимальный урон юнита - " + this.totalDamage);
                if(findUnits(battlefield)){
                    System.out.println("1. Двигаться");
                    System.out.println("2. Атаковать");
                }else{
                    System.out.println("1. Двигаться");
                }
                System.out.print("Выберите действие: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Очистка буфера

                switch (choice) {
                    case 1:
                        System.out.print("Введите направление W,A,S,D: ");
                        String direction = scanner.nextLine().toUpperCase();
                        moveUnit(battlefield, direction);
                        return; // Завершаем ход после успешного действия
                    case 2:
                        attackUnit(battlefield, scanner);
                        return; // Завершаем ход после успешного действия
                    default:
                        System.out.println("Неверный выбор. Введите 1 или 2.");
                        break;
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Ошибка: введите число (1 или 2).");
                scanner.nextLine(); // Очистка буфера после некорректного ввода
            }
        }
    }

    public void moveUnit(Path[][] battlefield, String direction){
        if (this.isMoved) {
            System.out.println("Этот юнит уже перемещался в этом ходу.");
            return;
        }

        int newX = this.unitX;
        int newY = this.unitY;
        int stepsRemaining = this.move; // Оставшееся количество шагов

        while (stepsRemaining > 0) {
            int nextX = newX;
            int nextY = newY;

            // Определяем следующую клетку в зависимости от направления
            switch (direction.toUpperCase()) {
                case "W": // Вверх
                    nextY = Math.max(0, newY - 1);
                    break;
                case "A": // Влево
                    nextX = Math.max(0, newX - 1);
                    break;
                case "S": // Вниз
                    nextY = Math.min(battlefield.length - 1, newY + 1);
                    break;
                case "D": // Вправо
                    nextX = Math.min(battlefield[0].length - 1, newX + 1);
                    break;
                default:
                    System.out.println("Неверное направление.");
                    return;
            }

            // Проверяем, можно ли переместиться на следующую клетку
            //if (battlefield[nextY][nextX] instanceof Let) {
            if (battlefield[nextY][nextX] != null) {

                    // Освобождаем текущую клетку
                battlefield[newY][newX] = new Let();

                // Перемещаем юнита на следующую клетку
                newX = nextX;
                newY = nextY;
                battlefield[newY][newX] = this;

                stepsRemaining--; // Уменьшаем количество оставшихся шагов
            } else {
                // Если следующая клетка недоступна, прерываем перемещение
                break;
            }
        }

        // Обновляем координаты юнита
        this.unitX = newX;
        this.unitY = newY;
        this.isMoved = true;

        System.out.println("Юнит переместился на (" + newY + ", " + newX + ")");
    }

    public boolean findUnits(Path[][] battlefield){
        boolean hasEnemies = false;
        for (int i = Math.max(0, this.unitY - this.distance); i <= Math.min(battlefield.length - 1, this.unitY + this.distance) ; i++) {
            for (int j = Math.max(0, this.unitX - this.distance); j <= Math.min(battlefield[0].length - 1, this.unitX + this.distance); j++) {
                if(battlefield[i][j] instanceof Unit && battlefield[i][j] != this){
                    Unit target = (Unit)battlefield[i][j];

                    if(!target.isGamerUnit) {
                        System.out.println("Цель: (" + i + ", " + j + ") - " + target.design);
                        hasEnemies = true;
                    }
                }
            }
        }
        return hasEnemies;
    }

    public void attackUnit(Path[][] battlefield, Scanner scanner){
        System.out.println("Выберите цель для атаки:");

        findUnits(battlefield);

        System.out.print("Введите координаты цели (x, y): ");
        int targetRow = scanner.nextInt(); // Строка цели
        int targetCol = scanner.nextInt(); // Столбец цели
        scanner.nextLine(); // Очистка буфера

        // Проверка, что цель находится в пределах досягаемости
        if (targetRow >= 0 && targetRow < battlefield.length && targetCol >= 0 && targetCol < battlefield[0].length) {
            if (Math.abs(this.unitY - targetRow) <= this.distance && Math.abs(this.unitX - targetCol) <= this.distance) {
                if (battlefield[targetRow][targetCol] instanceof Unit) {
                    Unit target = (Unit) battlefield[targetRow][targetCol];
                    if (!target.isGamerUnit) { // Проверяем, что цель не является юнитом игрока
                        System.out.println("У " + target.design + " здоровья: " + target.totalHP);
                        System.out.println(this.design + " атакует " + target.design + "!");
                        System.out.println("Нанесено урона: " + this.totalDamage);
                        target.takeDamage(this.totalDamage);
                        System.out.println("У " + target.design + " осталось здоровья: " + target.totalHP);

                        if (target.count <= 0) {
                            battlefield[targetRow][targetCol] = new Let();
                            System.out.println(target.design + " уничтожен!");
                        }
                    } else {
                        System.out.println("Нельзя атаковать своих юнитов.");
                    }
                } else {
                    System.out.println("Цель не является юнитом.");
                }
            } else {
                System.out.println("Цель вне зоны досягаемости.");
            }
        } else {
            System.out.println("Некорректные координаты цели.");
        }
    }
}

 */

public class Unit extends Path{
    UI ui = new UI();
    public int price;
    public int count;
    public int HP;
    public int damage;
    public int totalHP; // Суммарное здоровье группы
    public int totalDamage; // Суммарный урон группы
    public int distance;
    public int move;
    public int unitX;
    public int x;
    public int unitY;
    public int y;
    public boolean isMoved = false;
    public boolean isGamerUnit;

    public void takeDamage(int damage){
        int unitLost = damage / this.HP; // сколько юнитов точно умрут от этой атаки
        int remainDamage = damage % this.HP;// сколько хп останется у последнего юнита

        this.count = Math.max(0, this.count - unitLost);
        this.totalHP -= unitLost * this.HP;

        if (this.count > 0 && remainDamage > 0){
            this.totalHP -= remainDamage;
            if (this.totalHP <= 0) {
                this.count--; // Вся группа уничтожена
                this.totalHP = 0; // Здоровье группы равно 0
            }
        }

        update();
    }

    public void update(){
        this.totalDamage = this.count * this.damage;
        System.out.println();
    }


    public void act(Path[][] battlefield, int i, int j, Scanner scanner, UI ui){
        while (true) {
            try {
                this.ui.printUnitStats(this, x, y);


                this.ui.chooseAct(findUnits(battlefield, this.ui));

                int choice = scanner.nextInt();
                scanner.nextLine(); // Очистка буфера

                switch (choice) {
                    case 1:
                        this.ui.unitMove();
                        String direction = scanner.nextLine().toUpperCase();
                        moveUnit(battlefield, direction, this.ui);
                        return; // Завершаем ход после успешного действия
                    case 2:
                        attackUnit(battlefield, scanner, this.ui);
                        return; // Завершаем ход после успешного действия
                    default:
                        this.ui.wrongOneOrTwo();
                        break;
                }
            } catch (java.util.InputMismatchException e) {
                this.ui.wrongOneOrTwo();
                scanner.nextLine(); // Очистка буфера после некорректного ввода
            }
        }

        /*while (true) {
            try {
                System.out.println("Здоровье одного юнита - " + this.HP);
                System.out.println("Урон одного юнита - " + this.HP);
                System.out.println("Максимальное здоровье юнита " + this.totalHP);
                System.out.println("Максимальная дистанция атаки юнита - " + this.distance);
                System.out.println("Максимальная дистанция перемещения - " + this.move);
                System.out.println("Максимальный урон юнита - " + this.totalDamage);
                if(findUnits(battlefield)){
                    System.out.println("1. Двигаться");
                    System.out.println("2. Атаковать");
                }else{
                    System.out.println("1. Двигаться");
                }
                System.out.print("Выберите действие: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Очистка буфера

                switch (choice) {
                    case 1:
                        System.out.print("Введите направление (WASD): ");
                        String direction = scanner.nextLine().toUpperCase();
                        moveUnit(battlefield, direction);
                        return; // Завершаем ход после успешного действия
                    case 2:
                        attackUnit(battlefield, scanner);
                        return; // Завершаем ход после успешного действия
                    default:
                        System.out.println("Неверный выбор. Введите 1 или 2.");
                        break;
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Ошибка: введите число (1 или 2).");
                scanner.nextLine(); // Очистка буфера после некорректного ввода
            }
        }

         */
    }

    public void moveUnit(Path[][] battlefield, String direction, UI ui){
        if (this.isMoved) {
            //System.out.println("Этот юнит уже перемещался в этом ходу.");
            return;
        }

        int newX = this.unitX;
        int newY = this.unitY;
        int stepsRemaining = this.move; // Оставшееся количество шагов

        while (stepsRemaining > 0) {
            int nextX = newX;
            int nextY = newY;

            // Определяем следующую клетку в зависимости от направления
            switch (direction.toUpperCase()) {
                case "W": // Вверх
                    nextY = Math.max(0, newY - 1);
                    break;
                case "A": // Влево
                    nextX = Math.max(0, newX - 1);
                    break;
                case "S": // Вниз
                    nextY = Math.min(battlefield.length - 1, newY + 1);
                    break;
                case "D": // Вправо
                    nextX = Math.min(battlefield[0].length - 1, newX + 1);
                    break;
                default:
                    this.ui.invalidInput();
                    //System.out.println("Неверное направление.");
                    return;
            }

            // Проверяем, можно ли переместиться на следующую клетку
            if (battlefield[nextY][nextX] instanceof Let) {
                // Освобождаем текущую клетку
                battlefield[newY][newX] = new Let();

                // Перемещаем юнита на следующую клетку
                newX = nextX;
                newY = nextY;
                battlefield[newY][newX] = this;

                stepsRemaining--; // Уменьшаем количество оставшихся шагов
            } else {
                // Если следующая клетка недоступна, прерываем перемещение
                break;
            }
        }

        // Обновляем координаты юнита
        this.unitX = newX;
        this.unitY = newY;
        this.isMoved = true;

        this.ui.printMoveUnit(newY, newX);
        //System.out.println("Юнит переместился на (" + newY + ", " + newX + ")");
    }

    private boolean findUnits(Path[][] battlefield, UI ui){
        boolean hasEnemies = false;
        for (int i = Math.max(0, this.unitY - this.distance); i <= Math.min(battlefield.length - 1, this.unitY + this.distance) ; i++) {
            for (int j = Math.max(0, this.unitX - this.distance); j <= Math.min(battlefield[0].length - 1, this.unitX + this.distance); j++) {
                if(battlefield[i][j] instanceof Unit && battlefield[i][j] != this){
                    Unit target = (Unit)battlefield[i][j];

                    if(!target.isGamerUnit) {
                        ui.printTarget(target, i, j);
                        hasEnemies = true;
                    }

                    //if(!target.isGamerUnit) {
                      //  System.out.println("Цель: (" + i + ", " + j + ") - " + target.design);
                        //hasEnemies = true;
                    //}
                }
            }
        }
        return hasEnemies;
    }

    public void chooseTarget(int targetRow, int targetCol, Path[][] battlefield){
        // Проверка, что цель находится в пределах досягаемости
        if (targetRow >= 0 && targetRow < battlefield.length && targetCol >= 0 && targetCol < battlefield[0].length) {
            if (Math.abs(this.unitY - targetRow) <= this.distance && Math.abs(this.unitX - targetCol) <= this.distance) {
                if (battlefield[targetRow][targetCol] instanceof Unit) {
                    Unit target = (Unit) battlefield[targetRow][targetCol];
                    if (!target.isGamerUnit) { // Проверяем, что цель не является юнитом игрока

                        ui.printAttackUnit(this, target);

                        target.takeDamage(this.totalDamage);

                        ui.printDamageLeft(target);

                        if (target.count <= 0) {
                            battlefield[targetRow][targetCol] = new Let();
                            ui.printUnitDie(target);
                        }
                    } else {
                        ui.printFriendlyFire();
                    }
                } else {
                    ui.targetIsNotUnit();
                }
            } else {
                ui.targetOutOfScope();
            }
        } else {
            ui.invalidInput();
        }
    }

    private void attackUnit(Path[][] battlefield, Scanner scanner, UI ui){
        findUnits(battlefield, ui);
        ui.chooseForAttack();

        int targetRow = scanner.nextInt(); // Строка цели
        int targetCol = scanner.nextInt(); // Столбец цели
        scanner.nextLine(); // Очистка буфера

        chooseTarget(targetRow, targetCol, battlefield);
    }

    /*
    private void attackUnit(Path[][] battlefield, Scanner scanner){
        System.out.println("Выберите цель для атаки:");

        findUnits(battlefield);

        System.out.print("Введите координаты цели (x, y): ");
        int targetRow = scanner.nextInt(); // Строка цели
        int targetCol = scanner.nextInt(); // Столбец цели
        scanner.nextLine(); // Очистка буфера

        // Проверка, что цель находится в пределах досягаемости
        if (targetRow >= 0 && targetRow < battlefield.length && targetCol >= 0 && targetCol < battlefield[0].length) {
            if (Math.abs(this.unitY - targetRow) <= this.distance && Math.abs(this.unitX - targetCol) <= this.distance) {
                if (battlefield[targetRow][targetCol] instanceof Unit) {
                    Unit target = (Unit) battlefield[targetRow][targetCol];
                    if (!target.isGamerUnit) { // Проверяем, что цель не является юнитом игрока
                        System.out.println("У " + target.design + " здоровья: " + target.totalHP);
                        System.out.println(this.design + " атакует " + target.design + "!");
                        System.out.println("Нанесено урона: " + this.totalDamage);
                        target.takeDamage(this.totalDamage);
                        System.out.println("У " + target.design + " осталось здоровья: " + target.totalHP);

                        if (target.count <= 0) {
                            battlefield[targetRow][targetCol] = new Let();
                            System.out.println(target.design + " уничтожен!");
                        }
                    } else {
                        System.out.println("Нельзя атаковать своих юнитов.");
                    }
                } else {
                    System.out.println("Цель не является юнитом.");
                }
            } else {
                System.out.println("Цель вне зоны досягаемости.");
            }
        } else {
            System.out.println("Некорректные координаты цели.");
        }
    }

     */
}
