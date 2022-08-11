package com.example.json_db_service.model.input;

import com.example.json_db_service.model.criteria.BadCustomersCriteria;
import com.example.json_db_service.model.criteria.ExpensesCriteria;
import com.example.json_db_service.model.criteria.LastNameCriteria;
import com.example.json_db_service.model.criteria.ProductNameCriteria;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchInput {

    private List<Object> criterias;

    public SearchInput() {
        if (criterias == null) {
            criterias = new ArrayList<>();
            criterias.add(new ArrayList<LastNameCriteria>());
            criterias.add(new ArrayList<ProductNameCriteria>());
            criterias.add(new ArrayList<ExpensesCriteria>());
            criterias.add(new ArrayList<BadCustomersCriteria>());


        }
    }

}
