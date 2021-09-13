package ru.geekbrains.enums;

import lombok.Getter;

public enum InvalidP {

        INVALID_PRODUCT(1132900, "Unable to find product with id: %d");

        @Getter
        private Integer id;

        @Getter
        private String messageFormat;

        InvalidP(Integer id, String name) {
            this.id = id;
            this.messageFormat = name;
        }

        public String getMessage() {
            return String.format(messageFormat, id);
        }
    }

