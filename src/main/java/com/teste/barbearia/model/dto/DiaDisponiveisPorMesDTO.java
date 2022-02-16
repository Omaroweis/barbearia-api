package com.teste.barbearia.model.dto;

public class DiaDisponiveisPorMesDTO {
  private String mes;
  private Long id_prestador;
  public String getMes() {
    return mes;
  }
  public void setMes(String mes) {
    this.mes = mes;
  }
  
  @Override
  public String toString() {
    // TODO Auto-generated method stub
    return "cpf_prestador : " + this.id_prestador + " mes: " + this.mes;
  }
  public Long getId_prestador() {
    return id_prestador;
  }
  public void setId_prestador(Long id_prestador) {
    this.id_prestador = id_prestador;
  }
}
