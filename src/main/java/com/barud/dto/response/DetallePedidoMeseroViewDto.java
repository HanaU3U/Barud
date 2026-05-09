package com.barud.dto.response;

import java.math.BigDecimal;

public record DetallePedidoMeseroViewDto(
    Integer idPedido,
    String producto,
    Integer cantidad,
    BigDecimal precioUnitario,
    BigDecimal subtotal
) {
}
