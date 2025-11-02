package br.unitins.tp1.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.CategoriaDTO;
import br.unitins.tp1.dto.CategoriaDTOResponse;
import br.unitins.tp1.model.Categoria;
import br.unitins.tp1.repository.CategoriaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CategoriaServiceImpl implements CategoriaService {

    @Inject
    CategoriaRepository repository;

    @Override
    public List<CategoriaDTOResponse> getAll() {
        return repository.listAll()
                .stream()
                .map(CategoriaDTOResponse::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaDTOResponse findById(Long id) {
        Categoria categoria = repository.findById(id);
        return (categoria != null) ? CategoriaDTOResponse.valueOf(categoria) : null;
    }

    @Override
    public List<CategoriaDTOResponse> findByNome(String nome) {
        return repository.findByNome(nome)
                .stream()
                .map(CategoriaDTOResponse::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoriaDTOResponse create(CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.nome());
        categoria.setDescricao(dto.descricao());

        repository.persist(categoria);
        return CategoriaDTOResponse.valueOf(categoria);
    }

    @Override
    @Transactional
    public CategoriaDTOResponse update(Long id, CategoriaDTO dto) {
        Categoria categoria = repository.findById(id);
        if (categoria != null) {
            categoria.setNome(dto.nome());
            categoria.setDescricao(dto.descricao());
        }
        return CategoriaDTOResponse.valueOf(categoria);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
