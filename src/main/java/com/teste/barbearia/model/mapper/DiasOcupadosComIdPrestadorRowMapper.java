package com.teste.barbearia.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.teste.barbearia.model.dto.DiasOcupadosComIdPrestadorDTO;
import com.teste.barbearia.model.dto.DiasOcupadosDTO;
import com.teste.barbearia.model.entity.Agendamento;
import com.teste.barbearia.model.entity.Cliente;

public class DiasOcupadosComIdPrestadorRowMapper implements RowMapper<DiasOcupadosComIdPrestadorDTO>{

  @Override
  public DiasOcupadosComIdPrestadorDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    DiasOcupadosComIdPrestadorDTO diasOcupados = new DiasOcupadosComIdPrestadorDTO();
    diasOcupados.setDiaOcupado(rs.getInt("DIA"));
    diasOcupados.setQuantidade(rs.getInt("QUANTIDADE"));  
    diasOcupados.setId_prestador(Long.valueOf(rs.getString("id_prestador")));
    return diasOcupados;
  }

}
