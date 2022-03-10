package com.teste.barbearia.model.dto;

import com.teste.barbearia.model.entity.Cliente;
import com.teste.barbearia.model.entity.Endereco;
import com.teste.barbearia.model.entity.Prestador;

public class PrestadorSaidaDTO {
  private String nome;
  private String cpf;
  private Long id;
  private Endereco endereco;
  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }
  public String getCpf() {
    return cpf;
  }
  public void setCpf(String cpf) {
    this.cpf = cpf;
  }
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public Endereco getEndereco() {
    return endereco;
  }
  public void setEndereco(Endereco endereco) {
    this.endereco = endereco;
  }
  
  public PrestadorSaidaDTO(Prestador prestador, Endereco endereco) {
    this.nome = prestador.getNome();
    this.cpf = prestador.getCpf();
    this.id = prestador.getId();
    this.endereco = endereco;
  }
}
