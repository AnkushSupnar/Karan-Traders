package com.ankush.karantraders.data.repository;

import com.ankush.karantraders.data.entities.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
    Optional<Site> findBySitenameAndCustomer_Id(String sitename, Long id);

    @Query("select sitename from Site where customerid=:customerid")
    List<String> getSiteNameByCustomer(@Param("customerid") Long customerid);

    Site findByCustomer_IdAndSitename(Long id, String sitename);


}