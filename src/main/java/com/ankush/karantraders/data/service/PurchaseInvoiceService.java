package com.ankush.karantraders.data.service;

import com.ankush.karantraders.data.entities.PurchaseInvoice;
import com.ankush.karantraders.data.repository.PurchaseInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseInvoiceService {
    @Autowired
    private PurchaseInvoiceRepository repository;

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
}
