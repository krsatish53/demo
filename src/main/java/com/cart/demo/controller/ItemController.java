package com.cart.demo.controller;


import java.util.List;

import javax.validation.Valid;

import com.cart.demo.entity.Item;
import com.cart.demo.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by satish on 15/12/18.
 */
@RestController
@RequestMapping("/item")
public class ItemController {

    //nirlendu@omnicuris.com

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Item createItem(@Valid @RequestBody Item item) {
        return itemService.createItem(item);
    }


    @PutMapping(value = "{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public Item updateItem(@PathVariable Long itemId, @RequestBody Item item) {
        return itemService.updateItem(itemId, item);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteItem(@RequestParam(value = "item_id") Long itemId) {
        itemService.deleteItem(itemId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Item getItem(@RequestParam(value = "item_id") Long itemId) {
        return itemService.getItem(itemId);
    }

    @GetMapping(value = "all")
    @ResponseStatus(HttpStatus.OK)
    public List<Item> getItem() {
        return itemService.getAllItems();
    }


}
