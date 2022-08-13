package com.example.json_db_service.model.input;

import com.example.json_db_service.model.criteria.Criteria;
import com.example.json_db_service.model.output.SearchOutput;
import com.example.json_db_service.model.output.SearchResult;
import lombok.Data;

import java.util.ArrayList;

@Data
public class SearchInput {

    private ArrayList<Criteria> criterias;

    public SearchOutput generateOutput() {
        SearchOutput searchOutput = new SearchOutput();

        for (Criteria criteria : criterias ) {
            searchOutput.addResult(new SearchResult(criteria));
        }
        return searchOutput;
    }

}
