package com.ankush.karantraders.data.service;

import com.ankush.karantraders.data.entities.ItemStock;
import com.ankush.karantraders.data.repository.ItemStockRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemStockService {
    @Autowired
    private ItemStockRepository repository;

    public ItemStock getByItemId(Integer itemid){
        return repository.findByItem_Id(itemid);
    }
    public ItemStock getById(Long id){return repository.getById(id);}
    public int save(ItemStock stock)
    {
        ItemStock oldStock = getByItemId(stock.getItem().getId());
        if(oldStock==null)
        {
            repository.save(stock);
            return 1;
        }
        else
        {
            oldStock.setQuantity(oldStock.getQuantity()+stock.getQuantity());
            repository.save(oldStock);
            return 2;
        }
    }
    public void reduceStock(ItemStock stock)
    {
        ItemStock oldStock = getById(stock.getId());
        oldStock.setQuantity(oldStock.getQuantity()-stock.getQuantity());
        repository.save(oldStock);
    }
}
