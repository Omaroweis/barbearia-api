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

import com.teste.barbearia.exception.ApiRequestException;
import com.teste.barbearia.model.dto.ClienteDTO;
import com.teste.barbearia.model.dto.ClienteSaidaDTO;
import com.teste.barbearia.model.dto.DiaDisponiveisPorMesDTO;
import com.teste.barbearia.model.dto.HorariosDisponiveisPorDiaDTO;
import com.teste.barbearia.model.entity.Agendamento;
import com.teste.barbearia.model.entity.Cliente;
import com.teste.barbearia.model.entity.Prestador;
import com.teste.barbearia.model.enuns.Horarios;
import com.teste.barbearia.model.enuns.Mensagens;
import com.teste.barbearia.service.ClienteService;
@RestController
@RequestMapping("/clientes")
public class ClienteController {
  
  @Resource
  private ClienteService clienteService;
  @ResponseStatus(value = HttpStatus.OK)
  @GetMapping
   public List<Cliente> listar(){
    return clienteService.listar();
   }
  @ResponseStatus(value = HttpStatus.OK)
  @PostMapping
  public ClienteSaidaDTO inserir(@RequestBody ClienteDTO cliente) {
    return clienteService.insere(cliente);
    
  }
  @ResponseStatus(value = HttpStatus.OK)
  @GetMapping(value = "/obterCliente")
  public Cliente getCliente(@RequestParam(required = false) String cpf, @RequestParam(required = false) Long id) throws Exception {
    if(cpf != null)
      return clienteService.getCliente(cpf);
    if(id != null)
      return clienteService.getCliente(id);
    
    throw new ApiRequestException(Mensagens.ERRO_FALTA_ID_CPF.getMensagem());
  }
  
  @PutMapping(value="/atualizar")
  @ResponseStatus(value = HttpStatus.OK)
  public ClienteSaidaDTO atualiza(@RequestBody ClienteDTO clienteDTO) throws Exception {
    return clienteService.updateCliente(clienteDTO);
  }
  @ResponseStatus(value = HttpStatus.OK)
  @GetMapping(value="/listarPrestadores")
  public List<Prestador> obterPrestadores() {
    return clienteService.listAllPrestadores();
  }
  
  
  @ResponseStatus(value=HttpStatus.OK)
  @DeleteMapping(value = "/DeletarCliente")
  public String apagarCliente(@RequestParam Long id) throws Exception{
    return this.clienteService.deletarCliente(id);
  }
}
