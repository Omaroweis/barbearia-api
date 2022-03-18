package com.teste.barbearia.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.teste.barbearia.exception.ApiRequestException;
import com.teste.barbearia.factory.Factory;
import com.teste.barbearia.model.dao.EnderecoDAO;
import com.teste.barbearia.model.entity.Endereco;
import com.teste.barbearia.model.enuns.Mensagens;
import com.teste.barbearia.provider.ApiEndereco;

@Component
public class EnderecoService {
    @Resource
    private EnderecoDAO enderecoDAO;
    
    public Endereco Request(String pais, String postal) {
      
      ApiEndereco enderecoApi = Factory.getInstace(pais);
      
      Endereco endereco = enderecoApi.request(postal); 
 
      return endereco;      
    }
    public Long save(Endereco endereco, String complemento) {
      if(endereco.getPostal() == null || endereco.getPostal().isEmpty())
        throw new ApiRequestException(Mensagens.ERRO_POSTAL_NAO_EXISTE.getMensagem());
      if( complemento == null || complemento.isEmpty())
        throw new ApiRequestException(Mensagens.ERRO_COMPLEMENTO_NAO_EXISTE.getMensagem());
      endereco.setComplemento(complemento);
      Long id_ret = this.enderecoDAO.save(endereco);
      return id_ret;
      
    }
    public List<String> getPrestadorByEndereco(String postal){
     return this.enderecoDAO.getPrestadorByEndereco(postal);
    }
    public Endereco getById(Long id_endereco) {
      return this.enderecoDAO.getById(id_endereco);
    }
}
