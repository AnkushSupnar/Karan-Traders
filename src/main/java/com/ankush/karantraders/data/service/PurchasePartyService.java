package com.ankush.karantraders.data.service;



import java.util.List;

import com.ankush.karantraders.data.entities.PurchaseParty;
import com.ankush.karantraders.data.repository.PurchasePartyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchasePartyService {
    @Autowired
    private PurchasePartyRepository repository;
    public PurchaseParty getPartyById(int id){return repository.getById(id);}
    public List<PurchaseParty>getAllPurchaseParty(){
      return repository.findAll();
    }
    public List<PurchaseParty> getPartyByName(String name){
        return repository.getByName(name);
    }
    public int savePurchaseParty(PurchaseParty party)
    {
        if(party.getId()==null)
        {
            repository.save(party);
            return 1;
        }
        else{
            repository.save(party);
            return 2;
        }
    }
    public List<String>getAllPartyNames(){
        return repository.getAllNames();
    }
}
