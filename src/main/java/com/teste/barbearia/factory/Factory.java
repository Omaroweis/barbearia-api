package com.teste.barbearia.factory;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.teste.barbearia.exception.ApiRequestException;
import com.teste.barbearia.model.dao.EnderecoDAO;
import com.teste.barbearia.model.dao.interfaces.EnderecoIntefaceDAO;
import com.teste.barbearia.model.enuns.Mensagens;
import com.teste.barbearia.provider.ApiEndereco;
import com.teste.barbearia.provider.dto.EnderecoGeoCodeDTO;
import com.teste.barbearia.provider.dto.EnderecoViaCepDTO;
import com.teste.barbearia.provider.dto.interfaces.EnderecoDTO;
import com.teste.barbearia.provider.implementacoes.ApiGeoCode;
import com.teste.barbearia.provider.implementacoes.ApiViaCep;

import net.bytebuddy.utility.dispatcher.JavaDispatcher.Instance;

public class Factory {

  public static ApiEndereco getInstace(String pais) {
    
    pais = pais.toUpperCase();
    switch (pais) {
        case "BR":
          return new ApiViaCep();
        case "CA":
          return new ApiGeoCode();
        default:
          throw new ApiRequestException(Mensagens.ERRO_PAIS_INVALIDO.getMensagem());
    }
    
  }
}
