package com.teste.barbearia.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.teste.barbearia.model.dao.AgendamentoDAO;
import com.teste.barbearia.model.dao.PrestadorDAO;
import com.teste.barbearia.model.entity.Prestador;
@Component
public class PrestadorService {
  @Resource 
  private PrestadorDAO prestadorDAO;
  public Prestador insere(Prestador prestador) {
   prestadorDAO.save(prestador);
   return this.prestadorDAO.search(prestador.getCpf()).get(0);
  }
  
  public Prestador getPrestador(Long id) {
    // TODO: tratar exceções
    
    return prestadorDAO.getById(id);
  }
  
  public Prestador getPrestador(String cpf) throws Exception {
    // TODO: tratar excecoes
    
    Prestador prestador = this.prestadorDAO.search(cpf).get(0);
    
    return prestadorDAO.getById(prestador.getId());
  }

  public Prestador updatePrestador(Prestador prestador) throws Exception{
   // TODO: tratar exceção

     Long id = prestador.getId();
     if(this.prestadorDAO.getById(id) == null) {
       throw new Exception();
     }
     this.prestadorDAO.update(prestador, id);
     return prestador;
  }

  public String deletarPrestador(Long id)throws Exception {

    // TODO: tratar exceções
    this.prestadorDAO.delete(id);
    return "Prestador removido com sucesso!";
  }

}
