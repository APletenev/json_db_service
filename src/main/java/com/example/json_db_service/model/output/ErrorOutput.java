package com.example.json_db_service.model.output;

import lombok.Getter;
import lombok.Setter;

import static com.example.json_db_service.model.OperationType.error;

@Getter
@Setter
public class ErrorOutput extends Output {
    private String message;

    public ErrorOutput() {
        type = error;
    }

    public ErrorOutput(String message) {
        type = error;
        this.message = message;
    }
}
