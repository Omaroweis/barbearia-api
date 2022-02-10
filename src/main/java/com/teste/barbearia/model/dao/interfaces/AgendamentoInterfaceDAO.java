package com.teste.barbearia.model.dao.interfaces;


import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.teste.barbearia.model.entity.Agendamento;
import com.teste.barbearia.model.enuns.Horarios;

public interface AgendamentoInterfaceDAO{
  List<Agendamento> list();
  
  void save(Agendamento agendamento);
  
  Agendamento getById(String cpf_prestador, Date data, Horarios horario) throws Exception;
  
  void update(Agendamento agendamento, String cpf_prestador_param, Horarios horario_param, Date data_param);
  
  void delete(Agendamento agendamento);
  
  List<Agendamento> listByCliente(String cpf_cliente);
  
  List<Agendamento> listByPrestador(String cpf_prestador);
}
