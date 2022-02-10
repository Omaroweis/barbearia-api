package com.teste.barbearia.model.dto;

public class DiasOcupadosDTO {
  private Integer diaOcupado;
  private Integer quantidade;
  public Integer getDiaOcupado() {
    return diaOcupado;
  }
  public void setDiaOcupado(Integer diaOcupado) {
    this.diaOcupado = diaOcupado;
  }
  public Integer getQuantidade() {
    return quantidade;
  }
  public void setQuantidade(Integer quantidade) {
    this.quantidade = quantidade;
  }
  @Override
  public String toString() {
    // TODO Auto-generated method stub
    return this.diaOcupado + " quantidade: " + this.quantidade;
  }
}
