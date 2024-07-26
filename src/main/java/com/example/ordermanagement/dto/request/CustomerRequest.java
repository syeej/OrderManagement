package com.example.ordermanagement.dto.request;

import com.example.ordermanagement.domain.entity.Customer;
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
public class CustomerRequest implements Serializable {
    private Long customerId;
    private String name;
    private String address;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Customer toEntity(){
        return Customer.builder()
                .name(this.getName())
                .address(this.getAddress())
                .phone(this.getPhone())
                .build();
    }
}
