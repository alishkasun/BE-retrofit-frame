package ru.geekbrains.enums;

import lombok.Getter;

public enum Invalid {
   INVALID(5, "Unable to find category with id: 5");


    @Getter
    private Integer id;
    @Getter
    private String name;




    Invalid(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
