package com.news.backendproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenaralDataResponse {
    private boolean status;
    private String token;
}
