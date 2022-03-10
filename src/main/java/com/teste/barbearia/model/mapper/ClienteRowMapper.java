package com.teste.barbearia.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.teste.barbearia.model.entity.Cliente;

public class ClienteRowMapper implements RowMapper<Cliente>{

  @Override
  public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
    Cliente cliente = new Cliente();
    cliente.setId(Long.valueOf(rs.getString("id")));
    cliente.setCpf(rs.getString("cpf"));
    cliente.setNome(rs.getString("nome"));
    cliente.setId_endereco(Long.valueOf(rs.getString("id_endereco")));
    
    return cliente;
  }

}
