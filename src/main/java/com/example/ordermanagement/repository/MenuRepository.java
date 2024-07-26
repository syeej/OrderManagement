package com.example.ordermanagement.repository;

import com.example.ordermanagement.domain.entity.Menu;
import com.example.ordermanagement.domain.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    //가게별 메뉴 조회
    List<Menu> findByStore(Store store);
    //카테고리별 메뉴 조회
    List<Menu> findAllByCat(String category);
}