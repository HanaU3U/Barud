package com.barud.dto.response;

import java.time.LocalDate;

public record EmpleadoResponseDto(
    Integer idEmpleado,
    String nombre,
    String rol,
    LocalDate fechaIngreso,
    String estado
) {
}
