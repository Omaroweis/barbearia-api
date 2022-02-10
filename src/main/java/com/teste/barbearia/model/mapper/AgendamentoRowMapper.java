package com.teste.barbearia.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.teste.barbearia.model.entity.Agendamento;
import com.teste.barbearia.model.entity.Cliente;

public class AgendamentoRowMapper implements RowMapper<Agendamento>{

  @Override
  public Agendamento mapRow(ResultSet rs, int rowNum) throws SQLException {
    Agendamento agendamento= new Agendamento();
    agendamento.setCpf_cliente(rs.getString("cpf_cliente"));
    agendamento.setCpf_prestador(rs.getString("cpf_prestador"));
    agendamento.setDate(rs.getDate("data"));
    agendamento.setHorario(rs.getString("horario"));

    
    return agendamento;
  }

}
