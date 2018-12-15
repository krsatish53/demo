package com.cart.demo.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.cart.demo.entity.Item;
import com.cart.demo.repository.ItemRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by satish on 15/12/18.
 */
@Service
public class ItemService {

    private final ItemRepository itemRepository;


    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item createItem(@NotNull Item item) {
        return itemRepository.save(item);
    }

    public Item updateItem(Long itemId, Item itemReq) {
        Item item = itemRepository.findOne(itemId);
        if (item == null) {
            throw new IllegalArgumentException("Item not found");
        }
        BeanUtils.copyProperties(itemReq, item, "id");
        return itemRepository.save(item);
    }

    public void deleteItem(Long itemId) {
        Item item = itemRepository.findOne(itemId);
        if (item == null) {
            throw new IllegalArgumentException("Item not found");
        }
        itemRepository.delete(itemId);
    }

    public Item getItem(Long itemId) {
        Item item = itemRepository.findOne(itemId);
        if (item == null) {
            throw new IllegalArgumentException("Item not found");
        }
        return item;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
}
