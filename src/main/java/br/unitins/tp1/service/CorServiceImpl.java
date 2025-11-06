package br.unitins.tp1.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.CorDTO;
import br.unitins.tp1.dto.CorDTOResponse;
import br.unitins.tp1.model.Cor;
import br.unitins.tp1.repository.CorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CorServiceImpl implements CorService {

    @Inject
    CorRepository repository;

    @Override
    @Transactional
    public CorDTOResponse insert(CorDTO dto) {
        Cor cor = new Cor(dto.nome(), dto.descricao());
        repository.persist(cor);
        return CorDTOResponse.valueOf(cor);
    }

    @Override
    @Transactional
    public CorDTOResponse update(Long id, CorDTO dto) {
        Cor cor = repository.findById(id);
        if (cor == null)
            return null;  // ✅ NÃO LANÇA EXCEÇÃO

        cor.setNome(dto.nome());
        cor.setDescricao(dto.descricao());
        return CorDTOResponse.valueOf(cor);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Cor cor = repository.findById(id);
        if (cor != null) {
            repository.delete(cor);
        }
    }

    @Override
    public CorDTOResponse findById(Long id) {
        Cor cor = repository.findById(id);
        if (cor == null)
            return null; // ✅ NÃO LANÇA EXCEÇÃO

        return CorDTOResponse.valueOf(cor);
    }

    @Override
    public List<CorDTOResponse> findAll() {
        return repository.listAll()
                         .stream()
                         .map(CorDTOResponse::valueOf)
                         .collect(Collectors.toList());
    }
}
