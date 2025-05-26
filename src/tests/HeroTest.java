
package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import buildings.Building;
import Hero.Hero;

import static org.junit.jupiter.api.Assertions.*;

public class HeroTest {

    private Hero hero;
    private Building building;


    @BeforeEach
    public void setUp() {
        hero = new Hero(100);
        building = new Building(); // Инициализируем здание
        building.guardPost = true; // Указываем, что здание может нанимать копейщиков
    }

    @Test
    public void testResetMoves() {
        hero.move = 20; // Устанавливаем текущее количество ходов
        hero.resetMoves(); // Восстанавливаем ходы
        assertEquals(40, hero.move); // Проверяем, что количество ходов восстановилось до 40
    }

    @Test
    public void testHandleUnitPurchase() {
        hero.handleUnitPurchase(building, "1", 20); // Покупаем копейщиков
        assertTrue(hero.army.containsKey("SpearMan")); // Проверяем, что копейщики были добавлены в армию
        assertEquals(4, hero.army.get("SpearMan")); // Ожидаем 4 копейщика (20 золота / 5 цена копейщика)
    }

}