package br.unitins.tp1.resource;

import java.util.List;

import br.unitins.tp1.dto.CategoriaDTO;
import br.unitins.tp1.dto.CategoriaDTOResponse;
import br.unitins.tp1.service.CategoriaService;
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

@Path("/categorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriaResource {

    @Inject
    CategoriaService service;

    @GET
    public List<CategoriaDTOResponse> getAll() {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public CategoriaDTOResponse findById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @GET
    @Path("/search/{nome}")
    public List<CategoriaDTOResponse> findByNome(@PathParam("nome") String nome) {
        return service.findByNome(nome);
    }

    @POST
    @Transactional
    public CategoriaDTOResponse create(CategoriaDTO dto) {
        return service.create(dto);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public CategoriaDTOResponse update(@PathParam("id") Long id, CategoriaDTO dto) {
        return service.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        service.delete(id);
    }
}
