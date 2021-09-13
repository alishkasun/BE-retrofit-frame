package ru.geekbrains.enums;

import lombok.Getter;

public enum Invalid {

    INVALID(5, "Unable to find category with id: %d");

    @Getter
    private Integer id;

    @Getter
    private String messageFormat;

    Invalid(Integer id, String name) {
        this.id = id;
        this.messageFormat = name;
    }

    public String getMessage() {
        return String.format(messageFormat, id);
    }
}
