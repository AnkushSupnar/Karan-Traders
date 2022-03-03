package com.ankush.karantraders.data.repository;

import com.ankush.karantraders.data.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByDate(LocalDate date);

    List<Bill> findByCustomer_Customername(String customername);

    Optional<Bill> findByBillno(Long billno);


}