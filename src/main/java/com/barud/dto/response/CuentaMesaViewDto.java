package com.barud.dto.response;

import java.math.BigDecimal;

public record CuentaMesaViewDto(
    Integer idCuenta,
    Integer numeroMesa,
    BigDecimal subtotal,
    BigDecimal impuestos,
    BigDecimal total,
    String estado
) {
}
