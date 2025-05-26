/*
package tests;

import static org.junit.jupiter.api.Assertions.*;

import maps.Gam;
import maps.GameMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


import Hero.Hero;

// Заглушка, если JUnit 5
class GameMapTest {

    private GameMap map;
    private Hero[] heroes;

    @BeforeEach
    void setUp() {
        int testHeight = 10;
        int testWidth = 10;
        heroes = new Hero[2];
        heroes[0] = new Hero(100);
        heroes[1] = new Hero(100);

        map = new GameMap(testHeight, testWidth, heroes);
    }

    @Test
    void testMapInitialization() {
        // Тестирование начальной инициализации карты и позиций героев
        assertNotNull(map.world, "Карта не должна быть null");
        assertEquals(10, map.height, "Высота должна быть равна 10");
        assertEquals(10, map.weight, "Ширина должна быть равна 10");
        assertTrue(map.world[heroes[0].heroY][heroes[0].heroX] instanceof Hero, "Игрок должен начинать с правильной позиции");
        assertTrue(map.world[heroes[1].heroY][heroes[1].heroX] instanceof Hero, "Компьютерный герой должен быть на своей позиции");
    }

    @Test
    void testObstacleGeneration() {
        // Тестирование присутствия 10 препятствий
        int obstacleCount = 0;
        for (int i = 0; i < map.height; i++) {
            for (int j = 0; j < map.weight; j++) {
                if (map.world[i][j] instanceof Gam) {
                    obstacleCount++;
                }
            }
        }
        assertEquals(10, obstacleCount, "Должно быть 10 препятствий на карте");
    }

    @Test
    void testMoveHero() throws InterruptedException {
        // Тестирование перемещения героя
        Hero player = heroes[0];
        map.moveHero(player, 1, 0); // Переместить вправо
        assertEquals(2, player.heroX, "Герой должен быть перемещен вправо на 1 клетку");
        assertTrue(map.world[player.heroY][player.heroX] instanceof Hero, "Герой должен быть на новой позиции");
    }
}

 */