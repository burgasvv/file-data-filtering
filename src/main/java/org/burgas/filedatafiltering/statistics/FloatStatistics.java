package org.burgas.filedatafiltering.statistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс реализации для оперирования данными статистики по вещественным числам;
 */
public class FloatStatistics implements Statistics<Float> {

    private final List<Float> values;

    /**
     * Конструктор для создания объекта класса и списка элементов вещественных чисел;
     */
    public FloatStatistics() {
        values = new ArrayList<>();
    }

    /**
     * Метод получения списка элементов значений статистики;
     * @return Список элементов вещественных чисел;
     */
    public List<Float> getValues() {
        return values;
    }

    /**
     * Метод для добавления данных вещественных чисел;
     * @param item Тип данных вещественного числа;
     */
    @Override
    public void add(Float item) {
        values.add(item);
    }

    /**
     * Метод для удаления данных вещественного типа;
     * @param item Параметр удаления данных из статистики;
     */
    @Override
    public void remove(Float item) {
        values.remove(item);
    }

    /**
     * Метод получения статистики по вещественным числам с использованием полученных параметров:
     * -f полная статистика, -s краткая статистика;
     * @param params Параметры статистики;
     * @return Получение данных статистики по вещественным числам;
     */
    @Override
    public String getStatistics(String ...params) {
        String title = "СТАТИСТИКА ПО ВЕЩЕСТВУННЫМ ЧИСЛАМ: ";
        List<String> paramList = Arrays.stream(params).toList();
        if (
                paramList.contains("-f") ||
                (paramList.contains("-s") && paramList.contains("-f"))
        )
            return title +
                   "\nКоличество записанных элементов: " + getValues().size() +
                   "\nМаксимальное значение: " + getFloatMax() +
                   "\nМинимальное значение: " + getFloatMin() +
                   "\nСреднее значение: " + getFloatAverage() +
                   "\nСумма записанных элементов: " + getFloatSum();

        else if (paramList.contains("-s"))
            return title +
                   "\nКоличество записанных элементов: " + getValues().size();

        else
            return title + "Отсутствуют аргументы получения статистики";
    }

    /**
     * Получение минимального элемента в списке вещественных чисел;
     * @return минимальный элемент в списке;
     */
    public Float getFloatMin() {
        return values.stream()
                .min(Float::compareTo)
                .orElse(0.0f);
    }

    /**
     * Получение максимального значения элемента в списке вещественных чисел;
     * @return максимальный элемент в списке;
     */
    public Float getFloatMax() {
        return values.stream()
                .max(Float::compareTo)
                .orElse(0.0f);
    }

    /**
     * Получение среднего значения элемента в списке вещественных чисел;
     * @return средний элемент в списке;
     */
    public Float getFloatAverage() {
        return (float) values.stream()
                .mapToDouble(Float::floatValue)
                .average()
                .orElse(0.0);
    }

    /**
     * Расчет суммы значений элементов в списке вещественных чисел;
     * @return сумма элементов списка вещественных чисел;
     */
    public Float getFloatSum() {
        return (float) values.stream()
                .mapToDouble(Float::doubleValue)
                .sum();
    }
}
