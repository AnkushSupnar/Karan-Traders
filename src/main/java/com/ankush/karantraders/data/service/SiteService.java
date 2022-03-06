package com.ankush.karantraders.data.service;

import com.ankush.karantraders.data.entities.Site;
import com.ankush.karantraders.data.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteService {
    @Autowired
    private SiteRepository repository;

    public int saveSite(Site site) {
        if(site.getId()==null)
        {
            repository.save(site);
            return 1;
        }
        else{
            repository.save(site);
            return 2;
        }
    }
    public Site getByNameAndCustomerid(String name,Long customerid){ return repository.findBySitenameAndCustomer_Id(name,customerid).orElse(null);}
    public List<String>getSiteNameByCustomer(Long customerid){
        return repository.getSiteNameByCustomer(customerid);
    }
    public Site getByCustomerAndSiteName(Long customerid,String sitename){
        return repository.findByCustomer_IdAndSitename(customerid,sitename);
    }
}
