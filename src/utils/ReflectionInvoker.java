/*
package utils;

import java.lang.reflect.Method;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ReflectionInvoker {
    private static final Logger logger = Logger.getLogger(ReflectionInvoker.class.getName());

    public static Object invokePrivateMethod(Object target, String methodName, Class<?>[] paramTypes, Object[] params) {
        try {
            Method method = target.getClass().getDeclaredMethod(methodName, paramTypes);
            method.setAccessible(true); // Делаем метод доступным
            logger.log(Level.SEVERE, "Вызов приватного метода: " + methodName);
            return method.invoke(target, params);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Не удалось вызвать метод: " + methodName + ", ошибка: " + e.getMessage());
            return null;
        }
    }
}

 */

package utils; // Пакет utils для утилитарных классов

import java.lang.reflect.Method; // Импортируем класс Method для работы с рефлексией
import java.util.logging.Logger; // Импортируем класс Logger для логирования
import java.util.logging.Level; // Импортируем класс Level для уровней логирования

public class ReflectionInvoker {
    // Создаем экземпляр логгера для данного класса, чтобы записывать сообщения
    private static final Logger logger = Logger.getLogger(ReflectionInvoker.class.getName());

    /**
     * Вызывает приватный метод заданного объекта с использованием рефлексии.
     *
     * @param target Объект, на котором будет вызван приватный метод.
     * @param methodName Имя приватного метода, который нужно вызвать.
     * @param paramTypes Массив классов, представляющих типы параметров метода.
     * @param params Массив объектов, которые будут переданы в качестве аргументов метода.
     * @return Результат вызова метода или null, если произошла ошибка.
     */
    public static Object invokePrivateMethod(Object target, String methodName, Class<?>[] paramTypes, Object[] params) {
        try {
            // Получаем приватный метод по его имени и типам параметров из класса целевого объекта
            Method method = target.getClass().getDeclaredMethod(methodName, paramTypes);
            method.setAccessible(true); // Делаем метод доступным для вызова

            // Логируем информацию о вызове приватного метода
            logger.log(Level.SEVERE, "Вызов приватного метода: " + methodName);

            // Вызываем метод на целевом объекте с переданными параметрами и возвращаем результат
            return method.invoke(target, params);
        } catch (Exception e) {
            // Логируем сообщение об ошибке, если вызов метода не удался
            logger.log(Level.SEVERE, "Не удалось вызвать метод: " + methodName + ", ошибка: " + e.getMessage());
            return null; // Возвращаем null в случае ошибки
        }
    }
}