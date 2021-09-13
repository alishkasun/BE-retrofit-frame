package ru.geekbrains.enums;

import lombok.Getter;

import javax.swing.*;

public enum Category {
    FOOD(1, "Food"),
    ELECTRONIC(2, "Electronic"),
    INVALID_TITLE(5, "Error");



    @Getter
    private Integer id;
    @Getter
    private String name;



    Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
