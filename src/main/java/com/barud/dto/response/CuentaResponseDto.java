package com.barud.dto.response;

import com.barud.model.enums.CuentaEstado;
import java.math.BigDecimal;

public record CuentaResponseDto(
    Integer idCuenta,
    Integer idPedido,
    BigDecimal subtotal,
    BigDecimal impuestos,
    BigDecimal total,
    CuentaEstado estado
) {
}
