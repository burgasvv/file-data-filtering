package org.burgas.filedatafiltering.statistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс реализации для оперирования данными статистики по целым числам;
 */
public class LongStatistics implements Statistics<Long> {

    private final List<Long> values;

    /**
     * Конструктор для создания объекта класса и списка элементов целых чисел;
     */
    public LongStatistics() {
        values = new ArrayList<>();
    }

    /**
     * Метод получения списка элементов значений статистики;
     * @return Список элементов целых чисел;
     */
    public List<Long> getValues() {
        return values;
    }

    /**
     * Метод для добавления данных целых чисел;
     * @param item Тип данных целого числа;
     */
    @Override
    public void add(Long item) {
        values.add(item);
    }

    /**
     * Метод для удаления данных целых чисел;
     * @param item Тип данных целого числа;
     */
    @Override
    public void remove(Long item) {
        values.remove(item);
    }

    /**
     * Метод получения статистики по вещественным числам с использованием полученных параметров:
     * -f полная статистика, -s краткая статистика;
     * @param params Параметры статистики;
     * @return Получение данных статистики по целым числам;
     */
    @Override
    public String getStatistics(String ...params) {
        String title = "СТАТИСТИКА ПО ЦЕЛЫМ ЧИСЛАМ: ";
        List<String> paramList = Arrays.stream(params).toList();
        if (
                paramList.contains("-f") ||
                (paramList.contains("-s") && paramList.contains("-f"))
        )
            return title +
                   "\nКоличество записанных элементов: " + getValues().size() +
                   "\nМаксимальное значение: " + getLongMax() +
                   "\nМинимальное значение: " + getLongMin() +
                   "\nСреднее значение: " + getLongAverage() +
                   "\nСумма записанных элементов: " + getLongSum();

        else if (paramList.contains("-s"))
            return title +
                   "\nКоличество записанных элементов: " + getValues().size();

        else
            return title + "Отсутствуют аргументы получения статистики";
    }

    /**
     * Получение минимального элемента в списке целых чисел;
     * @return минимальный элемент в списке;
     */
    public Long getLongMin() {
        return values.stream()
                .mapToLong(Long::longValue)
                .min()
                .orElse(0L);
    }

    /**
     * Получение максимального значения элемента в списке целых чисел;
     * @return максимальный элемент в списке;
     */
    public Long getLongMax() {
        return values.stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L);
    }

    /**
     * Получение среднего значения элемента в списке целых чисел;
     * @return средний элемент в списке;
     */
    public Long getLongAverage() {
        return (long) values.stream()
                .mapToLong(Long::longValue)
                .average()
                .orElse(0.0);
    }

    /**
     * Расчет суммы значений элементов в списке целых чисел;
     * @return сумма элементов списка целых чисел;
     */
    public Long getLongSum() {
        return values.stream()
                .mapToLong(Long::longValue)
                .sum();
    }
}
