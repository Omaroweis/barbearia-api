package com.teste.barbearia.model.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.sun.javafx.collections.MappingChange.Map;
import org.springframework.jdbc.core.PreparedStatementCallback;
import com.teste.barbearia.model.dao.interfaces.ClienteInterfaceDAO;
import com.teste.barbearia.model.entity.Cliente;
import com.teste.barbearia.model.mapper.ClienteRowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
@Repository
public class ClienteDAO implements ClienteInterfaceDAO{
  
  private NamedParameterJdbcTemplate template; 
  
  public ClienteDAO(NamedParameterJdbcTemplate template) {  
    this.template = template;  
    }

  @Override
  public List<Cliente> list() {
    String sql = "SELECT * FROM cliente";
    return template.query(sql, new ClienteRowMapper());
  }

  @Override
  public void save(Cliente cliente) {
    String sql = "INSERT INTO cliente(nome, cpf) VALUES (:nome, :cpf)";
    
    KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("nome", cliente.getNome())
        .addValue("cpf", cliente.getCpf());
    
    template.update(sql,param, holder);
    
  }

  @Override
  public Cliente getById(Long id)  {
    // TODO: tratar exceções
    String sql = "SELECT * FROM cliente WHERE id = :id";
    KeyHolder holder = new GeneratedKeyHolder();
    
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("id", id);
    List<Cliente> listaProvisoria = template.query(sql, param, new ClienteRowMapper());
//    if(listaProvisoria.size() <= 0)
//      throw new Exception("Nao existe cliente com esse cpf!");
    
    return listaProvisoria.get(0);
  }

  @Override
  public void update(Cliente p, Long id) {
    // TODO: tratar excecoes 
    String sql = "UPDATE cliente set nome = :nome, cpf= :cpf WHERE id = :id";
    KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("nome",p.getNome())
        .addValue("cpf", p.getCpf())
        .addValue("id",id);
    
    template.update(sql,param, holder);
    
  }

  @Override
  public void delete(Long id) {
    String sql = "DELETE FROM cliente WHERE id = :id";
    
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

  public List<Cliente> search(String cpf) {
    String sql = "SELECT * FROM cliente where cpf = :cpf";
    
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("cpf", cpf);
    return template.query(sql, param, new ClienteRowMapper());
    
    
  } 

  
}
