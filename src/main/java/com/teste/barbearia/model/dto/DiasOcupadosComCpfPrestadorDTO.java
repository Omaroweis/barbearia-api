package com.teste.barbearia.model.dto;

public class DiasOcupadosComCpfPrestadorDTO {
  private Integer diaOcupado;
  private Integer quantidade;
  private String cpf_prestador;
  public String getCpf_prestador() {
    return cpf_prestador;
  }
  public void setCpf_prestador(String cpf_prestador) {
    this.cpf_prestador = cpf_prestador;
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
