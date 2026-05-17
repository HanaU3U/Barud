package com.barud.service;

import com.barud.dto.response.BebidaAlcoholicaMasVendidaViewDto;
import com.barud.dto.response.ComidaNoPedidaViewDto;
import com.barud.dto.response.CuentaMesaViewDto;
import com.barud.dto.response.DetalleCuentaMesaViewDto;
import com.barud.dto.response.DetallePedidoCompletoViewDto;
import com.barud.dto.response.DetallePedidoMeseroViewDto;
import com.barud.dto.response.DivisionCuentaMesaViewDto;
import com.barud.dto.response.IngresosDiaSemanaViewDto;
import com.barud.dto.response.PedidosMeseroViewDto;
import com.barud.dto.response.PedidosPorDiaViewDto;
import com.barud.dto.response.ProductoDisponibleViewDto;
import com.barud.dto.response.ProductosMasVendidosViewDto;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class VistaQueryService {

    private final JdbcTemplate jdbcTemplate;

    public VistaQueryService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<DetalleCuentaMesaViewDto> detalleCuentaMesa() {
        return jdbcTemplate.query(
            "SELECT id_cuenta, numero_mesa, resumen_productos, subtotal, impuestos, total, fecha, cuenta_dividida FROM v_detalle_cuenta_mesa",
            (rs, rowNum) -> new DetalleCuentaMesaViewDto(
                rs.getInt("id_cuenta"),
                rs.getInt("numero_mesa"),
                rs.getString("resumen_productos"),
                rs.getBigDecimal("subtotal"),
                rs.getBigDecimal("impuestos"),
                rs.getBigDecimal("total"),
                toLocalDateTime(rs.getObject("fecha")),
                rs.getString("cuenta_dividida")
            )
        );
    }

    public List<IngresosDiaSemanaViewDto> ingresosDiaSemana() {
        return jdbcTemplate.query(
            "SELECT dia_semana, total_pagos, ingresos_totales, promedio_pago FROM v_ingresos_dia_semana",
            (rs, rowNum) -> new IngresosDiaSemanaViewDto(
                rs.getString("dia_semana"),
                toLong(rs.getObject("total_pagos")),
                toBigDecimal(rs.getObject("ingresos_totales")),
                toBigDecimal(rs.getObject("promedio_pago"))
            )
        );
    }

    public List<ProductosMasVendidosViewDto> productosMasVendidos() {
        return jdbcTemplate.query(
            "SELECT nombre, tipo, total_vendido, ingresos FROM v_productos_mas_vendidos",
            (rs, rowNum) -> new ProductosMasVendidosViewDto(
                rs.getString("nombre"),
                rs.getString("tipo"),
                toLong(rs.getObject("total_vendido")),
                toBigDecimal(rs.getObject("ingresos"))
            )
        );
    }

    public List<PedidosPorDiaViewDto> pedidosPorDia() {
        return jdbcTemplate.query(
            "SELECT fecha, total_pedidos, ingresos FROM v_pedidos_por_dia",
            (rs, rowNum) -> new PedidosPorDiaViewDto(
                toLocalDate(rs.getObject("fecha")),
                toLong(rs.getObject("total_pedidos")),
                toBigDecimal(rs.getObject("ingresos"))
            )
        );
    }

    public List<ProductoDisponibleViewDto> productosDisponibles() {
        return jdbcTemplate.query(
            "SELECT id_producto, nombre, tipo, precio, stock FROM v_productos_disponibles",
            (rs, rowNum) -> new ProductoDisponibleViewDto(
                rs.getInt("id_producto"),
                rs.getString("nombre"),
                rs.getString("tipo"),
                rs.getBigDecimal("precio"),
                rs.getInt("stock")
            )
        );
    }

    public List<DetallePedidoCompletoViewDto> detallePedidoCompleto() {
        return jdbcTemplate.query(
            "SELECT id_pedido, mesa, producto, cantidad, precio_unitario, subtotal, estado FROM v_detalle_pedido_completo",
            (rs, rowNum) -> new DetallePedidoCompletoViewDto(
                rs.getInt("id_pedido"),
                rs.getInt("mesa"),
                rs.getString("producto"),
                rs.getInt("cantidad"),
                rs.getBigDecimal("precio_unitario"),
                rs.getBigDecimal("subtotal"),
                rs.getString("estado")
            )
        );
    }

    public List<PedidosMeseroViewDto> pedidosActivos() {
        return jdbcTemplate.query(
            "SELECT id_pedido, numero_mesa, mesero, fecha_hora, numero_personas, estado FROM v_pedidos_activos",
            (rs, rowNum) -> new PedidosMeseroViewDto(
                rs.getInt("id_pedido"),
                rs.getInt("numero_mesa"),
                rs.getString("mesero"),
                toLocalDateTime(rs.getObject("fecha_hora")),
                rs.getInt("numero_personas"),
                rs.getString("estado")
            )
        );
    }

    public List<PedidosMeseroViewDto> pedidosMesero() {
        return jdbcTemplate.query(
            "SELECT id_pedido, numero_mesa, mesero, fecha_hora, numero_personas, estado FROM v_pedidos_mesero",
            (rs, rowNum) -> new PedidosMeseroViewDto(
                rs.getInt("id_pedido"),
                rs.getInt("numero_mesa"),
                rs.getString("mesero"),
                toLocalDateTime(rs.getObject("fecha_hora")),
                rs.getInt("numero_personas"),
                rs.getString("estado")
            )
        );
    }

    public List<DetallePedidoMeseroViewDto> detallePedidoMesero() {
        return jdbcTemplate.query(
            "SELECT id_pedido, producto, cantidad, precio_unitario, subtotal FROM v_detalle_pedido_mesero",
            (rs, rowNum) -> new DetallePedidoMeseroViewDto(
                rs.getInt("id_pedido"),
                rs.getString("producto"),
                rs.getInt("cantidad"),
                rs.getBigDecimal("precio_unitario"),
                rs.getBigDecimal("subtotal")
            )
        );
    }

    public List<CuentaMesaViewDto> cuentaMesa() {
        return jdbcTemplate.query(
            "SELECT id_cuenta, numero_mesa, subtotal, impuestos, total, estado FROM v_cuenta_mesa",
            (rs, rowNum) -> new CuentaMesaViewDto(
                rs.getInt("id_cuenta"),
                rs.getInt("numero_mesa"),
                rs.getBigDecimal("subtotal"),
                rs.getBigDecimal("impuestos"),
                rs.getBigDecimal("total"),
                rs.getString("estado")
            )
        );
    }

    public List<DivisionCuentaMesaViewDto> divisionCuentaMesa() {
        return jdbcTemplate.query(
            "SELECT id_division, numero_mesa, descripcion, monto FROM v_division_cuenta_mesa",
            (rs, rowNum) -> new DivisionCuentaMesaViewDto(
                rs.getInt("id_division"),
                rs.getInt("numero_mesa"),
                rs.getString("descripcion"),
                rs.getBigDecimal("monto")
            )
        );
    }

    public List<BebidaAlcoholicaMasVendidaViewDto> bebidasAlcoholicasMasVendidas() {
        return jdbcTemplate.query(
            "SELECT p.nombre, SUM(dp.cantidad) AS total_vendido " +
            "FROM producto p " +
            "JOIN detalle_pedido dp ON dp.id_producto = p.id_producto " +
            "WHERE p.tipo = 'Bebida alcoholica' " +
            "GROUP BY p.id_producto, p.nombre " +
            "HAVING SUM(dp.cantidad) > ( " +
            "    SELECT AVG(dp2.cantidad) " +
            "    FROM detalle_pedido dp2 " +
            "    WHERE dp2.id_producto = p.id_producto " +
            ") " +
            "ORDER BY total_vendido ASC",
            (rs, rowNum) -> new BebidaAlcoholicaMasVendidaViewDto(
                rs.getString("nombre"),
                toLong(rs.getObject("total_vendido"))
            )
        );
    }

    public List<ComidaNoPedidaViewDto> comidasNoPedidas() {
        return jdbcTemplate.query(
            "SELECT p.nombre, p.precio " +
            "FROM producto p " +
            "WHERE p.tipo = 'Comida' " +
            "AND p.id_producto NOT IN ( " +
            "    SELECT dp.id_producto FROM detalle_pedido dp " +
            ") " +
            "ORDER BY p.precio ASC",
            (rs, rowNum) -> new ComidaNoPedidaViewDto(
                rs.getString("nombre"),
                rs.getBigDecimal("precio")
            )
        );
    }

    private static Long toLong(Object value) {
        if (value == null) {
            return 0L;
        }
        return ((Number) value).longValue();
    }

    private static BigDecimal toBigDecimal(Object value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        if (value instanceof BigDecimal decimalValue) {
            return decimalValue;
        }
        if (value instanceof Number numberValue) {
            return BigDecimal.valueOf(numberValue.doubleValue());
        }
        return new BigDecimal(value.toString());
    }

    private static LocalDate toLocalDate(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof LocalDate localDateValue) {
            return localDateValue;
        }
        if (value instanceof Date sqlDate) {
            return sqlDate.toLocalDate();
        }
        if (value instanceof Timestamp timestampValue) {
            return timestampValue.toLocalDateTime().toLocalDate();
        }
        return LocalDate.parse(value.toString());
    }

    private static LocalDateTime toLocalDateTime(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof LocalDateTime localDateTimeValue) {
            return localDateTimeValue;
        }
        if (value instanceof Timestamp timestampValue) {
            return timestampValue.toLocalDateTime();
        }
        return LocalDateTime.parse(value.toString().replace(" ", "T"));
    }
}
