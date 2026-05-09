package com.barud.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagoResponseDto(
    Integer idPago,
    Integer idCuenta,
    String metodo,
    BigDecimal monto,
    LocalDateTime fecha
) {
}
