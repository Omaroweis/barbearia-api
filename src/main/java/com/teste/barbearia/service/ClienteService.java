package com.teste.barbearia.service;

import java.util.ArrayList;

import com.teste.barbearia.exception.ApiRequestException;
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
import org.springframework.http.HttpStatus;
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
import com.teste.barbearia.model.enuns.Mensagens;
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

    
    if(!this.clienteDAO.search(cliente.getCpf()).isEmpty()) {
      throw new ApiRequestException(Mensagens.ERRO_CPF_JA_EXISTE.getMensagem());
    }
    if(cliente.getCpf().isEmpty() || cliente.getNome().isEmpty())
      throw new ApiRequestException(Mensagens.ERRO_INPUT_CLIENTE_VAZIO.getMensagem());
    
      
    try {
      new CPFValidator().assertValid(cliente.getCpf()); 
    }catch (Exception e) {
      throw new ApiRequestException(Mensagens.ERRO_CPF_INVALIDO.getMensagem());
    }
    
    clienteDAO.save(cliente);
    return clienteDAO.search(cliente.getCpf()).get(0);
  }
  
  public Cliente getCliente(String cpf) throws Exception {
    
    if(this.clienteDAO.search(cpf).isEmpty())
      throw new ApiRequestException(Mensagens.ERRO_CPF_NAO_EXISTE.getMensagem());
    
    Cliente cliente = this.clienteDAO.search(cpf).get(0);
    
    return clienteDAO.getById(cliente.getId()).get(0);
  }
  public Cliente getCliente(Long id) {
    
    if(this.clienteDAO.getById(id).isEmpty())
      throw new ApiRequestException(Mensagens.ERRO_ID_NAO_EXISTE.getMensagem());
    return clienteDAO.getById(id).get(0);
  }
  
  public Cliente updateCliente(Cliente cliente) throws Exception {
    
    if(this.clienteDAO.getById(cliente.getId()).isEmpty()) {
      throw new ApiRequestException(Mensagens.ERRO_ID_NAO_EXISTE.getMensagem());
    }
        
    try {
      new CPFValidator().assertValid(cliente.getCpf()); 
    }catch (Exception e) {
      throw new ApiRequestException(Mensagens.ERRO_CPF_INVALIDO.getMensagem());
    }
    
    if(!this.clienteDAO.search(cliente.getCpf()).isEmpty()) {
      // verifica se o cpf que replicou era referente ao antigo registro do usuario no banco
      if (this.clienteDAO.search(cliente.getCpf()).get(0).getId() != cliente.getId())
        throw new ApiRequestException(Mensagens.ERRO_CPF_JA_EXISTE.getMensagem());
      
    }
    Long id = cliente.getId();
    clienteDAO.update(cliente, id);
    return this.clienteDAO.getById(id).get(0);
  }
  
  public List<Prestador> listAllPrestadores(){
    return prestadorDAO.list();
  }
  

  public String deletarCliente(Long id) throws Exception {
    
    if(this.clienteDAO.getById(id).isEmpty())
      throw new ApiRequestException(Mensagens.ERRO_ID_NAO_EXISTE.getMensagem());
       
    this.clienteDAO.delete(id);
   return Mensagens.SUCESSO_REMOVER_CLIENTE.getMensagem();
  }
}
