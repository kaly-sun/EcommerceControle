package br.unitins.tp1.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.MarcaDTO;
import br.unitins.tp1.dto.MarcaDTOResponse;
import br.unitins.tp1.model.Marca;
import br.unitins.tp1.repository.MarcaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class MarcaServiceImpl implements MarcaService {

    @Inject
    MarcaRepository repository;

    @Override
    public List<MarcaDTOResponse> getAll() {
        return repository.listAll()
                .stream()
                .map(MarcaDTOResponse::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public MarcaDTOResponse findById(Long id) {
        Marca marca = repository.findById(id);
        return (marca != null) ? MarcaDTOResponse.valueOf(marca) : null;
    }

    @Override
    public List<MarcaDTOResponse> findByNome(String nome) {
        return repository.findByNome(nome)
                .stream()
                .map(MarcaDTOResponse::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MarcaDTOResponse create(MarcaDTO dto) {
        Marca marca = new Marca();
        marca.setNome(dto.nome());
        marca.setPaisOrigem(dto.paisOrigem());
        marca.setAnoFundacao(dto.anoFundacao());
        marca.setSiteOficial(dto.siteOficial());
        marca.setLogo(dto.logo());

        repository.persist(marca);
        return MarcaDTOResponse.valueOf(marca);
    }

    @Override
    @Transactional
    public MarcaDTOResponse update(Long id, MarcaDTO dto) {
        Marca marca = repository.findById(id);
        if (marca != null) {
            marca.setNome(dto.nome());
            marca.setPaisOrigem(dto.paisOrigem());
            marca.setAnoFundacao(dto.anoFundacao());
            marca.setSiteOficial(dto.siteOficial());
            marca.setLogo(dto.logo());
        }
        return MarcaDTOResponse.valueOf(marca);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
