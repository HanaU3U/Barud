package com.barud.dto.request;

import com.barud.model.enums.ProductoTipo;
import java.math.BigDecimal;

public record ProductoRequestDto(
    String nombre,
    ProductoTipo tipo,
    BigDecimal precio,
    Integer stock
) {
}
