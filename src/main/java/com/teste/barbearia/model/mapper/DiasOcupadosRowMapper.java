package com.teste.barbearia.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.teste.barbearia.model.dto.DiasOcupadosDTO;
import com.teste.barbearia.model.entity.Agendamento;
import com.teste.barbearia.model.entity.Cliente;

public class DiasOcupadosRowMapper implements RowMapper<DiasOcupadosDTO>{

  @Override
  public DiasOcupadosDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    DiasOcupadosDTO diasOcupados = new DiasOcupadosDTO();
    diasOcupados.setDiaOcupado(rs.getInt("DIA"));
    diasOcupados.setQuantidade(rs.getInt("QUANTIDADE"));   
    return diasOcupados;
  }

}
