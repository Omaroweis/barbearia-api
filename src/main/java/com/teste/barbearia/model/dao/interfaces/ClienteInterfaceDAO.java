package com.teste.barbearia.model.dao.interfaces;

import java.util.List;
import java.util.Optional;

import com.teste.barbearia.model.entity.Cliente;

public interface ClienteInterfaceDAO {
  List<Cliente> list();
  
  void save(Cliente t);
  
  Cliente getById(Long id) throws Exception;
  
  void update(Cliente p, Long id);
  
  void delete(Long id);
}
