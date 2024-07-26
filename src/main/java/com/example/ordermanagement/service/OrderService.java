package com.example.ordermanagement.service;

import com.example.ordermanagement.domain.OrderStatus;
import com.example.ordermanagement.domain.entity.*;
import com.example.ordermanagement.dto.request.OrderItemRequest;
import com.example.ordermanagement.dto.request.OrderRequest;
import com.example.ordermanagement.dto.response.OrderResponse;
import com.example.ordermanagement.dto.response.SalesResponse;
import com.example.ordermanagement.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerRepository customerRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final OrderItemRepository orderItemRepository;

    //주문 등록
    @Transactional
    public OrderResponse createOrder(OrderRequest orderReq) {
        Customer customer = customerRepository.findById(orderReq.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer Not Found"));
        List<OrderItem> orderItemList = new ArrayList<>();

        Store store = storeRepository.findById(orderReq.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store Not Found"));

        Order order = Order.builder()
                .customer(customer)
                .store(store)
                .status(OrderStatus.RECEIVED)
                .orderItems(orderItemList)
                .build();
        //long totalPrice = 0L;
        for (OrderItemRequest itemRequest : orderReq.getOrderItems()) {
            Menu menu = menuRepository.findById(itemRequest.getMenuId())
                    .orElseThrow(() -> new RuntimeException("Menu Not Found"));
            OrderItem orderItem = OrderItemRequest.toEntity(itemRequest, order, menu);
            orderItemList.add(orderItem);
        }
        //처음 주문 등록 : STATUS(RECEIVED)
        //order.updateTotalAmount(getTotalAmount(orderItemList));
        order.updateTotalAmount(0L);
        Order savedOrder = orderRepository.save(order);
        return OrderResponse.toDTO(savedOrder);
    }

    //주문 전체 조회 : 전체 주문 조회
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderResponse::toDTO)
                .collect(Collectors.toList());
    }
    //주문 전체 조회 : 가게의 전체 주문
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrderByStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store Not Found"));

        return orderRepository.findAllByStore(store).stream()
                .map(OrderResponse::toDTO)
                .collect(Collectors.toList());
    }
    //주문 전체 조회 : 고객의 전체 주문
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrderByCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer Not Found"));

        return orderRepository.findAllByCustomer(customer).stream()
                .map(OrderResponse::toDTO)
                .collect(Collectors.toList());
    }
    //주문 단건 조회
    @Transactional(readOnly = true)
    public Optional<OrderResponse> getOrder(Long orderId) {
        return orderRepository.findById(orderId).map(OrderResponse::toDTO);
    }
    //주문 수정
    @Transactional
    public OrderResponse upDateOrder(Long orderId, OrderRequest orderReq) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order Not Found"));
        //orderItem 수정
        List<OrderItem> newOrderItems = new ArrayList<>();
        for(OrderItemRequest newItemReq : orderReq.getOrderItems()){
           Menu menu = menuRepository.findById(newItemReq.getMenuId())
                   .orElseThrow(() -> new RuntimeException("Menu Not Found"));

            Optional<OrderItem> existingItemOpt = existingOrder.getOrderItems().stream()
                    //.filter(item -> item.getMenu().getMenuId().equals(menu.getMenuId()))
                    .filter(item -> item.getOrderItemId().equals(newItemReq.getOrderItemId()))
                    .findFirst();
            if(existingItemOpt.isPresent()){
                OrderItem existingItem =  existingItemOpt.get();
                existingItem.updateCount(newItemReq.getCount());
                newOrderItems.add(existingItem);
            }else{
                //새 주문 항목
                OrderItem item = OrderItemRequest.toEntity(newItemReq, existingOrder, menu);
                newOrderItems.add(item);
            }
        }
        //existingOrder.updateTotalAmount(getTotalAmount(newOrderItems));
        existingOrder.updateOrderItems(newOrderItems);
        existingOrder.updateStatus();
        existingOrder.updateDate();
        return OrderResponse.toDTO(orderRepository.save(existingOrder));
    }

    //주문 상태 변경 STATUS(RECEIVED -> COMPLETED)
    @Transactional
    public OrderResponse updateOrderStatus(Long orderId) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order Not Found"));
        //주문 상태 변경
        existingOrder.updateStatus();
        existingOrder.updateTotalAmount(getTotalAmount(existingOrder.getOrderItems()));

        //existingOrder.cancelOrder();  //status, totalAmount, updatedAt

        return OrderResponse.toDTO(orderRepository.save(existingOrder));
    }

    //주문 삭제(주문 취소)
    @Transactional
    public OrderResponse deleteOrder(Long orderId) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order Not Found"));
        //주문 상태 취소
        existingOrder.cancelOrder();  //status, totalAmount, updatedAt
        //주문 항목 삭제
        orderItemRepository.deleteAllByOrder(existingOrder);

        return OrderResponse.toDTO(orderRepository.save(existingOrder));
    }
    //Order의 totalAmount 계산
    private Long getTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream()
                .mapToLong(item -> item.getPrice() * item.getCount())
                .sum();
    }
    //특정 기간 동안의 매출을 매장별로 조회
    public List<SalesResponse> getSalesByStoreandDate(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startD = startDate.atStartOfDay();
        LocalDateTime endD = endDate.atTime(LocalTime.MAX);

        List<Object[]> results = orderRepository.findSalesByStoreAndDate(startD, endD);
        return results.stream()
                .map(result -> new SalesResponse(
                        (Long)result[0],
                        (String)result[1],
                        (Long)result[2]))
                .collect(Collectors.toList());
    }
}
