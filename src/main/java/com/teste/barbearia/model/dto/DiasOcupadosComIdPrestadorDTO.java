package com.teste.barbearia.model.dto;

public class DiasOcupadosComIdPrestadorDTO {
  private Integer diaOcupado;
  private Integer quantidade;
  private Long id_prestador;
  public Long getId_prestador() {
    return id_prestador;
  }
  public void setId_prestador(Long id_prestador) {
    this.id_prestador = id_prestador;
  }
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
  
}
