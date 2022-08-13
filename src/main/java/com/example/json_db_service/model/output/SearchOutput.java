package com.example.json_db_service.model.output;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static com.example.json_db_service.model.OperationType.search;

@Data
public class SearchOutput extends Output {

    private List<SearchResult> results;

    public SearchOutput() {
        type = search;
        results = new ArrayList<SearchResult>();
    }

    public void addResult(SearchResult result) {
        results.add(result);
    }
}
