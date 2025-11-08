package com.controleDeAcesso.service;

import com.controleDeAcesso.dao.VisitanteDAO;
import com.controleDeAcesso.dto.VisitanteDTO;
import com.controleDeAcesso.model.Prestador;
import com.controleDeAcesso.model.Visitante;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitanteService {

    VisitanteDAO visitanteDAO;

    public VisitanteService(){
        this.visitanteDAO = new VisitanteDAO();
    }

    private VisitanteDTO toDTO(Visitante visitante){
       VisitanteDTO dto = new VisitanteDTO();

       dto.setId(visitante.getId());
       dto.setMorCadastraId(visitante.getMorCadastraId());
       dto.setDataAutorizacao(visitante.getDataAutorizacao());

       return dto;
    }

    public int incluirPrestador(VisitanteDTO visitanteDTO){

        PessoaService.validarPessoaDTO(visitanteDTO);

        Visitante visitante = new Visitante();

        visitante.setNome(visitanteDTO.getNome());
        visitante.setCpf(visitanteDTO.getCpf());
        visitante.setDataNasc(visitanteDTO.getDataNasc());
        visitante.setTelefone(visitanteDTO.getTelefone());
        visitante.setEmail(visitanteDTO.getEmail());
        visitante.setAtiva(visitanteDTO.isAtiva());
        visitante.setTipo(visitanteDTO.getTipo());
        visitante.setMorCadastraId(visitanteDTO.getMorCadastraId());

        try{
            return visitanteDAO.incluirVisitante(visitante);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao incluir visitante no banco de dados", e);
        }
    }

    public boolean atualizarPrestador(VisitanteDTO visitanteDTO){

        PessoaService.validarPessoaDTO(visitanteDTO);

        Visitante visitante = new Visitante();

        visitante.setNome(visitanteDTO.getNome());
        visitante.setCpf(visitanteDTO.getCpf());
        visitante.setDataNasc(visitanteDTO.getDataNasc());
        visitante.setTelefone(visitanteDTO.getTelefone());
        visitante.setEmail(visitanteDTO.getEmail());
        visitante.setAtiva(visitanteDTO.isAtiva());
        visitante.setTipo(visitanteDTO.getTipo());
        visitante.setMorCadastraId(visitanteDTO.getMorCadastraId());

        try{
            return visitanteDAO.atualizarVisitante(visitante);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar visitante no banco de dados", e);
        }
    }

    public boolean deletarPrestador(int vis_id){

        try{
            return visitanteDAO.deletarVisitante(vis_id);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar visitante no banco de dados", e);
        }
    }

    public VisitanteDTO consultarPrestador(int vis_id){

        try{
            return toDTO(visitanteDAO.consultarVisitante(vis_id));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar visitante no banco de dados", e);
        }
    }

    public List<VisitanteDTO> consultarS(int mor_id){

        try{
            return visitanteDAO.consultarVisitantesPorMorador(mor_id)
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar visitantes por morador no banco de dados", e);
        }
    }

}
