package com.foodOrder.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodOrder.DTO.RestaurantDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String email;

    private String phoneNumber;

    private String password;

    private USER_ROLE role=USER_ROLE.CUSTOMER;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Order> order=new ArrayList<>();

    @ElementCollection
    private List<RestaurantDTO> favorite=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> address=new ArrayList<>();

//    @JsonIgnore
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    private Cart car;

}
