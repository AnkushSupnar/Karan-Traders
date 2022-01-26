package com.ankush.karantraders.data.repository;

import java.util.List;

import com.ankush.karantraders.data.entities.Item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
    
    @Query("select DISTINCT(unit)from Item")
    List<String>getItemUnitList();

    List<Item> findByDescription(String description);
    Item findByCode(Long code);

    List<Item> findByHsn(Long hsn);
}
