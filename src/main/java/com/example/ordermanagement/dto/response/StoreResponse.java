package com.example.ordermanagement.dto.response;

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
public class StoreResponse implements Serializable {
    private Long storeId;
    private String name;
    private String address;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static StoreResponse toDTO(Store store){
        return StoreResponse.builder()
                .storeId(store.getStoreId())
                .name(store.getName())
                .address(store.getStoreAddr())
                .phone(store.getStorePhone())
                .createdAt(store.getCreatedAt())
                .updatedAt(store.getUpdatedAt())
                .build();
    }
}
