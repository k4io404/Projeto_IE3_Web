package com.controleDeAcesso.controller;

import com.controleDeAcesso.dto.LocalControladoDTO;
import com.controleDeAcesso.service.LocalControladoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/locais")
public class LocalControladoApiController {

    @Autowired
    private LocalControladoService localControladoService;

    public LocalControladoApiController(){
        this.localControladoService = new LocalControladoService();
    }

    @GetMapping
    public List<LocalControladoDTO> listarLocais(){
        return localControladoService.consultarNomesLocais();
    }

}
