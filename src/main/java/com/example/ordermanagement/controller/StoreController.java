package com.example.ordermanagement.controller;

import com.example.ordermanagement.dto.request.StoreRequest;
import com.example.ordermanagement.dto.response.StoreResponse;
import com.example.ordermanagement.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/store")
public class StoreController {
    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService){
        this.storeService = storeService;
    }
    //매장 등록
    @PostMapping
    public ResponseEntity<StoreResponse> saveStore(@RequestBody StoreRequest storeReq){
        StoreResponse storeResponse = storeService.createStore(storeReq);
        return ResponseEntity.status(201).body(storeResponse);
    }
    //매장 전체 조회
    @GetMapping
    public ResponseEntity<List<StoreResponse>> getStoreList(){
        List<StoreResponse> list = storeService.getAllStores();
        return ResponseEntity.ok(list);
    }
    //매장 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<StoreResponse> getStore(@PathVariable("id")Long id){
        return storeService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    //매장 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<StoreResponse> updateStore(@PathVariable("id")Long id,
                                                     @RequestBody StoreRequest storeReq){
        return storeService.updateStore(id, storeReq)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    //매장 정보 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable("id") Long id){
        boolean deleted = storeService.deleteStore(id);
        return deleted? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
