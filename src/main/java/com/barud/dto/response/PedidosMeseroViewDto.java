package com.barud.dto.response;

import java.time.LocalDateTime;

public record PedidosMeseroViewDto(
    Integer idPedido,
    Integer numeroMesa,
    String mesero,
    LocalDateTime fechaHora,
    Integer numeroPersonas,
    String estado
) {
}
