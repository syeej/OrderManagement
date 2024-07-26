package com.example.ordermanagement.dto.request;

import com.example.ordermanagement.domain.entity.Menu;
import com.example.ordermanagement.domain.entity.Store;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuRequest implements Serializable {
    private Long menuId;
    private Long storeId;
    private String name;
    private String cat;
    private long price;
    private String info;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Menu toEntity(Store store){
        return Menu.builder()
                .store(store)
                .name(this.getName())
                .cat(this.getCat())
                .price(this.getPrice())
                .info(this.getInfo())
                .build();
    }
}
