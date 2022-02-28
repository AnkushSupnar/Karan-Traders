package com.ankush.karantraders.data.service;

import java.util.List;

import com.ankush.karantraders.data.entities.Item;
import com.ankush.karantraders.data.repository.ItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    @Autowired
    private ItemRepository repository;

    public List<String> getUiList() {
        return repository.getItemUnitList();
    }

    public List<Item> getAllItems() {
        return repository.findAll();
    }

    public Item getByDescription(String description) {
        if(repository.findByDescription(description).size()>0)
        return repository.findByDescription(description).get(0);
        else return null;
    }
    public Item getByCode(String code) {
        return repository.findByCode(code);
    }
    public List<Item>getByHsn(Long hsn){
        if(repository.findByHsn(hsn).size()>0)
        return repository.findByHsn(hsn);
        else return null;
    }
    public List<String>getAllItemNames(){
        return repository.findAllItemName();
    }
    public Item getByCodeAndDescription(String code, String description)
    {
        return repository.findByCodeAndDescription(code,description);
    }

    public int save(Item item) {
        if (item.getId() == null) {
            repository.save(item);
            return 1;
        } else {
            repository.save(item);
            return 2;
        }
    }
    public List<String>getUnitNames()
    {
        return repository.getUnits();
    }
}
