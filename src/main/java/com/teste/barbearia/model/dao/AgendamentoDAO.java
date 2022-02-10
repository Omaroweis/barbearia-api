package com.teste.barbearia.model.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import com.teste.barbearia.model.dto.DiasOcupadosComCpfPrestadorDTO;
import com.teste.barbearia.model.dto.DiasOcupadosDTO;
import com.teste.barbearia.model.entity.Agendamento;
import com.teste.barbearia.model.entity.Cliente;
import com.teste.barbearia.model.entity.Prestador;
import com.teste.barbearia.model.enuns.Horarios;
import com.teste.barbearia.model.mapper.AgendamentoRowMapper;
import com.teste.barbearia.model.mapper.ClienteRowMapper;
import com.teste.barbearia.model.mapper.DiasOcupadosComCpfPrestadorRowMapper;
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
 String sql = "INSERT INTO agendamento(cpf_cliente, cpf_prestador, horario, data) VALUES (:cpf_cliente, :cpf_prestador, :horario, :data)";
    
    KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("cpf_cliente", agendamento.getCpf_cliente())
        .addValue("cpf_prestador", agendamento.getCpf_prestador())
        .addValue("horario",agendamento.getHorario().toString())
        .addValue("data",agendamento.getDate());
    
    template.update(sql,param, holder);
    
  }

  @Override
  public Agendamento getById(String cpf_prestador, Date data, Horarios horario) throws Exception {
    String sql = "SELECT * FROM agendamento WHERE cpf_prestador = :cpf AND data = :data AND horario = :horario";
    KeyHolder holder = new GeneratedKeyHolder();
    
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("cpf_prestador", cpf_prestador)
        .addValue("data", data)
        .addValue("horario", horario.toString());
    List<Agendamento> listaProvisoria = template.query(sql, param, new AgendamentoRowMapper());
    if(listaProvisoria.size() <= 0)
      throw new Exception("Nao existe agendamento com esse prestador nesse horario!");
    
    return listaProvisoria.get(0);
  }

  @Override
  public void update(Agendamento agendamento, String cpf_prestador_param, Horarios horario_param, Date data_param) {
    String sql = "UPDATE agendamento set cpf_cliente = :cpf_cliente, cpf_prestador= :cpf_prestador, data = :data, horario = :horario WHERE cpf_prestador = :cpf_prestador_param AND horario = :horario_param AND data = :data_param ";
    KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("cpf_cliente",agendamento.getCpf_cliente())
        .addValue("cpf_prestador", agendamento.getCpf_prestador())
        .addValue("horario",agendamento.getHorario().toString())
        .addValue("data", agendamento.getDate())
        .addValue("cpf_prestador_param", cpf_prestador_param)
        .addValue("horario_param", horario_param)
        .addValue("data_param", data_param);
    
    template.update(sql,param, holder);
    
  }

  @Override
  public void delete(Agendamento agendamento) {
    

    String sql = "DELETE FROM agendamento WHERE cpf_prestador = :cpf_prestador AND horario =:horario AND data = :data";
    
    HashMap<String,Object> map = new HashMap<String,Object>();
    
    map.put("cpf_prestador", agendamento.getCpf_prestador());
    map.put("horario", agendamento.getHorario().toString());
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
  public List<Agendamento> listByCliente(String cpf_cliente) {
    String sql = "SELECT * FROM agendamento WHERE cpf_cliente = :cpf_cliente";
    KeyHolder holder = new GeneratedKeyHolder();
    
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("cpf_cliente", cpf_cliente); 
    return template.query(sql, param, new AgendamentoRowMapper());
  }

  @Override
  public List<Agendamento> listByPrestador(String cpf_prestador) {
    String sql = "SELECT * FROM agendamento WHERE cpf_prestador = :cpf_prestador";
    KeyHolder holder = new GeneratedKeyHolder();
    
    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("cpf_cliente", cpf_prestador); 
    return template.query(sql, param, new AgendamentoRowMapper());
  }
 public List<Prestador> listPrestadoresByDataAndHorario(Date data, Horarios horario){
   String sql = "SELECT p.* FROM agendamento a inner join prestador p ON p.cpf = a.cpf_prestador where a.data = :data AND a.horario = :horario";
   
   
   SqlParameterSource param = new MapSqlParameterSource()
       .addValue("data",data)
       .addValue("horario", horario.toString());
   return template.query(sql, param, new PrestadorRowMapper());
 }
 
 /**
  * 
  * Recebe o mes e o prestador e retorna os dias no mes em que o prestador ja esta indisponivel
  * @param mes
  * @param cpf_prestador
  * @return
  */
 
 public ArrayList<Integer> listDiasOcupadosByMesAndPrestador(String mes, String cpf_prestador){
   String sql = "select EXTRACT(DAY FROM a.data) as DIA, count(*) as QUANTIDADE from agendamento a where (EXTRACT(MONTH FROM a.data) = :mes) and (cpf_prestador = :cpf_prestador) group by EXTRACT(DAY FROM a.data)";
   SqlParameterSource param = new MapSqlParameterSource()
       .addValue("mes",Integer.valueOf(mes))
       .addValue("cpf_prestador", cpf_prestador);
   List<DiasOcupadosDTO>  diasOcupados = template.query(sql, param, new DiasOcupadosRowMapper());
   
 
   
   ArrayList<Integer> diaOcupadoIndisponivel = new ArrayList<>();
   
   diasOcupados.forEach(dia -> {
     if(dia.getQuantidade() >= Cliente.quantidadeHorarios.MAX()) // nao tem horario disponivel
       diaOcupadoIndisponivel.add(dia.getDiaOcupado());
   });
   
   
   
   return diaOcupadoIndisponivel;
 }
 

 private List<DiasOcupadosComCpfPrestadorDTO> getDiasSaturados(Date date, String cpf_prestador){
   Integer mes = date.toLocalDate().getMonthValue();
   
   String sql = "select * from (select EXTRACT(DAY FROM a.data) as DIA, count(*) as QUANTIDADE, a.cpf_prestador from agendamento a where (EXTRACT(MONTH FROM a.data) = :mes) and (cpf_prestador = :cpf_prestador)group by EXTRACT(DAY FROM a.data), cpf_prestador) as dias_ocupados where dias_ocupados.quantidade >= :QTD_MAX";
   
   SqlParameterSource param = new MapSqlParameterSource()
       .addValue("mes",mes)
       .addValue("cpf_prestador", cpf_prestador)
       .addValue("QTD_MAX", Cliente.quantidadeHorarios.MAX());
   
   return template.query(sql, param, new DiasOcupadosComCpfPrestadorRowMapper());
 }
   

 
 public List<String> listHorariosOcupadosByData(Date date, String cpf_prestador){
   
   String sql = "select horario from agendamento a where a.data = :data and cpf_prestador = :cpf_prestador";
   SqlParameterSource param = new MapSqlParameterSource()
       .addValue("data",date)
       .addValue("cpf_prestador", cpf_prestador);
   
   return template.query(sql, param, (rs, arg0) -> {     
     String horario = rs.getString("horario");
     return horario;
   });
 }
 public List<Agendamento> searchByCliente(Agendamento agendamento) {
   String sql = "select * from agendamento a where a.horario = :horario and a.\"data\"  =:data and a.cpf_cliente = :cpf_cliente";
   
   SqlParameterSource param = new MapSqlParameterSource()
       .addValue("horario",agendamento.getHorario().toString())
       .addValue("cpf_cliente", agendamento.getCpf_cliente())
       .addValue("data",agendamento.getDate());
   
   return template.query(sql, param, new AgendamentoRowMapper());
   
 }
 public List<Agendamento> searchByPrestador(Agendamento agendamento){
 String sql = "select * from agendamento a where a.horario = :horario and a.\"data\"  =:data and a.cpf_prestador = :cpf_prestador";
 
 SqlParameterSource param = new MapSqlParameterSource()
     .addValue("horario",agendamento.getHorario().toString())
     .addValue("cpf_prestador", agendamento.getCpf_prestador())
     .addValue("data",agendamento.getDate());
 return template.query(sql, param, new AgendamentoRowMapper());
}
}
