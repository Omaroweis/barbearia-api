package com.teste.barbearia.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.teste.barbearia.model.dto.DiaDisponiveisPorMesDTO;
import com.teste.barbearia.model.dto.HorariosDisponiveisPorDiaDTO;
import com.teste.barbearia.model.entity.Agendamento;
import com.teste.barbearia.model.entity.Cliente;
import com.teste.barbearia.model.entity.Prestador;
import com.teste.barbearia.model.enuns.Horarios;
import com.teste.barbearia.service.ClienteService;
@RestController
@RequestMapping("/clientes")
public class ClienteController {
  
  @Resource
  private ClienteService clienteService;
  @ResponseStatus(value = HttpStatus.OK)
  @GetMapping(value = "/listarClientes")
   public List<Cliente> listar(){
    return clienteService.listar();
   }
  @ResponseStatus(value = HttpStatus.OK)
  @PostMapping(value = "/inserir")
  public Cliente inserir(@RequestBody Cliente cliente) {
    return clienteService.insere(cliente);
    
  }
  @ResponseStatus(value = HttpStatus.OK)
  @GetMapping(value = "/prestadoresDisponiveis")
  public List<Prestador> getPrestadoresPelaData(@RequestBody Agendamento agendamento) {
    return clienteService.prestadoresDisponiveisPeloHorario(agendamento);
  }
  @ResponseStatus(value = HttpStatus.OK)
  @GetMapping(value = "/obterCliente")
  public Cliente getCliente(@RequestParam(required = true) String cpf) throws Exception {
    return clienteService.getCliente(cpf);
  }
  
  @PutMapping(value="/atualizar")
  @ResponseStatus(value = HttpStatus.OK)
  public Cliente atualiza(@RequestBody Cliente cliente) throws Exception {
    return clienteService.updateCliente(cliente);
  }
  @ResponseStatus(value = HttpStatus.OK)
  @GetMapping(value="/listarPrestadores")
  public List<Prestador> obterPrestadores() {
    return clienteService.listAllPrestadores();
  }
  @ResponseStatus(value = HttpStatus.OK)
  @GetMapping(value = "/PrestadoresPorMes")
  public List<Integer> obterPrestadoresPorMes(@RequestBody DiaDisponiveisPorMesDTO diaDisponiveisPorMesDTO) throws Exception{
    return this.clienteService.listaPrestadoresByMes(diaDisponiveisPorMesDTO);
  }
  @ResponseStatus(value=HttpStatus.OK)
  @GetMapping(value = "/HorariosPorDia")
  public List<String> obterHorariosPorDia(@RequestBody HorariosDisponiveisPorDiaDTO horariosDisponiveisPorDiaDTO ){
    return this.clienteService.ListHorarioByDia(horariosDisponiveisPorDiaDTO.getDate(), horariosDisponiveisPorDiaDTO.getCpf_prestador());
  }
  
  @ResponseStatus(value=HttpStatus.OK)
  @PostMapping(value = "/Agendar")
  public Agendamento agendar(@RequestBody Agendamento agendamento) throws Exception {
    try{
      return this.clienteService.agendar(agendamento);
    }catch (Exception e) {
      throw e;
    }
  }
  @ResponseStatus(value=HttpStatus.OK)
  @DeleteMapping(value = "/DeletarAgendamento")
  public String apagarAgendamento(@RequestBody Agendamento agendamento) throws Exception{
    return this.clienteService.deletarAgendamento(agendamento);
  }
  
  @ResponseStatus(value=HttpStatus.OK)
  @DeleteMapping(value = "/DeletarCliente")
  public String apagarCliente(@RequestBody Cliente cliente) throws Exception{
    return this.clienteService.deletarCliente(cliente);
  }
}
