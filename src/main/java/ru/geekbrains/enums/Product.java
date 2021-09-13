package ru.geekbrains.enums;
import lombok.Getter;

public enum Product {

    FOOD(11329, "Ebiten maki");


    @Getter
    private Integer id;
    @Getter
    private String name;



    Product(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
