package com.mobiquity.packer.model;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item {

    private int index;
    private float weight;
    private BigDecimal price;

}
