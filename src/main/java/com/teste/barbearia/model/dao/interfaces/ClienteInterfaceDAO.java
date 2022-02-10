package com.teste.barbearia.model.dao.interfaces;

import java.util.List;
import java.util.Optional;

import com.teste.barbearia.model.entity.Cliente;

public interface ClienteInterfaceDAO {
  List<Cliente> list();
  
  void save(Cliente t);
  
  Cliente getById(String cpf) throws Exception;
  
  void update(Cliente p);
  
  void delete(Cliente cliente);
}
