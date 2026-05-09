package com.barud.dto.response;

import com.barud.model.enums.ProductoTipo;
import java.math.BigDecimal;

public record ProductoResponseDto(
    Integer idProducto,
    String nombre,
    ProductoTipo tipo,
    BigDecimal precio,
    Integer stock
) {
}
