package ru.geekbrains.dto;

import lombok.Data;
@Data

public class InvalidProduct {
    private int status;
    private String message;
    public String timestamp;
}
