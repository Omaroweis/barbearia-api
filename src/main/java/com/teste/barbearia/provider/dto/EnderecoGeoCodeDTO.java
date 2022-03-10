package com.teste.barbearia.provider.dto;

import com.teste.barbearia.model.dto.StandardDTO;
import com.teste.barbearia.provider.dto.interfaces.EnderecoDTO;

public class EnderecoGeoCodeDTO implements EnderecoDTO{
  private String postal;
  private Long id;
  private String complemento;
  private StandardDTO standard;
  @Override
  public Long getId() {
    return this.id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
    
  }

  @Override
  public String getCidade() {
    return this.standard.getCity();
  }

  @Override
  public void setCidade(String cidade) {
      this.standard.setCity(cidade);
    
  }

  @Override
  public String getEstado() {
    return this.standard.getProv();
  }

  @Override
  public void setEstado(String estado) {
    this.standard.setProv(estado);
    
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
   return this.postal;
  }

  @Override
  public void setPostal(String postal) {
    this.postal = postal;
    
  }

  @Override
  public String getPais() {
    return "CA";
  }
  @Override
  public String toString() {
    
    return"ENDERECO GEOCODER POSTAL: " + this.postal;
  }

  public StandardDTO getStandard() {
    return standard;
  }

  public void setStandard(StandardDTO standard) {
    this.standard = standard;
  }
  


}
