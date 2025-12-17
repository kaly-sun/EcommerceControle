package br.unitins.tp1.resource;

import java.util.List;

import br.unitins.tp1.dto.ControleDTO;
import br.unitins.tp1.dto.ControleDTOResponse;
import br.unitins.tp1.service.ControleService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
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

@Path("/controles")
@RolesAllowed({"ADMIN", "USER"})
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ControleResource {

    @Inject
    ControleService service;

    @POST
    public Response create(ControleDTO dto) {
        try {
            ControleDTOResponse response = service.create(dto);
            return Response.status(Response.Status.CREATED).entity(response).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (PersistenceException e) {
            return Response.status(Response.Status.CONFLICT).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, ControleDTO dto) {
        try {
            ControleDTOResponse response = service.update(id, dto);
            if (response == null)
                return Response.status(Response.Status.NOT_FOUND).build();
            return Response.ok(response).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (PersistenceException e) {
            return Response.status(Response.Status.CONFLICT).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            service.delete(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        ControleDTOResponse response = service.findById(id);
        if (response == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(response).build();
    }

    @GET
    public Response findAll() {
        List<ControleDTOResponse> list = service.findAll();
        return Response.ok(list).build();
    }

    @GET
    @Path("/search/nome/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        List<ControleDTOResponse> list = service.findByNome(nome);
        return Response.ok(list).build();
    }

    @GET
    @Path("/search/categoria/{nome}")
    public Response findByCategoria(@PathParam("nome") String nome) {
        List<ControleDTOResponse> list = service.findByCategoria(nome);
        return Response.ok(list).build();
    }
}
