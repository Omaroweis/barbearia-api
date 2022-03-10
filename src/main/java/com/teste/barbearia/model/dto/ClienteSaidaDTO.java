package com.teste.barbearia.model.dto;

import com.teste.barbearia.model.entity.Cliente;
import com.teste.barbearia.model.entity.Endereco;

public class ClienteSaidaDTO {
  private String nome;
  private String cpf;
  private Long id;
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
  private Endereco endereco;
  public ClienteSaidaDTO(Cliente cliente, Endereco endereco) {
    this.nome = cliente.getNome();
    this.cpf = cliente.getCpf();
    this.id = cliente.getId();
    this.endereco = endereco;
  }
}
