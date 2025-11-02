package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.MarcaDTO;
import br.unitins.tp1.dto.MarcaDTOResponse;

public interface MarcaService {
    List<MarcaDTOResponse> getAll();
    MarcaDTOResponse findById(Long id);
    List<MarcaDTOResponse> findByNome(String nome);
    MarcaDTOResponse create(MarcaDTO dto);
    MarcaDTOResponse update(Long id, MarcaDTO dto);
    void delete(Long id);
}
