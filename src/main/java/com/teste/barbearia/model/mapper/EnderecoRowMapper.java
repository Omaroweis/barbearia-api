package com.teste.barbearia.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.teste.barbearia.model.entity.Endereco;
import com.teste.barbearia.model.entity.Prestador;
import com.teste.barbearia.provider.dto.EnderecoViaCepDTO;
import com.teste.barbearia.provider.dto.interfaces.EnderecoDTO;

public class EnderecoRowMapper  implements RowMapper<Endereco> {

  
  @Override
  public Endereco mapRow(ResultSet rs, int rowNum) throws SQLException{
    Endereco endereco = new Endereco();     
    endereco.setId(Long.valueOf(rs.getString("id")));
    endereco.setPostal(rs.getString("codigo_postal"));
    endereco.setCidade(rs.getString("cidade"));
    endereco.setEstado(rs.getString("estado"));
    endereco.setComplemento(rs.getString("complemento"));
    endereco.setPais(rs.getString("pais"));
    
    return endereco;
  }
}
