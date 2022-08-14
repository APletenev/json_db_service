package com.example.json_db_service.model.output;

import com.example.json_db_service.model.criteria.Criteria;
import com.example.json_db_service.model.entity.Customer;
import lombok.Data;

import java.util.List;

@Data
public class SearchResult {
    private Criteria criteria;
    private List<Customer> results;

    public SearchResult(Criteria criteria) throws Exception {
        this.criteria = criteria;
        results = this.criteria.getResults();
    }

}
