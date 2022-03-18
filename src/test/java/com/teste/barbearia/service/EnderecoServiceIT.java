package com.teste.barbearia.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.service.spi.InjectService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.teste.barbearia.BarbeariaApplication;
import com.teste.barbearia.model.dao.EnderecoDAO;
import com.teste.barbearia.model.entity.Endereco;
import com.teste.barbearia.model.enuns.Mensagens;
@Transactional
@SpringBootTest(classes = BarbeariaApplication.class)
public class EnderecoServiceIT {
  @Resource
  @InjectMocks
  private EnderecoService enderecoService;
  @Resource
  @InjectMocks
  private EnderecoDAO enderecoDAO;
  @Test
  public void deveriaAceitarPaisCaRequisicaoEndereco() {
    
    
    Endereco endereco = enderecoService.Request("Ca", "T0H2N2");
    Endereco enderecoCerto = new Endereco();
    enderecoCerto.setCidade("Northern Sunrise County");
    enderecoCerto.setEstado("AB");
    enderecoCerto.setPais("CA");
    enderecoCerto.setPostal("T0H2N2");
    
    assertEquals(enderecoCerto, endereco);
    
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
  public void deveriaRetornarListaDePrestadores() {
    List<String> prestadores = this.enderecoService.getPrestadorByEndereco("74565440");
    assertEquals(prestadores, this.enderecoDAO.getPrestadorByEndereco("74565440"));
    
  }
  @Test
  public void deveriaRetornarEnderecoPeloID() {
    Endereco endereco = this.enderecoService.getById(Long.valueOf(46));
    assertEquals(endereco, this.enderecoDAO.getById(Long.valueOf(46)));
  }
  
  
}
