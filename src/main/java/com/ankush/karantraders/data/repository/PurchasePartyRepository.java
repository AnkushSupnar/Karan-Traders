package com.ankush.karantraders.data.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.ankush.karantraders.data.entities.PurchaseParty;

@Repository
public interface PurchasePartyRepository extends JpaRepository<PurchaseParty, Integer> {

    List<PurchaseParty> getByName(String name);

    @Query("select name from PurchaseParty")
    List<String>getAllNames();
}