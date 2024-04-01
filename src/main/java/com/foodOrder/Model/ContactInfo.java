package com.foodOrder.Model;


import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactInfo {

    private String email;
    private  String mobile;
    private String twitter;
    private String instagram;
}
