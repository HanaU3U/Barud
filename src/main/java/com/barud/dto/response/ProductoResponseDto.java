package com.barud.dto.response;

import java.math.BigDecimal;

public record ProductoResponseDto(
    Integer idProducto,
    String nombre,
    String tipo,
    BigDecimal precio,
    Integer stock
) {
}
