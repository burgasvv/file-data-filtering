package org.burgas.filedatafiltering.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс обработки аргументов для чтения и записи
 */
public class ArgHandler {

    private String prefixOutputFilePath;
    private String prefixOutputFileName;
    private String shortStatistics;
    private String fullStatistics;
    private boolean fileWriteAppend;
    private final List<String> inputFilePaths;
    private final Map<String, String> outputFilePathsMap;

    /**
     * Конструктор со встроенной логикой обработки массива аргументов и их записи в свойства класса;
     * @param args аргументы для обработки;
     */
    public ArgHandler(String[] args) {
        shortStatistics = "";
        fullStatistics = "";
        prefixOutputFilePath = "";
        prefixOutputFileName = "";
        fileWriteAppend = false;
        inputFilePaths = new ArrayList<>();
        outputFilePathsMap = new HashMap<>(
                Map.of(
                        "strings", "strings.txt",
                        "integers", "integers.txt",
                        "floats", "floats.txt"
                )
        );

        for (int i = 0; i < args.length; i++) {

            if (args[i].equals("-o") && !args[i + 1].startsWith("-"))
                prefixOutputFilePath = args[i + 1];

            if (args[i].equals("-p") && !args[i + 1].startsWith("-"))
                prefixOutputFileName = args[i + 1];

            if (args[i].equals("-a"))
                fileWriteAppend = true;

            if (args[i].equals("-s"))
                shortStatistics = args[i];

            if (args[i].equals("-f"))
                fullStatistics = args[i];
        }

        for (String param : args) {
            if (param.endsWith(".txt"))
                inputFilePaths.add(param);
        }

        outputFilePathsMap.replace("strings", prefixOutputFilePath + prefixOutputFileName + "strings.txt");
        outputFilePathsMap.replace("integers", prefixOutputFilePath + prefixOutputFileName + "integers.txt");
        outputFilePathsMap.replace("floats", prefixOutputFilePath + prefixOutputFileName + "floats.txt");
    }

    /**
     * Метод получения приватного поля режима добавления в данных в файл;
     * @return Объект условного типа данных;
     */
    public boolean isFileWriteAppend() {
        return fileWriteAppend;
    }

    /**
     * Метод получения приватного поля аргумента краткой статистики;
     * @return объект краткой статистики строкового типа;
     */
    public String getShortStatistics() {
        return shortStatistics;
    }

    /**
     * Метод получения приватного поля аргумента полной статистики;
     * @return объект полной статистики строкового типа;
     */
    public String getFullStatistics() {
        return fullStatistics;
    }

    /**
     * Метод получения приватного поля списка местоположений файлов для считывания;
     * @return список строк - местоположений файлов на носителе для считывания из файла;
     */
    public List<String> getInputFilePaths() {
        return inputFilePaths;
    }

    /**
     * Метод получения приватного поля: ассоциативного массива местоположений файлов для записи;
     * @return ассоциативный массив местоположений файлов на носителе для записи в файл;
     */
    public Map<String, String> getOutputFilePathsMap() {
        return outputFilePathsMap;
    }

    @Override
    public String toString() {
        return "ParameterHandler{" +
               "pathResult='" + prefixOutputFilePath + '\'' +
               ", prefixNameResult='" + prefixOutputFileName + '\'' +
               ", shortStatistics='" + shortStatistics + '\'' +
               ", fullStatistics='" + fullStatistics + '\'' +
               ", fileWriteAppend=" + fileWriteAppend +
               ", inputFilePaths=" + inputFilePaths +
               ", resultPathMap=" + outputFilePathsMap +
               '}';
    }
}
