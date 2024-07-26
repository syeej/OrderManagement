package com.example.ordermanagement.service;

import com.example.ordermanagement.domain.entity.Customer;
import com.example.ordermanagement.dto.request.CustomerRequest;
import com.example.ordermanagement.dto.response.CustomerResponse;
import com.example.ordermanagement.repository.CustomerRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    //고객 등록
    @Transactional
    public CustomerResponse createCustomer(CustomerRequest customerReq) {
        Customer newCustomer = customerReq.toEntity();
        Customer savedCustomer = customerRepository.save(newCustomer);
        return CustomerResponse.toDTO(savedCustomer);
    }
    //고객 전체 조회
    @Transactional(readOnly = true)
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(CustomerResponse::toDTO)
                .collect(Collectors.toList());
    }
    //고객 조회 1건
    @Transactional(readOnly = true)
    public Optional<CustomerResponse> getBookById(Long id) {
        return customerRepository.findById(id)
                .map(CustomerResponse::toDTO);
    }
    //고객 정보 수정
    @Transactional
    public Optional<CustomerResponse> updateCustomer(Long id, CustomerRequest customerReq) {
        return customerRepository.findById(id)
                .map(existing -> {
                    existing.updateCustomer(customerReq.getName(),
                            customerReq.getAddress(),
                            customerReq.getPhone());
                    return CustomerResponse.toDTO(customerRepository.save(existing));
                });
    }

    @Transactional
    public boolean deleteCustomer(Long id) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customerRepository.delete(customer);
                    return true;}).orElse(false);
    }
}
