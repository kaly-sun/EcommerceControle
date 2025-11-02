package br.unitins.tp1.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.dto.EspecificacaoTecDTO;
import br.unitins.tp1.dto.EspecificacaoTecDTOResponse;
import br.unitins.tp1.model.EspecificacaoTecnica;
import br.unitins.tp1.repository.EspecificacaoTecRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EspecificacaoTecServiceImpl implements EspecificacaoTecService {

    @Inject
    EspecificacaoTecRepository repository;

    @Override
    public List<EspecificacaoTecDTOResponse> getAll() {
        return repository.listAll()
                .stream()
                .map(EspecificacaoTecDTOResponse::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public EspecificacaoTecDTOResponse findById(Long id) {
        EspecificacaoTecnica e = repository.findById(id);
        return (e != null) ? EspecificacaoTecDTOResponse.valueOf(e) : null;
    }

    @Override
    @Transactional
    public EspecificacaoTecDTOResponse create(EspecificacaoTecDTO dto) {
        EspecificacaoTecnica e = new EspecificacaoTecnica();
        e.setPeso(dto.peso());
        e.setMaterial(dto.material());
        e.setAutonomiaBateria(dto.autonomiaBateria());
        e.setConectividade(dto.conectividade());
        e.setDimensoes(dto.dimensoes());
        e.setSensoresExtras(dto.sensoresExtras());

        repository.persist(e);
        return EspecificacaoTecDTOResponse.valueOf(e);
    }

    @Override
    @Transactional
    public EspecificacaoTecDTOResponse update(Long id, EspecificacaoTecDTO dto) {
        EspecificacaoTecnica e = repository.findById(id);
        if (e != null) {
            e.setPeso(dto.peso());
            e.setMaterial(dto.material());
            e.setAutonomiaBateria(dto.autonomiaBateria());
            e.setConectividade(dto.conectividade());
            e.setDimensoes(dto.dimensoes());
            e.setSensoresExtras(dto.sensoresExtras());
        }
        return EspecificacaoTecDTOResponse.valueOf(e);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
