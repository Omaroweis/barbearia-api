package com.teste.barbearia.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.teste.barbearia.exception.ApiRequestException;
import com.teste.barbearia.model.dao.AgendamentoDAO;
import com.teste.barbearia.model.dao.PrestadorDAO;
import com.teste.barbearia.model.dto.PrestadorDTO;
import com.teste.barbearia.model.dto.PrestadorSaidaDTO;
import com.teste.barbearia.model.entity.Endereco;
import com.teste.barbearia.model.entity.Prestador;
import com.teste.barbearia.model.enuns.Mensagens;

import br.com.caelum.stella.validation.CPFValidator;
@Component
public class PrestadorService {
  @Resource 
  private PrestadorDAO prestadorDAO;
  @Resource
  private EnderecoService enderecoService;
  
  
  public PrestadorSaidaDTO insere(PrestadorDTO prestadorDTO) {
    
    if(!this.prestadorDAO.search(prestadorDTO.getCpf()).isEmpty()) {
      throw new ApiRequestException(Mensagens.ERRO_CPF_JA_EXISTE.getMensagem());
    }
    
    if(prestadorDTO.getCpf().isEmpty() || prestadorDTO.getNome().isEmpty())
      throw new ApiRequestException(Mensagens.ERRO_INPUT_PRESTADOR_VAZIO.getMensagem());
    
    try {
      new CPFValidator().assertValid(prestadorDTO.getCpf()); 
    }catch (Exception e) {
      throw new ApiRequestException(Mensagens.ERRO_CPF_INVALIDO.getMensagem());
    }
    
    Endereco endereco = this.enderecoService.Request(prestadorDTO.getPais(), prestadorDTO.getCep());
    Long id_endereco = this.enderecoService.save(endereco,prestadorDTO.getComplemento());;
    
   
   prestadorDAO.save(prestadorDTO, id_endereco);
   Prestador p =  this.prestadorDAO.search(prestadorDTO.getCpf()).get(0);
   return new PrestadorSaidaDTO(p, this.enderecoService.getById(id_endereco));
   
   
  }
  
  public Prestador getPrestador(Long id) {
    if(this.prestadorDAO.getById(id).isEmpty())
      throw new ApiRequestException(Mensagens.ERRO_ID_NAO_EXISTE.getMensagem());
    
    return prestadorDAO.getById(id).get(0);
  }
  
  public Prestador getPrestador(String cpf) throws Exception {
    if(this.prestadorDAO.search(cpf).isEmpty())
      throw new ApiRequestException(Mensagens.ERRO_CPF_NAO_EXISTE.getMensagem());
    
    Prestador prestador = this.prestadorDAO.search(cpf).get(0);
    
    return prestadorDAO.getById(prestador.getId()).get(0);
  }

  public PrestadorSaidaDTO updatePrestador(PrestadorDTO prestadorDTO) throws Exception{
   
    if(this.prestadorDAO.getById(prestadorDTO.getId()).isEmpty()) {
      throw new ApiRequestException(Mensagens.ERRO_ID_NAO_EXISTE.getMensagem());
    }
    
    try {
      new CPFValidator().assertValid(prestadorDTO.getCpf()); 
    }catch (Exception e) {
      throw new ApiRequestException(Mensagens.ERRO_CPF_INVALIDO.getMensagem());
    }
    
    if(!this.prestadorDAO.search(prestadorDTO.getCpf()).isEmpty()) {
      // verifica se o cpf que replicou era referente ao antigo registro do usuario no banco
      if (this.prestadorDAO.search(prestadorDTO.getCpf()).get(0).getId() != prestadorDTO.getId())
        throw new ApiRequestException(Mensagens.ERRO_CPF_JA_EXISTE.getMensagem());
    }
     Long id = prestadorDTO.getId();
     if(this.prestadorDAO.getById(id) == null) {
       throw new Exception();
     }
     
     Endereco endereco = this.enderecoService.Request(prestadorDTO.getPais(), prestadorDTO.getCep());
     Long id_endereco = this.enderecoService.save(endereco,prestadorDTO.getComplemento());
     
     
     this.prestadorDAO.update(prestadorDTO, id, id_endereco);
     Prestador p = this.prestadorDAO.getById(id).get(0);
     return new PrestadorSaidaDTO(p, this.enderecoService.getById(id_endereco));
  }

  public String deletarPrestador(Long id)throws Exception {

    if(this.prestadorDAO.getById(id).isEmpty())
      throw new ApiRequestException(Mensagens.ERRO_ID_NAO_EXISTE.getMensagem());
    this.prestadorDAO.delete(id);
    return Mensagens.SUCESSO_REMOVER_PRESTADOR.getMensagem();
  }

  public List<String> getPrestadorByEndereco(String cep) {
    if(!cep.matches("[a-z|A-Z]+.+")) {
    String finalCep = cep.subSequence(0, 5) + "-" + cep.substring(5, cep.length());
    return  this.enderecoService.getPrestadorByEndereco(finalCep);
    }
    return  this.enderecoService.getPrestadorByEndereco(cep);
  }

}
