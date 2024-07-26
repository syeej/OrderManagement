package com.example.ordermanagement.controller;

import com.example.ordermanagement.dto.request.CustomerRequest;
import com.example.ordermanagement.dto.response.CustomerResponse;
import com.example.ordermanagement.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    //고객 등록
    @PostMapping
    public ResponseEntity<CustomerResponse> saveCustomer(@RequestBody CustomerRequest customerRequest){
        CustomerResponse result = customerService.createCustomer(customerRequest);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    //고객 전체 조회
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getCustomerList(){
        List<CustomerResponse> list = customerService.getAllCustomers();
        return ResponseEntity.ok(list);
    }
    //고객 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable("id")Long id){
        return customerService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    //고객 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable("id")Long id,
                                                           @RequestBody CustomerRequest customerReq){
        return customerService.updateCustomer(id, customerReq)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    //고객 정보 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Long id){
        boolean deleted = customerService.deleteCustomer(id);
        return deleted? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}