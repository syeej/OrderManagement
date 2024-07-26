package com.example.ordermanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesResponse {
    private Long storeId;
    private String storeName;
    private Long totalSales;
}
