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
   return prestador;
  }

  public Prestador getPrestador(String cpf) throws Exception {
   return prestadorDAO.getById(cpf);
  }

  public Prestador updatePrestador(Prestador prestador) {
   this.prestadorDAO.update(prestador);
   return prestador;
  }

  public String deletarPrestador(Prestador prestador)throws Exception {

    if(prestadorDAO.search(prestador.getCpf()).isEmpty())
      throw new Exception("nao existe nenhum prestador com esses dados");
    this.prestadorDAO.delete(prestador);
    return "Prestador removido com sucesso!";
  }

}
