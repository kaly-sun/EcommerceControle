package br.unitins.tp1.resource;

import java.util.List;

import br.unitins.tp1.dto.ControleDTO;
import br.unitins.tp1.dto.ControleDTOResponse;
import br.unitins.tp1.service.ControleService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/controles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ControleResource {

    @Inject
    ControleService service;

    @POST
    public ControleDTOResponse create(ControleDTO dto) {
        return service.create(dto);
    }

    @PUT
    @Path("/{id}")
    public ControleDTOResponse update(@PathParam("id") Long id, ControleDTO dto) {
        return service.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        service.delete(id);
    }

    @GET
    @Path("/{id}")
    public ControleDTOResponse findById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @GET
    public List<ControleDTOResponse> findAll() {
        return service.findAll();
    }

    @GET
    @Path("/search/{nome}")
    public List<ControleDTOResponse> findByNome(@PathParam("nome") String nome) {
        return service.findByNome(nome);
    }
}
