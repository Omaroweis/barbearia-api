package com.teste.barbearia.provider.implementacoes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.teste.barbearia.exception.ApiRequestException;
import com.teste.barbearia.model.entity.Endereco;
import com.teste.barbearia.model.enuns.Mensagens;
import com.teste.barbearia.provider.ApiEndereco;
import com.teste.barbearia.provider.dto.EnderecoViaCepDTO;
import com.teste.barbearia.provider.dto.interfaces.EnderecoDTO;

public class ApiViaCep implements ApiEndereco{

  @Override
  public Endereco request(String cep) {
    String url = "https://viacep.com.br/ws/"+cep+"/json/";
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<EnderecoViaCepDTO> response = null;
    try {
      response = restTemplate.getForEntity(url, EnderecoViaCepDTO.class);
    } catch (Exception e) {
      throw new ApiRequestException(Mensagens.ERRO_POSTAL_INVALIDO.getMensagem());
    }
   
    Endereco endereco = null;
    try {
       endereco = new Endereco(response.getBody());
    }catch (NullPointerException e) {
      throw new ApiRequestException(Mensagens.ERRO_POSTAL_INVALIDO.getMensagem());
    }
    
    return endereco;
  }

}
