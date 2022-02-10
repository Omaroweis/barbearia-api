package com.teste.barbearia.model.dao.interfaces;

import java.util.List;
import java.util.Optional;

import com.teste.barbearia.model.entity.Agendamento;
import com.teste.barbearia.model.entity.Prestador;

public interface PrestadorInterfaceDAO {
  
  List<Prestador> list();
  
  void save(Prestador prestador);
  
  Prestador getById(String cpf) throws Exception;
  
  void update(Prestador prestador);
  
  void delete(Prestador prestador);
}