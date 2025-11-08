package com.controleDeAcesso.controller;

import com.controleDeAcesso.dto.MoradorDTO;
import com.controleDeAcesso.dto.PessoaDTO;
import com.controleDeAcesso.dto.PrestadorDTO;
import com.controleDeAcesso.dto.VisitanteDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cadastro")
public class CadastroPessoaApiController {

    @PostMapping("/pessoa")
    public ResponseEntity<String> cadastrarPessoa(@RequestBody PessoaDTO pessoaDTO){

        System.out.println(pessoaDTO);

        return ResponseEntity.ok("Recebido com sucesso");
    }


    @PostMapping("/morador")
    public ResponseEntity<String> cadastrarMorador(@RequestBody MoradorDTO moradorDTO){

        System.out.println(moradorDTO);

        return ResponseEntity.ok("Recebido com sucesso");
    }

    @PostMapping("/visitante")
    public ResponseEntity<String> cadastrarVisitante(@RequestBody VisitanteDTO visitanteDTO){

        System.out.println(visitanteDTO);

        return ResponseEntity.ok("Recebido com sucesso");
    }

    @PostMapping("/prestador")
    public ResponseEntity<String> cadastrarPrestador(@RequestBody PrestadorDTO prestadorDTO){

        System.out.println(prestadorDTO);

        return ResponseEntity.ok("Recebido com sucesso");
    }










}
