package com.example.ordermanagement.domain.entity;

import com.example.ordermanagement.domain.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders") //order는 예약어
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store; //7/25 수정

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();
    //mappedBy 속성 : 양방향 관계 설정

    @Enumerated(EnumType.STRING) // 문자열로 설정
    @Column(nullable = false)
    private OrderStatus status;

    @Column(name = "total_amount", nullable = false)
    private Long totalAmount;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TIMESTAMP", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void updateTotalAmount(Long newAmount){
        this.totalAmount = newAmount;
    }
    public void updateOrderItems(List<OrderItem> orderItems){
        this.orderItems = orderItems;
    }
    public void updateDate(){
        this.updatedAt = LocalDateTime.now();
    }
    public void updateStatus(){
        this.status = OrderStatus.COMPLETED;
    }
    //주문취소
    public void cancelOrder(){
        this.status = OrderStatus.CANCELLATION;
        this.totalAmount = 0L;
        this.getOrderItems().clear();
        this.updatedAt = LocalDateTime.now();
    }
}
