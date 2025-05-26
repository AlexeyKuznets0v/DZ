/*
package Que;

import java.util.List;

public class AddQue extends Thread {
    private final List<String> list;
    private final List<String> names;

    public AddQue(List<String> list, List<String> names) {
        this.list = list;
        this.names = names;
    }

    @Override
    public void run() {
        for (String name : names) {
            synchronized (list) {
                list.add(name);
                list.notify();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}

 */

package Que;

import java.util.List;

// Этот класс расширяет класс Thread и отвечает за добавление имен в заданный список.
public class AddQue extends Thread {
    // Ссылка на список, куда будут добавляться имена.
    private final List<String> list;
    // Список имен, которые будут добавлены в целевой список.
    private final List<String> names;

    // Конструктор для инициализации экземпляра AddQue со списком и именами.
    public AddQue(List<String> list, List<String> names) {
        this.list = list; // Инициализация списка, в который будут добавляться имена.
        this.names = names; // Инициализация списка имен, которые нужно добавить.
    }

    // Метод run является точкой входа для потока.
    @Override
    public void run() {
        // Перебор каждого имени в списке имен
        for (String name : names) {
            // Синхронизация доступа к списку для обеспечения потокобезопасности
            synchronized (list) {
                // Добавление имени в список
                list.add(name);
                // Уведомление других потоков о том, что элемент был добавлен в список
                list.notify();
            }
            try {
                // Приостановка исполнения на 500 миллисекунд
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // Если поток был прерван, устанавливаем флаг прерывания и выходим
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}