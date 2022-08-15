package com.example.json_db_service.model.input;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
public class StatInput {
    private Date startDate;
    private Date endDate;

}
