package com.controleDeAcesso.service;

import com.controleDeAcesso.dao.CasaDAO;
import com.controleDeAcesso.view.CasaView;
import com.controleDeAcesso.dto.CasaDTO;
import com.controleDeAcesso.model.Casa;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CasaService {

    private CasaDAO casaDAO;

    public CasaService(){
        this.casaDAO = new CasaDAO();
    }

    private CasaDTO toDTO(Casa casa){
        CasaDTO dto = new CasaDTO();

        dto.setId(casa.getId());
        dto.setEndereco(casa.getEndereco());

        return dto;
    }

    public void validarCasaDTO(CasaDTO casaDTO){
        if(casaDTO.getEndereco().isEmpty() || casaDTO.getEndereco() == null)
            throw new IllegalArgumentException("Endereço de casa inválido");
    }

    public int incluirCasa(CasaDTO casaDTO){
        validarCasaDTO(casaDTO);

        Casa casa = new Casa(
                casaDTO.getEndereco()
        );

        try{
            return casaDAO.incluirCasa(casa);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao incluir casa no banco de dados", e);
        }
    }

    public boolean atualizarCasa(CasaDTO casaDTO){
        validarCasaDTO(casaDTO);

        Casa casa = new Casa(
                casaDTO.getEndereco()
        );

        try{
            return casaDAO.atualizarCasa(casa);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar casa no banco de dados", e);
        }

    }

    public CasaDTO consultarCasa(int casa_id){
        try{
            return toDTO(casaDAO.consultarCasa(casa_id));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar casa no banco de dados", e);
        }
    }

    public boolean deletarCasa(int casa_id){
        try{
            return casaDAO.deletarCasa(casa_id);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar casa no banco de dados", e);
        }
    }

    public List<CasaDTO> consultarCasasGeral(){

        try{
            return casaDAO.consultarCasasGeral()
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException("erro ao consultar casas no banco de dados", e);
        }
    }

    public CasaView consultarCasasView(int casa_id){
        try{
            return casaDAO.consultarCasaView(casa_id);
        } catch (SQLException e) {
            throw new RuntimeException("erro ao consultar casas no banco de dados", e);
        }
    }

    public List<CasaView> consultarCasasView(){
        try{
            return casaDAO.consultarCasasViewGeral();
        } catch (SQLException e) {
            throw new RuntimeException("erro ao consultar casas view no banco de dados", e);
        }
    }

    public List<CasaDTO> consultarNumerosCasas(){
        try{
            return casaDAO.consultarNumerosCasas()
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        }catch (SQLException e) {
            throw new RuntimeException("erro ao consultar números de casas no banco de dados", e);
        }
    }

}
