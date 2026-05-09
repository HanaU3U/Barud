package com.barud.dto.response;

import com.barud.model.enums.PedidoEstado;
import java.time.LocalDateTime;

public record PedidoResponseDto(
    Integer idPedido,
    Integer idMesa,
    Integer idMesero,
    LocalDateTime fechaHora,
    Integer numeroPersonas,
    PedidoEstado estado
) {
}
