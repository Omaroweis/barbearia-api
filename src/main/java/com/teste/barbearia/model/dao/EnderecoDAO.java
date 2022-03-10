package com.teste.barbearia.model.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.teste.barbearia.model.dao.interfaces.EnderecoIntefaceDAO;
import com.teste.barbearia.model.entity.Endereco;
import com.teste.barbearia.model.mapper.EnderecoRowMapper;
import com.teste.barbearia.provider.dto.EnderecoViaCepDTO;
import com.teste.barbearia.provider.dto.interfaces.EnderecoDTO;
@Repository
public class EnderecoDAO implements EnderecoIntefaceDAO{

  private NamedParameterJdbcTemplate template; 
  
  public EnderecoDAO(NamedParameterJdbcTemplate template) {  
    this.template = template;  
    }

  @Override
  public Long save(Endereco endereco) {
    String sql = "INSERT INTO ENDERECO(codigo_postal, estado, cidade, pais, complemento) VALUES (:postal, :estado, :cidade, :pais, :complemento) returning id";
    KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("postal", endereco.getPostal())
        .addValue("estado", endereco.getEstado())
        .addValue("cidade", endereco.getCidade())
        .addValue("pais", endereco.getPais())
        .addValue("complemento", endereco.getComplemento());
    
    Long.valueOf(template.update(sql,param, holder));
    return holder.getKey().longValue();
  
    
  }

  @Override
  public Endereco getById(Long id) {
    String sql = "SELECT * FROM endereco WHERE id = :id";
    KeyHolder holder = new GeneratedKeyHolder();
    
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("id", id);
    return template.query(sql,param, new EnderecoRowMapper()).get(0);
  }

  @Override
  public void update(Long id, Endereco endereco) {
    String sql = "UPDATE endereco set cidade = :cidade, estado = :estado, complemento = :complemento, codigo_postal = :codigoPostal, pais = :pais WHERE id = :id";
    KeyHolder holder = new GeneratedKeyHolder();
    
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("cidade",endereco.getCidade())
        .addValue("estado", endereco.getEstado())
        .addValue("complemento", endereco.getComplemento())
        .addValue("codigoPostal", endereco.getPostal())
        .addValue("pais", endereco.getPais())
        .addValue("id",id);
    
    template.update(sql, param, holder);

  }

  @Override
  public void delete(Long id) {
   String sql = "DELETE from endereco WHERE id = :id";
   HashMap<String,Object> map = new HashMap<String,Object>();
   map.put("id", id);
   template.execute(sql,map,new PreparedStatementCallback<Object>() {  
     @Override  
     public Object doInPreparedStatement(PreparedStatement ps)  
             throws SQLException, DataAccessException {  
         return ps.executeUpdate();  
     }  
 });  
  }
  
  public List<String> getPrestadorByEndereco(String postal){
    String sql = "SELECT p.nome as NOME from prestador p inner join endereco e on e.id = p.id_endereco where e.codigo_postal = :postal";
    KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("postal", postal);
    
    return template.query(sql,param, (rs, arg0) -> {
      
      String str = rs.getString("NOME");
      return str;
      
    });
    
  }
 

}
