package com.barud.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;

public enum CuentaEstado {
    ABIERTA("Abierta"),
    CERRADA("Cerrada");

    private final String dbValue;

    CuentaEstado(String dbValue) {
        this.dbValue = dbValue;
    }

    @JsonValue
    public String getDbValue() {
        return dbValue;
    }

    @JsonCreator
    public static CuentaEstado fromValue(String value) {
        return Arrays.stream(values())
            .filter(v -> v.dbValue.equalsIgnoreCase(value) || v.name().equalsIgnoreCase(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Estado de cuenta invalido: " + value));
    }
}
