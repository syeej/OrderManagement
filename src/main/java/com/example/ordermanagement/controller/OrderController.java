package com.example.ordermanagement.controller;

import com.example.ordermanagement.dto.request.OrderRequest;
import com.example.ordermanagement.dto.response.MenuResponse;
import com.example.ordermanagement.dto.response.OrderResponse;
import com.example.ordermanagement.dto.response.PopularMenuResponse;
import com.example.ordermanagement.dto.response.SalesResponse;
import com.example.ordermanagement.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    //주문 등록
    @PostMapping
    public ResponseEntity<OrderResponse> saveOrder(@RequestBody OrderRequest orderReq) {
        OrderResponse orderResponse = orderService.createOrder(orderReq);
        return ResponseEntity.ok(orderResponse);
    }

    //주문 전체 조회 : 전체 주문 조회
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrderList() {
        List<OrderResponse> response = orderService.getAllOrders();
        return ResponseEntity.ok(response);
    }

    //주문 전체 조회 : 가게의 전체 주문
    @GetMapping("/stores/{id}")
    public ResponseEntity<List<OrderResponse>> getOrderListByStore(@PathVariable("id") Long storeId) {
        List<OrderResponse> response = orderService.getAllOrderByStore(storeId);
        return ResponseEntity.ok(response);
    }

    //주문 전체 조회 : 고객의 전체 주문
    @GetMapping("/customers/{id}")
    public ResponseEntity<List<OrderResponse>> getOrderListByCustomer(@PathVariable("id") Long customerId) {
        List<OrderResponse> response = orderService.getAllOrderByCustomer(customerId);
        return ResponseEntity.ok(response);
    }

    //주문 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable("id") Long orderId) {
        return orderService.getOrder(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //주문 수정
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> upDateOrder(@PathVariable("id") Long orderId, @RequestBody OrderRequest orderReq) {
        OrderResponse orderResponse = orderService.upDateOrder(orderId, orderReq);
        return ResponseEntity.ok(orderResponse);
    }
    //주문 상태 변경 STATUS(RECEIVED -> COMPLETED)
    @PutMapping("/completion/{id}")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable("id") Long orderId){
        log.info("orderId : {}", orderId);
        OrderResponse orderResponse = orderService.updateOrderStatus(orderId);
        return ResponseEntity.ok(orderResponse);
    }

    //주문 삭제 (주문 취소)
    @PutMapping("/status/{id}")
    public ResponseEntity<OrderResponse> deleteOrder(@PathVariable("id") Long orderId){
        log.info("orderId : {}", orderId);
        OrderResponse orderResponse = orderService.deleteOrder(orderId);
        return ResponseEntity.ok(orderResponse);
    }

    //특정 기간 동안의 매출을 매장별로 조회
    @GetMapping("/sales")
    public ResponseEntity<List<SalesResponse>> getSalsByStore(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate startDate,
            @RequestParam("endDate")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate endDate){
        log.info("startDate : {}, endDate : {}", startDate, endDate);
        List<SalesResponse> list = orderService.getSalesByStoreandDate(startDate, endDate);
        return ResponseEntity.ok(list);
    }

}
