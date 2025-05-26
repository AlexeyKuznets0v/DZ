package buildings;
import maps.Path;
import Hero.Hero;
import tests.LoggerConfig;

import java.util.Random;
import java.util.logging.Logger;

public class Tower extends Path {

    public static final Logger logger = LoggerConfig.setupLogger(Tower.class.getName());

    public int effectType; // 0 - damage, 1 - heal, 2 - skip turn
    public static final int DAMAGE_AMOUNT = 5;
    public static final int HEAL_AMOUNT = 7;

    public Tower() {
        // Случайный выбор типа эффекта: 0 (дамажит), 1 (хилит), 2 (пропуск хода)
        Random random = new Random();
        this.effectType = random.nextInt(3); // Получаем случайный тип
        setDesign(); // Устанавливаем внешний вид башни в зависимости от типа
        logger.info("Создана башня с типом эффекта: " + effectType);
    }

    public void setDesign() {
        switch (effectType) {
            case 0: this.design = "🗡️"; break; // Урон
            case 1: this.design = "💖"; break; // Хил
            case 2: this.design = "🚫"; break; // Пропуск хода
        }
        logger.info("Внешний вид башни установлен: " + design);
    }

    public void effect(Hero hero) {
        // Начало обработки эффекта
        logger.info("Применение эффекта башни к герою. Тип эффекта: " + effectType);
        switch (effectType) {
            case 0: // Дамажит
                if (Building.hospital) {
                    logger.info("Лечебница убрала урон от башни.");
                    System.out.println("Лечебница убрала урон от башни.");
                    System.out.println("Текущее здоровье игрока: " + hero.health);
                    return; // Если есть лечебница, не наносим урон
                }
                System.out.println("Башня нанесла " + DAMAGE_AMOUNT + " урона!");
                hero.takeDamage(DAMAGE_AMOUNT); // Вызов метода takeDamage
                logger.warning("Башня нанесла " + DAMAGE_AMOUNT + " урона герою.");
                break;

                //System.out.println("Башня нанесла " + DAMAGE_AMOUNT + " урона!");
                //hero.health -= DAMAGE_AMOUNT;
                //System.out.println("Текущее здоровье игрока: " + hero.health);

/*
                // Проверка, чтобы не было минимального здоровья
                if (hero.health <= 0) {
                    System.out.println("Здоровье минимально!");
                    hero.health = 0; // Устанавливаем здоровье на минимум
                }
                System.out.println("Текущее здоровье игрока: " + hero.health); // Отображаем текущее здоровье

 */


            case 1: // Хилит
                System.out.println("Башня исцелила " + HEAL_AMOUNT + " здоровья!");
                hero.health += HEAL_AMOUNT;
                logger.info("Башня исцелила героя на " + HEAL_AMOUNT + ". Текущее здоровье: " + hero.health);
                // Проверка, чтобы не превышать максимальное здоровье
                if (hero.health > 100) {
                    System.out.println("Здоровье максимально!");
                    hero.health = 100; // Устанавливаем здоровье на максимум
                    logger.severe("Здоровье максимально!");
                }
                System.out.println("Текущее здоровье игрока: " + hero.health); // Отображаем текущее здоровье
                break;
            case 2: // Пропуск хода
                if (Building.hospital) {
                    logger.info("Лечебница убрала пропуск хода от башни.");
                    System.out.println("Лечебница убрала пропуск хода от башни.");
                    System.out.println("Текущее здоровье игрока: " + hero.health + " Оставшееся ходы: " + hero.move);
                    return; // Если есть лечебница, не вычетаем ход
                }
                System.out.println("Эта башня заставляет вас пропустить ход!");
                hero.move -= 1;
                logger.info("Герой пропускает ход. Осталось ходов: " + hero.move);
                System.out.println("Текущее здоровье игрока: " + hero.health + " Оставшееся ходы: " + hero.move ); // Отображаем текущее здоровье

                if (hero.move <= 0) {
                    logger.warning("У героя закончились ходы!");
                }
                break;
            default:
                logger.warning("Неизвестный тип эффекта башни!");
                break;

                // Проверка состояния здоровья для проигрыша
                //if (hero.health <= 0) {
                 //   System.out.println("Игрок проиграл, здоровье равно 0!");
                    // Вызов метода завершения игры или другого необходимого шага
               // } else {
                   // System.out.println("Текущее здоровье игрока: " + hero.health); // Отображаем текущее здоровье
               // }

        }
    }
}

