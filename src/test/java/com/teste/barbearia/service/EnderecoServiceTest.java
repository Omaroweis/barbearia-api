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
import com.teste.barbearia.factory.Factory;
import com.teste.barbearia.model.entity.Endereco;
import com.teste.barbearia.model.enuns.Mensagens;

@Transactional
@SpringBootTest(classes = BarbeariaApplication.class)
public class EnderecoServiceTest {
  @Resource
  @InjectMocks
  private EnderecoService enderecoService;
  @Test
  public void deveriaRejeitarPaisVazioRequisicaoEndereco() {
    try{
      this.enderecoService.Request("", "postal");
      fail();
    }catch (Exception e) {
      assertEquals(Mensagens.ERRO_PAIS_INVALIDO.getMensagem(), e.getMessage());
    }
    
  }
  @Test
  public void deveriaRejeitarPaisInvalidoRequisicaoEndereco() {
    try{
      this.enderecoService.Request("PAIS_QUALQUER", "POSTA");
      fail();
    }catch (Exception e) {
      assertEquals(Mensagens.ERRO_PAIS_INVALIDO.getMensagem(), e.getMessage());
    }
  }
  @Test
  public void deveriaRejeitarPostalVazioRequisicaoEndereco() {
    try {
      this.enderecoService.Request("BR", "");
      fail();
    }
    catch (Exception e) {
      assertEquals(Mensagens.ERRO_POSTAL_INVALIDO.getMensagem(), e.getMessage());
    }
  }
  @Test
  public void deveriaRejeitarPostalInvalidoBRRequisicaoEndereco() {
    try {
      this.enderecoService.Request("BR", "POSTAL INVALIDO");
      fail();
    }
    catch (Exception e) {
      assertEquals(Mensagens.ERRO_POSTAL_INVALIDO.getMensagem(), e.getMessage());
    }
  }
  
  @Test
  public void deveriaRejeitarPostalInvalidoCARequisicaoEndereco() {
    try {
      this.enderecoService.Request("CA", "POSTAL INVALIDO");
      fail();
    }
    catch (Exception e) {
      assertEquals(Mensagens.ERRO_POSTAL_INVALIDO.getMensagem(), e.getMessage());
    }
  }
  
  
}
