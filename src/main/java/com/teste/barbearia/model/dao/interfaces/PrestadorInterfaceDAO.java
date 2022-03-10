package com.teste.barbearia.model.dao.interfaces;

import java.util.List;
import java.util.Optional;

import com.teste.barbearia.model.dto.PrestadorDTO;
import com.teste.barbearia.model.entity.Agendamento;
import com.teste.barbearia.model.entity.Prestador;

public interface PrestadorInterfaceDAO {
  
  List<Prestador> list();
  
  void save(PrestadorDTO prestador, Long id_endereco);
  
  List<Prestador> getById(Long id) throws Exception;
  
  void update(PrestadorDTO prestadorDTO, Long id, long id_endereco);
  
  void delete(Long id);
}
