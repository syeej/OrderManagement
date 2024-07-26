package com.example.ordermanagement.dto.request;

import com.example.ordermanagement.domain.entity.Customer;
import com.example.ordermanagement.domain.entity.Store;
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
public class StoreRequest implements Serializable {
    private Long storeId;
    private String name;
    private String addr;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Store toEntity(){
        return Store.builder()
                .name(this.getName())
                .storeAddr(this.getAddr())
                .storePhone(this.getPhone())
                .build();
    }
}
