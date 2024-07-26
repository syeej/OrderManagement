package com.example.ordermanagement.dto.request;

import com.example.ordermanagement.domain.OrderStatus;
import com.example.ordermanagement.domain.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest implements Serializable {
    private Long customerId;
    private Long storeId;
    private List<OrderItemRequest> orderItems;

    public static Order toEntity(OrderRequest request, Customer customer, Store store, List<OrderItem> orderItems) {
        Order order = Order.builder()
                .customer(customer)
                .store(store)
                .status(OrderStatus.RECEIVED)
                .totalAmount(getTotalAmount(orderItems))
                .orderItems(orderItems)
                .build();
        return order;
    }
    // 주문에 있는 item 등 총 가격 계산
    private static Long getTotalAmount(List<OrderItem> items){
        return items.stream()
                .mapToLong(item-> item.getPrice() * item.getCount())
                .sum();
    }
}
