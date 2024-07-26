package com.example.ordermanagement.service;

import com.example.ordermanagement.domain.entity.Store;
import com.example.ordermanagement.dto.request.StoreRequest;
import com.example.ordermanagement.dto.response.StoreResponse;
import com.example.ordermanagement.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository){
        this.storeRepository = storeRepository;
    }

    //매장 등록
    @Transactional
    public StoreResponse createStore(StoreRequest StoreReq) {
        Store newStore = StoreReq.toEntity();
        Store savedStore = storeRepository.save(newStore);
        return StoreResponse.toDTO(savedStore);
    }
    //매장 전체 조회
    @Transactional(readOnly = true)
    public List<StoreResponse> getAllStores() {
        return storeRepository.findAll().stream()
                .map(StoreResponse::toDTO)
                .collect(Collectors.toList());
    }
    //매장 조회 1건
    @Transactional(readOnly = true)
    public Optional<StoreResponse> getBookById(Long id) {
        return storeRepository.findById(id)
                .map(StoreResponse::toDTO);
    }
    //매장 정보 수정
    @Transactional
    public Optional<StoreResponse> updateStore(Long id, StoreRequest StoreReq) {

        return storeRepository.findById(id)
                .map(existing -> {
                    existing.updateStore(StoreReq.getName(),
                            StoreReq.getAddr(),
                            StoreReq.getPhone());
                    return StoreResponse.toDTO(storeRepository.save(existing));
                });
    }
    //매장 삭제
    @Transactional
    public boolean deleteStore(Long id) {
        return storeRepository.findById(id)
                .map(Store -> {
                    storeRepository.delete(Store);
                    return true;}).orElse(false);
    }
}