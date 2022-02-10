package com.teste.barbearia.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import java.sql.Time;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.teste.barbearia.model.dao.AgendamentoDAO;
import com.teste.barbearia.model.dao.ClienteDAO;
import com.teste.barbearia.model.dao.PrestadorDAO;
import com.teste.barbearia.model.dto.DiaDisponiveisPorMesDTO;
import com.teste.barbearia.model.entity.Agendamento;
import com.teste.barbearia.model.entity.Cliente;
import com.teste.barbearia.model.entity.Prestador;
import com.teste.barbearia.model.enuns.Horarios;
import com.teste.barbearia.model.enuns.NumeroHorariosOfertados;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;

@Component
public class ClienteService {
  @Resource 
  private ClienteDAO clienteDAO;
  @Resource 
  private PrestadorDAO prestadorDAO;
  @Resource 
  private AgendamentoDAO agendamentoDAO;
  
  public List<Cliente> listar(){
    return clienteDAO.list();
  }
  
  public Cliente insere(Cliente cliente) {
    CPFValidator cpfValidator = new CPFValidator(); 
    try {
      cpfValidator.assertValid(cliente.getCpf()); 
    }catch (Exception e) {
      throw e;
    }
    clienteDAO.save(cliente);
    return cliente;
  }
  
  public ArrayList<Prestador> prestadoresDisponiveisPeloHorario(Agendamento agendamento){
    Date data = agendamento.getDate();
    Horarios horario = agendamento.getHorario();
    
   
    
    HashSet<Prestador> ocupadosPorHorario = new HashSet<>((ArrayList<Prestador>) agendamentoDAO.listPrestadoresByDataAndHorario(data, horario));
    
    HashSet<Prestador> todos = new HashSet<>((ArrayList<Prestador>) prestadorDAO.list());   

    todos.removeAll(ocupadosPorHorario);
    
    return new ArrayList<>(todos);
  }
  
  public Cliente getCliente(String cpf) throws Exception {
    return clienteDAO.getById(cpf);
  }
  
  public Cliente updateCliente(Cliente cliente) throws Exception {
    CPFValidator cpfValidator = new CPFValidator();
    List<ValidationMessage> erros = cpfValidator.invalidMessagesFor(cliente.getCpf());
    clienteDAO.update(cliente);
    return cliente;
  }
  
  public List<Prestador> listAllPrestadores(){
    return prestadorDAO.list();
  }
  public List<Integer> listaPrestadoresByMes(DiaDisponiveisPorMesDTO diaDisponiveisPorMesDTO)throws Exception{
    if(Integer.valueOf(diaDisponiveisPorMesDTO.getMes()) > 12) {
      throw new Exception("Mes invalido!");
    }
    if(prestadorDAO.search(diaDisponiveisPorMesDTO.getCpf_prestador()).isEmpty())
      throw new Exception("Prestador invalido!");
    
   List<Integer> ocupados = agendamentoDAO.listDiasOcupadosByMesAndPrestador(diaDisponiveisPorMesDTO.getMes(), diaDisponiveisPorMesDTO.getCpf_prestador());
 
   Integer numeroDiasMes = this.quantidadeDiasPorMes(diaDisponiveisPorMesDTO.getMes());
   List<Integer> disponiveis = new ArrayList<>();
     for(int i=1; i<=numeroDiasMes; ++i) {
         if(!ocupados.contains(i))
           disponiveis.add(i);
   }
     return disponiveis;
  }
  private Integer quantidadeDiasPorMes(String mes) {
    switch (Integer.valueOf(mes)) {
      case 2:
          return 28;
      case 4:
      case 6:
      case 9:
      case 11:
          return 30;
      default:
          return 31;
  }
  }

  public ArrayList<String> ListHorarioByDia(Date data, String cpf_prestador) {
    List<String> horariosOcupados = this.agendamentoDAO.listHorariosOcupadosByData(data, cpf_prestador);
    Horarios[] horarios = Horarios.values();
    HashSet<String> todos = new HashSet<>();
    for(Horarios horario: horarios) {
      todos.add(horario.toString());
    }
    todos.removeAll(horariosOcupados);
    return new ArrayList<String>(todos);
    
  }

  public Agendamento agendar(Agendamento agendamento) throws Exception {

    LocalDate dataAgendamento = agendamento.getDate().toLocalDate();
    LocalDate hoje = LocalDate.now();
    if(hoje.isAfter(dataAgendamento)) {
      throw new Exception("Impossivel agendar para o passado");
    }
    if(hoje.equals(dataAgendamento)) {
      if(IsHorarioInvaldo(agendamento.getHorario())) {
        throw new Exception("Esse horario ja passou");
      }
    }
    if(!this.agendamentoDAO.searchByCliente(agendamento).isEmpty()) {
      throw new Exception("Cliente ja tem agendamento marcado pra esse dia e horario");
    }

    try {
    this.agendamentoDAO.save(agendamento);
    return agendamento;
    }catch (Exception e) {
      throw new Exception("Falha ao concluir agendamento!");
    }
    

  }
  private boolean IsHorarioInvaldo(Horarios horario) {
 
    
    Time agora = Time.valueOf(LocalTime.now());
    
    if(agora.compareTo(horario.getHora()) <= 0) {
      return false;
    }
    return true;
  }

  public String deletarAgendamento(Agendamento agendamento) throws Exception{
    
    if(this.agendamentoDAO.searchByPrestador(agendamento).isEmpty())
      throw new Exception("Horario nao esta agendado para este prestador nesse dia!");
    this.agendamentoDAO.delete(agendamento);
    return "Agendamento removido com sucesso!";
    
   
  }

  public String deletarCliente(Cliente cliente) throws Exception {
   if(this.clienteDAO.search(cliente.getCpf()).isEmpty()) {
     throw new Exception("Nao existe nenhum cliente com esses dados!");
   }
    this.clienteDAO.delete(cliente);
   return "Cliente removido com sucesso!";
  }
}
