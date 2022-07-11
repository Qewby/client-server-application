package com.qewby.network.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodGroupDto {
    private Integer id;
    @With
    private String name;
    @With
    private Integer group_id;
    @With
    private String description;
    @With
    private String manufacturer;
    @With
    private Integer number;
    @With
    private BigDecimal price;
}
