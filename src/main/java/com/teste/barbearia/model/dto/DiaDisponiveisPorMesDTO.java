package com.teste.barbearia.model.dto;

public class DiaDisponiveisPorMesDTO {
  private String mes;
  private String cpf_prestador;
  public String getMes() {
    return mes;
  }
  public void setMes(String mes) {
    this.mes = mes;
  }
  public String getCpf_prestador() {
    return cpf_prestador;
  }
  public void setCpf_prestador(String cpf_prestador) {
    this.cpf_prestador = cpf_prestador;
  }
  
  @Override
  public String toString() {
    // TODO Auto-generated method stub
    return "cpf_prestador : " + this.cpf_prestador + " mes: " + this.mes;
  }
}
