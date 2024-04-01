package com.foodOrder.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Embeddable
@Data
@Getter
@Setter
@EqualsAndHashCode(of={"id"})
public class RestaurantDTO {

    private long id;

    private String name;

    @Column(length = 1000)
    private List<String> images;

    private String description;
}
