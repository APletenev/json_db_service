package com.example.json_db_service.model.output;

import lombok.Data;

import java.util.List;

@Data
public class StatResult {
    private String name;
    private List<StatProductExpenses> purchases;
    private Long totalExpenses;

    public StatResult(String name, List<StatProductExpenses> purchases, Long totalExpenses) {
        this.name = name; // Фамилия и имя покупателя
        this.purchases = purchases; // Список всех уникальных товаров, купленных покупателем за этот период, упорядоченных по суммарной стоимости по убыванию
        this.totalExpenses = totalExpenses; // Общая стоимость покупок этого покупателя за период (то есть сумма всех стоимостей покупок всех товаров)
    }
}
