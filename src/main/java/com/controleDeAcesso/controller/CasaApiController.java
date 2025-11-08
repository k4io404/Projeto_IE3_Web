package com.controleDeAcesso.controller;

import com.controleDeAcesso.dto.CasaDTO;
import com.controleDeAcesso.service.CasaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/casas")
public class CasaApiController {

    CasaService casaService;

    public CasaApiController(){
        this.casaService = new CasaService();
    }

    @GetMapping
    public List<CasaDTO> listarNomeCasas(){
        return casaService.consultarNumerosCasas();
    }


}
