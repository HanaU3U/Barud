package com.barud.service;

import com.barud.dto.request.EmpleadoRequestDto;
import com.barud.model.Empleado;
import com.barud.model.enums.EmpleadoEstado;
import com.barud.model.enums.EmpleadoRol;
import com.barud.repository.EmpleadoRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public List<Empleado> listarConFiltros(
        String nombre,
        EmpleadoRol rol,
        EmpleadoEstado estado,
        LocalDate fechaDesde,
        LocalDate fechaHasta
    ) {
        return empleadoRepository.findByFilters(nombre, rol, estado, fechaDesde, fechaHasta);
    }

    public Optional<Empleado> obtenerPorId(Integer id) {
        return empleadoRepository.findById(id);
    }

    public Empleado crear(EmpleadoRequestDto dto) {
        Empleado empleado = new Empleado(null, dto.nombre(), dto.rol(), dto.fechaIngreso(), dto.estado());
        return empleadoRepository.save(empleado);
    }

    public Optional<Empleado> actualizar(Integer id, EmpleadoRequestDto dto) {
        if (!empleadoRepository.existsById(id)) {
            return Optional.empty();
        }
        Empleado empleado = new Empleado(id, dto.nombre(), dto.rol(), dto.fechaIngreso(), dto.estado());
        return Optional.of(empleadoRepository.save(empleado));
    }

    public boolean eliminar(Integer id) {
        if (!empleadoRepository.existsById(id)) {
            return false;
        }
        empleadoRepository.deleteById(id);
        return true;
    }
}
