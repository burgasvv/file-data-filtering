package org.burgas.filedatafiltering.statistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс реализации для оперирования данными статистики по строковым значениям;
 */
public class StringStatistics implements Statistics<String> {

    private final List<String> strings;

    /**
     * Конструктор для создания объекта класса и списка элементов строковых значений;
     */
    public StringStatistics() {
        strings = new ArrayList<>();
    }

    /**
     * Метод получения списка элементов значений статистики;
     * @return Список элементов строковых значений;
     */
    public List<String> getStrings() {
        return strings;
    }

    /**
     * Метод для добавления данных строковых значений;
     * @param item Тип данных строка;
     */
    @Override
    public void add(String item) {
        strings.add(item);
    }

    /**
     * Метод для удаления данных строковых значений;
     * @param item Тип данных строка;
     */
    @Override
    public void remove(String item) {
        strings.remove(item);
    }

    /**
     * Метод получения статистики по строковым значениям с использованием полученных параметров:
     * -f полная статистика, -s краткая статистика;
     * @param params Параметры статистики;
     * @return Получение данных статистики по строковым значениям;
     */
    @Override
    public String getStatistics(String ...params) {
        String title = "СТАТИСТИКА ПО СТРОКАМ: ";
        List<String> paramList = Arrays.stream(params).toList();
        if (
                paramList.contains("-f") ||
                (paramList.contains("-s") && paramList.contains("-f"))
        )
            return title +
                   "\nКоличество записанных элементов: " + getStrings().size() +
                   "\nСамая короткая строка: " + getMinLengthString() +
                   "\nСамая длинная строка: " + getMaxLengthString();

        else if (paramList.contains("-s"))
            return title + "\nКоличество записанных элементов: " + getStrings().size();

        else
            return title + "Отсутствуют аргументы получения статистики";
    }

    /**
     * Метод поиска элемента строки с наименьшей длинной;
     * @return получение элемента с наименьшей длиной;
     */
    public Integer getMinLengthString() {
        return strings.stream()
                .mapToInt(String::length)
                .min()
                .orElse(0);
    }

    /**
     * Метод поиска элемента строки с наибольшей длинной;
     * @return получение элемента с наименьшей длиной;
     */
    public Integer getMaxLengthString() {
        return strings.stream()
                .mapToInt(String::length)
                .max()
                .orElse(0);
    }
}
