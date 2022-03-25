package com.teste.barbearia.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.teste.barbearia.exception.ApiRequestException;
import com.teste.barbearia.model.dao.AgendamentoDAO;
import com.teste.barbearia.model.dao.ClienteDAO;
import com.teste.barbearia.model.dao.PrestadorDAO;
import com.teste.barbearia.model.dto.ClienteDTO;
import com.teste.barbearia.model.dto.ClienteSaidaDTO;
import com.teste.barbearia.model.entity.Cliente;
import com.teste.barbearia.model.entity.Endereco;
import com.teste.barbearia.model.entity.Prestador;
import com.teste.barbearia.model.enuns.Mensagens;

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
