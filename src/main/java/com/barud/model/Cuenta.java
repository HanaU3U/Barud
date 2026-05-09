package com.barud.model;

import com.barud.model.enums.CuentaEstado;
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
@Table("cuenta")
public class Cuenta {

    @Id
    @Column("id_cuenta")
    private Integer idCuenta;

    @Column("id_pedido")
    private Integer idPedido;

    @Column("subtotal")
    private BigDecimal subtotal;

    @Column("impuestos")
    private BigDecimal impuestos;

    @Column("total")
    private BigDecimal total;

    @Column("estado")
    private CuentaEstado estado;
}
