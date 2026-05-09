package com.barud.model;

import java.time.LocalDate;
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
@Table("empleado")
public class Empleado {

    @Id
    @Column("id_empleado")
    private Integer idEmpleado;

    @Column("nombre")
    private String nombre;

    @Column("rol")
    private String rol;

    @Column("fecha_ingreso")
    private LocalDate fechaIngreso;

    @Column("estado")
    private String estado;
}
