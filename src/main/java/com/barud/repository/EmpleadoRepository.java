package com.barud.repository;

import com.barud.model.Empleado;
import com.barud.model.enums.EmpleadoEstado;
import com.barud.model.enums.EmpleadoRol;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class EmpleadoRepository {

	private final JdbcTemplate jdbcTemplate;

	private final RowMapper<Empleado> rowMapper = (rs, rowNum) -> new Empleado(
		rs.getInt("id_empleado"),
		rs.getString("nombre"),
		EmpleadoRol.fromValue(rs.getString("rol")),
		rs.getDate("fecha_ingreso").toLocalDate(),
		EmpleadoEstado.fromValue(rs.getString("estado"))
	);

	public EmpleadoRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Iterable<Empleado> findAll() {
		return jdbcTemplate.query(
			"SELECT id_empleado, nombre, rol, fecha_ingreso, estado FROM empleado ORDER BY id_empleado",
			rowMapper
		);
	}

	public List<Empleado> findByFilters(
		String nombre,
		EmpleadoRol rol,
		EmpleadoEstado estado,
		LocalDate fechaDesde,
		LocalDate fechaHasta
	) {
		StringBuilder sql = new StringBuilder(
			"SELECT id_empleado, nombre, rol, fecha_ingreso, estado FROM empleado WHERE 1=1"
		);
		List<Object> params = new ArrayList<>();

		if (nombre != null && !nombre.isBlank()) {
			sql.append(" AND LOWER(nombre) LIKE LOWER(?)");
			params.add("%" + nombre + "%");
		}
		if (rol != null) {
			sql.append(" AND rol = ?");
			params.add(rol.getDbValue());
		}
		if (estado != null) {
			sql.append(" AND estado = ?");
			params.add(estado.getDbValue());
		}
		if (fechaDesde != null) {
			sql.append(" AND fecha_ingreso >= ?");
			params.add(fechaDesde);
		}
		if (fechaHasta != null) {
			sql.append(" AND fecha_ingreso <= ?");
			params.add(fechaHasta);
		}

		sql.append(" ORDER BY id_empleado DESC");
		return jdbcTemplate.query(sql.toString(), rowMapper, params.toArray());
	}

	public Optional<Empleado> findById(Integer id) {
		List<Empleado> result = jdbcTemplate.query(
			"SELECT id_empleado, nombre, rol, fecha_ingreso, estado FROM empleado WHERE id_empleado = ?",
			rowMapper,
			id
		);
		return result.stream().findFirst();
	}

	public boolean existsById(Integer id) {
		Integer count = jdbcTemplate.queryForObject(
			"SELECT COUNT(1) FROM empleado WHERE id_empleado = ?",
			Integer.class,
			id
		);
		return count != null && count > 0;
	}

	public Empleado save(Empleado empleado) {
		if (empleado.getIdEmpleado() == null) {
			Integer id = jdbcTemplate.queryForObject(
				"INSERT INTO empleado (nombre, rol, fecha_ingreso, estado) VALUES (?, ?, ?, ?) RETURNING id_empleado",
				Integer.class,
				empleado.getNombre(),
				empleado.getRol().getDbValue(),
				empleado.getFechaIngreso(),
				empleado.getEstado().getDbValue()
			);
			empleado.setIdEmpleado(id);
			return empleado;
		}

		jdbcTemplate.update(
			"UPDATE empleado SET nombre = ?, rol = ?, fecha_ingreso = ?, estado = ? WHERE id_empleado = ?",
			empleado.getNombre(),
			empleado.getRol().getDbValue(),
			empleado.getFechaIngreso(),
			empleado.getEstado().getDbValue(),
			empleado.getIdEmpleado()
		);
		return empleado;
	}

	public void deleteById(Integer id) {
		jdbcTemplate.update("DELETE FROM empleado WHERE id_empleado = ?", id);
	}
}
