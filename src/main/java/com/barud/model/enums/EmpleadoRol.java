package com.barud.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;

public enum EmpleadoRol {
    MESERO("Mesero"),
    BARTENDER("Bartender"),
    CAJERO("Cajero"),
    ADMINISTRADOR("Administrador");

    private final String dbValue;

    EmpleadoRol(String dbValue) {
        this.dbValue = dbValue;
    }

    @JsonValue
    public String getDbValue() {
        return dbValue;
    }

    @JsonCreator
    public static EmpleadoRol fromValue(String value) {
        return Arrays.stream(values())
            .filter(v -> v.dbValue.equalsIgnoreCase(value) || v.name().equalsIgnoreCase(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Rol de empleado invalido: " + value));
    }
}
