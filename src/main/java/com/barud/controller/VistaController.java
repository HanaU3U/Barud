package com.barud.controller;

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
import com.barud.service.VistaQueryService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vistas")
public class VistaController {

    private final VistaQueryService vistaQueryService;

    public VistaController(VistaQueryService vistaQueryService) {
        this.vistaQueryService = vistaQueryService;
    }

    @GetMapping("/detalle-cuenta-mesa")
    public List<DetalleCuentaMesaViewDto> detalleCuentaMesa() {
        return vistaQueryService.detalleCuentaMesa();
    }

    @GetMapping("/ingresos-dia-semana")
    public List<IngresosDiaSemanaViewDto> ingresosDiaSemana() {
        return vistaQueryService.ingresosDiaSemana();
    }

    @GetMapping("/productos-mas-vendidos")
    public List<ProductosMasVendidosViewDto> productosMasVendidos() {
        return vistaQueryService.productosMasVendidos();
    }

    @GetMapping("/pedidos-por-dia")
    public List<PedidosPorDiaViewDto> pedidosPorDia() {
        return vistaQueryService.pedidosPorDia();
    }

    @GetMapping("/productos-disponibles")
    public List<ProductoDisponibleViewDto> productosDisponibles() {
        return vistaQueryService.productosDisponibles();
    }

    @GetMapping("/detalle-pedido-completo")
    public List<DetallePedidoCompletoViewDto> detallePedidoCompleto() {
        return vistaQueryService.detallePedidoCompleto();
    }

    @GetMapping("/pedidos-activos")
    public List<PedidosMeseroViewDto> pedidosActivos() {
        return vistaQueryService.pedidosActivos();
    }

    @GetMapping("/pedidos-mesero")
    public List<PedidosMeseroViewDto> pedidosMesero() {
        return vistaQueryService.pedidosMesero();
    }

    @GetMapping("/detalle-pedido-mesero")
    public List<DetallePedidoMeseroViewDto> detallePedidoMesero() {
        return vistaQueryService.detallePedidoMesero();
    }

    @GetMapping("/cuenta-mesa")
    public List<CuentaMesaViewDto> cuentaMesa() {
        return vistaQueryService.cuentaMesa();
    }

    @GetMapping("/division-cuenta-mesa")
    public List<DivisionCuentaMesaViewDto> divisionCuentaMesa() {
        return vistaQueryService.divisionCuentaMesa();
    }
}
