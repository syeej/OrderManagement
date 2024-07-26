package com.example.ordermanagement.dto.response;

import com.example.ordermanagement.domain.OrderStatus;
import com.example.ordermanagement.domain.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse implements Serializable {
    private Long orderId;
    private Long customerId;
    private Long storeId;
    private OrderStatus status;
    private List<OrderItemResponse> orderItems;
    private Long totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OrderResponse toDTO(Order order){
        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .customerId(order.getCustomer().getCustomerId())
                .storeId(order.getStore().getStoreId())
                .status(order.getStatus())
                .orderItems(order.getOrderItems().stream()
                        .map(OrderItemResponse::toDTO)
                        .collect(Collectors.toList()))
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
}
