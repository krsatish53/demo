package com.cart.demo.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Created by satish on 15/12/18.
 */
@Data
public class OrderCreateRequest {

    @NotNull
    private String email;
    @NotNull
    private String mobile;
    private List<ItemDto> items;

}
