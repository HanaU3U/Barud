package com.barud.service;

import com.barud.dto.request.MesaRequestDto;
import com.barud.model.Mesa;
import com.barud.repository.MesaRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;

@Service
public class MesaService {

    private final MesaRepository mesaRepository;

    public MesaService(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    public List<Mesa> listarTodas() {
        return StreamSupport.stream(mesaRepository.findAll().spliterator(), false).toList();
    }

    public Optional<Mesa> obtenerPorId(Integer id) {
        return mesaRepository.findById(id);
    }

    public Mesa crear(MesaRequestDto dto) {
        Mesa mesa = new Mesa(null, dto.numero(), dto.capacidad(), dto.estado());
        return mesaRepository.save(mesa);
    }

    public Optional<Mesa> actualizar(Integer id, MesaRequestDto dto) {
        if (!mesaRepository.existsById(id)) {
            return Optional.empty();
        }
        Mesa mesa = new Mesa(id, dto.numero(), dto.capacidad(), dto.estado());
        return Optional.of(mesaRepository.save(mesa));
    }

    public boolean eliminar(Integer id) {
        if (!mesaRepository.existsById(id)) {
            return false;
        }
        mesaRepository.deleteById(id);
        return true;
    }
}
