package com.teste.barbearia.provider.implementacoes;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import com.teste.barbearia.exception.ApiRequestException;
import com.teste.barbearia.model.entity.Endereco;
import com.teste.barbearia.model.enuns.Mensagens;
import com.teste.barbearia.provider.ApiEndereco;
import com.teste.barbearia.provider.dto.EnderecoGeoCodeDTO;
import com.teste.barbearia.provider.dto.EnderecoViaCepDTO;


public class ApiGeoCode implements ApiEndereco {

  @Override
  public Endereco request(String postal) {
   
    
    // https://geocoder.ca/H3Z2Y7?json=1
    String url = "https://geocoder.ca/" + postal + "?json=1";
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<EnderecoGeoCodeDTO> response = 
        restTemplate.getForEntity(url, EnderecoGeoCodeDTO.class);
    Endereco endereco = null;
    try {
       endereco = new Endereco(response.getBody());
    }catch (NullPointerException e) {
      throw new ApiRequestException(Mensagens.ERRO_POSTAL_INVALIDO.getMensagem());
    }
    
    return endereco;
  }

}
