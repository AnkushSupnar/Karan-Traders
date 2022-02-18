package com.ankush.karantraders.data.service;

import com.ankush.karantraders.data.entities.PurchaseInvoice;
import com.ankush.karantraders.data.repository.PurchaseInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PurchaseInvoiceService {
    @Autowired
    private PurchaseInvoiceRepository repository;

    public PurchaseInvoice getById(Long id){
       try {
           return repository.findById(id).get();
       }catch(Exception e)
       {
           return null;
       }
    }
    public List<PurchaseInvoice>getAllBill(){
        return repository.findAll();
    }
    public int savePurchaseInvoice(PurchaseInvoice invoice){
        if(invoice.getId()==null)
        {
            repository.save(invoice);
            return 1;
        }
        else
        {
            repository.save(invoice);
            return 2;
        }

    }
    public List<PurchaseInvoice>getByPartyName(String partyname){
        return repository.findByParty_Name(partyname);
    }
    public List<PurchaseInvoice>getByInvoiceNo(String invoiceno){
        return repository.findByInvoiceno(invoiceno);
    }
    public List<PurchaseInvoice>getByDate(LocalDate date){return repository.findByDate(date);}
}
