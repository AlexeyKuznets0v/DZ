package tests;

import buildings.Tower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Hero.Hero;
import static org.junit.jupiter.api.Assertions.*;

public class TowerTest {

    private Tower tower;
    private Hero hero;

    @BeforeEach
    public void setUp() {
        tower = new Tower(); // Инициализация башни
        hero = new Hero(100); // Инициализация героя с 100 единицами здоровья
    }

    @Test
    public void testEffectDamage() {
        tower.effectType = 0; // Установите тип эффекта на "урон"
        tower.effect(hero); // Применяем эффект
        assertTrue(hero.health < 100); // Проверяем, что здоровье уменьшилось
    }

    @Test
    public void testEffectHeal() {
        hero.takeDamage(10); // Наносим 10 единиц урона
        tower.effectType = 1; // Установите тип эффекта на "хил"
        tower.effect(hero);
        assertTrue(hero.health > 90); // Проверяем, что здоровье увеличилось
    }

    @Test
    public void testEffectSkipTurn() {
        tower.effectType = 2; // Установите тип эффекта на "пропустить ход"
        int initialMoves = hero.move; // Сохраняем начальное количество ходов
        tower.effect(hero); // Применяем эффект
        assertTrue(hero.move < initialMoves); // Проверяем, что количество ходов уменьшилось
    }
}
