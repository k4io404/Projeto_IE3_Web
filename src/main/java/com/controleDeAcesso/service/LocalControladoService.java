package com.controleDeAcesso.service;

import com.controleDeAcesso.dao.LocalControladoDAO;
import com.controleDeAcesso.dto.LocalControladoDTO;
import com.controleDeAcesso.model.LocalControlado;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocalControladoService {

    private LocalControladoDAO localControladoDAO;

    public LocalControladoService() {
        this.localControladoDAO = new LocalControladoDAO();
    }

    private LocalControladoDTO toDTO(LocalControlado localControlado){
        LocalControladoDTO dto = new LocalControladoDTO();

        dto.setId(localControlado.getId());
        dto.setNome(localControlado.getNome());
        dto.setDescricao(localControlado.getDescricao());

        return dto;
    }

    public void validarLocalControladoDTO(LocalControladoDTO localControladoDTO){

        if(localControladoDTO.getNome().isEmpty() || localControladoDTO.getNome() == null)
            throw new IllegalArgumentException("Nome do local controlado é inválido");
    }

    public int incluirLocalControlado(LocalControladoDTO localControladoDTO){

        validarLocalControladoDTO(localControladoDTO);

        LocalControlado localControlado = new LocalControlado(
                localControladoDTO.getNome(),
                localControladoDTO.getDescricao()
        );

        try {
            return localControladoDAO.incluirLocalControlado(localControlado);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao incluir local controlado no banco de dados", e);
        }
    }

    public boolean atualizarLocalControlado(LocalControladoDTO localControladoDTO){

        validarLocalControladoDTO(localControladoDTO);

        LocalControlado localControlado = new LocalControlado(
                localControladoDTO.getNome(),
                localControladoDTO.getDescricao()
        );

        try {
            return localControladoDAO.atualizarLocalControlado(localControlado);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar local controlado no banco de dados", e);
        }
    }

    public boolean deletarLocalControlado(int local_id){

        try {
            return localControladoDAO.deletarLocalControlado(local_id);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar local controlado no banco de dados", e);
        }
    }

    public LocalControladoDTO consultarLocalControlado(int local_id){

        try{
            return toDTO(localControladoDAO.consultarLocalControlado(local_id));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar local controlado no banco de dados",e);
        }
    }

    public List<LocalControladoDTO> consultarLocaisControladosGeral(){

        try {
            return localControladoDAO.consultarLocaisControladosGeral()
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar locais controlados no banco de dados",e);
        }
    }

    public List<LocalControladoDTO> consultarNomesLocais(){

        try {
            return localControladoDAO.consultarNomesLocais()
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar nomes dos locais controlados no banco de dados",e);
        }
    }


}
