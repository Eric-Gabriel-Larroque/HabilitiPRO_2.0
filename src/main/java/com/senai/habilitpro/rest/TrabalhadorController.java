package com.senai.habilitpro.rest;

import com.senai.habilitpro.config.mapstruct.TrabalhadorMapper;
import com.senai.habilitpro.model.dto.TrabalhadorFiltroDTO;
import com.senai.habilitpro.model.dto.request.TrabalhadorCreateDTO;
import com.senai.habilitpro.model.dto.request.TrabalhadorUpdateDTO;
import com.senai.habilitpro.model.dto.response.TrabalhadorSimpleResponseDTO;
import com.senai.habilitpro.model.entity.Trabalhador;
import com.senai.habilitpro.security.Authorize;
import com.senai.habilitpro.service.TrabalhadorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/v1/trabalhador")
@Produces("application/json")
@Consumes("application/json")
public class TrabalhadorController {

    private static final Logger LOG = LogManager.getLogger(TrabalhadorController.class);

    @Inject
    private TrabalhadorService trabalhadorService;

    @GET
    public Response buscarTodos(@QueryParam("sorted") String ordem) {
        LOG.info("Iniciando busca de trabalhador...");
        TrabalhadorFiltroDTO filtro = new TrabalhadorFiltroDTO();
        filtro.setOrderBy(ordem);
        List<Trabalhador> trabalhadores = trabalhadorService.buscarComFiltro(filtro);
        List<TrabalhadorSimpleResponseDTO> trabalhadoresResponse = trabalhadores.stream()
                .map(TrabalhadorMapper.INSTANCE::fromEntityToSimpleResponse)
                .collect(Collectors.toList());
        return Response.ok(trabalhadoresResponse).build();
    }

    @GET
    @Path("/{trabalhadorId}")
    public Response buscarPorId(@PathParam("trabalhadorId") Long trabalhadorId) {
        LOG.info(String.format("Iniciando busca de trabalhador com o id %d...",trabalhadorId));
        Trabalhador trabalhador = trabalhadorService.buscarPorId(trabalhadorId);
        TrabalhadorSimpleResponseDTO trabalhadorResponse = TrabalhadorMapper.INSTANCE.fromEntityToSimpleResponse(trabalhador);
        return Response.ok(trabalhadorResponse).build();
    }

    @Authorize
    @DELETE
    @Path("/{trabalhadorId}")
    public Response removePorId(@PathParam("trabalhadorId") Long trabalhadorId) {
        LOG.info("Iniciando remoção de trabalhador...");
        trabalhadorService.removePorId(trabalhadorId);
        return Response.noContent().build();
    }

    @Authorize
    @POST
    public Response cadastrar(@Valid TrabalhadorCreateDTO trabalhadorCreateDTO) {
        LOG.info("Iniciando cadastro de trabalhador...");
        Trabalhador trabalhador = TrabalhadorMapper.INSTANCE.fromCreateDtoToEntity(trabalhadorCreateDTO);
        trabalhadorService.cadastrar(trabalhador);
        TrabalhadorSimpleResponseDTO trabalhadorResponse = TrabalhadorMapper.INSTANCE.fromEntityToSimpleResponse(trabalhador);
        return Response.created(null).entity(trabalhadorResponse).build();
    }

    @Authorize
    @PATCH
    @Path("/{trabalhadorId}")
    public Response atualizar(@PathParam("trabalhadorId") Long trabalhadorId, TrabalhadorUpdateDTO trabalhadorUpdateDTO) throws IllegalAccessException {
        LOG.info("Iniciando atualização de trabalhador...");
        Trabalhador trabalhador = TrabalhadorMapper.INSTANCE.fromUpdateDtoToEntity(trabalhadorUpdateDTO);
        trabalhador = trabalhadorService.atualizar(trabalhadorId, trabalhador);
        TrabalhadorSimpleResponseDTO trabalhadorResponse = TrabalhadorMapper.INSTANCE.fromEntityToSimpleResponse(trabalhador);
        return Response.ok(trabalhadorResponse).build();
    }
}
