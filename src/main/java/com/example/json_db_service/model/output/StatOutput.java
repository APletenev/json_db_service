package com.example.json_db_service.model.output;

import com.example.json_db_service.model.input.StatInput;
import com.example.json_db_service.service.CustomerService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.example.json_db_service.model.OperationType.stat;

@Data
@Component
@JsonPropertyOrder({"type", "totalDays","customers", "totalExpenses", "avgExpenses" })
public class StatOutput extends Output {

    @JsonIgnore
    @Autowired
    private CustomerService customerService;
    @JsonIgnore
    private Date startDate;
    @JsonIgnore
    private Date endDate;

    private long totalExpenses;  // Сумма покупок всех покупателей за период

    private float avgExpenses;  // Средние затраты всех покупателей за период

    public StatOutput() {
        type = stat;
    }

    @JsonProperty
    public long totalDays() {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Отсутствует дата");
        }

        //Перевод Date в LocalDate
        LocalDate startLocalDate = startDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate endLocalDate = endDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate().plusDays(1); //Включаем в расчет конечную дату

        // Общее количество дней
        long daysBetween = ChronoUnit.DAYS.between(startLocalDate, endLocalDate);

        // Предикат является ли день выходным
        Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.SUNDAY;

        return Stream.iterate(startLocalDate, date -> date.plusDays(1))
                .limit(daysBetween)
                .filter(isWeekend.negate())
                .count();

    }

    public void setDates(StatInput statInput) {
        this.startDate = statInput.getStartDate();
        this.endDate = statInput.getEndDate();
    }

    @JsonProperty
    private List<StatResult> customers() {
        long sumOfTotalExpenses = 0;
        List<StatResult> statResults = customerService.statResults(startDate, endDate);

        for (StatResult sr : statResults) {
            sumOfTotalExpenses+=sr.getTotalExpenses();
        }
        setTotalExpenses(sumOfTotalExpenses);
        setAvgExpenses((float) Math.round((float)sumOfTotalExpenses/statResults.size() * 100) / 100);

        return statResults;
    }


}
