package com.news.backendproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeneralDto {
    private boolean status;
    private String token;
}
