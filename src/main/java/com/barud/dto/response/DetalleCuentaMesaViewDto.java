package com.barud.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DetalleCuentaMesaViewDto(
    Integer idCuenta,
    Integer numeroMesa,
    String resumenProductos,
    BigDecimal subtotal,
    BigDecimal impuestos,
    BigDecimal total,
    LocalDateTime fecha,
    String cuentaDividida
) {
}
