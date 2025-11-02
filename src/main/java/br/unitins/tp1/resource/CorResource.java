package br.unitins.tp1.resource;

import java.util.List;

import br.unitins.tp1.dto.CorDTO;
import br.unitins.tp1.dto.CorDTOResponse;
import br.unitins.tp1.service.CorService;
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

@Path("/cor")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CorResource {

    @Inject
    CorService service;

    @POST
    public CorDTOResponse insert(CorDTO dto) {
        return service.insert(dto);
    }

    @PUT
    @Path("/{id}")
    public CorDTOResponse update(@PathParam("id") Long id, CorDTO dto) {
        return service.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        service.delete(id);
    }

    @GET
    @Path("/{id}")
    public CorDTOResponse findById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @GET
    public List<CorDTOResponse> findAll() {
        return service.findAll();
    }
}
