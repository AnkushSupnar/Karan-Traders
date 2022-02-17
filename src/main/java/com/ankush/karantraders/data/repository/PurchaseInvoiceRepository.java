package com.ankush.karantraders.data.repository;

import com.ankush.karantraders.data.entities.PurchaseInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseInvoiceRepository extends JpaRepository<PurchaseInvoice,Long> {

}
