package com.example.ordermanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PopularMenuResponse {
    private Long menuId;
    private String menuName;
    private String storeName;
    private Long orderCount;
}
