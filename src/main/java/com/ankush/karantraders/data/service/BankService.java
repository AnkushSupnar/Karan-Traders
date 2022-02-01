package com.ankush.karantraders.data.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.ankush.karantraders.data.entities.Bank;
import com.ankush.karantraders.data.repository.BankRepository;

@Service
public class BankService {
    @Autowired
    BankRepository repository;

    public Bank getById(int id)
    {
        return repository.getById(id);
    }
    public List<Bank>getAllBank()
    {
        return repository.findAll();
    }
    public List<String>getAllBankNames(){return repository.getAllBankNames();}
    public int save(Bank bank)
    {
        if(bank.getId()==null){
            repository.save(bank);
            return 1;
        }
        else
        {
            repository.save(bank);
            return 2;
        }
    }
    public Bank getByName(String name)
    {
        return repository.getByName(name);
    }
}
