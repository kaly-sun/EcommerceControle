package br.unitins.tp1.resource;

import br.unitins.tp1.dto.AuthDTO;
import br.unitins.tp1.dto.AuthResponseDTO;
import br.unitins.tp1.model.Usuario;
import br.unitins.tp1.service.HashService;
import br.unitins.tp1.service.JwtService;
import br.unitins.tp1.service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status; 

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    HashService hashService;

    @Inject
    JwtService jwtService;

    @Inject
    UsuarioService usuarioService;

    @POST
    public Response login(@Valid AuthDTO dto) {
        try {
         
            String hash = hashService.getHashSenha(dto.senha());

          
            Usuario usuario = usuarioService.findByLoginAndSenha(dto.login(), hash);

           
            if (usuario == null) {
    return Response.status(Status.UNAUTHORIZED)
                   .entity("Login ou senha inválidos.")
                   .build();
}


            
            String token = jwtService.generateJwt(usuario);

          
            AuthResponseDTO responseDTO = AuthResponseDTO.valueOf(usuario, token);

          
            return Response.ok(responseDTO)
                           .header("Authorization", "Bearer " + token)
                           .build();

        } catch (RuntimeException e) {
    return Response.status(Status.INTERNAL_SERVER_ERROR)
                   .entity("Erro ao processar autenticação.")
                   .build();
}

    }
}
