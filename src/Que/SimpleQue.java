/*
package Que;

public class SimpleQue extends Thread{
    @Override
    public synchronized void run() {
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


 */

package Que;

// Класс SimpleQue расширяет функциональность класса Thread
public class SimpleQue extends Thread {
    // Переопределяем метод run, который будет выполняться при запуске потока
    @Override
    public synchronized void run() {
        try {
            // Приостанавливаем выполнение текущего потока на 5000 миллисекунд (5 секунд)
            sleep(5000);
        } catch (InterruptedException e) {
            // Если поток был прерван, выбрасываем исключение с информацией об ошибке
            throw new RuntimeException(e);
        }
    }
}