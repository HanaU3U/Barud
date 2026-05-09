package com.barud.model;

import com.barud.model.enums.ProductoTipo;
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
@Table("producto")
public class Producto {

    @Id
    @Column("id_producto")
    private Integer idProducto;

    @Column("nombre")
    private String nombre;

    @Column("tipo")
    private ProductoTipo tipo;

    @Column("precio")
    private BigDecimal precio;

    @Column("stock")
    private Integer stock;
}
