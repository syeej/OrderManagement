package com.example.ordermanagement.dto.response;

import com.example.ordermanagement.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuResponse implements Serializable {
    private Long menuId;
    private Long storeId;
    private String name;
    private String cat;
    private long price;
    private String info;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static MenuResponse toDTO(Menu menu){
        return MenuResponse.builder()
                .menuId(menu.getMenuId())
                .storeId(menu.getStore().getStoreId())
                .name(menu.getName())
                .cat(menu.getCat())
                .price(menu.getPrice())
                .info(menu.getInfo())
                .createdAt(menu.getCreatedAt())
                .updatedAt(menu.getUpdatedAt())
                .build();
    }
}
