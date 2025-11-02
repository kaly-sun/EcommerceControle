package br.unitins.tp1.resource;

import java.util.List;

import br.unitins.tp1.dto.PlataformaDTO;
import br.unitins.tp1.dto.PlataformaDTOResponse;
import br.unitins.tp1.service.PlataformaService;
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
import jakarta.ws.rs.core.Response;

@Path("/plataformas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlataformaResource {

    @Inject
    PlataformaService service;

    @GET
    public List<PlataformaDTOResponse> getAll() {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        PlataformaDTOResponse response = service.findById(id);
        if (response == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(response).build();
    }

    @GET
    @Path("/search/{nome}")
    public List<PlataformaDTOResponse> findByNome(@PathParam("nome") String nome) {
        return service.findByNome(nome);
    }

    @POST
    public Response create(PlataformaDTO dto) {
        return Response.status(Response.Status.CREATED).entity(service.create(dto)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, PlataformaDTO dto) {
        PlataformaDTOResponse response = service.update(id, dto);
        if (response == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
