package com.example.ordermanagement.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="menu_id")
    private Long menuId;

    @ManyToOne
    @JoinColumn(name="store_id", nullable = false)
    private Store store;

    @Column(name = "menu_name", nullable = false)
    private String name;

    @Column(name = "menu_cat", nullable = false)
    private String cat; //메뉴 카테고리

    @Column(name = "menu_price", nullable = false)
    private long price;

    @Column(name = "menu_info", columnDefinition = "TEXT")
    private String info; //설명

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(columnDefinition = "TIMESTAMP", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    //메뉴 수정
    public void updateMenu(String name, String cat, long price, String info){
        this.name = name;
        this.cat = cat;
        this.price = price;
        this.info = info;
        this.updatedAt = LocalDateTime.now();
    }
}
