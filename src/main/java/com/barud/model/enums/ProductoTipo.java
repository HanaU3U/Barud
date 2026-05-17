package com.barud.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;

public enum ProductoTipo {
    BEBIDA_ALCOHOLICA("Bebida alcoholica"),
    BEBIDA_NO_ALCOHOLICA("Bebida no alcoholica"),
    COMIDA("Comida");

    private final String dbValue;

    ProductoTipo(String dbValue) {
        this.dbValue = dbValue;
    }

    @JsonValue
    public String getDbValue() {
        return dbValue;
    }

    @JsonCreator
    public static ProductoTipo fromValue(String value) {
        return Arrays.stream(values())
            .filter(v -> v.dbValue.equalsIgnoreCase(value) || v.name().equalsIgnoreCase(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(
                "Tipo de producto invalido: '" + value + "'. Valores aceptados: " +
                Arrays.stream(values()).map(v -> v.dbValue).toList()
            ));
    }
}
