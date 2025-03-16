package org.burgas.filedatafiltering.filter;

import org.burgas.filedatafiltering.handler.ArgHandler;
import org.burgas.filedatafiltering.io.IoFileLibrary;
import org.burgas.filedatafiltering.statistics.FloatStatistics;
import org.burgas.filedatafiltering.statistics.LongStatistics;
import org.burgas.filedatafiltering.statistics.StringStatistics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;

import static java.lang.String.*;
import static java.util.concurrent.Executors.newCachedThreadPool;

/**
 * Класс, позволяющий считывать данные из файлов,
 * распределять данные по их типу, и записывать в файл;
 */
public class FileFilter {

    /**
     * Ссылка на объект Обработчик аргументов;
     */
    private final ArgHandler argHandler;

    /**
     * Ссылка на объект для чтения и записи файлов;
     */
    private final IoFileLibrary ioFileLibrary;

    /**
     * Ссылки на объекты-обработчики статистики строковых, целочисленных и вещественных данных;
     */
    private final StringStatistics stringStatistics;
    private final LongStatistics longStatistics;
    private final FloatStatistics floatStatistics;

    /**
     * Конструктор для создания объектов и добавления файлов для считывания данных и дальнейшей обработки;
     * @param args аргументы для обработки;
     * @throws FileNotFoundException исключение, получаемое при отсутствии файлов для считывания;
     */
    public FileFilter(String[] args) throws FileNotFoundException {
        argHandler = new ArgHandler(args);
        ioFileLibrary = new IoFileLibrary();
        stringStatistics = new StringStatistics();
        longStatistics = new LongStatistics();
        floatStatistics = new FloatStatistics();
        ioFileLibrary.addReaders(argHandler.getInputFilePaths());
    }

    /**
     * Статистика по строковым, вещественным и целочисленным типам данных;
     * @return получение статистики в виде строки;
     */
    public String getStatistics() {
        return "\n" + stringStatistics
                       .getStatistics(
                               argHandler.getShortStatistics(), argHandler.getFullStatistics()) + "\n\n" +
               longStatistics
                       .getStatistics(
                               argHandler.getShortStatistics(), argHandler.getFullStatistics()) + "\n\n" +
               floatStatistics
                       .getStatistics(
                               argHandler.getShortStatistics(), argHandler.getFullStatistics()
                       );
    }

    /**
     * Метод записи в файл с учетом проверки на существование данного файла по его наименованию;
     * @param filename наименование файла;
     * @param content информация для записи;
     * @throws IOException исключение, получаемое в случае невозможности записи;
     */
    private void writeToFile(String filename, String content)
            throws IOException {

        if (ioFileLibrary.getWriters().containsKey(filename))
            ioFileLibrary.write(filename, content);
        else {
            ioFileLibrary.addWriter(
                    filename, ioFileLibrary.createFileWriter(
                            filename, argHandler.isFileWriteAppend()
                    )
            );
            ioFileLibrary.write(filename, content);
        }
    }

    /**
     * Метод, в котором происходит считывание информации в виде строкового типа данных из полученных файлов,
     * преобразование в целочисленные и вещественные типы данных и запись в соответствующие их типу файлы;
     */
    public void filter() {

        Map<String, String> resultPathMap = argHandler.getOutputFilePathsMap();

        try (ExecutorService executorService = newCachedThreadPool()){
            CopyOnWriteArrayList<Callable<String>> callables = new CopyOnWriteArrayList<>();

            for (Map.Entry<String, BufferedReader> entry : ioFileLibrary.getReaders().entrySet()) {

                Callable<String> callable = () -> {
                    BufferedReader reader = entry.getValue();
                    try {
                        while (reader.ready()) {
                            String line = reader.readLine();

                            try {
                                long aLong = Long.parseLong(line);
                                synchronized (FileFilter.class) {
                                    longStatistics.add(aLong);
                                    this.writeToFile(resultPathMap.get("integers"), valueOf(aLong));
                                }

                            } catch (NumberFormatException e) {

                                try {
                                    float aFloat = Float.parseFloat(line);
                                    synchronized (FileFilter.class) {
                                        floatStatistics.add(aFloat);
                                        this.writeToFile(resultPathMap.get("floats"), valueOf(aFloat));
                                    }

                                } catch (NumberFormatException e2) {
                                    synchronized (FileFilter.class) {
                                        stringStatistics.add(line);
                                        this.writeToFile(resultPathMap.get("strings"), line);
                                    }
                                }
                            }
                        }
                        return "It's ok, document ready";

                    } catch (RuntimeException | IOException e) {
                        throw new RuntimeException(e);
                    }
                };

                callables.add(callable);
            }
            executorService.invokeAll(callables);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Метод получения приватного поля обработчика аргументов;
     * @return объект обработчика аргументов;
     */
    public ArgHandler getArgHandler() {
        return argHandler;
    }
}
