package com.teste.barbearia.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import javax.annotation.Resource;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.teste.barbearia.BarbeariaApplication;
import com.teste.barbearia.exception.ApiRequestException;
import com.teste.barbearia.factory.Factory;
import com.teste.barbearia.model.dao.ClienteDAO;
import com.teste.barbearia.model.dao.PrestadorDAO;
import com.teste.barbearia.model.dto.ClienteDTO;
import com.teste.barbearia.model.entity.Cliente;
import com.teste.barbearia.model.entity.Endereco;
import com.teste.barbearia.model.entity.Prestador;
import com.teste.barbearia.model.enuns.Mensagens;
import com.teste.barbearia.provider.ApiEndereco;
import com.teste.barbearia.provider.implementacoes.ApiGeoCode;
import com.teste.barbearia.provider.implementacoes.ApiViaCep;

@Transactional
@SpringBootTest(classes = BarbeariaApplication.class)
public class ClienteServiceTest {
  @Resource 
  @InjectMocks
  private PrestadorDAO prestadorDAO;
  @InjectMocks
  @Resource 
  private EnderecoService enderecoService;
  @InjectMocks
  @Resource 
  private ClienteDAO clienteDAO;
  @Resource
  @InjectMocks
  private ClienteService clienteService;
    @Test
    public void deveriaRejeitarCpfRepetidoInserirCliente() {
      ClienteDTO clienteDTO = new ClienteDTO();
     
      
      clienteDTO.setCpf("70853968144");
      //assertThrows(ApiRequestException.class, () -> clienteService.insere(clienteDTO));
      try {
        this.clienteService.insere(clienteDTO);
        fail();
      }catch (Exception e) {
        assertEquals(Mensagens.ERRO_CPF_JA_EXISTE.getMensagem(), e.getMessage());
      }
    }
    @Test
    public void deveriaRejeitarNomeVazioInserirCliente() {
      ClienteDTO clienteDTO = new ClienteDTO();
      clienteDTO.setCpf("44217328001");
      clienteDTO.setNome("");
      //assertThrows(ApiRequestException.class, () -> clienteService.insere(clienteDTO));
      try {
        this.clienteService.insere(clienteDTO);
        fail("HOUVE FALHA NA VALIDAÇÂO DO NOME QUANDO INSERE VAZIO");
      }catch (Exception e) {
        assertEquals(Mensagens.ERRO_INPUT_CLIENTE_VAZIO.getMensagem(), e.getMessage());
      }
    }
    @Test    
    public void deveriaRejeitarCpfInvalidoPorRegraInserirCliente() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setCpf("789536914500");
        clienteDTO.setNome("teste");
        try {
          this.clienteService.insere(clienteDTO);
          fail();
        }catch (Exception e) {
          assertEquals(Mensagens.ERRO_CPF_INVALIDO.getMensagem(), e.getMessage());
        }
      
    }
    @Test
    public void deveriaRejeitarCpfInvalidoPorTamanhoMenorEsperadoInserirCliente() {
      ClienteDTO clienteDTO = new ClienteDTO();
      clienteDTO.setCpf("125487");
      clienteDTO.setNome("teste");
      try {
        this.clienteService.insere(clienteDTO);
        fail();
      }catch (Exception e) {
        assertEquals(Mensagens.ERRO_CPF_INVALIDO.getMensagem(), e.getMessage());
      }
    }
    @Test
    public void deveriaRejeitarCpfInvalidoPorTamanoMaiorEsperadoInserirCliente(){
      ClienteDTO clienteDTO = new ClienteDTO();
      clienteDTO.setCpf("0561516843104651025");
      clienteDTO.setNome("teste");
      try {
        this.clienteService.insere(clienteDTO);
        fail();
      }catch (Exception e) {
        assertEquals(Mensagens.ERRO_CPF_INVALIDO.getMensagem(), e.getMessage());
      }
    }
    @Test
    public void deveriaRejeitarPaisVazioInserirCliente() {
      ClienteDTO clienteDTO = new ClienteDTO();
      clienteDTO.setCpf("99107633033");
      clienteDTO.setNome("teste");
      clienteDTO.setPais("");
      
      try {
        this.clienteService.insere(clienteDTO);
        fail();
      } catch (Exception e) {
        assertEquals(Mensagens.ERRO_PAIS_INVALIDO.getMensagem(), e.getMessage());
      }
    }
    
    @Test
    public void deveriaRejeitarPaisInvalidoInserirCliente() {
      ClienteDTO clienteDTO = new ClienteDTO();
      clienteDTO.setCpf("57707744012");
      clienteDTO.setNome("teste");
      clienteDTO.setPais("USA");
      
      try {
        this.clienteService.insere(clienteDTO);
        fail();
      } catch (Exception e) {
        assertEquals(Mensagens.ERRO_PAIS_INVALIDO.getMensagem(), e.getMessage());
      }
    }
   
  
   
    @Test
    public void deveriaRejeitarComplementoVazioInserirCliente() {
      ClienteDTO clienteDTO = new ClienteDTO();
      clienteDTO.setCpf("04371831060");
      clienteDTO.setNome("teste");
      clienteDTO.setPais("CA");
      clienteDTO.setCep("H3Z2Y7");
      
      try {
        this.clienteService.insere(clienteDTO);
        fail();
      } catch (Exception e) {
        assertEquals(Mensagens.ERRO_COMPLEMENTO_NAO_EXISTE.getMensagem(), e.getMessage());
      }
    }
    @Test
    public void deveriaSalvarInserirCliente() {
      ClienteDTO cliente = new ClienteDTO();
      cliente.setCpf("47281275080");
      cliente.setNome("teste");
      cliente.setComplemento(Mockito.anyString());
      cliente.setCep("74565440");
      cliente.setPais("BR");
      cliente.setComplemento("teste");
      
      //Mockito.when(this.enderecoService.Request(Mockito.anyString(), Mockito.anyString())).thenReturn(new Endereco());
      //Mockito.when(this.enderecoService.save(Mockito.any(), Mockito.anyString())).thenReturn(Long.)
      
      //this.clienteDAO.save(cliente, Long.valueOf(31));
      
      this.clienteService.insere(cliente);
      assertEquals(this.clienteDAO.search(cliente.getCpf()).get(0).getCpf(), cliente.getCpf());
      
      
    }
    
    @Test
    public void deveriaRetornarClienteById() {
      Cliente cliente = new Cliente();
      cliente = this.clienteService.getCliente(Long.valueOf(43));
      assertEquals(cliente, this.clienteDAO.getById(Long.valueOf(43)).get(0));
      
    }
    @Test
    public void deveriaRejeitarUpdateCpfInvalido() {
      
      
      ClienteDTO clienteDTO = new ClienteDTO();
      clienteDTO.setId(Long.valueOf(44));
      clienteDTO.setCpf("00000000000");
      try{
        this.clienteService.updateCliente(clienteDTO);
      }catch (Exception e) {
        assertEquals(Mensagens.ERRO_CPF_INVALIDO.getMensagem(), e.getMessage());
      }
      
    }
    @Test
    public void deveriaRejeitarUpdateCpfExistente() {
      
      ClienteDTO clienteDTO = new ClienteDTO();
      clienteDTO.setId(Long.valueOf(43));
      clienteDTO.setCpf("70853968144");
      try{
        this.clienteService.updateCliente(clienteDTO);
      }catch (Exception e) {
        assertEquals(Mensagens.ERRO_CPF_JA_EXISTE.getMensagem(), e.getMessage());
      }
      
    }
    @Test
    public void deveriaAceitarUpdateMantendoCpf() {
      
      ClienteDTO clienteDTO = new ClienteDTO();
      clienteDTO.setId(Long.valueOf(44));
      clienteDTO.setCpf("70853968144");
      clienteDTO.setCep("74565100");
      clienteDTO.setComplemento("teste Complemento");
      clienteDTO.setNome("teste nome");
      clienteDTO.setPais("BR");
      
      try{
        this.clienteService.updateCliente(clienteDTO);
      }
      catch (Exception e) {
        fail();
      }
      assertEquals(clienteDTO.getId(), this.clienteDAO.getById(Long.valueOf(44)).get(0).getId());
      
      
    }
    @Test
    public void deveriaListarTodosPrestadores() {
      List <Prestador> listaPrestadores = this.clienteService.listAllPrestadores();
      assertEquals(listaPrestadores, this.prestadorDAO.list());
    }
    @Test
    public void deveriaDeletarCliente() {
      try{
        this.clienteService.deletarCliente(Long.valueOf(58));
        
      }catch (Exception e) {
        fail();
      }
      try {
        this.clienteService.getCliente(Long.valueOf(58));
        fail();
      }
      catch (Exception e) {
        assertEquals(Mensagens.ERRO_ID_NAO_EXISTE.getMensagem(), e.getMessage());
      }
      
    }
    
    @Test
    public void deveriaRejeitarDeletarClienteSemIdRegistrado() {
      try{
        this.clienteService.deletarCliente(Long.valueOf(1000000));
        fail();
      }catch (Exception e) {
        assertEquals(Mensagens.ERRO_ID_NAO_EXISTE.getMensagem(), e.getMessage());
      }
     
      
    }
    
    
}
