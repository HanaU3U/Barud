package com.barud.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;

public enum EmpleadoEstado {
    ACTIVO("Activo"),
    INACTIVO("Inactivo");

    private final String dbValue;

    EmpleadoEstado(String dbValue) {
        this.dbValue = dbValue;
    }

    @JsonValue
    public String getDbValue() {
        return dbValue;
    }

    @JsonCreator
    public static EmpleadoEstado fromValue(String value) {
        return Arrays.stream(values())
            .filter(v -> v.dbValue.equalsIgnoreCase(value) || v.name().equalsIgnoreCase(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Estado de empleado invalido: " + value));
    }
}
