package org.burgas.filedatafiltering;

import org.burgas.filedatafiltering.filter.FileFilter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import static java.lang.System.*;

public class Main {

    public static final String ARGUMENTS_RECEIVED_SUCCESSFULLY =
            "\nАргументы с (input) файлами для чтения с расширением .txt получены";
    public static final String ARGUMENTS_NOT_FOUND =
            "Аргументы с (input) файлами с расширением .txt не найдены";
    public static final String FILE_NOT_FOUND =
            "Файл (input) чтения с расширением .txt %s не найден на носителе";
    public static final String ARGUMENTS = "Аргументы: %s";

    public static void main(String[] args) throws IOException {

        long start = currentTimeMillis();
        out.println();

        boolean inputFiles = false;
        for (String arg : args)

            if (arg.endsWith(".txt")) {
                try {
                    new BufferedReader(new FileReader(arg)).close();

                } catch (FileNotFoundException e) {
                    out.printf(FILE_NOT_FOUND, arg);
                    return;
                }
                inputFiles = true;
                out.println("Файл для чтения: " + arg + " получен");
            }

        if (inputFiles) {
            out.println(ARGUMENTS_RECEIVED_SUCCESSFULLY);
            out.printf(ARGUMENTS, Arrays.toString(args) + "\n");
            FileFilter fileFilter = new FileFilter(args);
            out.println("\nОбработчик аргументов: " + fileFilter.getArgHandler());
            fileFilter.filter();
            out.println(fileFilter.getStatistics());

        } else
            out.println(ARGUMENTS_NOT_FOUND);

        long end = currentTimeMillis();
        out.println((end - start));
    }
}
