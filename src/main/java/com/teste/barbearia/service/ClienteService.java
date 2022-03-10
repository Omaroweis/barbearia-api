package com.teste.barbearia.service;

import java.util.ArrayList;

import com.teste.barbearia.exception.ApiRequestException;
import com.teste.barbearia.factory.Factory;

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
import com.teste.barbearia.model.dao.EnderecoDAO;
import com.teste.barbearia.model.dao.PrestadorDAO;
import com.teste.barbearia.model.dto.ClienteDTO;
import com.teste.barbearia.model.dto.ClienteSaidaDTO;
import com.teste.barbearia.model.dto.DiaDisponiveisPorMesDTO;
import com.teste.barbearia.model.entity.Agendamento;
import com.teste.barbearia.model.entity.Cliente;
import com.teste.barbearia.model.entity.Endereco;
import com.teste.barbearia.model.entity.Prestador;
import com.teste.barbearia.model.enuns.Horarios;
import com.teste.barbearia.model.enuns.Mensagens;
import com.teste.barbearia.model.enuns.NumeroHorariosOfertados;
import com.teste.barbearia.provider.ApiEndereco;
import com.teste.barbearia.provider.dto.EnderecoViaCepDTO;
import com.teste.barbearia.provider.dto.interfaces.EnderecoDTO;
import com.teste.barbearia.provider.implementacoes.ApiGeoCode;
import com.teste.barbearia.provider.implementacoes.ApiViaCep;
import com.teste.barbearia.utils.EnderecoUtils;

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
  @Resource
  private EnderecoService enderecoService;
  
  
  public List<Cliente> listar(){
    return clienteDAO.list();
  }
  
  public ClienteSaidaDTO insere(ClienteDTO cliente) {
    
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

    
    Endereco endereco = this.enderecoService.Request(cliente.getPais(), cliente.getCep());
    Long id_endereco = this.enderecoService.save(endereco, cliente.getComplemento());
           
    clienteDAO.save(cliente, id_endereco);
   
    Cliente c = clienteDAO.search(cliente.getCpf()).get(0);
    return new ClienteSaidaDTO(c, this.enderecoService.getById(id_endereco));
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
  
  public ClienteSaidaDTO updateCliente(ClienteDTO clienteDTO) throws Exception {
    
    if(this.clienteDAO.getById(clienteDTO.getId()).isEmpty()) {
      throw new ApiRequestException(Mensagens.ERRO_ID_NAO_EXISTE.getMensagem());
    }
        
    try {
      new CPFValidator().assertValid(clienteDTO.getCpf()); 
    }catch (Exception e) {
      throw new ApiRequestException(Mensagens.ERRO_CPF_INVALIDO.getMensagem());
    }
    
    if(!this.clienteDAO.search(clienteDTO.getCpf()).isEmpty()) {
      // verifica se o cpf que replicou era referente ao antigo registro do usuario no banco
      if (this.clienteDAO.search(clienteDTO.getCpf()).get(0).getId() != clienteDTO.getId())
        throw new ApiRequestException(Mensagens.ERRO_CPF_JA_EXISTE.getMensagem());
      
    }
    Long id = clienteDTO.getId();
    Endereco endereco = this.enderecoService.Request(clienteDTO.getPais(), clienteDTO.getCep());
    Long id_endereco = this.enderecoService.save(endereco,clienteDTO.getComplemento());
    clienteDAO.update(clienteDTO, id, id_endereco);
    Cliente c =  this.clienteDAO.getById(id).get(0);
    return new ClienteSaidaDTO(c, this.enderecoService.getById(id_endereco));
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
