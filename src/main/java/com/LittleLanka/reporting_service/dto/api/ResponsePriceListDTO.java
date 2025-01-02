package com.LittleLanka.reporting_service.dto.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePriceListDTO {
    private Long productId;
    private Double price;
}
