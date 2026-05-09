package com.barud.dto.response;

import java.time.LocalDateTime;

public record PedidoResponseDto(
    Integer idPedido,
    Integer idMesa,
    Integer idMesero,
    LocalDateTime fechaHora,
    Integer numeroPersonas,
    String estado
) {
}
