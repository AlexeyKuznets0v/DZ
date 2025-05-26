/*
package Que;

import maps.GameMap;
import UI.UI;

import java.util.List;

public class RemoveQue extends Thread {
    private final List<String> list;
    private final GameMap gameMap;
    private UI ui = new UI();

    public RemoveQue(List<String> list, GameMap gameMap) {
        this.list = list;
        this.gameMap = gameMap;
    }

    @Override
    public void run() {
        while (!list.isEmpty()) {
            synchronized (list) {
                if (!list.isEmpty()) {
                    ui.printNPC(list.remove(0));
                }
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        ui.endOfQue();
    }
}

 */

package Que;

import maps.GameMap; // Импорт класса GameMap для работы с картой игры
import UI.UI; // Импорт класса UI для взаимодействия с пользовательским интерфейсом

import java.util.List; // Импорт класса List для работы со списками

// Класс RemoveQue отвечает за удаление имен из очереди в отдельном потоке
public class RemoveQue extends Thread {
    // Ссылка на список, из которого будут удаляться имена
    private final List<String> list;
    // Ссылка на объект карты игры (может использоваться позже, если потребуется)
    private final GameMap gameMap;
    // Объект пользовательского интерфейса для вывода информации на экран
    private UI ui = new UI();

    // Конструктор для инициализации экземпляра RemoveQue с заданным списком и картой игры
    public RemoveQue(List<String> list, GameMap gameMap) {
        this.list = list; // Инициализация списка, из которого будут удаляться имена
        this.gameMap = gameMap; // Инициализация карты игры
    }

    // Метод run является точкой входа для потока
    @Override
    public void run() {
        // Запускаем цикл, пока список не окажется пустым
        while (!list.isEmpty()) {
            // Синхронизируем доступ к списку для обеспечения потокобезопасности
            synchronized (list) {
                // Проверяем, что список не пустой
                if (!list.isEmpty()) {
                    // Удаляем первое имя из списка и выводим его на экран
                    ui.printNPC(list.remove(0));
                }
            }
            try {
                // Приостановка выполнения потока на 3000 миллисекунд (3 секунды)
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // Если поток был прерван, устанавливаем флаг прерывания и выходим из метода
                Thread.currentThread().interrupt();
                return;
            }
        }
        // Когда все имена удалены, вызываем метод окончания очереди в пользовательском интерфейсе
        ui.endOfQue();
    }
}