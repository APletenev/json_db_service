package com.example.json_db_service.model.criteria;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
public class BadCustomersCriteria extends Criteria {
    private int badCustomers;

}
