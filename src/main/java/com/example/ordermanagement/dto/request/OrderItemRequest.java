package com.example.ordermanagement.dto.request;

import com.example.ordermanagement.domain.entity.Menu;
import com.example.ordermanagement.domain.entity.Order;
import com.example.ordermanagement.domain.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {
    private Long orderItemId;
    private Long menuId;
    private long count;

    public static OrderItem toEntity(OrderItemRequest request, Order order, Menu menu) {
        return OrderItem.builder()
                .order(order)
                .menu(menu)
                .count(request.getCount())
                .price(menu.getPrice())
                .build();
    }
}
