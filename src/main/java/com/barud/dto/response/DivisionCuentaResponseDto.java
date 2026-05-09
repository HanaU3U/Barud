package com.barud.dto.response;

import java.math.BigDecimal;

public record DivisionCuentaResponseDto(
    Integer idDivision,
    Integer idCuenta,
    String descripcion,
    BigDecimal monto
) {
}
