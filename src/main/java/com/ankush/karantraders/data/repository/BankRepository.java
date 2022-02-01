package com.ankush.karantraders.data.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.ankush.karantraders.data.entities.Bank;

@Repository
public interface BankRepository extends JpaRepository<Bank, Integer> {

    @Query("select name from Bank")
    List<String>getAllBankNames();

    Bank getByName(@Param("name") String name);
}