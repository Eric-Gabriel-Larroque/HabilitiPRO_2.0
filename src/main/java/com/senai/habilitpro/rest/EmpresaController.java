package com.senai.habilitpro.rest;

import com.senai.habilitpro.config.mapstruct.EmpresaMapper;
import com.senai.habilitpro.model.dto.EmpresaDTO;
import com.senai.habilitpro.model.dto.EmpresaFiltroDTO;
import com.senai.habilitpro.model.dto.request.EmpresaCreateApiDTO;
import com.senai.habilitpro.model.dto.request.EmpresaUpdateDTO;
import com.senai.habilitpro.model.dto.response.EmpresaResponseDTO;
import com.senai.habilitpro.model.entity.Empresa;
import com.senai.habilitpro.security.AuthJwtFilter;
import com.senai.habilitpro.security.Authorize;
import com.senai.habilitpro.service.EmpresaService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/v1/empresa")
@Produces("application/json")
@Consumes("application/json")
public class EmpresaController {

    private static final Logger LOG = LogManager.getLogger(EmpresaController.class);

    @Inject
    private EmpresaService empresaService;


    @GET
    public Response getTodasEmpresas(@QueryParam("nome") String nomeEmpresa) {
        LOG.error("Iniciando busca de empresas...");
        EmpresaFiltroDTO empresaFiltroDTO = new EmpresaFiltroDTO();
        empresaFiltroDTO.setNome(nomeEmpresa);
        List<Empresa> todasEmpresas = empresaService.buscar(empresaFiltroDTO);
        List<EmpresaResponseDTO> empresas = todasEmpresas.stream().map(EmpresaMapper.INSTANCE::toApiResponse)
                .collect(Collectors.toList());
        return Response.ok(empresas).build();
    }

    @GET
    @Path("/{empresaId}")
    public Response getEmpresaById(@PathParam("empresaId") Long empresaId) {
        LOG.info(String.format("Iniciando busca de usuário com o id: %d...",empresaId));
        Empresa empresaModel = empresaService.buscarPorId(empresaId);
        EmpresaResponseDTO empresaResponse = EmpresaMapper.INSTANCE.toApiResponse(empresaModel);
        return Response.ok(empresaResponse).build();
    }

    @Authorize
    @DELETE
    @Path("/{empresaId}")
    public Response removeEmpresaById(@PathParam("empresaId") Long empresaId) {
        LOG.info(String.format("Iniciando deleção de empresa com o id %d...",empresaId));
        empresaService.deleteById(empresaId);
        return Response.noContent().build();
    }

    @Authorize
    @POST
    public Response cadastrar(@Valid EmpresaCreateApiDTO empresa) {
        LOG.info("Iniciando processo de cadastro de empresa...");
        EmpresaDTO empresaDTO = EmpresaMapper.INSTANCE.toPersistanceDTO(empresa);
        Empresa empresaCadastrada = empresaService.cadastrar(empresaDTO);
        EmpresaResponseDTO empresaResponse = EmpresaMapper.INSTANCE.toApiResponse(empresaCadastrada);
        return Response.created(null).entity(empresaResponse).build();
    }

    @Authorize
    @PATCH
    @Path("/{empresaId}")
    public Response atualizaEmpresaById(@PathParam("empresaId") Long empresaId, EmpresaUpdateDTO empresa) throws IllegalAccessException {
        LOG.info("Iniciando processo de atualização da empresa...");
        Empresa empresaAtualizada =
                empresaService.atualizaEmpresa(empresaId, EmpresaMapper.INSTANCE.fromPatchDtoToModel(empresa));
        EmpresaResponseDTO empresaResponse = EmpresaMapper.INSTANCE.toApiResponse(empresaAtualizada);
        return Response.ok(empresaResponse).build();
    }
}
