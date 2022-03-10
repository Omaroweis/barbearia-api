package com.teste.barbearia.utils;

import com.teste.barbearia.factory.Factory;
import com.teste.barbearia.model.dao.EnderecoDAO;
import com.teste.barbearia.model.dao.interfaces.EnderecoIntefaceDAO;
import com.teste.barbearia.model.entity.Endereco;
import com.teste.barbearia.provider.ApiEndereco;
import com.teste.barbearia.provider.dto.EnderecoViaCepDTO;
import com.teste.barbearia.provider.dto.interfaces.EnderecoDTO;

public class EnderecoUtils {

  public static Endereco get(String pais, String cep) {
    
    ApiEndereco enderecoApi = Factory.getInstace(pais);

    Endereco endereco = enderecoApi.request(cep); 
       
    return endereco;
    
  }
}
