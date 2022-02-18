package com.teste.barbearia.model.dao.interfaces;


import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.teste.barbearia.model.entity.Agendamento;
import com.teste.barbearia.model.enuns.Horarios;

public interface AgendamentoInterfaceDAO{
  List<Agendamento> list();
  
  void save(Agendamento agendamento);
  
   
  List<Agendamento> getById(Long id);
  
  List<Agendamento> getByConstraint(Long id_prestador, Date data, Horarios horario);
 
  
  void delete(Agendamento agendamento);
  
  void delete(Long id);
  
  List<Agendamento> listByCliente(Long id_liente);
  
  List<Agendamento> listByPrestador(Long id_prestador);
}
