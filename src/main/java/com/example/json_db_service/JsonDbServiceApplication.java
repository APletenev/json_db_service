package com.example.json_db_service;

import com.example.json_db_service.model.OperationType;
import com.example.json_db_service.model.output.ErrorOutput;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class JsonDbServiceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(JsonDbServiceApplication.class, args);
    }

    public static boolean outputFilenameValid(String filename) throws IOException {
        File file = new File(filename);
        boolean created = false;
        try {
            created = file.createNewFile();
            return created;
        } finally {
            if (created) {
                file.delete();
            }
        }
    }

    public static OperationType stringToOperationType(String s) throws Exception {
        try {
            return Enum.valueOf(OperationType.class, s);
        } catch (IllegalArgumentException e) {
            throw new Exception("Неправильный тип операции: " + s
                    + ". Допустимые значения: " + OperationType.search + ", " + OperationType.stat);
        }
    }


    @Override
    public void run(String[] args) throws IOException {

        String outputFile = "error.json"; //Используется для вывода ошибки, если не удается создать заданный выходной файл
        try {
            if (args.length != 3) throw new Exception("Неправильное количество параметров. "
                    + "Входные параметры: тип операции, путь к входному файлу, путь к файлу результата");
            if (outputFilenameValid(args[2])) // В первую очередь валидируем путь к файлу результата. т.к. ошибки тоже пишутся в него
                outputFile = args[2];

            switch (stringToOperationType(args[0])) {
                case search:

                    break;
                case stat:

                    break;
            }


        } catch (Exception e) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            objectMapper.writeValue(new File(outputFile), new ErrorOutput(e.getMessage()));
        }
    }
}