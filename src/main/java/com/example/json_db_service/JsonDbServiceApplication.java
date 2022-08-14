package com.example.json_db_service;

import com.example.json_db_service.model.OperationType;
import com.example.json_db_service.model.input.SearchInput;
import com.example.json_db_service.model.input.StatInput;
import com.example.json_db_service.model.output.ErrorOutput;
import com.example.json_db_service.model.output.SearchOutput;
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

    @SuppressWarnings("ResultOfMethodCallIgnored")
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

        String outputFile = "output.json"; //Используется для вывода, если не удается создать заданный выходной файл
        try {
            if (args.length != 3) throw new Exception("Неправильное количество параметров. "
                    + "Входные параметры: тип операции, путь к входному файлу, путь к файлу результата");
            if (outputFilenameValid(args[2])) // В первую очередь валидируем путь к файлу результата. т.к. ошибки тоже пишутся в него
                outputFile = args[2];

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            switch (stringToOperationType(args[0])) {
                case search:
                    SearchInput searchInput = objectMapper.readValue(new File(args[1]), SearchInput.class);
                    System.out.println(searchInput);

                    SearchOutput searchOutput = searchInput.generateOutput();

                    objectMapper.writeValue(new File(outputFile), searchOutput);
                    break;
                case stat:
                    StatInput statInput = objectMapper.readValue(new File(args[1]), StatInput.class);
                    System.out.println(statInput);
                    break;
            }
            System.out.println("Загружен запрос из файла " + args[1]);
            System.out.println("Сохранен результат в файл " + outputFile);

        } catch (Exception e) {
            e.printStackTrace();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            objectMapper.writeValue(new File(outputFile), new ErrorOutput(e.getMessage()));
        }
    }
}