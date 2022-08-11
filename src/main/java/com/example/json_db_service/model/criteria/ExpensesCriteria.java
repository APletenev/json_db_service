package com.example.json_db_service.model.criteria;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
public class ExpensesCriteria extends Criteria {
    private long minExpenses;
    private long maxExpenses;
}
