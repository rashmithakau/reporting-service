package com.LittleLanka.reporting_service.service;

import com.LittleLanka.reporting_service.dto.request.RequestDailySalesReportDto;
import com.LittleLanka.reporting_service.dto.response.ResponseDailySalesReportDto;

public interface ReportService {
    ResponseDailySalesReportDto getDailySalesReportData(RequestDailySalesReportDto requestDailySalesReportDto);

    ResponseDailySalesReportDto getAvgSalesTop5ById(Integer itemId);
}
