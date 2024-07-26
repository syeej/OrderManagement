package com.example.ordermanagement.repository;

import com.example.ordermanagement.domain.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
