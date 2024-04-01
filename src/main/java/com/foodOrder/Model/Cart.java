package com.foodOrder.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    private User customer;

    private Double total;

    @OneToMany(mappedBy ="cart", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CartItem> cartItem=new ArrayList<>();



}
