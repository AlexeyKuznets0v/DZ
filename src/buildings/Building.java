package buildings;
import maps.*;
import units.*;
import Hero.Hero;

import java.io.Serializable;

import static buildings.Tower.logger;



public class Building extends Path {
    // Переменная для хранения героя, который защищает здание
    public Hero defence;

    // Флаг, указывающий на то, является ли это здание башней игрока
    public boolean isGamerTower;

    // Флаги, определяющие наличие различных зданий для улучшения игрового процесса
    //protected boolean tavern = false; // Таверна - используется для найма Героев
    //protected boolean stable = false; // Конюшня - увеличивает дальность перемещения для всех Героев
    public boolean guardPost = false; // Сторожевой пост - используется для найма Юнитов 1 уровня: копейщиков
    public boolean towerOfCrossBowMen = false; // Башня арбалетчиков - используется для найма юнитов 2 уровня: арбалетчиков
    public boolean armory = false; // Оружейная - используется для найма юнитов 3 уровня: мечников
    public boolean arena = false; // Арена - используется для найма юнитов 4 уровня: кавалеристов
    public boolean cathedral = false; // Собор - используется для найма юнитов 5 уровня: паладинов

    public static boolean hospital = false; // Лечебница - уменьшает пагубные эффекты от башни

    /*
    // Метод для отображения доступных зданий и их статуса
    public void printBuildings() {
        System.out.println("Доступные здания:");
        logger.info("Доступные здания:");
        // Проверка наличия таверны и возможность ее покупки

        if (!tavern) {
            System.out.println("Купить таверну за 15 золота? (H)");
        } else {
            System.out.println("Таверна уже куплена.");
        }


        // Проверка наличия конюшни и возможность ее покупки
        if (!stable) {
            System.out.println("Купить конюшню за 20 золота? (S)");
        } else {
            System.out.println("Конюшня уже куплена.");
        }


        // Проверка наличия сторожевого поста и возможность его покупки
        if (!guardPost) {
            System.out.println("Купить сторожевой пост за 13 золота? (G)");
        } else {
            System.out.println("Сторожевой пост уже куплен.");
        }
        // Проверка наличия башни арбалетчиков и возможность ее покупки
        if (!towerOfCrossBowMen) {
            System.out.println("Купить башню арбалетчиков за 16 золота? (T)");
        } else {
            System.out.println("Башня арбалетчиков уже куплена.");
        }
        // Проверка наличия оружейной и возможность ее покупки
        if (!armory) {
            System.out.println("Купить оружейную за 20 золота? (A)");
        } else {
            System.out.println("Оружейная уже куплена.");
        }
        // Проверка наличия арены и возможность ее покупки
        if (!arena) {
            System.out.println("Купить арену за 25 золота? (R)");
        } else {
            System.out.println("Арена уже куплена.");
        }
        // Проверка наличия собора и возможность его покупки
        if (!cathedral) {
            System.out.println("Купить собор за 30 золота? (C)");
        } else {
            System.out.println("Собор уже куплен.");
        }
        // Проверка наличия лечебницы и возможность ее покупки
        if (!hospital) {
            System.out.println("Купить лечебницу за 25 золота? (L)");
        } else {
            System.out.println("Лечебница уже куплена.");
        }
        System.out.println("Введите 'Q' для выхода"); // Программа предлагает выйти
    }

     */



    // Метод обработки выбора игрока для покупки зданий
    public void handleBuildingPurchase(Hero hero, String choice) {
        // Использование switch-case для определения действий по выбору
        switch (choice) {
            /*case "H":
                BuyTavern(hero); // Покупка таверны
                break;

             */
            /*
            case "S":
                BuyStable(hero); // Покупка конюшни
                break;

             */
            case "G":
                BuyGuardPost(hero); // Покупка сторожевого поста
                break;
            case "T":
                BuyTowerOfCrossBowMan(hero); // Покупка башни арбалетчиков
                break;
            case "A":
                BuyArmory(hero); // Покупка оружейной
                break;
            case "R":
                BuyArena(hero); // Покупка арены
                break;
            case "C":
                BuyCathedral(hero); // Покупка собора
                break;
            case "L":
                BuyHospital(hero); // Покупка лечебницы
                break;
            default:
                System.out.println("Неверный выбор."); // Обработка неверного выбора
                break;
        }
    }
/*
    // Метод для покупки таверны
    private void BuyTavern(Hero hero) {
        // Проверка, что таверна еще не куплена
        if (!tavern) {
            // Проверка, достаточно ли золота у героя для покупки
            if (hero.gold >= 15) {
                hero.gold -= 15; // Списание стоимости таверны с золота героя
                this.tavern = true; // Установка флага, что таверна куплена
            }
        }
    }

 */
/*
    // Метод для покупки конюшни
    private void BuyStable(Hero hero) {
        // Проверка, что конюшня еще не куплена
        if (!stable) {
            // Проверка, достаточно ли золота у героя для покупки
            if (hero.gold >= 20) {
                hero.gold -= 20; // Списание стоимости конюшни с золота героя
                hero.isStable = true; // Установка флага, что у героя есть конюшня
                this.stable = true; // Установка флага, что конюшня куплена
            }
        }
    }

 */

    // Метод для покупки сторожевого поста
    public void BuyGuardPost(Hero hero) {
        // Проверка, что сторожевой пост еще не куплен
        if (!guardPost) {
            // Проверка, достаточно ли золота у героя для покупки
            if (hero.gold >= 13) {
                hero.gold -= 13; // Списание стоимости сторожевого поста с золота героя
                this.guardPost = true; // Установка флага, что сторожевой пост куплен
            }
        }
    }

    // Метод для покупки башни арбалетчиков
    public void BuyTowerOfCrossBowMan(Hero hero) {
        // Проверка, что башня арбалетчиков еще не куплена
        if (!towerOfCrossBowMen) {
            // Проверка, достаточно ли золота у героя для покупки
            if (hero.gold >= 16) {
                hero.gold -= 16; // Списание стоимости башни арбалетчиков с золота героя
                this.towerOfCrossBowMen = true; // Установка флага, что башня арбалетчиков куплена
            }
        }
    }

    // Метод для покупки оружейной
    public void BuyArmory(Hero hero) {
        // Проверка, что оружейная еще не куплена
        if (!armory) {
            // Проверка, достаточно ли золота у героя для покупки
            if (hero.gold >= 20) {
                hero.gold -= 20; // Списание стоимости оружейной с золота героя
                this.armory = true; // Установка флага, что оружейная куплена
            }
        }
    }

    // Метод для покупки арены
    public void BuyArena(Hero hero) {
        // Проверка, что арена еще не куплена
        if (!arena) {
            // Проверка, достаточно ли золота у героя для покупки
            if (hero.gold >= 25) {
                hero.gold -= 25; // Списание стоимости арены с золота героя
                this.arena = true; // Установка флага, что арена куплена
            }
        }
    }

    // Метод для покупки собора
    public void BuyCathedral(Hero hero) {
        // Проверка, что собор еще не куплен
        if (!cathedral) {
            // Проверка, достаточно ли золота у героя для покупки
            if (hero.gold >= 30) {
                hero.gold -= 30; // Списание стоимости собора с золота героя
                this.cathedral = true; // Установка флага, что собор куплен
            }
        }
    }
    public void BuyHospital(Hero hero) {
        if (!hospital) {
            if (hero.gold >= 25) {
                hero.gold -= 25;
                this.hospital = true; // Установка флага, что лечебница куплена
                System.out.println("Лечебница успешно куплена.");
            } else {
                System.out.println("Недостаточно золота для покупки лечебницы.");
            }
        } else {
            System.out.println("Лечебница уже куплена.");
        }
    }
    public void applyHospitalEffect(Hero hero, Building building) {
        if (hospital) {
            // Если лечебница куплена, уменьшаем пагубные эффекты от башни
            System.out.println("Эффект лечебницы применен: уменьшен урон от башни.");
            hero.health += 5; // Или другое значение для уменьшения урона/восстановления
        }
    }
}