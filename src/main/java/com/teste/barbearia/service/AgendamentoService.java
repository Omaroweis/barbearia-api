package com.teste.barbearia.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.teste.barbearia.model.dao.AgendamentoDAO;
import com.teste.barbearia.model.dao.ClienteDAO;
import com.teste.barbearia.model.dao.PrestadorDAO;
import com.teste.barbearia.model.dto.DiaDisponiveisPorMesDTO;
import com.teste.barbearia.model.dto.HorariosDisponiveisPorDiaDTO;
import com.teste.barbearia.model.entity.Agendamento;
import com.teste.barbearia.model.entity.Prestador;
import com.teste.barbearia.model.enuns.Horarios;
@Component
public class AgendamentoService {
  
  @Resource 
  private ClienteDAO clienteDAO;
  @Resource 
  private PrestadorDAO prestadorDAO;
  @Resource 
  private AgendamentoDAO agendamentoDAO;
  
  public List<Integer> listaPrestadoresByMes(DiaDisponiveisPorMesDTO diaDisponiveisPorMesDTO)throws Exception{
    if(Integer.valueOf(diaDisponiveisPorMesDTO.getMes()) > 12) {
      throw new Exception("Mes invalido!");
    }
    if(prestadorDAO.getById(diaDisponiveisPorMesDTO.getId_prestador()) == null)
      throw new Exception("Prestador invalido!");
    
   List<Integer> ocupados = agendamentoDAO.listDiasOcupadosByMesAndPrestador(diaDisponiveisPorMesDTO.getMes(), diaDisponiveisPorMesDTO.getId_prestador());
 
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
  
  public Agendamento agendar(Agendamento agendamento) throws Exception {
    
    System.out.println("SERVICE######");
    
    System.out.println(agendamento);
    
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
    // TODO: tratar exceções
    System.out.println("TESTE#################");
    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println();
    this.agendamentoDAO.save(agendamento);
    return agendamentoDAO.getByConstraint(agendamento.getId_prestador(), agendamento.getDate(), agendamento.getHorario());
  
    

  }
  
private boolean IsHorarioInvaldo(Horarios horario) {
 
    
    Time agora = Time.valueOf(LocalTime.now());
    
    if(agora.compareTo(horario.getHora()) <= 0) {
      return false;
    }
    return true;
  }

  public String deletarAgendamento(Long id) throws Exception{
    
   // tratar exceções
    this.agendamentoDAO.delete(id);
    return "Agendamento removido com sucesso!";
    
   
  }
  
  public ArrayList<String> ListHorarioByDia(HorariosDisponiveisPorDiaDTO horariosDisponiveisPorDiaDTO) {
    List<LocalTime> horariosOcupados = this.agendamentoDAO.listHorariosOcupadosByData(horariosDisponiveisPorDiaDTO.getDate(), horariosDisponiveisPorDiaDTO.getId());
    
    
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    final LocalTime dateStart = LocalTime.parse(Horarios.OITO.getHora().toString(), formatter);
    final LocalTime dateEnd = LocalTime.parse(Horarios.DEZESSETE.getHora().toString(), formatter);
    final int hourInterval = 1;
    
    HashSet<String> todos = new HashSet<>();
    for(LocalTime data = dateStart; data.isBefore(dateEnd); data = data.plusHours(hourInterval)) {
      todos.add(data.toString());
    }
    

    HashSet<String> horariosOcupadosString  = new HashSet<>();
    horariosOcupados.forEach(h-> horariosOcupadosString.add(h.toString()));    
    
    todos.removeAll(horariosOcupadosString);
    return new ArrayList<String>(todos);
    
  }
  
  public ArrayList<Prestador> prestadoresDisponiveisPeloHorario(Agendamento agendamento){
    Date data = agendamento.getDate();
    Horarios horario = agendamento.getHorario();
    
   
    
    HashSet<Prestador> ocupadosPorHorario = new HashSet<>((ArrayList<Prestador>) agendamentoDAO.listPrestadoresByDataAndHorario(data, horario));
    
    HashSet<Prestador> todos = new HashSet<>((ArrayList<Prestador>) prestadorDAO.list());   

    todos.removeAll(ocupadosPorHorario);
    
    return new ArrayList<>(todos);
  }
  public Agendamento getById(Long id) {
   
    return this.agendamentoDAO.getById(id);
  }
}
