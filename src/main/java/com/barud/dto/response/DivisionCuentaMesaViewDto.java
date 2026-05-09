package com.barud.dto.response;

import java.math.BigDecimal;

public record DivisionCuentaMesaViewDto(
    Integer idDivision,
    Integer numeroMesa,
    String descripcion,
    BigDecimal monto
) {
}
