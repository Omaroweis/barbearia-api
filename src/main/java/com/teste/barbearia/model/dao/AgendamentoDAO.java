package com.teste.barbearia.model.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.RowMapper;
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

import com.teste.barbearia.model.dao.interfaces.AgendamentoInterfaceDAO;
import com.teste.barbearia.model.dto.DiasOcupadosComIdPrestadorDTO;
import com.teste.barbearia.model.dto.DiasOcupadosDTO;
import com.teste.barbearia.model.entity.Agendamento;
import com.teste.barbearia.model.entity.Cliente;
import com.teste.barbearia.model.entity.Prestador;
import com.teste.barbearia.model.enuns.Horarios;
import com.teste.barbearia.model.mapper.AgendamentoRowMapper;
import com.teste.barbearia.model.mapper.ClienteRowMapper;
import com.teste.barbearia.model.mapper.DiasOcupadosComIdPrestadorRowMapper;
import com.teste.barbearia.model.mapper.DiasOcupadosRowMapper;
import com.teste.barbearia.model.mapper.PrestadorRowMapper;

@Repository
public class AgendamentoDAO implements AgendamentoInterfaceDAO{

 private NamedParameterJdbcTemplate template; 
  
  public AgendamentoDAO(NamedParameterJdbcTemplate template) {  
    this.template = template;  
    }

  @Override
  public List<Agendamento> list() {
    String sql = "SELECT * FROM agendamento";
    return template.query(sql, new AgendamentoRowMapper());
  }

  @Override
  public void save(Agendamento agendamento) {

 String sql = "INSERT INTO agendamento(id_cliente, id_prestador, horario, data) VALUES (:id_cliente, :id_prestador, :horario, :data)";
    
    KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("id_cliente", agendamento.getId_cliente())
        .addValue("id_prestador", agendamento.getId_prestador())
        .addValue("horario",agendamento.getHorario().getHora())
        .addValue("data",agendamento.getDate());
    
    template.update(sql,param, holder);

  }

  @Override
  public List<Agendamento> getById(Long id) {
    String sql = "SELECT * FROM agendamento WHERE id = :id";
    KeyHolder holder = new GeneratedKeyHolder();
    
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("id",id);
    List<Agendamento> listaProvisoria = template.query(sql, param, new AgendamentoRowMapper());
//    if(listaProvisoria.size() <= 0)
//      throw new Exception("Nao existe agendamento com esse prestador nesse horario!");
    
    return listaProvisoria;
  }

 

  @Override
  public void delete(Agendamento agendamento) {
    
//    TODO: tratar exceções
    
    String sql = "DELETE FROM agendamento WHERE id_prestador = :id_prestador AND horario =:horario AND data = :data";
    
    HashMap<String,Object> map = new HashMap<String,Object>();
    
    map.put("id_prestador", agendamento.getId_prestador());
    map.put("horario", agendamento.getHorario().getHora());
    map.put("data",agendamento.getDate());
    
    template.execute(sql,map,new PreparedStatementCallback<Object>() {  
      @Override  
      public Object doInPreparedStatement(PreparedStatement ps)  
              throws SQLException, DataAccessException {  
          return ps.executeUpdate();  
      }  
  });  
    
  } 
 
  
  @Override
  public void delete (Long id) {
    String sql = "DELETE FROM agendamento WHERE id = :id";
    
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

  @Override
  public List<Agendamento> listByCliente(Long id_cliente) {
    String sql = "SELECT * FROM agendamento WHERE id_cliente = :id_cliente";
    KeyHolder holder = new GeneratedKeyHolder();
    
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("id_cliente", id_cliente); 
    return template.query(sql, param, new AgendamentoRowMapper());
  }

  @Override
  public List<Agendamento> listByPrestador(Long id_prestador) {
    String sql = "SELECT * FROM agendamento WHERE id_prestador = :id_prestador";
    KeyHolder holder = new GeneratedKeyHolder();
    
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("id_cliente", id_prestador); 
    return template.query(sql, param, new AgendamentoRowMapper());
  }
 public List<Prestador> listPrestadoresByDataAndHorario(Date data, Horarios horario){
   String sql = "SELECT p.* FROM agendamento a inner join prestador p ON p.id = a.id_prestador where a.data = :data AND a.horario = :horario";
   
   
   SqlParameterSource param = new MapSqlParameterSource()
       .addValue("data",data)
       .addValue("horario", horario.getHora());
   return template.query(sql, param, new PrestadorRowMapper());
 }
 

 
 public ArrayList<Integer> listDiasOcupadosByMesAndPrestador(String mes, Long id_prestador){
   String sql = "select EXTRACT(DAY FROM a.data) as DIA, count(*) as QUANTIDADE from agendamento a where (EXTRACT(MONTH FROM a.data) = :mes) and (id_prestador = :id_prestador) group by EXTRACT(DAY FROM a.data)";
   SqlParameterSource param = new MapSqlParameterSource()
       .addValue("mes",Integer.valueOf(mes))
       .addValue("id_prestador", id_prestador);
   List<DiasOcupadosDTO>  diasOcupados = template.query(sql, param, new DiasOcupadosRowMapper());
   
 
   
   ArrayList<Integer> diaOcupadoIndisponivel = new ArrayList<>();
   
   diasOcupados.forEach(dia -> {
     if(dia.getQuantidade() >= Cliente.quantidadeHorarios.MAX()) // nao tem horario disponivel
       diaOcupadoIndisponivel.add(dia.getDiaOcupado());
   });
   
   
   
   return diaOcupadoIndisponivel;
 }
 

 private List<DiasOcupadosComIdPrestadorDTO> getDiasSaturados(Date date, Long id_prestador){
   Integer mes = date.toLocalDate().getMonthValue();
   
   String sql = "select * from (select EXTRACT(DAY FROM a.data) as DIA, count(*) as QUANTIDADE, a.id_prestador from agendamento a where (EXTRACT(MONTH FROM a.data) = :mes) and (id_prestador = :id_prestador)group by EXTRACT(DAY FROM a.data), id_prestador) as dias_ocupados where dias_ocupados.quantidade >= :QTD_MAX";
   
   SqlParameterSource param = new MapSqlParameterSource()
       .addValue("mes",mes)
       .addValue("id_prestador", id_prestador)
       .addValue("QTD_MAX", Cliente.quantidadeHorarios.MAX());
   
   return template.query(sql, param, new DiasOcupadosComIdPrestadorRowMapper());
 }
   

 
 public List<LocalTime> listHorariosOcupadosByData(Date date, Long id){
   
   System.out.println("Testando");
   
   System.out.println(date);
   System.out.println(id);
   
   String sql = "select horario from agendamento a where a.data = :data and id_prestador = :id_prestador";
   SqlParameterSource param = new MapSqlParameterSource()
       .addValue("data",date)
       .addValue("id_prestador", id);
   
   return template.query(sql, param, (rs, arg0) -> {     
     Agendamento a = new Agendamento();
     a.setHorario(rs.getTime("horario"));
     return a.getHorario().getHora().toLocalTime();
   });
 }
 public List<Agendamento> searchByCliente(Agendamento agendamento) {
   String sql = "select * from agendamento a where a.horario = :horario and a.data  =:data and a.id_cliente = :id_cliente";
   
   SqlParameterSource param = new MapSqlParameterSource()
       .addValue("horario",agendamento.getHorario().getHora())
       .addValue("id_cliente", agendamento.getId_cliente())
       .addValue("data",agendamento.getDate());
   
   return template.query(sql, param, new AgendamentoRowMapper());
   
 }
 public List<Agendamento> searchByPrestador(Agendamento agendamento){
 String sql = "select * from agendamento a where a.horario = :horario and a.\"data\"  =:data and a.id_prestador = :id_prestador";
 
 SqlParameterSource param = new MapSqlParameterSource()
     .addValue("horario",agendamento.getHorario().getHora())
     .addValue("id_prestador", agendamento.getId_prestador())
     .addValue("data",agendamento.getDate());
 return template.query(sql, param, new AgendamentoRowMapper());
}

@Override
public List<Agendamento> getByConstraint(Long id_prestador, Date data, Horarios horario) {
  String sql = "SELECT * FROM agendamento WHERE id_prestador = :id AND data = :data AND horario = :horario";
  
  KeyHolder holder = new GeneratedKeyHolder();
  
  SqlParameterSource param = new MapSqlParameterSource()
      .addValue("id", id_prestador)
      .addValue("data", data)
      .addValue("horario", horario.getHora());
  
  List<Agendamento> listaProvisoria = template.query(sql, param, new AgendamentoRowMapper());
  return listaProvisoria;
}
}





