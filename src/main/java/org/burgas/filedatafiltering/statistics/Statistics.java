package org.burgas.filedatafiltering.statistics;

/**
 * Интерфейс для оперирования данными статистики;
 * @param <T> Тип данных оперируемых в статистике;
 */
public interface Statistics<T> {

    /**
     * Метод для добавления данных;
     * @param item Параметр добавления данных в статистику;
     */
    void add(T item);

    /**
     * Метод для удаления данных;
     * @param item Параметр удаления данных из статистики;
     */
    @SuppressWarnings("unused")
    void remove(T item);

    /**
     * Метод получения статистики по указанным параметрам;
     * @param params Параметры статистики;
     * @return Получение полной информации о статистике;
     */
    String getStatistics(String ...params);
}
