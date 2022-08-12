package com.senai.habilitpro.config.mapstruct;

import com.senai.habilitpro.enumerator.RegionalSenaiEnum;
import com.senai.habilitpro.enumerator.SegmentoEmpresaEnum;
import com.senai.habilitpro.model.dto.EmpresaDTO;
import com.senai.habilitpro.model.dto.request.EmpresaCreateApiDTO;
import com.senai.habilitpro.model.dto.request.EmpresaUpdateDTO;
import com.senai.habilitpro.model.dto.response.EmpresaResponseDTO;
import com.senai.habilitpro.model.dto.response.EnderecoResponseApiDTO;
import com.senai.habilitpro.model.dto.response.UsuarioResponseEmpresaApiDTO;
import com.senai.habilitpro.model.entity.Empresa;
import com.senai.habilitpro.model.entity.Endereco;
import com.senai.habilitpro.model.entity.Usuario;
import com.senai.habilitpro.utils.Formatador;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmpresaMapper {
    EmpresaMapper INSTANCE = Mappers.getMapper(EmpresaMapper.class);

    @Mapping(source = "supervisor", target = "supervisor", qualifiedByName = "usuarioModelParaUsuarioDTO")
    @Mapping(source = "endereco", target = "endereco", qualifiedByName = "enderecoModelParaEnderecoDTO")
    @Mapping(source = "cnpj", target = "cnpj", qualifiedByName = "formataCnpj")
    EmpresaResponseDTO toApiResponse(Empresa empresaModel);

    @Mapping(source = "enderecoId", target = "idEndereco")
    @Mapping(source = "supervisorId", target = "idUsuario")
    @Mapping(source = "segmento", target = "segmento", qualifiedByName = "segmentoIntParaSegmentoEnum")
    @Mapping(source = "regionalSenai", target = "regionalSenai", qualifiedByName = "regionalIntParaRegionalEnum")
    EmpresaDTO toPersistanceDTO(EmpresaCreateApiDTO empresa);

    @Mapping(source = "segmento", target = "segmento", qualifiedByName = "segmentoIntParaSegmentoEnum")
    @Mapping(source = "regionalSenai", target = "regionalSenai", qualifiedByName = "regionalIntParaRegionalEnum")
    @Mapping(source = "supervisorId", target = "supervisor", qualifiedByName = "supervisorIdParaEntidadeSupervisor")
    @Mapping(source = "enderecoId", target = "endereco", qualifiedByName = "enderecoIdParaEntidadeEndereco")
    Empresa fromPatchDtoToModel(EmpresaUpdateDTO empresa);

    @Mapping(source = "idUsuario", target = "supervisor", qualifiedByName = "supervisorIdParaEntidadeSupervisor")
    @Mapping(source = "idEndereco", target = "endereco", qualifiedByName = "enderecoIdParaEntidadeEndereco")
    Empresa fromGenericDtoToModel(EmpresaDTO empresaDTO);

    EmpresaResponseDTO fromEntityToResponseDto(Empresa empresa);

    @Named("formataCnpj")
    static String formataCnpj(String cnpj) {
        return Formatador.cnpj(cnpj);
    }

    @Named("usuarioModelParaUsuarioDTO")
    static UsuarioResponseEmpresaApiDTO usuarioModelParaUsuarioDTO(Usuario usuarioModel) {
        return UsuarioMapper.INSTANCE.toEmpresaApiResponse(usuarioModel);
    }

    @Named("enderecoModelParaEnderecoDTO")
    static EnderecoResponseApiDTO enderecoModelParaEnderecoDTO(Endereco enderecoModel) {
        return EnderecoMapper.INSTANCE.toApiResponse(enderecoModel);
    }

    @Named("segmentoIntParaSegmentoEnum")
    static SegmentoEmpresaEnum segmentoIntParaSegmentoEnum(Integer segmento) {
        if (segmento == null) {
            return null;
        }
        return SegmentoEmpresaEnum.values()[segmento];
    }

    @Named("regionalIntParaRegionalEnum")
    static RegionalSenaiEnum regionalIntParaRegionalEnum(Integer regional) {
        if (regional == null) {
            return null;
        }
        return RegionalSenaiEnum.values()[regional];
    }

    @Named("supervisorIdParaEntidadeSupervisor")
    static Usuario supervisorIdParaEntidadeSupervisor(Long supervisorId) {
        Usuario usuario = new Usuario();
        usuario.setId(supervisorId);
        return usuario;
    }

    @Named("enderecoIdParaEntidadeEndereco")
    static Endereco enderecoIdParaEntidadeEndereco(Long enderecoId) {
        Endereco endereco = new Endereco();
        endereco.setId(enderecoId);
        return endereco;
    }

    public static Long empresaModelParaEmpresaId(Empresa empresa) {
        if(empresa==null) empresa = new Empresa();
        empresa.setId(0L);
        return empresa.getId();
    }
}
