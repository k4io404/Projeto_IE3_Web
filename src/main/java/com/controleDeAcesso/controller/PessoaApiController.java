package com.controleDeAcesso.controller;

import com.controleDeAcesso.dto.PessoaDTO;
import com.controleDeAcesso.service.PessoaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PessoaApiController {

    private PessoaService pessoaService;

    public PessoaApiController(){
        pessoaService = new PessoaService();
    }


  @GetMapping("/api/moradores")
    public List<PessoaDTO> listarNomesMoradores(){
        return pessoaService.consultarNomesMoradores();
    }


}
