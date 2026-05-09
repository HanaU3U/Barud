package com.barud.dto.response;

import java.math.BigDecimal;

public record DetallePedidoCompletoViewDto(
    Integer idPedido,
    Integer mesa,
    String producto,
    Integer cantidad,
    BigDecimal precioUnitario,
    BigDecimal subtotal,
    String estado
) {
}
