package com.example.json_db_service.model.output;

import com.example.json_db_service.model.Json;
import com.example.json_db_service.model.OperationType;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Output implements Json {

    @JsonProperty
    protected OperationType type;
}
