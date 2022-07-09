package com.qewby.network.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDto {
    private Object object;
    private Integer status; 
    private String errorMessage;
}
