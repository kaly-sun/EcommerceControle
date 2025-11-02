package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.CategoriaDTO;
import br.unitins.tp1.dto.CategoriaDTOResponse;

public interface CategoriaService {
    List<CategoriaDTOResponse> getAll();
    CategoriaDTOResponse findById(Long id);
    List<CategoriaDTOResponse> findByNome(String nome);
    CategoriaDTOResponse create(CategoriaDTO dto);
    CategoriaDTOResponse update(Long id, CategoriaDTO dto);
    void delete(Long id);
}
