package com.example.demo.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateInput {

    // dd/MM/yyyy HH:mm:ss
    private String startDate;
    private String endDate;

}
