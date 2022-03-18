package com.teste.barbearia.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.teste.barbearia.BarbeariaApplication;
import com.teste.barbearia.model.dao.PrestadorDAO;
import com.teste.barbearia.model.dto.ClienteDTO;
import com.teste.barbearia.model.dto.PrestadorDTO;
import com.teste.barbearia.model.entity.Cliente;
import com.teste.barbearia.model.entity.Prestador;
import com.teste.barbearia.model.dto.PrestadorDTO;
import com.teste.barbearia.model.enuns.Mensagens;

@Transactional
@SpringBootTest(classes = BarbeariaApplication.class)
public class PrestadorServiceTest {
    @Resource
    @InjectMocks
    private PrestadorService prestadorService;
    @Resource
    @InjectMocks
    private PrestadorDAO prestadorDAO;
    
    @Test
    public void deveriaRejeitarCpfRepetidoPrestador() {
      PrestadorDTO prestadorDTO = new PrestadorDTO();
      PrestadorDTO prestadorDTO2 = new PrestadorDTO();
      prestadorDTO.setCpf("47281275080");
      prestadorDTO.setNome("teste");
      
      prestadorDTO.setCep("74565440");
      prestadorDTO.setPais("BR");
      prestadorDTO.setComplemento("teste");
      try {
      this.prestadorService.insere(prestadorDTO);
      }catch (Exception e) {
        // TODO: handle exception
      }
      prestadorDTO2.setCpf("47281275080");
      prestadorDTO2.setNome("TESTE");
      //assertThrows(ApiRequestException.class, () -> clienteService.insere(clienteDTO));
      try {
        this.prestadorService.insere(prestadorDTO2);
        fail();
      }catch (Exception e) {
        assertEquals(Mensagens.ERRO_CPF_JA_EXISTE.getMensagem(), e.getMessage());
      }
    }
    
    @Test
    public void deveriaRejeitarNomeVazioInserirPrestador() {
      PrestadorDTO prestadorDTO = new PrestadorDTO();
      prestadorDTO.setCpf("44217328001");
      prestadorDTO.setNome("");
      //assertThrows(ApiRequestException.class, () -> clienteService.insere(clienteDTO));
      try {
        this.prestadorService.insere(prestadorDTO);
        fail("HOUVE FALHA NA VALIDAÇÂO DO NOME QUANDO INSERE VAZIO");
      }catch (Exception e) {
        assertEquals(Mensagens.ERRO_INPUT_PRESTADOR_VAZIO.getMensagem(), e.getMessage());
      }
    }
    
    @Test    
    public void deveriaRejeitarCpfInvalidoPorRegraInserirPrestador() {
        PrestadorDTO prestadorDTO = new PrestadorDTO();
        prestadorDTO.setCpf("789536914500");
        prestadorDTO.setNome("teste");
        try {
          this.prestadorService.insere(prestadorDTO);
          fail();
        }catch (Exception e) {
          assertEquals(Mensagens.ERRO_CPF_INVALIDO.getMensagem(), e.getMessage());
        }
      
    }
    
    @Test
    public void deveriaRejeitarCpfInvalidoPorTamanhoMenorEsperadoInserirPrestador() {
      PrestadorDTO prestadorDTO = new PrestadorDTO();
      prestadorDTO.setCpf("125487");
      prestadorDTO.setNome("teste");
      try {
        this.prestadorService.insere(prestadorDTO);
        fail();
      }catch (Exception e) {
        assertEquals(Mensagens.ERRO_CPF_INVALIDO.getMensagem(), e.getMessage());
      }
    }
    
    @Test
    public void deveriaRejeitarCpfInvalidoPorTamanoMaiorEsperadoInserirPrestador(){
      PrestadorDTO prestadorDTO = new PrestadorDTO();
      prestadorDTO.setCpf("0561516843104651025");
      prestadorDTO.setNome("teste");
      try {
        this.prestadorService.insere(prestadorDTO);
        fail();
      }catch (Exception e) {
        assertEquals(Mensagens.ERRO_CPF_INVALIDO.getMensagem(), e.getMessage());
      }
    }
    
    @Test
    public void deveriaRejeitarPaisVazioInserirPrestador() {
      PrestadorDTO prestadorDTO= new PrestadorDTO();
      prestadorDTO.setCpf("99107633033");
      prestadorDTO.setNome("teste");
      prestadorDTO.setPais("");
      
      try {
        this.prestadorService.insere(prestadorDTO);
        fail();
      } catch (Exception e) {
        assertEquals(Mensagens.ERRO_PAIS_INVALIDO.getMensagem(), e.getMessage());
      }
    }
    
    @Test
    public void deveriaRejeitarPaisInvalidoInserirPrestador() {
      PrestadorDTO prestador = new PrestadorDTO();
      prestador.setCpf("57707744012");
      prestador.setNome("teste");
      prestador.setPais("USA");
      
      try {
        this.prestadorService.insere(prestador);
        fail();
      } catch (Exception e) {
        assertEquals(Mensagens.ERRO_PAIS_INVALIDO.getMensagem(), e.getMessage());
      }
    }
    
    @Test
    public void deveriaRejeitarComplementoVazioInserirPrestador() {
      PrestadorDTO prestadorDTO = new PrestadorDTO();
      prestadorDTO.setCpf("04371831060");
      prestadorDTO.setNome("teste");
      prestadorDTO.setPais("CA");
      prestadorDTO.setCep("H3Z2Y7");
      
      try {
        this.prestadorService.insere(prestadorDTO);
        fail();
      } catch (Exception e) {
        assertEquals(Mensagens.ERRO_COMPLEMENTO_NAO_EXISTE.getMensagem(), e.getMessage());
      }
    }
    
    @Test
    public void deveriaSalvarInserirPrestador() {
      PrestadorDTO prestadorDTO= new PrestadorDTO();
      prestadorDTO.setCpf("47281275080");
      prestadorDTO.setNome("teste");
      prestadorDTO.setComplemento(Mockito.anyString());
      prestadorDTO.setCep("74565440");
      prestadorDTO.setPais("BR");
      prestadorDTO.setComplemento("teste");
      
      //Mockito.when(this.enderecoService.Request(Mockito.anyString(), Mockito.anyString())).thenReturn(new Endereco());
      //Mockito.when(this.enderecoService.save(Mockito.any(), Mockito.anyString())).thenReturn(Long.)
      
      //this.clienteDAO.save(cliente, Long.valueOf(31));
      
      this.prestadorService.insere(prestadorDTO);
      assertEquals(this.prestadorDAO.search(prestadorDTO.getCpf()).get(0).getCpf(), prestadorDTO.getCpf());
      
      
    }
    
    @Test
    public void deveriaRetornarPrestadorById() {
      
      PrestadorDTO prestadorDTO= new PrestadorDTO();
      prestadorDTO.setCpf("47281275080");
      prestadorDTO.setNome("teste");
      prestadorDTO.setComplemento(Mockito.anyString());
      prestadorDTO.setCep("74565440");
      prestadorDTO.setPais("BR");
      prestadorDTO.setComplemento("teste");
      this.prestadorService.insere(prestadorDTO);
      Long id = this.prestadorDAO.search(prestadorDTO.getCpf()).get(0).getId();
      
      
      Prestador prestador = new Prestador();
      prestador = this.prestadorService.getPrestador(Long.valueOf(id));
      assertEquals(prestador, this.prestadorDAO.getById(Long.valueOf(id)).get(0));
      
    }
    @Test
    public void deveriaRejeitarUpdateCpfInvalido() {
      
      PrestadorDTO prestadorDTO= new PrestadorDTO();
      prestadorDTO.setCpf("47281275080");
      prestadorDTO.setNome("teste");
      prestadorDTO.setComplemento(Mockito.anyString());
      prestadorDTO.setCep("74565440");
      prestadorDTO.setPais("BR");
      prestadorDTO.setComplemento("teste");
      try {
        this.prestadorService.insere(prestadorDTO);  
      }catch (Exception e) {
        fail("CPF JA EXISTE  NO BANCO");
      }
      
      Long id = this.prestadorDAO.search(prestadorDTO.getCpf()).get(0).getId();
      prestadorDTO.setId(id);
      prestadorDTO.setCpf("000000000000");
      
      try{
        this.prestadorService.updatePrestador(prestadorDTO);
      }catch (Exception e) {
        assertEquals(Mensagens.ERRO_CPF_INVALIDO.getMensagem(), e.getMessage());
      }
      
    }
    @Test
    public void deveriaRejeitarUpdateCpfExistente() {
      
      PrestadorDTO prestadorQualquer = new PrestadorDTO();
      prestadorQualquer.setCpf("06676325063");
      prestadorQualquer.setNome("Teste");
      prestadorQualquer.setCep("74565440");
      prestadorQualquer.setPais("BR");
      prestadorQualquer.setComplemento("teste");
      
      Long id_qualquer = null;
      try {
        this.prestadorService.insere(prestadorQualquer);
      } catch (Exception e1) {
        fail("O cpf testado ja existe no banco");
      }
      try{
        id_qualquer = this.prestadorService.getPrestador(prestadorQualquer.getCpf()).getId();
      }catch (Exception e) {
        
      }
      
      
      PrestadorDTO prestadorCorreto = new PrestadorDTO();
      prestadorCorreto.setCpf("70853968144");
      prestadorCorreto.setNome("Teste");
      prestadorCorreto.setCep("74565440");
      prestadorCorreto.setPais("BR");
      prestadorCorreto.setComplemento("teste");
      
     
      
      
      try {
        this.prestadorService.insere(prestadorCorreto);
      } catch (Exception e1) {
        fail("O cpf testado ja existe no banco");
      }
      
      
      prestadorQualquer.setCpf("70853968144");
      prestadorQualquer.setId(id_qualquer);
      try{
        this.prestadorService.updatePrestador(prestadorQualquer);
      }catch (Exception e) {
        assertEquals(Mensagens.ERRO_CPF_JA_EXISTE.getMensagem(), e.getMessage());
      }
      
    }
    
    @Test
    public void deveriaAceitarUpdateMantendoCpf() {
      
      
      PrestadorDTO prestadorDTO2 = new PrestadorDTO();
      prestadorDTO2.setCpf("17152611009");
      prestadorDTO2.setNome("Teste");
      prestadorDTO2.setCep("74565440");
      prestadorDTO2.setPais("BR");
      prestadorDTO2.setComplemento("teste");
      
      this.prestadorService.insere(prestadorDTO2);
      
      Long id = null;
      try {
        id = this.prestadorService.getPrestador(prestadorDTO2.getCpf()).getId();
      } catch (Exception e1) {
        fail();
      }
      
      PrestadorDTO prestadorDTO = new PrestadorDTO();
      prestadorDTO.setId(id);
      prestadorDTO.setCpf("70853968144");
      prestadorDTO.setCep("74565100");
      prestadorDTO.setComplemento("teste Complemento");
      prestadorDTO.setNome("teste nome");
      prestadorDTO.setPais("BR");
      
      try{
        this.prestadorService.updatePrestador(prestadorDTO);
      }
      catch (Exception e) {
        fail();
      }
      assertEquals(prestadorDTO.getId(), this.prestadorDAO.getById(id).get(0).getId());
      
      
    }
    @Test
    public void deveriaDeletarPrestador() {
      
      PrestadorDTO prestadorDTO2 = new PrestadorDTO();
      prestadorDTO2.setCpf("57510238030");
      prestadorDTO2.setNome("Teste");
      prestadorDTO2.setCep("74565440");
      prestadorDTO2.setPais("BR");
      prestadorDTO2.setComplemento("teste");
      
      try{
        this.prestadorService.insere(prestadorDTO2);
      }catch (Exception e) {
        fail("cpf ja existe no banco");
      }
      
      Long id = null;
      try {
        id = this.prestadorService.getPrestador(prestadorDTO2.getCpf()).getId();
      } catch (Exception e1) {
        fail();
      }
      
      try{
        this.prestadorService.deletarPrestador(id);
        
      }catch (Exception e) {
        fail();
      }
      try {
        this.prestadorService.getPrestador(id);
        fail();
      }
      catch (Exception e) {
        assertEquals(Mensagens.ERRO_ID_NAO_EXISTE.getMensagem(), e.getMessage());
      }
      
    }
    
    @Test
    public void deveriaRejeitarDeletarPrestadorSemIdRegistrado() {
      try{
        this.prestadorService.deletarPrestador(Long.valueOf(10000000));
        fail();
      }catch (Exception e) {
        assertEquals(Mensagens.ERRO_ID_NAO_EXISTE.getMensagem(), e.getMessage());
      }
    }
    
   
}
