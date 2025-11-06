package br.unitins.tp1.resource;

import java.util.List;

import br.unitins.tp1.dto.ModeloDTO;
import br.unitins.tp1.dto.ModeloDTOResponse;
import br.unitins.tp1.service.ModeloService;
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
import jakarta.ws.rs.core.Response;

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
    public Response buscarPorId(@PathParam("id") Long id) {
        ModeloDTOResponse response = service.buscarPorId(id);
        if (response == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(response).build();
    }

@POST
@Transactional
public Response criar(ModeloDTO dto) {
    ModeloDTOResponse response = service.criar(dto);
    return Response.ok(response).build(); // 200
}


    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizar(@PathParam("id") Long id, ModeloDTO dto) {
        ModeloDTOResponse response = service.atualizar(id, dto);
        if (response == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletar(@PathParam("id") Long id) {

        // verificar se existe antes de deletar
        ModeloDTOResponse response = service.buscarPorId(id);
        if (response == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        service.deletar(id);
        return Response.noContent().build();
    }
}
