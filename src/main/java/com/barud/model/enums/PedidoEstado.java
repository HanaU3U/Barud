package com.barud.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;

public enum PedidoEstado {
    ABIERTO("Abierto"),
    EN_PREPARACION("En preparacion"),
    CERRADO("Cerrado");

    private final String dbValue;

    PedidoEstado(String dbValue) {
        this.dbValue = dbValue;
    }

    @JsonValue
    public String getDbValue() {
        return dbValue;
    }

    @JsonCreator
    public static PedidoEstado fromValue(String value) {
        return Arrays.stream(values())
            .filter(v -> v.dbValue.equalsIgnoreCase(value) || v.name().equalsIgnoreCase(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Estado de pedido invalido: " + value));
    }
}
