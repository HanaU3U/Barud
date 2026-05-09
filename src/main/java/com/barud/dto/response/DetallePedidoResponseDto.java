package com.barud.dto.response;

import java.math.BigDecimal;

public record DetallePedidoResponseDto(
    Integer idDetalle,
    Integer idPedido,
    Integer idProducto,
    Integer cantidad,
    BigDecimal precioUnitario,
    String estado
) {
}
