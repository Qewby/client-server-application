package com.qewby.network.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private Object object;
    private Integer status = 200; 
    private String errorMessage;
    private Map<String, String> headers = new HashMap<>();
}
