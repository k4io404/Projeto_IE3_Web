package com.controleDeAcesso.controller;


import com.controleDeAcesso.dto.LocalControladoDTO;
import com.controleDeAcesso.service.LocalControladoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

@Controller
public class LocalControladoController {

    @Autowired
    private LocalControladoService localControladoService;

    public LocalControladoController(){
        localControladoService = new LocalControladoService();
    }

    @GetMapping(value = "/locais")
    public String listarAcessos(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(required = false) String pesquisa,
                                Model model){

        model.addAttribute("botaoPressionado","locais");

        //vão se tornar uma linha só futuramente com JPA
        PageRequest pageable = PageRequest.of(page,Constantes.registrosPorPagina);
        Page<LocalControladoDTO> pagina =  PageUtils.toPage(localControladoService.consultarLocaisControladosGeral(),pageable);
        //Page<AcessoDTO> pagina = acessoService.buscarAcessosPaginados(page, tamanhoPagina, pesquisa);

        model.addAttribute("locaisDTO",pagina.getContent());
        model.addAttribute("paginaAtual", page);
        model.addAttribute("totalPaginas", pagina.getTotalPages());
        model.addAttribute("pesquisa", pesquisa);

        return "locais";
    }



}
