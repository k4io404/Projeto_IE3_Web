package com.controleDeAcesso.controller;

import com.controleDeAcesso.dto.PessoaDTO;
import com.controleDeAcesso.service.PessoaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pessoas")
public class PessoaController {

    PessoaService pessoaService;

    public PessoaController(){
        this.pessoaService = new PessoaService();
    }

    @GetMapping()
    public String listaPessoas(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(required = false) String pesquisa,
                               Model model){

        model.addAttribute("botaoPressionado","pessoas");

        //vão se tornar uma linha só futuramente com JPA
        PageRequest pageable = PageRequest.of(page,Constantes.registrosPorPagina);
        Page<PessoaDTO> pagina =  PageUtils.toPage(pessoaService.consultarPessoasGeral(),pageable);
        //Page<PessoaDTO> pagina = pessoaService.buscarAcessosPaginados(page, tamanhoPagina, pesquisa);

        model.addAttribute("pessoasDTO",pagina.getContent());
        model.addAttribute("paginaAtual", page);
        model.addAttribute("totalPaginas", pagina.getTotalPages());
        model.addAttribute("pesquisa", pesquisa);

        return "pessoas";
    }

    @GetMapping("/cadastro")
    public String exibirFormularioCadastro(Model model){

        return "pessoaCadastro";
    }

}
