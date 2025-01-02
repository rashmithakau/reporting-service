package com.LittleLanka.reporting_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDailySalesReportDto {
    private Date date;
    private String outletName;
}
