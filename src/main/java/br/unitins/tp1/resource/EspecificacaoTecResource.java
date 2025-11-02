package br.unitins.tp1.resource;

import java.util.List;

import br.unitins.tp1.dto.EspecificacaoTecDTO;
import br.unitins.tp1.dto.EspecificacaoTecDTOResponse;
import br.unitins.tp1.service.EspecificacaoTecService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/especificacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EspecificacaoTecResource {

    @Inject
    EspecificacaoTecService service;

    @GET
    public List<EspecificacaoTecDTOResponse> getAll() {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public EspecificacaoTecDTOResponse findById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @POST
    @Transactional
    public EspecificacaoTecDTOResponse create(EspecificacaoTecDTO dto) {
        return service.create(dto);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public EspecificacaoTecDTOResponse update(@PathParam("id") Long id, EspecificacaoTecDTO dto) {
        return service.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        service.delete(id);
    }
}
