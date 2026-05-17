package com.barud.dto.request;

import java.math.BigDecimal;

public record DivisionCuentaRequestDto(
    Integer idCuenta,
    String descripcion,
    BigDecimal monto
) {
}
