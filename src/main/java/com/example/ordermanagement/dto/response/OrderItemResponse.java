package com.example.ordermanagement.dto.response;

import com.example.ordermanagement.domain.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private Long orderItemId;
    private Long menuId;
    private Long price;
    private Long count;
    private String menuName;

    public static OrderItemResponse toDTO(OrderItem orderItem){
        return OrderItemResponse.builder()
                .orderItemId(orderItem.getOrderItemId())
                .menuId(orderItem.getMenu().getMenuId())
                .menuName(orderItem.getMenu().getName())
                .count(orderItem.getCount())
                .price(orderItem.getPrice())
                .build();
    }
}
