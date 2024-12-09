package org.burgas.filedatafiltering;

import org.burgas.filedatafiltering.filter.FileFilter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static final String ARGUMENTS_RECEIVED_SUCCESSFULLY =
            "\nАргументы с (input) файлами для чтения с расширением .txt получены";
    public static final String ARGUMENTS_NOT_FOUND =
            "Аргументы с (input) файлами с расширением .txt не найдены";
    public static final String FILE_NOT_FOUND =
            "Файл (input) чтения с расширением .txt %s не найден на носителе";
    public static final String ARGUMENTS = "Аргументы: %s";

    public static void main(String[] args) throws IOException {

        System.out.println();
        boolean inputFiles = false;
        for (String arg : args)

            if (arg.endsWith(".txt")) {
                try {
                    new BufferedReader(new FileReader(arg)).close();

                } catch (FileNotFoundException e) {
                    System.out.printf(FILE_NOT_FOUND, arg);
                    return;
                }
                inputFiles = true;
                System.out.println("Файл для чтения: " + arg + " получен");
            }

        if (inputFiles) {
            System.out.println(ARGUMENTS_RECEIVED_SUCCESSFULLY);
            System.out.printf(ARGUMENTS, Arrays.toString(args) + "\n");
            FileFilter fileFilter = new FileFilter(args);
            System.out.println("\nОбработчик аргументов: " + fileFilter.getArgHandler());
            fileFilter.filter();
            System.out.println(fileFilter.getStatistics());

        } else
            System.out.println(ARGUMENTS_NOT_FOUND);
    }
}
