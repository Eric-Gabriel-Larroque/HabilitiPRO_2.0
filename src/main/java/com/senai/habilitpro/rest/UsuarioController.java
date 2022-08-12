package com.senai.habilitpro.rest;

import com.senai.habilitpro.config.mapstruct.UsuarioMapper;
import com.senai.habilitpro.exception.RegistroExistenteException;
import com.senai.habilitpro.exception.UsuarioException;
import com.senai.habilitpro.model.dto.UsuarioDTO;
import com.senai.habilitpro.model.dto.UsuarioFiltroDTO;
import com.senai.habilitpro.model.dto.request.UsuarioRequestApiDTO;
import com.senai.habilitpro.model.dto.request.UsuarioUpdateRequestApiDTO;
import com.senai.habilitpro.model.dto.response.UsuarioResponseApiDTO;
import com.senai.habilitpro.model.entity.Usuario;
import com.senai.habilitpro.security.Authorize;
import com.senai.habilitpro.service.UsuarioService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/v1/usuario")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioController {

    @Inject
    private UsuarioService service;

    private static final Logger LOG = LogManager.getLogger(UsuarioController.class);

    @Authorize
    @GET
    public Response obterUsuarios() {
        LOG.info("Iniciando listagem de usuários...");
        List<UsuarioResponseApiDTO> usuariosEncontrados = service
                .obterUsuarios().stream().map(UsuarioMapper.INSTANCE::toApiResponse)
                .collect(Collectors.toList());
        LOG.info("Listagem finalizada.");
        return Response.ok().entity(usuariosEncontrados).build();
    }
    @Authorize
    @GET
    @Path("{id}")
    public Response obterUsuarioPorId(@PathParam("id") long id) {
        LOG.info(String.format("Iniciando busca de usuário com o id: %d...",id));
        UsuarioFiltroDTO filtro = new UsuarioFiltroDTO();
        filtro.setId(id);
        List<Usuario> usuarioEncontrado = service.filtrar(filtro);
        UsuarioResponseApiDTO resp = UsuarioMapper.INSTANCE.toApiResponse(usuarioEncontrado.get(0));
        return Response.ok().entity(resp).build();
    }

    @Authorize
    @POST
    public Response criarUsuario(@Valid UsuarioRequestApiDTO request) {
        try {
            LOG.info("Iniciando criação de Usuário...");
            UsuarioDTO usuarioDTO = UsuarioMapper.INSTANCE.toDTO(request);
            Usuario usuarioCriado = service.cadastrar(usuarioDTO);
            UsuarioResponseApiDTO resp = UsuarioMapper.INSTANCE.toApiResponse(usuarioCriado);
            return Response.status(Response.Status.CREATED).entity(resp).build();
        } catch (UsuarioException e) {
            LOG.error("Não foi possível criar usuário");
            throw new RegistroExistenteException(e.getErros());
        }
    }

    @Authorize
    @PUT
    @Path("{id}")
    public Response alterarUsuario(@PathParam("id") long id, @Valid UsuarioUpdateRequestApiDTO request) {
        try {
            LOG.info("Iniciando processo de atualização de usuário...");
            UsuarioDTO usuarioDTO = UsuarioMapper.INSTANCE.toDTO(request);
            usuarioDTO.setId(id);
            Usuario usuarioAlterado = service.alterar(usuarioDTO);
            UsuarioResponseApiDTO resp = UsuarioMapper.INSTANCE.toApiResponse(usuarioAlterado);
            return Response.ok().entity(resp).build();
        } catch (UsuarioException e) {
            LOG.error("Não foi possível atualizar usuário");
            throw new RegistroExistenteException(e.getErros());
        }
    }

    @Authorize
    @DELETE
    @Path("{id}")
    public Response removerUsuario(@PathParam("id") long id) {
        try {
            LOG.info("Iniciando processo de remoção de usuário...");
            service.removerUsuario(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (RuntimeException e) {
            throw new WebApplicationException("Erro ao tentar deletar Usuário - Usuário possui entidades dependentes",Response.Status.BAD_REQUEST);
        }
    }
}