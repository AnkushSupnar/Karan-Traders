package com.ankush.karantraders.data.service;

import com.ankush.karantraders.data.entities.Bill;
import com.ankush.karantraders.data.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BillService {

    @Autowired
    private BillRepository repository;
    public int saveBill(Bill bill){
        if(bill.getBillno()==null)
        {
            repository.save(bill);
            return 1;
        }
        else
        {
            repository.save(bill);
            return 2;
        }
    }
    public List<Bill>getAllBills(){
        return repository.findAll();
    }
    public List<Bill>getBillByDate(LocalDate date){
        return repository.findByDate(date);
    }
    public List<Bill>getBillByCustomerName(String customername){

            return repository.findByCustomer_Customername(customername);


    }
    public Bill getByBillno(Long billno){
        return repository.getById(billno);
    }
    public Bill findByBillNo(Long billno) {
        if (!repository.findByBillno(billno).isEmpty()) {
            return repository.findByBillno(billno).orElse(null);
        }
        else return null;
    }

}
