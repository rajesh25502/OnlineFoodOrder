package com.foodOrder.Request;

import com.foodOrder.Model.Address;
import com.foodOrder.Model.ContactInfo;
import lombok.Data;

import java.util.List;

@Data
public class CreateRestaurantRequest {

    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInfo contactInfo;
    private String openingHours;
    private List<String> images;
}
