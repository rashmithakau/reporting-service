package com.LittleLanka.reporting_service.service.impl;

import com.LittleLanka.reporting_service.dto.SoldItemDto;
import com.LittleLanka.reporting_service.dto.api.SoldItemDaySumDto;
import com.LittleLanka.reporting_service.dto.request.RequestDailySalesReportDto;
import com.LittleLanka.reporting_service.dto.response.ResponseDailySalesReportDto;
import com.LittleLanka.reporting_service.dto.api.ResponsePriceListDTO;
import com.LittleLanka.reporting_service.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final  WebClient webClient;

    @Override
    public ResponseDailySalesReportDto getDailySalesReportData(RequestDailySalesReportDto requestDailySalesReportDto) {

        List<SoldItemDaySumDto> soldItemDaySumDtosDtoList=null;

        try {
            soldItemDaySumDtosDtoList = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/v1/outlet/sold-items-summ/{outletName}/{date}")
                            .build(requestDailySalesReportDto.getOutletName(), requestDailySalesReportDto.getDate()))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<SoldItemDaySumDto>>() {})
                    .block();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<Integer> productIdList=getProductIdList(soldItemDaySumDtosDtoList);

        HashMap<Integer,ResponsePriceListDTO> priceList;

        try {
            priceList = webClient.post()
                    .uri("http://localhost:8080/api/v1/product/get-price-list-by-date-and-productId-list")
                    .bodyValue(productIdList) // Set the request body
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference< HashMap<Integer,ResponsePriceListDTO>>() {}) // Deserialize the generic list
                    .block();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<SoldItemDto> soldItemDtoList = new ArrayList<>();
        double dayTotalSale=0.0;

        for (SoldItemDaySumDto soldItemDaySumDto : soldItemDaySumDtosDtoList) {
            int productId = soldItemDaySumDto.getProductId();

            if (!priceList.containsKey(productId)) {
                throw new NoSuchElementException("Price for product ID " + productId + " not found.");
            }

            double unitPrice = priceList.get(productId).getPrice();
            double totalSale = soldItemDaySumDto.getSoldQuantity() * unitPrice;
            dayTotalSale += totalSale;

            soldItemDtoList.add(
                    SoldItemDto.builder()
                            .productId(productId)
                            .productName(soldItemDaySumDto.getProductName())
                            .unitPrice(unitPrice)
                            .soldQuantity(soldItemDaySumDto.getSoldQuantity())
                            .unitName(soldItemDaySumDto.getUnitName())
                            .totalSale(totalSale)
                            .build()
            );
        }

        return  ResponseDailySalesReportDto.builder()
                .outletName(requestDailySalesReportDto.getOutletName())
                .dayTotalSale(dayTotalSale)
                .soldItems(soldItemDtoList)
                .date(requestDailySalesReportDto.getDate())
                .build();
    }

    @Override
    public ResponseDailySalesReportDto getAvgSalesTop5ById(Integer itemId) {
        return null;
    }

    private List<Integer> getProductIdList( List<SoldItemDaySumDto> soldItemSumDtoList) {
        List<Integer> productIdList=new ArrayList<>();
        for(SoldItemDaySumDto soldItemDaySumDto:soldItemSumDtoList){
            productIdList.add(soldItemDaySumDto.getProductId());
        }
        return productIdList;
    }
}



