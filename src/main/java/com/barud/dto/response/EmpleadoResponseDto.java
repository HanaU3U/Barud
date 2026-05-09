package com.barud.dto.response;

import com.barud.model.enums.EmpleadoEstado;
import com.barud.model.enums.EmpleadoRol;
import java.time.LocalDate;

public record EmpleadoResponseDto(
    Integer idEmpleado,
    String nombre,
    EmpleadoRol rol,
    LocalDate fechaIngreso,
    EmpleadoEstado estado
) {
}
