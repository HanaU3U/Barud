package com.barud.dto.response;

import java.math.BigDecimal;

public record CuentaResponseDto(
    Integer idCuenta,
    Integer idPedido,
    BigDecimal subtotal,
    BigDecimal impuestos,
    BigDecimal total,
    String estado
) {
}
