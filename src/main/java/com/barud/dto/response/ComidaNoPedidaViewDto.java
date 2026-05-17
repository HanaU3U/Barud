package com.barud.dto.response;

import java.math.BigDecimal;

public record ComidaNoPedidaViewDto(
    String nombre,
    BigDecimal precio
) {
}
