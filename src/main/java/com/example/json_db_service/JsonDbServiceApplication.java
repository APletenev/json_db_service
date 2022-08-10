package com.example.json_db_service;

import com.example.json_db_service.model.Input;
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


    @Override
    public void run(String[] args) throws IOException {

        String outputFile = "error.json";
        try {
            if (args.length == 3) {
                outputFile = args[2];
            } else throw new Exception("Неправильное количество параметров."
                    + "Входные параметры: тип операции, путь к входному файлу, путь к файлу результата");

        } catch (Exception e) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            objectMapper.writeValue(new File(outputFile), new ErrorOutput(e.getMessage()));
        }
    }
}