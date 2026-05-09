package com.barud.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PedidosPorDiaViewDto(
    LocalDate fecha,
    Long totalPedidos,
    BigDecimal ingresos
) {
}
