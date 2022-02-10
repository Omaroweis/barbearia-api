package com.teste.barbearia.model.enuns;

public enum NumeroHorariosOfertados {
  NUMERO_HORARIOS_OFERTADOS(10);
  private Integer numero_max;

  NumeroHorariosOfertados(Integer i) {
    this.numero_max = i;
  }
  public Integer MAX() {
    return numero_max;
  }
  
}
