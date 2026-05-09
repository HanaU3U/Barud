package com.barud.model;

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
@Table("division_cuenta")
public class DivisionCuenta {

    @Id
    @Column("id_division")
    private Integer idDivision;

    @Column("id_cuenta")
    private Integer idCuenta;

    @Column("descripcion")
    private String descripcion;

    @Column("monto")
    private BigDecimal monto;
}
