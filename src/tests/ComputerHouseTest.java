package tests;


import Castle.ComputerHouse;
import Hero.Hero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import utils.ReflectionInvoker;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class ComputerHouseTest extends ComputerHouse{
    private ComputerHouse computerHouse;
    //private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    //private ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    //private ComputerHouse computerHouse;

    @BeforeEach
    public void setUp() {
        computerHouse = new ComputerHouse();
        System.setOut(new PrintStream(outputStreamCaptor)); // Перенаправляем вывод
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut); // Восстанавливаем оригинальный вывод
        //System.setIn(originalIn); // Восстанавливаем оригинальный ввод
    }
/*
    @Test
    public void testDefenderInitialization() {
        Hero defender = computerHouse.defence;
        assertNotNull(defender, "Защитник не должен быть null");
        assertEquals(100, defender.health, "Здоровье защитника должно быть 100");
        assertFalse(defender.isGamerHero, "Защитник должен принадлежать компьютеру (isGamerHero должно быть false)");
        // Дополнительная проверка вывода, если метод печатает информацию о защитнике
        String output = outputStreamCaptor.toString().trim();
        assertFalse(output.contains("Защитник инициализирован"), "Ожидалось сообщение о инициализации защитника");
    }

 */

/*
    @Test
    public void testDefenderInitialization() {
        // Проверяем, что защитник создан и имеет правильное здоровье
        Hero defender = computerHouse.defence;
        assertNotNull(defender, "Защитник не должен быть null");
        assertEquals(100, defender.health, "Здоровье защитника должно быть 100");
        assertFalse(defender.isGamerHero, "Защитник должен принадлежать компьютеру (isGamerHero должно быть false)");
    }

 */

    @Test
    public void testArmyInitialization() {
        // Проверяем, что армия действительно содержит 4 арбалетчиков
        Hero defender;
        defender = computerHouse.defence;
        assertTrue(defender.army.containsKey("CrossBowMan"), "Армия должна содержать арбалетчиков");
        assertEquals(4, defender.army.get("CrossBowMan"), "Должно быть 4 арбалетчика");
    }

    @Test
    public void testDesignInitialization() {
        // Проверяем, что графическое представление здания установлено правильно
        assertEquals("К", computerHouse.design, "Дизайн здания должен быть 'К'");
    }

    @Test
    public void testGamerTowerFlag() {
        // Проверяем, что флаг isGamerTower установлен на false
        assertFalse(computerHouse.isGamerTower, "Здание не должно принадлежать игроку (isGamerTower должно быть false)");
    }

    @Test
    public void testDefenderAttributes() {
        // Проверяем, что защитник имеет правильные атрибуты
        Hero defender = computerHouse.defence;
        assertNotNull(defender.army, "Армия защитника не должна быть null");
    }

    @Test
    public void testDefenderInitialization() {
        // Получаем объект защитника из компьютера
        Hero defender = computerHouse.defence;

        // Проверяем, что защитник не равен null, то есть он был успешно инициализирован
        assertNotNull(defender, "Защитник не должен быть null");

        // Проверяем, что здоровье защитника инициализировано со значением 100
        assertEquals(100, defender.health, "Здоровье защитника должно быть 100");

        // Проверяем, что защитник принадлежит компьютеру, а не игроку (isGamerHero должно быть false)
        assertFalse(defender.isGamerHero, "Защитник должен принадлежать компьютеру (isGamerHero должно быть false)");

        // Дополнительная проверка вывода, если метод печатает информацию о защитнике
        // Сохраняем текущий вывод в строку и убираем лишние пробелы
        String output = outputStreamCaptor.toString().trim();

        // Проверяем, что вывод не содержит фразы о инициализации защитника, так как она не должна выводиться
        assertFalse(output.contains("Защитник инициализирован"), "Ожидалось сообщение о инициализации защитника");
    }

    @Test
    public void testConsoleInput() {
        // Симулируем ввод строки "тестовый ввод" с переносом строки в конце
        String simulatedInput = "тестовый ввод\n";
        // Перенаправляем системный ввод на симулированный ввод
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Создаем новый сканер для чтения из системного ввода
        Scanner scanner = new Scanner(System.in);

        // Считываем строку из ввода
        String userInput = scanner.nextLine();

        // Проверяем, что считанная строка соответствует симулированному вводу
        assertEquals("тестовый ввод", userInput);
    }

    @Test
    public void testPrivateMethodInvocation() {
        // Предположим, что у нас есть приватный метод "calculateDefense" в ComputerHouse
        // Мы будем вызывать этот метод с помощью рефлексии
        Object result = ReflectionInvoker.invokePrivateMethod(computerHouse, "calculateDefense", new Class<?>[]{}, new Object[]{});
    }

}