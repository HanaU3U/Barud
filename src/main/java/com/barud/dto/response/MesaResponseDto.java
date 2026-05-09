package com.barud.dto.response;

import com.barud.model.enums.MesaEstado;

public record MesaResponseDto(
    Integer idMesa,
    Integer numero,
    Integer capacidad,
    MesaEstado estado
) {
}
