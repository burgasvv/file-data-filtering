package org.burgas.filedatafiltering.io;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.System.out;

/**
 * Класс реализации для работы с файлами и потоками чтения и записи;
 */
public class IoFileLibrary implements IoLibrary {

    private final ConcurrentHashMap<String, BufferedReader> readers = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, BufferedWriter> writers = new ConcurrentHashMap<>();

    /**
     * Добавление потока для чтения ассоциативный массив;
     * @param fileName путь к файлу;
     * @param reader объект потока для чтения;
     */
    @Override
    public void addReader(String fileName, BufferedReader reader) {
        readers.put(fileName, reader);
    }

    /**
     * Добавление потока для записи ассоциативный массив;
     * @param fileName путь к файлу;
     * @param writer объект потока для записи;
     */
    @Override
    public void addWriter(String fileName, BufferedWriter writer) {
        writers.put(fileName, writer);
    }

    /**
     * Удаление потока для чтения из ассоциативного массива;
     * @param fileName путь к файлу;
     */
    @Override
    public void removeReader(String fileName) {
        try (BufferedReader _ = readers.remove(fileName)) {
            out.println("Поток чтения удален для файла: " + fileName);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Удаление потока для записи из ассоциативного массива;
     * @param fileName путь к файлу;
     */
    @Override
    public void removeWriter(String fileName) {
        try (BufferedWriter _ = writers.remove(fileName)) {
            out.println("Поток записи удален для файла: " + fileName);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод создания потока для чтения;
     * @param fileName наименование и путь к файлу;
     * @return поток чтения из файла;
     * @throws FileNotFoundException исключение, получаемое по причине отсутствия файла по заданному пути;
     */
    public BufferedReader createFileReader(String fileName) throws FileNotFoundException {
        return new BufferedReader(
                new FileReader(fileName)
        );
    }

    /**
     * Метод создания потока для записи;
     * @param fileName наименование и путь к файлу;
     * @param append   параметр режима записи (с добавлением или перезаписью);
     * @return поток записи в файл;
     * @throws IOException исключение, получаемое по причине возникших проблем с созданием потока для записи;
     */
    public BufferedWriter createFileWriter(String fileName, boolean append) throws IOException {
        return new BufferedWriter(
                new FileWriter(fileName, append)
        );
    }

    /**
     * Метод добавления списка файлов для чтения в ассоциативный массив
     * @param fileNames список местоположений и наименований файлов на носителе;
     * @throws FileNotFoundException исключение, получаемое по причине отсутствия файла по заданному пути;
     */
    public void addReaders(List<String> fileNames) throws FileNotFoundException {
        for (String fileName : fileNames)
            this.addReader(fileName, this.createFileReader(fileName));
    }

    /**
     * Метод для записи в файл из ассоциативно массива;
     * @param fileName наименование и путь к файлу;
     * @param content информация для записи;
     * @throws IOException исключение, получаемое по причине возникших проблем с записью в файл;
     */
    public void write(String fileName, String content) throws IOException {
        BufferedWriter bufferedWriter = writers.get(fileName);
        bufferedWriter.write(content + "\n");
        bufferedWriter.flush();
    }

    /**
     * Метод получения приватного поля;
     * @return объект ассоциативного массива с потоками для чтения;
     */
    public Map<String, BufferedReader> getReaders() {
        return readers;
    }

    /**
     * Метод получения приватного поля;
     * @return объект ассоциативного массива с потоками для записи;
     */
    public Map<String, BufferedWriter> getWriters() {
        return writers;
    }
}
