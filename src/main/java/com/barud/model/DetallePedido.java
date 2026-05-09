package com.barud.model;

import com.barud.model.enums.DetallePedidoEstado;
import java.math.BigDecimal;
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
@Table("detalle_pedido")
public class DetallePedido {

    @Id
    @Column("id_detalle")
    private Integer idDetalle;

    @Column("id_pedido")
    private Integer idPedido;

    @Column("id_producto")
    private Integer idProducto;

    @Column("cantidad")
    private Integer cantidad;

    @Column("precio_unitario")
    private BigDecimal precioUnitario;

    @Column("estado")
    private DetallePedidoEstado estado;
}
