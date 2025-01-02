package com.LittleLanka.reporting_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SoldItemDto {
    private int productId;
    private String productName;
    private Double unitPrice;
    private String unitName;
    private Double soldQuantity;
    private Double totalSale;
}
