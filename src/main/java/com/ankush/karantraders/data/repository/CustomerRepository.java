package com.ankush.karantraders.data.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.ankush.karantraders.data.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("select customername from Customer")
    List<String>getAllCustomerNames();

    Customer getByCustomername(String customername);

}