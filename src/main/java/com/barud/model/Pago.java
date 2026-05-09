package com.barud.model;

import com.barud.model.enums.PagoMetodo;
import java.math.BigDecimal;
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
@Table("pago")
public class Pago {

    @Id
    @Column("id_pago")
    private Integer idPago;

    @Column("id_cuenta")
    private Integer idCuenta;

    @Column("metodo")
    private PagoMetodo metodo;

    @Column("monto")
    private BigDecimal monto;

    @Column("fecha")
    private LocalDateTime fecha;
}
