package com.barud.model;

import com.barud.model.enums.MesaEstado;
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
@Table("mesa")
public class Mesa {

	@Id
	@Column("id_mesa")
	private Integer idMesa;

	@Column("numero")
	private Integer numero;

	@Column("capacidad")
	private Integer capacidad;

	@Column("estado")
	private MesaEstado estado;
}
