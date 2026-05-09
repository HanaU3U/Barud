package com.barud.dto.response;

import com.barud.model.enums.DetallePedidoEstado;
import java.math.BigDecimal;

public record DetallePedidoResponseDto(
    Integer idDetalle,
    Integer idPedido,
    Integer idProducto,
    Integer cantidad,
    BigDecimal precioUnitario,
    DetallePedidoEstado estado
) {
}
