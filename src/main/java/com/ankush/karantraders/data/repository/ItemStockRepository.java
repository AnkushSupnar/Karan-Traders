package com.ankush.karantraders.data.repository;

import com.ankush.karantraders.data.entities.ItemStock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface ItemStockRepository extends JpaRepository<ItemStock,Long>{
    ItemStock findByItem_Id(Integer id);



}
