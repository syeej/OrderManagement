package com.example.ordermanagement.service;

import com.example.ordermanagement.domain.entity.Menu;
import com.example.ordermanagement.domain.entity.Store;
import com.example.ordermanagement.dto.request.MenuRequest;
import com.example.ordermanagement.dto.response.MenuResponse;
import com.example.ordermanagement.dto.response.PopularMenuResponse;
import com.example.ordermanagement.repository.MenuRepository;
import com.example.ordermanagement.repository.OrderItemRepository;
import com.example.ordermanagement.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final OrderItemRepository orderItemRepository;

    //메뉴 등록
    @Transactional
    public MenuResponse createMenu(MenuRequest menuReq) {
        Optional<Store> storeOptional = storeRepository.findById(menuReq.getStoreId());
        Store store = storeOptional.get();
        Menu menu = menuReq.toEntity(store);
        Menu storedMenu = menuRepository.save(menu);
        return MenuResponse.toDTO(storedMenu);
    }
    //메뉴 전체 조회
    @Transactional(readOnly = true)
    public List<MenuResponse> getAllMenus(Long storeId) {
        Optional<Store> storeOptional = storeRepository.findById(storeId);
        Store store = storeOptional.get();
        return menuRepository.findByStore(store)
                .stream().map(MenuResponse::toDTO)
                .collect(Collectors.toList());
    }
    //메뉴 단건 조회
    @Transactional(readOnly = true)
    public Optional<MenuResponse> getMenu(Long menuId) {
        return menuRepository.findById(menuId).map(MenuResponse::toDTO);
    }
    //메뉴 수정
    @Transactional
    public Optional<MenuResponse> updateMenu(Long menuId, MenuRequest menuReq) {
        return menuRepository.findById(menuId)
                .map(existing -> {
                    existing.updateMenu(menuReq.getName(),
                            menuReq.getCat(),
                            menuReq.getPrice(),
                            menuReq.getInfo());
                    return MenuResponse.toDTO(menuRepository.save(existing));
                });
    }
    //메뉴 삭제
    @Transactional
    public boolean deleteMenu(Long menuId) {
        return menuRepository.findById(menuId)
                .map(menu -> {
                    menuRepository.delete(menu);
                    return true;
                }).orElse(false);
    }
    //특정 카테고리의 모든 메뉴 조회
    @Transactional(readOnly = true)
    public List<MenuResponse> getAllMenusByCategory(String category) {
        return menuRepository.findAllByCat(category)
                .stream().map(MenuResponse::toDTO)
                .collect(Collectors.toList());
    }
    //인기 메뉴 TOP3 조회(모든 가게)
    public List<PopularMenuResponse> getPopularMenus() {
        List<Object[]> results = orderItemRepository.findPopularMenus();
        return results.stream()
                .map(result -> PopularMenuResponse.builder()
                        .menuId((Long)result[0])
                        .menuName((String)result[1])
                        .storeName((String)result[2])
                        .orderCount((Long)result[3])
                        .build()
                ).collect(Collectors.toList());
    }
    //인기 메뉴 TOP3 조회(가게별)
    public List<PopularMenuResponse> getPopularMenusByStore(Long storeId) {
        List<Object[]> results = orderItemRepository.findPopularMenusByStore(storeId);
        return results.stream()
                .map(result -> PopularMenuResponse.builder()
                        .menuId((Long)result[0])
                        .menuName((String)result[1])
                        .storeName((String)result[2])
                        .orderCount((Long)result[3])
                        .build()
                ).collect(Collectors.toList());
    }
}