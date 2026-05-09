package com.barud.dto.response;

import java.math.BigDecimal;

public record ProductosMasVendidosViewDto(
    String nombre,
    String tipo,
    Long totalVendido,
    BigDecimal ingresos
) {
}
