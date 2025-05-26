package Castle;
import Hero.*;
import buildings.*;

public class GamerHouse extends Building {
    // Конструктор класса GamerHouse
    // Этот класс представляет базу (дом) игрока и наследуется от класса Building
    public GamerHouse() {
        // Инициализируем объект защиты (Hero) базы игрока с показателем здоровья 100
        defence = new Hero(100);

        // Указываем, что данный герой (defence) принадлежит игроку
        defence.isGamerHero = true;

        // Формируем армию героя, состоящую из 4-х арбалетчиков (CrossBowMan)
        defence.army.put("CrossBowMan", 4);

        // Поле `isGamerTower` показывает, что это здание (дом) принадлежит игроку
        this.isGamerTower = true;

        // Устанавливаем специальное отображение для здания игрока
        //this.design = "\uD83C\uDFE0\uFE0F";
        this.design = "Д";
    }
}