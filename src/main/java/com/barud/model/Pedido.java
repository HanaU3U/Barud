package com.barud.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("pedido")
public class Pedido {

    @Id
    @Column("id_pedido")
    private Integer idPedido;

    @Column("id_mesa")
    private Integer idMesa;

    @Column("id_mesero")
    private Integer idMesero;

    @Column("fecha_hora")
    private LocalDateTime fechaHora;

    @Column("numero_personas")
    private Integer numeroPersonas;

    @Column("estado")
    private String estado;
}
