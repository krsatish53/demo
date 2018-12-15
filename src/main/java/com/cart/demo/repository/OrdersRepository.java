package com.cart.demo.repository;

import com.cart.demo.entity.Orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by satish on 15/12/18.
 */
@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

}
