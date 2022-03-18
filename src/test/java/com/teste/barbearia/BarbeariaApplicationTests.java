package com.teste.barbearia;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.teste.barbearia.exception.ApiRequestException;
import com.teste.barbearia.service.ClienteService;

@SpringBootTest
class BarbeariaApplicationTests {
  @Resource
  private ClienteService clienteService;
  
	@Test
	void contextLoads() {
	}
	
	

	// excessao - > usa assertThrows(excessao.class, () -> metodo
	
	// normalmente -> assert.equals(metodo, retorno esperado)
}
