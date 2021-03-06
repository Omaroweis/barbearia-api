package com.teste.barbearia.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.teste.barbearia.model.dto.PrestadorDTO;
import com.teste.barbearia.model.dto.PrestadorSaidaDTO;
import com.teste.barbearia.model.entity.Agendamento;
import com.teste.barbearia.model.entity.Cliente;
import com.teste.barbearia.model.entity.Prestador;
import com.teste.barbearia.service.ClienteService;
import com.teste.barbearia.service.PrestadorService;
@RestController
@RequestMapping("/prestador")
public class PrestadorController {
  @Resource
  private PrestadorService prestadorService;
  
  @ResponseStatus(value = HttpStatus.OK)
  @PostMapping(value = "/inserir")
  public PrestadorSaidaDTO inserir(@RequestBody PrestadorDTO prestadorDTO) {
    return prestadorService.insere(prestadorDTO);
    
  }
  @ResponseStatus(value = HttpStatus.OK)
  @GetMapping(value = "/obterPrestador")
  public Prestador getPRestador(@RequestParam(required = false) String cpf, @RequestParam(required = false) Long id) throws Exception {
    if(cpf != null)
      return prestadorService.getPrestador(cpf);
    if(id != null)
      return prestadorService.getPrestador(id);
    // TODO tratar quando nenhum parametro vem setado
    return null;
  }
  
  @PutMapping(value="/atualizar")
  @ResponseStatus(value = HttpStatus.OK)
  public PrestadorSaidaDTO atualiza(@RequestBody PrestadorDTO prestadorDTO) throws Exception {
    return prestadorService.updatePrestador(prestadorDTO);
  }
  
  @ResponseStatus(value=HttpStatus.OK)
  @DeleteMapping(value = "/deletar")
  public String apagarAgendamento(@RequestParam Long id) throws Exception{
    return this.prestadorService.deletarPrestador(id);
    
  }
    
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    public List<String> obterPrestadoresPeloEndereco(@RequestParam String cep){
      return this.prestadorService.getPrestadorByEndereco(cep);
    }
}

