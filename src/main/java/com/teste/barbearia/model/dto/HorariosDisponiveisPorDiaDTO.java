package com.teste.barbearia.model.dto;

import java.sql.Date;

public class HorariosDisponiveisPorDiaDTO {
  Date date;
  
  String cpf_prestador;

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getCpf_prestador() {
    return cpf_prestador;
  }

  public void setCpf_prestador(String cpf_prestador) {
    this.cpf_prestador = cpf_prestador;
  }
  
  
  
}
