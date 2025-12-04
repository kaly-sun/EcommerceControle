package br.unitins.tp1.resource;

import java.util.List;

import br.unitins.tp1.dto.EspecificacaoTecDTO;
import br.unitins.tp1.dto.EspecificacaoTecDTOResponse;
import br.unitins.tp1.service.EspecificacaoTecService;
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

@Path("/especificacoes")
@RolesAllowed({"ADMIN", "USER"})
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EspecificacaoTecResource {

    @Inject
    EspecificacaoTecService service;

    @GET
    public Response getAll() {
        List<EspecificacaoTecDTOResponse> list = service.getAll();
        return Response.ok(list).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        EspecificacaoTecDTOResponse dto = service.findById(id);

        if (dto == null)
            return Response.status(Response.Status.NOT_FOUND).build(); // ✅ TESTE EXIGE 404

        return Response.ok(dto).build();
    }

    @POST
    @Transactional
    public Response create(EspecificacaoTecDTO dto) {
        EspecificacaoTecDTOResponse response = service.create(dto);
        return Response.ok(response).build(); // ✅ TESTE ESPERA 200
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, EspecificacaoTecDTO dto) {
        EspecificacaoTecDTOResponse response = service.update(id, dto);

        if (response == null)
            return Response.status(Response.Status.NOT_FOUND).build(); // ✅ requerido

        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {

        EspecificacaoTecDTOResponse existente = service.findById(id);

        if (existente == null)
            return Response.status(Response.Status.NOT_FOUND).build(); // ✅ sem isso o teste falha

        service.delete(id);
        return Response.noContent().build(); // ✅ 204
    }
}
