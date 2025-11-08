package com.controleDeAcesso.service;

import com.controleDeAcesso.dao.PessoaDAO;
import com.controleDeAcesso.dto.PessoaDTO;
import com.controleDeAcesso.model.Pessoa;
import com.controleDeAcesso.util.TipoPessoa;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    private PessoaDAO pessoaDAO;

    public PessoaService(){
        this.pessoaDAO = new PessoaDAO();
    }

    public static void validarPessoaDTO(PessoaDTO pessoaDTO){
        if(pessoaDTO.getNome() == null )
            throw new IllegalArgumentException("Nome de pessoa inválido");

        if(pessoaDTO.getNome().length() > 60)
            throw new IllegalArgumentException("Nome de pessoa maior que o permitido (60 caracteres)");

        if(pessoaDTO.getCpf() == null || pessoaDTO.getCpf().length() != 11)
            throw new IllegalArgumentException("CPF inválido");

        if(pessoaDTO.getDataNasc() == null)
            throw new IllegalArgumentException("Data de nascimento inválida");

        if(pessoaDTO.getEmail().length() > 100)
            throw new IllegalArgumentException("Email maior que o permitido (100 caracteres)");

    }

    private PessoaDTO toDTO(Pessoa pessoa) {
        PessoaDTO dto = new PessoaDTO();

        dto.setId(pessoa.getId());
        dto.setNome(pessoa.getNome());
        dto.setCpf(pessoa.getCpf());
        dto.setDataNasc(pessoa.getDataNasc());
        dto.setTelefone(pessoa.getTelefone());
        dto.setEmail(pessoa.getEmail());
        dto.setTipo(pessoa.getTipo());
        dto.setAtiva(pessoa.isAtiva());

        return dto;
    }

    public List<PessoaDTO> consultarPessoasGeral() {

        try {
            return pessoaDAO.consultarPessoasGeral()
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar pessoas geral no banco de dados", e);
        }
    }

    public List<PessoaDTO> consultarPessoasPorTipo(TipoPessoa tipoPessoa){

        try{
            return pessoaDAO.consultarPessoasPorTipo(tipoPessoa)
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar pessoas por tipo no banco de dados", e);
        }
    }

    public List<PessoaDTO> consultarNomesMoradores(){

        try{
            return pessoaDAO.consultarNomesMoradores()
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar nomes de moradores no banco de dados",e);
        }

    }


    public PessoaDTO consultarPessoaCpf(String cpf) {

        try {
            return toDTO(pessoaDAO.consultarPessoaCpf(cpf));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar pessoa por cpf no banco de dados", e);
        }
    }

    @Deprecated
    public PessoaDTO consultarPessoaNome(String nome) {

        try {
            return toDTO(pessoaDAO.consultarPessoaNome(nome));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar pessoa por nome no banco de dados", e);
        }
    }

    public boolean reincluirPessoa(int pessoa_id) {
        try {
            return pessoaDAO.reincluirPessoa(pessoa_id);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao reincluir pessoa no banco de dados", e);
        }
    }
}


