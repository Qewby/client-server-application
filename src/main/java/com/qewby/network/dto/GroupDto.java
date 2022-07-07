package com.qewby.network.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {
    private int id;
    @With
    private String name;
    @With
    private String description;
}
