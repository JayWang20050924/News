package com.news.backendproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeneralDataResponse {
    private boolean status;
    private String token;
}
