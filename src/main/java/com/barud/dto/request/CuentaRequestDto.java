package com.barud.dto.request;

import com.barud.model.enums.CuentaEstado;
import java.math.BigDecimal;

public record CuentaRequestDto(
    Integer idPedido,
    BigDecimal subtotal,
    BigDecimal impuestos,
    BigDecimal total,
    CuentaEstado estado
) {
}
