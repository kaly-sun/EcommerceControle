package br.unitins.tp1.resource;

import java.util.List;

import br.unitins.tp1.dto.MarcaDTO;
import br.unitins.tp1.dto.MarcaDTOResponse;
import br.unitins.tp1.service.MarcaService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/marcas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MarcaResource {

    @Inject
    MarcaService service;

    @GET
    public List<MarcaDTOResponse> getAll() {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public MarcaDTOResponse findById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @GET
    @Path("/search/{nome}")
    public List<MarcaDTOResponse> findByNome(@PathParam("nome") String nome) {
        return service.findByNome(nome);
    }

    @POST
    @Transactional
    public MarcaDTOResponse create(MarcaDTO dto) {
        return service.create(dto);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public MarcaDTOResponse update(@PathParam("id") Long id, MarcaDTO dto) {
        return service.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        service.delete(id);
    }
}
