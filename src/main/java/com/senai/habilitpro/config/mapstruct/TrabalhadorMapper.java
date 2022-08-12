package com.senai.habilitpro.config.mapstruct;

import com.senai.habilitpro.model.dto.EmpresaDTO;
import com.senai.habilitpro.model.dto.TrabalhadorDTO;
import com.senai.habilitpro.model.dto.request.TrabalhadorCreateDTO;
import com.senai.habilitpro.model.dto.request.TrabalhadorUpdateDTO;
import com.senai.habilitpro.model.dto.response.*;
import com.senai.habilitpro.model.entity.Curso;
import com.senai.habilitpro.model.entity.Empresa;
import com.senai.habilitpro.model.entity.Nota;
import com.senai.habilitpro.model.entity.Trabalhador;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TrabalhadorMapper {
    TrabalhadorMapper INSTANCE = Mappers.getMapper(TrabalhadorMapper.class);

    TrabalhadorCompleteResponseDTO fromEntityToCompleteResponse(Trabalhador trabalhador);

    Trabalhador fromCompleteResponseToEntity(TrabalhadorCompleteResponseDTO trabalhadorResponseDTO);

    TrabalhadorSimpleResponseDTO fromEntityToSimpleResponse(Trabalhador trabalhador);

    Trabalhador fromSimpleResponseToEntity(TrabalhadorSimpleResponseDTO trabalhadorResponseDTO);

    @Mapping(source = "idEmpresa", target = "empresa", qualifiedByName = "fromIdEmpresaToEmpresaEntity")
    Trabalhador fromGenericDtoToEntity(TrabalhadorDTO trabalhadorDTO);

    @Mapping(source = "idEmpresa", target = "empresa", qualifiedByName = "fromIdEmpresaToEmpresaEntity")
    Trabalhador fromCreateDtoToEntity(TrabalhadorCreateDTO trabalhadorCreateDTO);

    @Mapping(source = "idEmpresa", target = "empresa", qualifiedByName = "fromIdEmpresaToEmpresaEntity")
    Trabalhador fromUpdateDtoToEntity(TrabalhadorUpdateDTO trabalhadorUpdateDTO);

    EmpresaDTO empresaToDto(Empresa empresa);
    EmpresaResponseDTO empresaToResponseDto(Empresa empresa);
    Empresa fromDtoToEntity(EmpresaDTO empresaDTO);
    Empresa fromResponseDtoToEntity(EmpresaResponseDTO empresaResponseDTO);

    List<CursoResponseDTO> fromCursoListToCursoResponseDtoList(List<Curso> cursoList);
    CursoResponseDTO fromCursoToCursoResponseDto(Curso curso);

    List<NotaResponseDTO> fromNotaListToNotaResponseDtoList(List<Nota> cursoList);
    NotaResponseDTO fromNotaToNotaResponseDto(Nota nota);

    @Named("fromIdEmpresaToEmpresaEntity")
    public static Empresa fromIdEmpresaToEmpresaEntity(Long idEmpresa) {
        Empresa empresa = new Empresa();
        empresa.setId(idEmpresa);
        return empresa;
    }
}
