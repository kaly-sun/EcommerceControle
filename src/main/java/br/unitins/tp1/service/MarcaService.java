package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.MarcaDTO;
import br.unitins.tp1.dto.MarcaDTOResponse;

public interface MarcaService {

    List<MarcaDTOResponse> listarTodos();

    MarcaDTOResponse buscarPorId(Long id);

    List<MarcaDTOResponse> buscarPorNome(String nome);

    MarcaDTOResponse criar(MarcaDTO dto);

    MarcaDTOResponse atualizar(Long id, MarcaDTO dto);

    void deletar(Long id);
}
