package com.teste.barbearia.provider.dto;

import com.teste.barbearia.provider.dto.interfaces.EnderecoDTO;

public class EnderecoViaCepDTO implements EnderecoDTO{
  
  private Long id;
  private String cep;
  private String logradouro;
  private String bairro;
  private String localidade;
  private String uf;
  private String ibge;
  private String gia;
  private String ddd;
  private String siafi;
  private String complemento;
  
  public String getCep() {
    return cep;
  }


  public void setCep(String cep) {
    this.cep = cep;
  }


  public String getLogradouro() {
    return logradouro;
  }


  public void setLogradouro(String logradouro) {
    this.logradouro = logradouro;
  }


  public String getBairro() {
    return bairro;
  }


  public void setBairro(String bairro) {
    this.bairro = bairro;
  }


  public String getLocalidade() {
    return localidade;
  }


  public void setLocalidade(String localidade) {
    this.localidade = localidade;
  }


  public String getUf() {
    return uf;
  }


  public void setUf(String uf) {
    this.uf = uf;
  }


  public String getIbge() {
    return ibge;
  }


  public void setIbge(String ibge) {
    this.ibge = ibge;
  }


  public String getGia() {
    return gia;
  }


  public void setGia(String gia) {
    this.gia = gia;
  }


  public String getDdd() {
    return ddd;
  }


  public void setDdd(String ddd) {
    this.ddd = ddd;
  }


  public String getSiafi() {
    return siafi;
  }


  public void setSiafi(String siafi) {
    this.siafi = siafi;
  }




 
  
  @Override
  public String toString() {
    
    return this.cep + " " + this.bairro + " " + this.logradouro + " " + this.localidade;
  }


  @Override
  public String getCidade() {
    
    return this.localidade;
  }


  @Override
  public void setCidade(String cidade) {
    this.localidade = cidade;
    
  }


  @Override
  public String getEstado() {
    return this.uf;
  }


  @Override
  public void setEstado(String estado) {
    this.uf = estado;
    
  }


  @Override
  public String getComplemento() {
    return this.complemento;
  }


  @Override
  public void setComplemento(String complemento) {
    this.complemento = complemento;
    
  }


  @Override
  public String getPostal() {
    return this.cep;
  }


  @Override
  public void setPostal(String postal) {
    this.cep = postal;
    
  }

  @Override
  public Long getId() {
    return this.id;
  }


  @Override
  public void setId(Long id) {
    this.id = id;
    
  }


  @Override
  public String getPais() {
    return "BR";
  }

  
  
}
