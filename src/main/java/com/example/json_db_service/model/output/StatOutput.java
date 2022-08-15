package com.example.json_db_service.model.output;

import com.example.json_db_service.model.input.StatInput;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.example.json_db_service.model.OperationType.search;

@Data
@Component
public class StatOutput extends Output {

    @JsonIgnore
    private Date startDate;
    @JsonIgnore
    private Date endDate;


    public StatOutput() {
        type = search;
    }

    @JsonProperty
    public long totalDays() {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Отсутствует дата");
        }

        //Перевод Date в LocaLDate
        LocalDate startlocalDate = startDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate endLocalDate = endDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate().plusDays(1); //Включаем в расчет конечную дату

        // Общее количество дней
        long daysBetween = ChronoUnit.DAYS.between(startlocalDate, endLocalDate);

        // Предикат является ли день выходным
        Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.SUNDAY;

        return Stream.iterate(startlocalDate, date -> date.plusDays(1))
                .limit(daysBetween)
                .filter(isWeekend.negate())
                .count();

    }

    public void setDates(StatInput statInput) {
        this.startDate = statInput.getStartDate();
        this.endDate = statInput.getEndDate();
    }


}
