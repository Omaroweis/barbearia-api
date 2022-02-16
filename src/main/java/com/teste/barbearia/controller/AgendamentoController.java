package com.teste.barbearia.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.teste.barbearia.model.dto.DiaDisponiveisPorMesDTO;
import com.teste.barbearia.model.dto.HorariosDisponiveisPorDiaDTO;
import com.teste.barbearia.model.entity.Agendamento;
import com.teste.barbearia.model.entity.Prestador;
import com.teste.barbearia.service.AgendamentoService;
import com.teste.barbearia.service.ClienteService;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {
  
  @Resource
  private AgendamentoService agendamentoService;
  
  @ResponseStatus(value = HttpStatus.OK)
  @GetMapping(value = "/PrestadoresPorMes")
  public List<Integer> obterPrestadoresPorMes(@RequestBody DiaDisponiveisPorMesDTO diaDisponiveisPorMesDTO) throws Exception{
    return this.agendamentoService.listaPrestadoresByMes(diaDisponiveisPorMesDTO);
  }
  
  @ResponseStatus(value=HttpStatus.OK)
  @PostMapping(value = "/Agendar")
  public Agendamento agendar(@RequestBody Agendamento agendamento) throws Exception {
    try{
      return this.agendamentoService.agendar(agendamento);
    }catch (Exception e) {
      throw e;
    }
  }
  
  @ResponseStatus(value=HttpStatus.OK)
  @GetMapping(value = "/HorariosPorDia")
  public List<String> obterHorariosPorDia(@RequestBody HorariosDisponiveisPorDiaDTO horariosDisponiveisPorDiaDTO ){
    return this.agendamentoService.ListHorarioByDia(horariosDisponiveisPorDiaDTO);
  }
  
  @ResponseStatus(value=HttpStatus.OK)
  @DeleteMapping(value = "/DeletarAgendamento")
  public String apagarAgendamento(@RequestParam Long id) throws Exception{
    return this.agendamentoService.deletarAgendamento(id);
  }
  
  @ResponseStatus(value = HttpStatus.OK)
  @GetMapping(value = "/prestadoresDisponiveis")
  public List<Prestador> getPrestadoresPelaData(@RequestBody Agendamento agendamento) {
    return agendamentoService.prestadoresDisponiveisPeloHorario(agendamento);
  }
  
  @ResponseStatus(value = HttpStatus.OK)
  @GetMapping(value = "/ObterPorId")
  public Agendamento getById(@RequestParam Long id) throws Exception{
    return this.agendamentoService.getById(id);
  }
}
