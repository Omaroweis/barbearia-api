package com.teste.barbearia.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.teste.barbearia.model.entity.Cliente;
import com.teste.barbearia.model.entity.Prestador;

public class PrestadorRowMapper implements RowMapper<Prestador>{

  @Override
  public Prestador mapRow(ResultSet rs, int rowNum) throws SQLException {
    Prestador prestador = new Prestador();
    prestador.setCpf(rs.getString("cpf"));
    prestador.setNome(rs.getString("nome"));
    
    return prestador;
  }

}
