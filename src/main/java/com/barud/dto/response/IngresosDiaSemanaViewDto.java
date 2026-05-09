package com.barud.dto.response;

import java.math.BigDecimal;

public record IngresosDiaSemanaViewDto(
    String diaSemana,
    Long totalPagos,
    BigDecimal ingresosTotales,
    BigDecimal promedioPago
) {
}
