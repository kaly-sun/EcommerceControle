package br.unitins.tp1.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.PlataformaDTO;
import br.unitins.tp1.dto.PlataformaDTOResponse;
import br.unitins.tp1.model.Plataforma;
import br.unitins.tp1.repository.PlataformaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PlataformaServiceImpl implements PlataformaService {

    @Inject
    PlataformaRepository repository;

    @Override
    public List<PlataformaDTOResponse> getAll() {
        return repository.listAll().stream().map(PlataformaDTOResponse::valueOf).collect(Collectors.toList());
    }

    @Override
    public PlataformaDTOResponse findById(Long id) {
        Plataforma plataforma = repository.findById(id);
        if (plataforma == null)
            return null;
        return PlataformaDTOResponse.valueOf(plataforma);
    }

    @Override
    public List<PlataformaDTOResponse> findByNome(String nome) {
        return repository.findByNome(nome).stream().map(PlataformaDTOResponse::valueOf).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PlataformaDTOResponse create(PlataformaDTO dto) {
        Plataforma plataforma = new Plataforma();
        updateData(plataforma, dto);
        repository.persist(plataforma);
        return PlataformaDTOResponse.valueOf(plataforma);
    }

    @Override
    @Transactional
    public PlataformaDTOResponse update(Long id, PlataformaDTO dto) {
        Plataforma plataforma = repository.findById(id);
        if (plataforma == null)
            return null;
        updateData(plataforma, dto);
        return PlataformaDTOResponse.valueOf(plataforma);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private void updateData(Plataforma plataforma, PlataformaDTO dto) {
        plataforma.setNome(dto.nome());
        plataforma.setFabricante(dto.fabricante());
        plataforma.setGeracao(dto.geracao());
        plataforma.setAnoLancamento(dto.anoLancamento());
        plataforma.setTipo(dto.tipo());
    }
}
