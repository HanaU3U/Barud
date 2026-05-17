package com.barud.dto.request;

import com.barud.model.enums.EmpleadoEstado;
import com.barud.model.enums.EmpleadoRol;
import java.time.LocalDate;

public record EmpleadoRequestDto(
    String nombre,
    EmpleadoRol rol,
    LocalDate fechaIngreso,
    EmpleadoEstado estado
) {
}
