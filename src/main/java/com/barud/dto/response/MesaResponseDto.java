package com.barud.dto.response;

public record MesaResponseDto(
    Integer idMesa,
    Integer numero,
    Integer capacidad,
    String estado
) {
}
