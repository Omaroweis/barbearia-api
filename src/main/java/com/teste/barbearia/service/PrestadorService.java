package com.teste.barbearia.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.teste.barbearia.exception.ApiRequestException;
import com.teste.barbearia.model.dao.AgendamentoDAO;
import com.teste.barbearia.model.dao.PrestadorDAO;
import com.teste.barbearia.model.entity.Prestador;
import com.teste.barbearia.model.enuns.Mensagens;

import br.com.caelum.stella.validation.CPFValidator;
@Component
public class PrestadorService {
  @Resource 
  private PrestadorDAO prestadorDAO;
  
  
  public Prestador insere(Prestador prestador) {
    
    if(!this.prestadorDAO.search(prestador.getCpf()).isEmpty()) {
      throw new ApiRequestException(Mensagens.ERRO_CPF_JA_EXISTE.getMensagem());
    }
    
    if(prestador.getCpf().isEmpty() || prestador.getNome().isEmpty())
      throw new ApiRequestException(Mensagens.ERRO_INPUT_PRESTADOR_VAZIO.getMensagem());
    
    try {
      new CPFValidator().assertValid(prestador.getCpf()); 
    }catch (Exception e) {
      throw new ApiRequestException(Mensagens.ERRO_CPF_INVALIDO.getMensagem());
    }
    
   prestadorDAO.save(prestador);
   return this.prestadorDAO.search(prestador.getCpf()).get(0);
  }
  
  public Prestador getPrestador(Long id) {
    if(this.prestadorDAO.getById(id).isEmpty())
      throw new ApiRequestException(Mensagens.ERRO_ID_NAO_EXISTE.getMensagem());
    
    return prestadorDAO.getById(id).get(0);
  }
  
  public Prestador getPrestador(String cpf) throws Exception {
    if(this.prestadorDAO.search(cpf).isEmpty())
      throw new ApiRequestException(Mensagens.ERRO_CPF_NAO_EXISTE.getMensagem());
    
    Prestador prestador = this.prestadorDAO.search(cpf).get(0);
    
    return prestadorDAO.getById(prestador.getId()).get(0);
  }

  public Prestador updatePrestador(Prestador prestador) throws Exception{
   
    if(this.prestadorDAO.getById(prestador.getId()).isEmpty()) {
      throw new ApiRequestException(Mensagens.ERRO_ID_NAO_EXISTE.getMensagem());
    }
    
    try {
      new CPFValidator().assertValid(prestador.getCpf()); 
    }catch (Exception e) {
      throw new ApiRequestException(Mensagens.ERRO_CPF_INVALIDO.getMensagem());
    }
    
    if(!this.prestadorDAO.search(prestador.getCpf()).isEmpty()) {
      // verifica se o cpf que replicou era referente ao antigo registro do usuario no banco
      if (this.prestadorDAO.search(prestador.getCpf()).get(0).getId() != prestador.getId())
        throw new ApiRequestException(Mensagens.ERRO_CPF_JA_EXISTE.getMensagem());
    }
     Long id = prestador.getId();
     if(this.prestadorDAO.getById(id) == null) {
       throw new Exception();
     }
     this.prestadorDAO.update(prestador, id);
     return prestador;
  }

  public String deletarPrestador(Long id)throws Exception {

    if(this.prestadorDAO.getById(id).isEmpty())
      throw new ApiRequestException(Mensagens.ERRO_ID_NAO_EXISTE.getMensagem());
    this.prestadorDAO.delete(id);
    return Mensagens.SUCESSO_REMOVER_PRESTADOR.getMensagem();
  }

}
