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
//    TODO: tratar exceçoes
    CPFValidator cpfValidator = new CPFValidator(); 
    try {
      cpfValidator.assertValid(cliente.getCpf()); 
    }catch (Exception e) {
      throw e;
    }
    clienteDAO.save(cliente);
    return clienteDAO.search(cliente.getCpf()).get(0);
  }
  
  public Cliente getCliente(String cpf) throws Exception {
    // TODO: tratar exceção
    Cliente cliente = this.clienteDAO.search(cpf).get(0);
    
    return clienteDAO.getById(cliente.getId());
  }
  public Cliente getCliente(Long id) {
    // TODO: tratar excecoes
    return clienteDAO.getById(id);
  }
  
  public Cliente updateCliente(Cliente cliente) throws Exception {
    // TODO: tratar exceçoes
    CPFValidator cpfValidator = new CPFValidator();
    List<ValidationMessage> erros = cpfValidator.invalidMessagesFor(cliente.getCpf());
    Long id = cliente.getId();
    clienteDAO.update(cliente, id);
    return this.clienteDAO.getById(id);
  }
  
  public List<Prestador> listAllPrestadores(){
    return prestadorDAO.list();
  }
  

  public String deletarCliente(Long id) throws Exception {
  // TODO: Tratar exceções
    this.clienteDAO.delete(id);
   return "Cliente removido com sucesso!";
  }
}
