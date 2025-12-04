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
@RolesAllowed({"ADMIN", "USER"})
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MarcaResource {

    @Inject
    MarcaService service;

    private static final Logger LOG = Logger.getLogger(MarcaResource.class);

    @GET
    public Response getAll() {
        return Response.ok(service.getAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        MarcaDTOResponse dto = service.findById(id);
        if (dto == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(dto).build();
    }

    @GET
    @Path("/search/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        // ✅ sempre retorna 200, mesmo se a lista estiver vazia
        return Response.ok(service.findByNome(nome)).build();
    }

    @POST
    @Transactional
    public Response create(MarcaDTO dto) {
        try {
            MarcaDTOResponse response = service.create(dto);
            return Response.status(Response.Status.OK)
                           .entity(response)
                           .build();
        } catch (Exception e) {
            // 🔧 substituí o printStackTrace por log
            LOG.error("Erro ao criar marca: " + e.getMessage());
            return Response.serverError()
                    .entity("Erro ao criar marca: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, MarcaDTO dto) {
        MarcaDTOResponse response = service.update(id, dto);
        if (response == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        MarcaDTOResponse existente = service.findById(id);

        if (existente == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        service.delete(id);
        return Response.noContent().build(); // ✅ 204 sem corpo
    }
}
