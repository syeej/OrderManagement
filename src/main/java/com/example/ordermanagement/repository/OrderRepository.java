package com.example.ordermanagement.repository;

import com.example.ordermanagement.domain.entity.Customer;
import com.example.ordermanagement.domain.entity.Order;
import com.example.ordermanagement.domain.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    //한 가게의 전체 주문 조회xx
    List<Order> findAllByStore(Store store);

    //한 고객의 전체 주문 조회
    List<Order> findAllByCustomer(Customer customer);

    //특정 기간 동안의 매출을 매장별로 조회
    @Query("SELECT o.store.storeId, o.store.name, SUM(o.totalAmount) " +
            "FROM Order o " +
            "WHERE o.status = com.example.ordermanagement.domain.OrderStatus.COMPLETED " +
            "AND o.updatedAt BETWEEN :startDate AND :endDate " +
            "GROUP BY o.store.storeId, o.store.name")
    List<Object[]> findSalesByStoreAndDate(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);


}
