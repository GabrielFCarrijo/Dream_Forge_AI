package infrastructure.resources;

import infrastructure.repositories.dto.EmailDTO;
import infrastructure.repositories.dto.UsuarioDTO;
import infrastructure.service.UsuarioService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService usuarioService;

    @POST
    @Path("/cadastrar")
    public Response cadastrarUsuario(UsuarioDTO usuarioDTO) {
        try {
            usuarioService.cadastrarUsuario(usuarioDTO.getLogin(), usuarioDTO.getSenha(), usuarioDTO.getEmail());
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/login")
    public Response validarLogin(UsuarioDTO usuarioDTO) {
        boolean isValid = usuarioService.validarLogin(usuarioDTO.getLogin(), usuarioDTO.getSenha());
        return Response.ok(isValid).build();
    }

    @POST
    @Path("/recuperar-senha")
    public Response recuperarSenha(UsuarioDTO usuarioDTO) {
        try {
            usuarioService.enviarEmailRecuperacaoSenha(usuarioDTO.getLogin());
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
