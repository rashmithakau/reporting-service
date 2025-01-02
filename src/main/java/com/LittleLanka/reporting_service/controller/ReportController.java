package com.LittleLanka.reporting_service.controller;

import com.LittleLanka.reporting_service.dto.request.RequestDailySalesReportDto;
import com.LittleLanka.reporting_service.dto.response.ResponseDailySalesReportDto;
import com.LittleLanka.reporting_service.service.ReportService;
import com.LittleLanka.reporting_service.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/")
public class ReportController {

    @Autowired
    private  ReportService reportService;

    @GetMapping("daily-sales-data-by-date-name")
     public ResponseEntity<StandardResponse> getDailySalesReportData(@RequestBody RequestDailySalesReportDto requestDailySalesReportDto){
        ResponseDailySalesReportDto responseDailySalesReportDto = reportService.getDailySalesReportData(requestDailySalesReportDto);
        return new ResponseEntity<>(new StandardResponse(HttpStatus.OK.value(),
                "Successfully retrieved daily sales report data",responseDailySalesReportDto),
                HttpStatus.OK);
    }
}
