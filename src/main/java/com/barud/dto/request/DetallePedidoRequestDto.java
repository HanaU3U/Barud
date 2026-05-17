package com.barud.dto.request;

import com.barud.model.enums.DetallePedidoEstado;
import java.math.BigDecimal;

public record DetallePedidoRequestDto(
    Integer idPedido,
    Integer idProducto,
    Integer cantidad,
    BigDecimal precioUnitario,
    DetallePedidoEstado estado
) {
}
