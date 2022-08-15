package com.example.json_db_service.model.output;

import lombok.Data;

@Data
public class StatProductExpenses {
    private String name; //Название товара
    private long expenses; // Суммарная стоимость всех покупок этого товара за период

    public StatProductExpenses(String name, long expenses) {
        this.name = name;
        this.expenses = expenses;
    }
}
