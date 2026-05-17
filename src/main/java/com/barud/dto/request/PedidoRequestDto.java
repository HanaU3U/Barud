package com.barud.dto.request;

import com.barud.model.enums.PedidoEstado;
import java.time.LocalDateTime;

public record PedidoRequestDto(
    Integer idMesa,
    Integer idMesero,
    LocalDateTime fechaHora,
    Integer numeroPersonas,
    PedidoEstado estado
) {
}
