package com.LittleLanka.reporting_service.dto.response;

import com.LittleLanka.reporting_service.dto.SoldItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDailySalesReportDto {
    private Date date;
    private String outletName;
    private List<SoldItemDto> soldItems;
    private Double dayTotalSale;
}
