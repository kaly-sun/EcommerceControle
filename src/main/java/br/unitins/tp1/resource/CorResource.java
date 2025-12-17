package br.unitins.tp1.resource;

import java.util.List;

import br.unitins.tp1.dto.CorDTO;
import br.unitins.tp1.dto.CorDTOResponse;
import br.unitins.tp1.service.CorService;
import jakarta.annotation.security.RolesAllowed;
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

@Path("/cor")
@RolesAllowed({"ADMIN", "USER"})
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CorResource {

    @Inject
    CorService service;

    @POST
    @Transactional
    public Response insert(CorDTO dto) {
        CorDTOResponse response = service.insert(dto);
        return Response.ok(response).build(); // 200
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, CorDTO dto) {

        CorDTOResponse response = service.update(id, dto);

        if (response == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(response).build(); // 200
    }

@DELETE
@Path("/{id}")
@Transactional
public Response delete(@PathParam("id") Long id) {

    CorDTOResponse existente = service.findById(id);

    if (existente == null) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    service.delete(id);

    return Response.noContent().build();
}

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {

        CorDTOResponse dto = service.findById(id);

        if (dto == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(dto).build(); 
    }

    @GET
    public Response findAll() {
        List<CorDTOResponse> list = service.findAll();
        return Response.ok(list).build(); 
    }
}
