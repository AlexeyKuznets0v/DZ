package tests;/*

package tests;

import battlef.BattleField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Hero.Hero;
import static org.junit.jupiter.api.Assertions.*;

public class BattleFieldTest {

    private Hero hero1;
    private Hero hero2;
    private BattleField battleField;

    @BeforeEach
    public void setUp() {
        hero1 = new Hero(100);
        hero1.army.put("SpearMan", 2);
        hero1.isGamerHero = true;

        hero2 = new Hero(100);
        hero2.army.put("CrossBowMan", 2);
        hero2.isGamerHero = false;

        try {
            battleField = new BattleField(hero1, hero2);
        } catch (InterruptedException e) {
            fail("Initialization failed: " + e.getMessage());
        }
    }

    @Test
    public void TestPlaceUnits() {
        assertEquals("➝", battleField.battle[0][0].design); // Проверяем, что первый юнит размещен правильно
        assertEquals(">", battleField.battle[0][battleField.weight - 1].design); // Проверка второго юнита
    }
}

 */

/*
import battlef.BattleField;
import Hero.Hero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import units.Unit;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;



class BattleFieldTest {


    private Hero hero1;
    private Hero hero2;
    private BattleField battleField;

    @BeforeEach
    public void setUp() {
        hero1 = new Hero(100);
        hero1.army.put("SpearMan", 2);
        hero1.isGamerHero = true;

        hero2 = new Hero(100);
        hero2.army.put("CrossBowMan", 2);
        hero2.isGamerHero = false;

        try {
            battleField = new BattleField(hero1, hero2);
        } catch (InterruptedException e) {
            fail("Initialization failed: " + e.getMessage());
        }
    }

    @Test
    void testBattleFieldInitialization() {
        // Проверяем, что поле боя создано и юниты размещены
        assertNotNull(battleField.battle, "Поле боя не должно быть null");
        assertTrue(battleField.battle[0][0] instanceof Unit, "Юниты должны быть корректно размещены");
    }

    @Test
    void testUnitPlacement() {
        // Проверяем правильное размещение юнитов
        boolean hasPlayerUnits = false;
        boolean hasEnemyUnits = false;

        for (int i = 0; i < battleField.height; i++) {
            for (int j = 0; j < battleField.weight; j++) {
                if (battleField.battle[i][j] instanceof Unit) {
                    Unit unit = (Unit) battleField.battle[i][j];
                    if (unit.isGamerUnit) {
                        hasPlayerUnits = true;
                    } else {
                        hasEnemyUnits = true;
                    }
                }
            }
        }

        assertTrue(hasPlayerUnits, "Игрок должен иметь юнитов на поле");
        assertTrue(hasEnemyUnits, "Компьютер должен иметь юнитов на поле");
    }


    @Test
    public void TestPlaceUnits() {
        assertEquals("➝", battleField.battle[0][0].design); // Проверяем, что первый юнит размещен правильно
        assertEquals(">", battleField.battle[0][battleField.weight - 1].design); // Проверка второго юнита
    }
}


 */