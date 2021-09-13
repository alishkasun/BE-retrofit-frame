package ru.geekbrains.dto;

import lombok.Data;

@Data
public class InvalidCategory {
    private int status;
    private String message;
    public String timestamp;
}


