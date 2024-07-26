package com.example.ordermanagement.controller;

import com.example.ordermanagement.dto.request.MenuRequest;
import com.example.ordermanagement.dto.response.MenuResponse;
import com.example.ordermanagement.dto.response.PopularMenuResponse;
import com.example.ordermanagement.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    //메뉴 등록
    @PostMapping("/stores/{storeId}")
    public ResponseEntity<MenuResponse> saveMenu(@PathVariable("storeId") Long storeId,
                                                 @RequestBody MenuRequest menuReq) {
        menuReq.setStoreId(storeId);
        MenuResponse menuResponse = menuService.createMenu(menuReq);
        return ResponseEntity.status(201).body(menuResponse);
    }

    //메뉴 전체 조회
    @GetMapping("/stores/{storeId}")
    public ResponseEntity<List<MenuResponse>> getMenuList(@PathVariable("storeId") Long storeId){
        List<MenuResponse> list = menuService.getAllMenus(storeId);
        return ResponseEntity.ok(list);
    }
    //메뉴 단건 조회
    @GetMapping("/{menuId}")
    public ResponseEntity<MenuResponse> getMenu(@PathVariable("menuId") Long menuId){
        return menuService.getMenu(menuId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    //특정 카테고리의 모든 메뉴 조회
    @GetMapping
    public ResponseEntity<List<MenuResponse>> getMenuListByCategory(@RequestParam(required = false, name = "category") String category){
        List<MenuResponse> list = menuService.getAllMenusByCategory(category);
        return ResponseEntity.ok(list);
    }
    //인기 메뉴 TOP3 조회(모든 가게)
    @GetMapping("/popular")
    public ResponseEntity<List<PopularMenuResponse>> getPopularMenuList(){
        List<PopularMenuResponse> list = menuService.getPopularMenus();
        return ResponseEntity.ok(list);
    }
    //인기 메뉴 TOP3 조회(가게)
    @GetMapping("/popular/{id}")
    public ResponseEntity<List<PopularMenuResponse>> getPopularMenuListByStore(@PathVariable("id") Long storeId){
        List<PopularMenuResponse> list = menuService.getPopularMenusByStore(storeId);
        return ResponseEntity.ok(list);
    }

    //메뉴 수정
    @PutMapping("/{menuId}")
    public ResponseEntity<MenuResponse> updateMenu(@PathVariable("menuId") Long menuId,
                                                   @RequestBody MenuRequest menuReq){
        return menuService.updateMenu(menuId, menuReq)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //메뉴 삭제
    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> deleteMenu(@PathVariable("menuId") Long menuId){
        boolean isDeleted = menuService.deleteMenu(menuId);
        return isDeleted? ResponseEntity.noContent().build():ResponseEntity.notFound().build();
    }
}