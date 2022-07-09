package com.qewby.network.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodDto {
    private Integer id;
    @With
    private String name;
    @With
    private GroupDto group;
    @With
    private String description;
    @With
    private String manufacturer;
    @With
    private Integer number;
    @With
    private BigDecimal price;
}
