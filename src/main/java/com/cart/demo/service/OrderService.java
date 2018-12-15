package com.cart.demo.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.cart.demo.dto.ItemDto;
import com.cart.demo.dto.OrderCreateRequest;
import com.cart.demo.entity.Item;
import com.cart.demo.entity.OrderItems;
import com.cart.demo.entity.Orders;
import com.cart.demo.repository.ItemRepository;
import com.cart.demo.repository.OrderItemsRepository;
import com.cart.demo.repository.OrdersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * Created by satish on 15/12/18.
 */
@Service
public class OrderService {

    private final OrdersRepository ordersRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public OrderService(OrdersRepository ordersRepository,
                        OrderItemsRepository orderItemsRepository,
                        ItemRepository itemRepository) {
        this.ordersRepository = ordersRepository;
        this.orderItemsRepository = orderItemsRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public void createOrder(@NotNull OrderCreateRequest orderCreateRequest) {
        if (CollectionUtils.isEmpty(orderCreateRequest.getItems())) {
            throw new IllegalArgumentException("No Item in order");
        }
        List<ItemDto> itemDtos = orderCreateRequest.getItems();
        //List<Long> itemIds = itemDtos.stream().map(ItemDto::getItemId).collect(Collectors.toList());
        //List<Item> items = itemRepository.findAll(itemIds);
        Orders order = mapRequestAndCreateOrder(orderCreateRequest);

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (ItemDto itemDto : itemDtos) {
            Item item = itemRepository.findOne(itemDto.getItemId());
            if (item == null) {
                throw new IllegalArgumentException("Item not found");
            }
            if (item.getCurrentStock() < itemDto.getQuantity()) {
                throw new IllegalArgumentException("Item out of stock");
            }
            item.setCurrentStock(item.getCurrentStock() - itemDto.getQuantity());
            itemRepository.save(item);
            OrderItems orderItem = new OrderItems();
            orderItem.setOrderId(order.getOrderId());
            orderItem.setItemId(itemDto.getItemId());
            orderItem.setQuantity(itemDto.getQuantity());
            orderItemsRepository.save(orderItem);
            totalAmount = totalAmount.add(item.getPrice());
        }
        order.setTotalAmount(totalAmount);
        ordersRepository.save(order);


    }

    private Orders mapRequestAndCreateOrder(OrderCreateRequest orderCreateRequest) {
        Orders order = new Orders();
        order.setEmail(orderCreateRequest.getEmail());
        order.setMobile(orderCreateRequest.getMobile());
        return ordersRepository.save(order);
    }

    public List<Orders> getAllOrders() {
        List<Orders> orders = ordersRepository.findAll();
        Map<Long, Item> itemMap = getItemIdMap(orders);
        orders.forEach(order -> order.getOrderItems().forEach(orderItem -> {
            orderItem.setName(itemMap.get(orderItem.getItemId()).getName());
        }));
        return orders;
    }

    private Map<Long, Item> getItemIdMap(List<Orders> orders) {
        Set<Long> itemId = orders.stream().map(Orders::getOrderItems).flatMap(List::stream).map(OrderItems::getItemId)
            .collect(Collectors.toSet());
        List<Item> items = itemRepository.findAll(itemId);
        return items.stream().collect(Collectors.toMap(Item::getId, Function.identity()));
    }
}
