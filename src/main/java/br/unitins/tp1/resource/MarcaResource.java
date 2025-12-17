package br.unitins.tp1.resource;

import org.jboss.logging.Logger;

import br.unitins.tp1.dto.MarcaDTO;
import br.unitins.tp1.dto.MarcaDTOResponse;
import br.unitins.tp1.service.MarcaService;
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

@Path("/marcas")
@RolesAllowed({ "ADMIN", "USER" })
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MarcaResource {

    @Inject
    MarcaService service;

    private static final Logger LOG = Logger.getLogger(MarcaResource.class);

    @GET
    public Response listarTodos() {
        return Response.ok(service.listarTodos()).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        MarcaDTOResponse dto = service.buscarPorId(id);
        if (dto == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(dto).build();
    }

    @GET
    @Path("/search/{nome}")
    public Response buscarPorNome(@PathParam("nome") String nome) {
        return Response.ok(service.buscarPorNome(nome)).build();
    }

    @POST
    @Transactional
    public Response criar(MarcaDTO dto) {
        try {
            MarcaDTOResponse response = service.criar(dto);
            return Response.ok(response).build();
        } catch (Exception e) {
            LOG.error("Erro ao criar marca", e);
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizar(@PathParam("id") Long id, MarcaDTO dto) {
        MarcaDTOResponse response = service.atualizar(id, dto);
        if (response == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletar(@PathParam("id") Long id) {
        MarcaDTOResponse existente = service.buscarPorId(id);
        if (existente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        service.deletar(id);
        return Response.noContent().build();
    }
}
