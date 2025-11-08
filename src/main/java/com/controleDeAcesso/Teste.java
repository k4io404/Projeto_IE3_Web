package com.controleDeAcesso;

import com.controleDeAcesso.dao.*;
import com.controleDeAcesso.service.*;
import com.controleDeAcesso.dto.*;
import com.controleDeAcesso.model.*;

import java.sql.SQLException;
import java.util.Date;

public class Teste {

    public static void main(String[] args) {

        MoradorDTO mDTO = new MoradorDTO();
        mDTO.setNome("Ana");
        mDTO.setCpf("99999999998");
        System.out.println("incluindo1");
        mDTO.setDataNasc(new Date());
        mDTO.setAtiva(true);
        mDTO.setTipo("M");
        System.out.println("incluindo2");

        MoradorService m = new MoradorService();
        m.incluirMorador(mDTO);
    }
}