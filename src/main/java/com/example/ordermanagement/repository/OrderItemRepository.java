package com.example.ordermanagement.repository;

import com.example.ordermanagement.domain.entity.Order;
import com.example.ordermanagement.domain.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    void deleteAllByOrder(Order order);

    //인기 메뉴 TOP3 조회(모든 가게)
    @Query("SELECT oi.menu.menuId, oi.menu.name, oi.menu.store.name, SUM(oi.count) as totalCount " +
            "FROM OrderItem oi " +
            "JOIN oi.order o " +
            "WHERE o.status = com.example.ordermanagement.domain.OrderStatus.COMPLETED " +
            "GROUP BY oi.menu.menuId, oi.menu.name " +
            "ORDER BY totalCount DESC " +
            "LIMIT 3")
    List<Object[]> findPopularMenus();

    //인기 메뉴 TOP3 조회(가게별)
    @Query("SELECT oi.menu.menuId, oi.menu.name, oi.menu.store.name, SUM(oi.count) as totalCount " +
            "FROM OrderItem oi " +
            "JOIN oi.order o " +
            "WHERE o.store.storeId = :storeId " +
            "AND o.status = com.example.ordermanagement.domain.OrderStatus.COMPLETED " +
            "GROUP BY oi.menu.menuId, oi.menu.name " +
            "ORDER BY totalCount DESC " +
            "LIMIT 3")
    List<Object[]> findPopularMenusByStore(@Param("storeId")Long storeId);
}
