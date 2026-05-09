package com.barud.dto;

import com.barud.dto.response.CuentaResponseDto;
import com.barud.dto.response.DetallePedidoResponseDto;
import com.barud.dto.response.DivisionCuentaResponseDto;
import com.barud.dto.response.EmpleadoResponseDto;
import com.barud.dto.response.MesaResponseDto;
import com.barud.dto.response.PagoResponseDto;
import com.barud.dto.response.PedidoResponseDto;
import com.barud.dto.response.ProductoResponseDto;
import com.barud.model.Cuenta;
import com.barud.model.DetallePedido;
import com.barud.model.DivisionCuenta;
import com.barud.model.Empleado;
import com.barud.model.Mesa;
import com.barud.model.Pago;
import com.barud.model.Pedido;
import com.barud.model.Producto;

public final class ResponseDtoMapper {

    private ResponseDtoMapper() {
    }

    public static MesaResponseDto toDto(Mesa mesa) {
        return new MesaResponseDto(mesa.getIdMesa(), mesa.getNumero(), mesa.getCapacidad(), mesa.getEstado());
    }

    public static EmpleadoResponseDto toDto(Empleado empleado) {
        return new EmpleadoResponseDto(
            empleado.getIdEmpleado(),
            empleado.getNombre(),
            empleado.getRol(),
            empleado.getFechaIngreso(),
            empleado.getEstado()
        );
    }

    public static ProductoResponseDto toDto(Producto producto) {
        return new ProductoResponseDto(
            producto.getIdProducto(),
            producto.getNombre(),
            producto.getTipo(),
            producto.getPrecio(),
            producto.getStock()
        );
    }

    public static PedidoResponseDto toDto(Pedido pedido) {
        return new PedidoResponseDto(
            pedido.getIdPedido(),
            pedido.getIdMesa(),
            pedido.getIdMesero(),
            pedido.getFechaHora(),
            pedido.getNumeroPersonas(),
            pedido.getEstado()
        );
    }

    public static DetallePedidoResponseDto toDto(DetallePedido detallePedido) {
        return new DetallePedidoResponseDto(
            detallePedido.getIdDetalle(),
            detallePedido.getIdPedido(),
            detallePedido.getIdProducto(),
            detallePedido.getCantidad(),
            detallePedido.getPrecioUnitario(),
            detallePedido.getEstado()
        );
    }

    public static CuentaResponseDto toDto(Cuenta cuenta) {
        return new CuentaResponseDto(
            cuenta.getIdCuenta(),
            cuenta.getIdPedido(),
            cuenta.getSubtotal(),
            cuenta.getImpuestos(),
            cuenta.getTotal(),
            cuenta.getEstado()
        );
    }

    public static DivisionCuentaResponseDto toDto(DivisionCuenta divisionCuenta) {
        return new DivisionCuentaResponseDto(
            divisionCuenta.getIdDivision(),
            divisionCuenta.getIdCuenta(),
            divisionCuenta.getDescripcion(),
            divisionCuenta.getMonto()
        );
    }

    public static PagoResponseDto toDto(Pago pago) {
        return new PagoResponseDto(
            pago.getIdPago(),
            pago.getIdCuenta(),
            pago.getMetodo(),
            pago.getMonto(),
            pago.getFecha()
        );
    }
}
