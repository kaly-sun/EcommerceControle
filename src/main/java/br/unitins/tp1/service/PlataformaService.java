package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.PlataformaDTO;
import br.unitins.tp1.dto.PlataformaDTOResponse;

public interface PlataformaService {

    List<PlataformaDTOResponse> getAll();

    PlataformaDTOResponse findById(Long id);

    List<PlataformaDTOResponse> findByNome(String nome);

    PlataformaDTOResponse create(PlataformaDTO dto);

    PlataformaDTOResponse update(Long id, PlataformaDTO dto);

    void delete(Long id);
}
