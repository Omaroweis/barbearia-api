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

import org.hibernate.hql.internal.classic.AbstractParameterInformation;
import org.springframework.stereotype.Component;

import com.teste.barbearia.exception.ApiRequestException;
import com.teste.barbearia.model.dao.AgendamentoDAO;
import com.teste.barbearia.model.dao.ClienteDAO;
import com.teste.barbearia.model.dao.PrestadorDAO;
import com.teste.barbearia.model.dto.DiaDisponiveisPorMesDTO;
import com.teste.barbearia.model.dto.HorariosDisponiveisPorDiaDTO;
import com.teste.barbearia.model.entity.Agendamento;
import com.teste.barbearia.model.entity.Prestador;
import com.teste.barbearia.model.enuns.Horarios;
import com.teste.barbearia.model.enuns.Mensagens;
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
      throw new ApiRequestException(Mensagens.ERRO_MES_INVALIDO.getMensagem());
    }
    if(prestadorDAO.getById(diaDisponiveisPorMesDTO.getId_prestador()).isEmpty())
      throw new ApiRequestException(Mensagens.ERRO_PRESTADOR_INVALIDO.getMensagem());
    
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
  
  public Agendamento agendar(Agendamento agendamento) {
    
    System.out.println(agendamento);
    
    if(agendamento.getHorario() == null)
      throw new ApiRequestException(Mensagens.ERRO_HORARIO_INVALIDO.getMensagem());
    if(agendamento.getDate() == null)
      throw new ApiRequestException(Mensagens.ERRO_DATA_INVALIDA.getMensagem());
    if(agendamento.getId_cliente() == null)
      throw new ApiRequestException(Mensagens.ERRO_CLIENTE_INVALIDO.getMensagem());
    if(agendamento.getId_prestador() == null)
      throw new ApiRequestException(Mensagens.ERRO_PRESTADOR_INVALIDO.getMensagem());
    if(this.clienteDAO.getById(agendamento.getId_cliente()).isEmpty()) 
      throw new ApiRequestException(Mensagens.ERRO_CLIENTE_INVALIDO.getMensagem());
    if(this.prestadorDAO.getById(agendamento.getId_prestador()).isEmpty())
      throw new ApiRequestException(Mensagens.ERRO_PRESTADOR_INVALIDO.getMensagem());
    
    LocalDate dataAgendamento = agendamento.getDate().toLocalDate();
    LocalDate hoje = LocalDate.now();
    if(hoje.isAfter(dataAgendamento)) {
      throw new ApiRequestException(Mensagens.ERRO_AGENDAMENTO_PASSADO.getMensagem());
    }
    if(hoje.equals(dataAgendamento)) {
      if(IsHorarioInvaldo(agendamento.getHorario())) {
        throw new ApiRequestException(Mensagens.ERRO_AGENDAMENTO_PASSADO.getMensagem());
      }
    }
    if(!this.agendamentoDAO.searchByCliente(agendamento).isEmpty()) {
      throw new ApiRequestException(Mensagens.ERRO_AGENDAMENTO_MARCADO_MESMO_HORARIO_CLIENTE.getMensagem());
    }
    
    this.agendamentoDAO.save(agendamento);
    return agendamentoDAO.getByConstraint(agendamento.getId_prestador(), agendamento.getDate(), agendamento.getHorario()).get(0);
  
    

  }
  
private boolean IsHorarioInvaldo(Horarios horario) {
 
    
    Time agora = Time.valueOf(LocalTime.now());
    
    if(agora.compareTo(horario.getHora()) <= 0) {
      return false;
    }
    return true;
  }

  public String deletarAgendamento(Long id) throws Exception{
    
    if(this.agendamentoDAO.getById(id).isEmpty())
      throw new ApiRequestException(Mensagens.ERRO_AGENDAMENTO_NAO_EXISTE.getMensagem());
    this.agendamentoDAO.delete(id);
    return "Agendamento removido com sucesso!";
    
   
  }
  
  public ArrayList<String> ListHorarioByDia(HorariosDisponiveisPorDiaDTO horariosDisponiveisPorDiaDTO) {
    
    if(horariosDisponiveisPorDiaDTO.getDate() == null)
      throw new ApiRequestException(Mensagens.ERRO_DATA_INVALIDA.getMensagem());
    
    if(this.prestadorDAO.getById(horariosDisponiveisPorDiaDTO.getId()).isEmpty())
      throw new ApiRequestException(Mensagens.ERRO_PRESTADOR_INVALIDO.getMensagem());
    
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
    horariosOcupados.forEach(h-> 
    horariosOcupadosString.add(h.toString()));    
    
    todos.removeAll(horariosOcupadosString);
    return new ArrayList<String>(todos);
    
  }
  
  public Agendamento getById(Long id) {
   if(this.agendamentoDAO.getById(id).isEmpty())
     throw new ApiRequestException(Mensagens.ERRO_AGENDAMENTO_NAO_EXISTE.getMensagem());
    return this.agendamentoDAO.getById(id).get(0);
  }
}
