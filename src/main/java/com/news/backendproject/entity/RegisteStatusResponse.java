package com.news.backendproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisteStatusResponse {
    private boolean register;
    private String token;
}
