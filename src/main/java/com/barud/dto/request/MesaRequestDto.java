package com.barud.dto.request;

import com.barud.model.enums.MesaEstado;

public record MesaRequestDto(
    Integer numero,
    Integer capacidad,
    MesaEstado estado
) {
}
