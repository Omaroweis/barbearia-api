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
    agendamento.setId(Long.valueOf(rs.getString("id")));
    agendamento.setId_cliente(Long.valueOf(rs.getString("id_cliente")));
    agendamento.setId_prestador(Long.valueOf(rs.getString("id_prestador")));
    agendamento.setDate(rs.getDate("data"));
    agendamento.setHorario(rs.getTime("horario"));

    
    return agendamento;
  }

}
