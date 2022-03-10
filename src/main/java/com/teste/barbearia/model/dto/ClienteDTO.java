package com.teste.barbearia.model.dto;

import com.teste.barbearia.model.dao.EnderecoDAO;
import com.teste.barbearia.model.entity.Cliente;
import com.teste.barbearia.model.entity.Endereco;

public class ClienteDTO {
  private Long id;
  private String nome;
  private String cpf;
  private String cep;
  private String pais;
  private String complemento;
  private Endereco endereco;

  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
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
  public String getCep() {
    return cep;
  }
  public void setCep(String cep) {
    this.cep = cep;
  }
  public String getPais() {
    return pais;
  }
  public void setPais(String pais) {
    this.pais = pais;
  }
  public String getComplemento() {
    return complemento;
  }
  public void setComplemento(String complemento) {
    this.complemento = complemento;
  }
 @Override
 public String toString() {
    return this.cpf + " " + this.nome + " " + this.complemento + " " + this.cep + this.pais;
  }
public Endereco getEndereco() {
  return endereco;
}
public void setEndereco(Endereco endereco) {
  this.endereco = endereco;
}
  
}
