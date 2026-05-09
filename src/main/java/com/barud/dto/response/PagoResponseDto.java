package com.barud.dto.response;

import com.barud.model.enums.PagoMetodo;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagoResponseDto(
    Integer idPago,
    Integer idCuenta,
    PagoMetodo metodo,
    BigDecimal monto,
    LocalDateTime fecha
) {
}
