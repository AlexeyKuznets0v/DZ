/*
package tests;

import Castle.ComputerHouse;
import Castle.GamerHouse;
import battlef.BattleField;
import Hero.Hero;
import org.junit.jupiter.api.AfterEach;
import units.*;
import buildings.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class ExtendedGameTest {

    private Hero hero1;
    private Hero hero2;
    private BattleField battleField;
    private GamerHouse gamerHouse;
    private ComputerHouse computerHouse;


    @BeforeEach
    public void setUp() {

        hero1 = new Hero(100);
        hero1.army.put("SpearMan", 3);
        hero1.isGamerHero = true;

        hero2 = new Hero(100);
        hero2.army.put("CrossBowMan", 2);
        hero2.isGamerHero = false;

        try {
            battleField = new BattleField(hero1, hero2);
        } catch (InterruptedException e) {
            fail("Initialization failed: " + e.getMessage());
        }

        gamerHouse = new GamerHouse();
        computerHouse = new ComputerHouse();
    }



    @Test
    public void testUnitDamageCalculation() {
        // Проверяем правильность расчета урона юнитов
        SpearMan spearMan = new SpearMan(5); // 5 копейщиков
        assertEquals(150, spearMan.totalDamage, "Общий урон копейщиков должен быть равен 150 (30 урона * 5 юнитов)");

        CrossBowMan crossBowMan = new CrossBowMan(3); // 3 арбалетчика
        assertEquals(150, crossBowMan.totalDamage, "Общий урон арбалетчиков должен быть равен 150 (50 урона * 3 юнита)");
    }

    @Test
    public void testDefenderInitialization() {
        // Проверяем, правильно ли инициализируется дом игрока (gamerHouse)
        assertNotNull(gamerHouse.defence, "Защитник должен быть инициализирован");
        assertEquals(100, gamerHouse.defence.health, "Здоровье защитника должно быть 100");
        assertTrue(gamerHouse.isGamerTower, "Это здание должно принадлежать игроку (isGamerTower должно быть true)");
    }

    @Test
    public void testComputerHouseDefender() {
        // Проверяем работу ComputerHouse
        assertNotNull(computerHouse.defence, "Защитник компьютера должен быть инициализирован");
        assertEquals(100, computerHouse.defence.health, "Здоровье защитника компьютера должно быть 100");
        assertFalse(computerHouse.isGamerTower, "Это здание не должно принадлежать игроку (isGamerTower должно быть false)");
    }



    @Test
    public void testBattleFieldInitialization() {
        // Проверка корректной инициализации поля боя
        assertNotNull(battleField.battle, "Поле боя должно быть инициализировано");
        assertEquals(5, battleField.height, "Высота поля должна быть 5");
        assertEquals(10, battleField.weight, "Ширина поля должна быть 10");
    }
}

 */