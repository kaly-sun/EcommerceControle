package br.unitins.tp1.resource;

import java.util.List;
import br.unitins.tp1.dto.ModeloDTO;
import br.unitins.tp1.dto.ModeloDTOResponse;
import br.unitins.tp1.service.ModeloService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/modelos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ModeloResource {

    @Inject
    ModeloService service;

    @GET
    public List<ModeloDTOResponse> listarTodos() {
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    public ModeloDTOResponse buscarPorId(@PathParam("id") Long id) {
        return service.buscarPorId(id);
    }

    @POST
    @Transactional
    public ModeloDTOResponse criar(ModeloDTO dto) {
        return service.criar(dto);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public ModeloDTOResponse atualizar(@PathParam("id") Long id, ModeloDTO dto) {
        return service.atualizar(id, dto);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void deletar(@PathParam("id") Long id) {
        service.deletar(id);
    }
}
