package com.teste.barbearia.model.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.teste.barbearia.model.dao.interfaces.PrestadorInterfaceDAO;
import com.teste.barbearia.model.entity.Agendamento;
import com.teste.barbearia.model.entity.Cliente;
import com.teste.barbearia.model.entity.Prestador;
import com.teste.barbearia.model.mapper.ClienteRowMapper;
import com.teste.barbearia.model.mapper.PrestadorRowMapper;
@Repository
public class PrestadorDAO implements PrestadorInterfaceDAO {
  
 private NamedParameterJdbcTemplate template; 
  
  public PrestadorDAO(NamedParameterJdbcTemplate template) {  
    this.template = template;  
    }

  @Override
  public List<Prestador> list() {
    String sql = "SELECT * FROM prestador ";
    return template.query(sql, new PrestadorRowMapper());
  }

  @Override
  public void save(Prestador prestador) {
String sql = "INSERT INTO prestador(nome, cpf) VALUES (:nome, :cpf)";
    
    KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("nome", prestador.getNome())
        .addValue("cpf", prestador.getCpf());
    
    template.update(sql,param, holder);
    
  }

  @Override
  public Prestador getById(String cpf) throws Exception {
    String sql = "SELECT * FROM prestador WHERE cpf = :cpf";
    KeyHolder holder = new GeneratedKeyHolder();
    
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("cpf", cpf);
    List<Prestador> listaProvisoria = template.query(sql, param, new PrestadorRowMapper());
    if(listaProvisoria.size() <= 0)
      throw new Exception("Nao existe prestador com esse cpf!");
    
    return listaProvisoria.get(0);
  }

  @Override
  public void update(Prestador prestador) {
    String sql = "UPDATE prestador set nome = :nome, cpf= :cpf WHERE cpf = :cpf_param";
    KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("nome",prestador.getNome())
        .addValue("cpf", prestador.getCpf())
        .addValue("cpf_param",prestador.getCpf());
    
    template.update(sql,param, holder);
    
  }

  @Override
  public void delete(Prestador prestador) {
 String sql = "DELETE FROM prestador WHERE cpf = :cpf";
    
    HashMap<String,Object> map = new HashMap<String,Object>();
    
    map.put("cpf", prestador.getCpf());
    
    template.execute(sql,map,new PreparedStatementCallback<Object>() {  
      @Override  
      public Object doInPreparedStatement(PreparedStatement ps)  
              throws SQLException, DataAccessException {  
          return ps.executeUpdate();  
      }  
  });  
    
  }

  public List<Prestador> search(String cpf){
    String sql = "SELECT * FROM prestador WHERE cpf = :cpf";
       
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("cpf", cpf);
    
    return template.query(sql, param, new PrestadorRowMapper());
  }
    
 }
  
