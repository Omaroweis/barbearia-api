package com.teste.barbearia.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.teste.barbearia.model.dto.DiasOcupadosComCpfPrestadorDTO;
import com.teste.barbearia.model.dto.DiasOcupadosDTO;
import com.teste.barbearia.model.entity.Agendamento;
import com.teste.barbearia.model.entity.Cliente;

public class DiasOcupadosComCpfPrestadorRowMapper implements RowMapper<DiasOcupadosComCpfPrestadorDTO>{

  @Override
  public DiasOcupadosComCpfPrestadorDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    DiasOcupadosComCpfPrestadorDTO diasOcupados = new DiasOcupadosComCpfPrestadorDTO();
    diasOcupados.setDiaOcupado(rs.getInt("DIA"));
    diasOcupados.setQuantidade(rs.getInt("QUANTIDADE"));  
    diasOcupados.setCpf_prestador(rs.getString("cpf_prestador"));
    return diasOcupados;
  }

}
