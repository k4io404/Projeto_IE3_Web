package com.controleDeAcesso.service;

import com.controleDeAcesso.dao.VeiculoDAO;
import com.controleDeAcesso.dto.VeiculoDTO;
import com.controleDeAcesso.model.Veiculo;
import com.controleDeAcesso.util.TipoPessoa;
import com.controleDeAcesso.view.VeiculoView;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VeiculoService {

    private VeiculoDAO veiculoDAO;

    public VeiculoService(){
        this.veiculoDAO = new VeiculoDAO();
    }

    private VeiculoDTO toDTO(Veiculo veiculo){

        VeiculoDTO dto = new VeiculoDTO();

        dto.setPlaca(veiculo.getPlaca());
        dto.setPesId(veiculo.getPesId());
        dto.setCor(veiculo.getCor());
        dto.setModelo(veiculo.getModelo());

        return dto;
    }

    public void validarVeiculoDTO(VeiculoDTO veiculoDTO){
        String placa = veiculoDTO.getPlaca();
        String modelo = veiculoDTO.getModelo();
        String cor = veiculoDTO.getCor();

        if(placa == null || placa.length() != 7)
            throw new IllegalArgumentException("Placa do veículo inválida");
        if(modelo != null && modelo.length() > 40){
            throw new IllegalArgumentException("Modelo do veículo com quantidade de caracteres acima do permitido");
        }
        if(cor != null && cor.length() > 20){
            throw new IllegalArgumentException("Cor do veiculo com quantidade de caracteres acima do permito");
        }
    }

    public int incluirVeiculo(VeiculoDTO veiculoDTO){

        validarVeiculoDTO(veiculoDTO);

        Veiculo veiculo = new Veiculo(
                veiculoDTO.getPlaca(),
                veiculoDTO.getPesId(),
                veiculoDTO.getCor(),
                veiculoDTO.getModelo()
        );

        try{
            return veiculoDAO.incluirVeiculo(veiculo);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao incluir veículo no banco de dados", e);
        }
    }

    public boolean deletarVeiculo(String vei_placa){

        try{
            return veiculoDAO.deletarVeiculo(vei_placa);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar veículo no banco de dados", e);
        }

    }

    public VeiculoDTO consultarVeiculo(String vei_placa){
        try {
            return toDTO(veiculoDAO.consultarVeiculo(vei_placa));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar veículo no banco de dados", e);
        }
    }

    public List<VeiculoDTO>  consultarVeiculosGeral(){

        try{
            return veiculoDAO.consultarVeiculosGeral()
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar veiculos geral no banco de dados",e);
        }
    }

    public List<VeiculoView>  consultarVeiculosViewGeral(){

        try{
            return veiculoDAO.consultarVeiculosViewGeral();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar veiculos view geral no banco de dados",e);
        }
    }

    public List<VeiculoDTO> consultarVeiculosPorTipoPessoa(TipoPessoa tipoPessoa){

        try{
            return veiculoDAO.consultarVeiculosPorTipoPessoa(tipoPessoa)
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar veiculos por tipo de pessoa no banco de dados",e);
        }
    }
}
