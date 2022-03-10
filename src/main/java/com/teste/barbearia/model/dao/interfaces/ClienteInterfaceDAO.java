package com.teste.barbearia.model.dao.interfaces;

import java.util.List;
import java.util.Optional;

import com.teste.barbearia.model.dto.ClienteDTO;
import com.teste.barbearia.model.entity.Cliente;

public interface ClienteInterfaceDAO {
  List<Cliente> list();
  
  void save(ClienteDTO clienteDTO, Long id_endereco);
  
  List<Cliente> getById(Long id);
  
  void update(ClienteDTO clienteDTO, Long id, Long id_endereco);
  
  void delete(Long id);
}
