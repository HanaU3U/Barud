package com.barud.service;

import com.barud.dto.request.PagoRequestDto;
import com.barud.model.DetallePedido;
import com.barud.model.Mesa;
import com.barud.model.Pago;
import com.barud.model.enums.DetallePedidoEstado;
import com.barud.model.enums.MesaEstado;
import com.barud.repository.CuentaRepository;
import com.barud.repository.DetallePedidoRepository;
import com.barud.repository.MesaRepository;
import com.barud.repository.PagoRepository;
import com.barud.repository.PedidoRepository;
import com.barud.repository.ProductoRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final CuentaRepository cuentaRepository;
    private final PedidoRepository pedidoRepository;
    private final MesaRepository mesaRepository;
    private final DetallePedidoRepository detallePedidoRepository;
    private final ProductoRepository productoRepository;

    public PagoService(PagoRepository pagoRepository, CuentaRepository cuentaRepository,
            PedidoRepository pedidoRepository, MesaRepository mesaRepository,
            DetallePedidoRepository detallePedidoRepository, ProductoRepository productoRepository) {
        this.pagoRepository = pagoRepository;
        this.cuentaRepository = cuentaRepository;
        this.pedidoRepository = pedidoRepository;
        this.mesaRepository = mesaRepository;
        this.detallePedidoRepository = detallePedidoRepository;
        this.productoRepository = productoRepository;
    }

    public List<Pago> listarTodos() {
        return StreamSupport.stream(pagoRepository.findAll().spliterator(), false).toList();
    }

    public Optional<Pago> obtenerPorId(Integer id) {
        return pagoRepository.findById(id);
    }

    @Transactional
    public Pago crear(PagoRequestDto dto) {
        Pago pagoPersistido = pagoRepository.save(new Pago(null, dto.idCuenta(), dto.metodo(), dto.monto(), dto.fecha()));

        cuentaRepository.findById(dto.idCuenta()).ifPresent(cuenta ->
            pedidoRepository.findById(cuenta.getIdPedido()).ifPresent(pedido -> {
                mesaRepository.findById(pedido.getIdMesa()).ifPresent(mesa -> {
                    Mesa mesaActualizada = new Mesa(mesa.getIdMesa(), mesa.getNumero(), mesa.getCapacidad(), MesaEstado.DISPONIBLE);
                    mesaRepository.save(mesaActualizada);
                });

                List<DetallePedido> detalles = detallePedidoRepository.findByIdPedido(pedido.getIdPedido());
                detallePedidoRepository.updateEstadoByIdPedido(pedido.getIdPedido(), DetallePedidoEstado.CANCELADO);
                detalles.forEach(d -> productoRepository.decrementarStock(d.getIdProducto(), d.getCantidad()));
            })
        );

        return pagoPersistido;
    }

    public Optional<Pago> actualizar(Integer id, PagoRequestDto dto) {
        if (!pagoRepository.existsById(id)) {
            return Optional.empty();
        }
        Pago pago = new Pago(id, dto.idCuenta(), dto.metodo(), dto.monto(), dto.fecha());
        return Optional.of(pagoRepository.save(pago));
    }

    public boolean eliminar(Integer id) {
        if (!pagoRepository.existsById(id)) {
            return false;
        }
        pagoRepository.deleteById(id);
        return true;
    }
}
