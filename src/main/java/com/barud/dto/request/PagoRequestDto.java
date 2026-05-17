package com.barud.dto.request;

import com.barud.model.enums.PagoMetodo;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagoRequestDto(
    Integer idCuenta,
    PagoMetodo metodo,
    BigDecimal monto,
    LocalDateTime fecha
) {
}
