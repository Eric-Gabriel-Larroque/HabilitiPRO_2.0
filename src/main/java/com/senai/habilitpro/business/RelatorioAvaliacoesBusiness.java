package com.senai.habilitpro.business;

import com.senai.habilitpro.model.dto.NotaDTO;
import com.senai.habilitpro.model.entity.*;
import com.senai.habilitpro.model.repository.EmpresaRepository;
import org.modelmapper.ModelMapper;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.senai.habilitpro.utils.NotaUtils.*;

public class RelatorioAvaliacoesBusiness {
    @Inject
    private EmpresaRepository empresaRepository;

    public void imprimir() throws IOException {
        List<String> cabecalho = List.of("Nome Empresa", "Curso", "Curso ID", "Módulo", "Avaliação ID", "Qtd trabalhadores", "Nota média", "Nota mínima", "Nota máxima");
        List<List<String>> linhasArquivo = montaLinhasArquivo();

        String horaAtual = LocalDateTime.now().toString();
        List<List<String>> conteudoArquivo = new ArrayList<>();
        conteudoArquivo.add(cabecalho);
        conteudoArquivo.addAll(linhasArquivo);

        String nomeArquivo = "Relatorio_" + horaAtual + ".csv";

        FacesContext currentInstance = FacesContext.getCurrentInstance();
        ExternalContext response = currentInstance.getExternalContext();
        response.responseReset();
        response.setResponseContentType("text/csv");
        response.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nomeArquivo + "\"");
        OutputStream output = response.getResponseOutputStream();

        for (List<String> linha : conteudoArquivo) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String celula : linha) {
                stringBuilder.append(celula).append(";");
            }
            stringBuilder.append("\n");
            output.write(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
        }

        output.flush();
        output.close();
        currentInstance.renderResponse();
        currentInstance.responseComplete();
    }

    private List<List<String>> montaLinhasArquivo() {
        List<Empresa> todasEmpresas = this.empresaRepository.getTodasEmpresas();
        List<List<String>> data = new ArrayList<>();

        ModelMapper modelMapper = new ModelMapper();

        for (Empresa empresa : todasEmpresas) {
            for (Curso curso : empresa.getCursos()) {
                for (Modulo modulo : curso.getModulos()) {
                    List<String> row = new ArrayList<>();
                    Avaliacao avaliacao = modulo.getAvaliacao();
                    List<NotaDTO> notas = new ArrayList<>();
                    for (Nota nota : avaliacao.getNotas()) {
                        notas.add(modelMapper.map(nota, NotaDTO.class));
                    }
                    row.add(empresa.getNome());
                    row.add(curso.getNome());
                    row.add(String.valueOf(curso.getId()));
                    row.add(modulo.getNome());
                    row.add(String.valueOf(avaliacao.getId()));
                    row.add(String.valueOf(modulo.getCurso().getTrabalhadores().size()));
                    row.add(String.valueOf(calculaNotaMedia(notas)));
                    row.add(String.valueOf(calculaNotaMinima(notas)));
                    row.add(String.valueOf(calculaNotaMaxima(notas)));

                    data.add(row);
                }
            }
        }
        return data;
    }
}
