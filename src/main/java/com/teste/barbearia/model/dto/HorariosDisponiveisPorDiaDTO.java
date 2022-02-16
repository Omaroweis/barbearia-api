package com.teste.barbearia.model.dto;

import java.sql.Date;

public class HorariosDisponiveisPorDiaDTO {
  Date date;
  
  Long id_prestador;

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Long getId() {
    return id_prestador;
  }

  public void setId_prestador(Long id) {
    this.id_prestador = id;
  }

  
  
  
}
