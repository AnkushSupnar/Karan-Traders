package com.ankush.karantraders.data.repository;

import com.ankush.karantraders.data.entities.PurchaseInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseInvoiceRepository extends JpaRepository<PurchaseInvoice,Long> {
    List<PurchaseInvoice> findByParty_Name(String name);


    @Override
    Optional<PurchaseInvoice> findById(Long aLong);

    List<PurchaseInvoice> findByInvoiceno(String invoiceno);

    List<PurchaseInvoice> findByDate(LocalDate date);



}
