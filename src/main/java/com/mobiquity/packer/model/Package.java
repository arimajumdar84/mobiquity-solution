package com.mobiquity.packer.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Package {

    private float weight;
    private List<Item> items;

}
