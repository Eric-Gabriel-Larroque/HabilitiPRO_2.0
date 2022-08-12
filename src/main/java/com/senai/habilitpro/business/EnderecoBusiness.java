package com.senai.habilitpro.business;

import com.senai.habilitpro.exception.RegistroNaoEncontradoException;
import com.senai.habilitpro.model.dto.EnderecoDTO;
import com.senai.habilitpro.model.entity.Endereco;
import com.senai.habilitpro.model.repository.EnderecoRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class EnderecoBusiness {

    private static final Logger LOG = LogManager.getLogger(EnderecoBusiness.class);

    @Inject
    private EnderecoRepository enderecoRepository;

    public void cadastrar(EnderecoDTO endereco) {
        persistirEndereco(endereco);
        LOG.info("Dados de Endereço persistidos com sucesso!...");
    }

    private void persistirEndereco(EnderecoDTO enderecoDTO) {
        LOG.info("Persistindo dados de Endereço...");
        Endereco endereco = new Endereco();

        endereco.setNumero(enderecoDTO.getNumero());
        endereco.setRua(enderecoDTO.getRua());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setEstado(enderecoDTO.getEstado());
        endereco.setPais(enderecoDTO.getPais());

        enderecoRepository.persist(endereco);
        enderecoDTO.setId(endereco.getId());
    }

    public Endereco buscarPorId(Long enderecoId) {
        LOG.info("Verificando se existe  o mesmo endereço criado no banco...");
        Endereco endereco = enderecoRepository.find(Endereco.class, enderecoId);
        if (endereco == null) {
            throw new RegistroNaoEncontradoException("Endereço", enderecoId.toString());
        }
        return endereco;
    }

}
