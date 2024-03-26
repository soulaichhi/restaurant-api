package com.soulaichhi.onlinefoodorderingapi.request;

import com.soulaichhi.onlinefoodorderingapi.model.Address;
import lombok.Data;

@Data
public class OrderRequest {
    private Long restaurantId;
    private Address deliveryAddress;
}
