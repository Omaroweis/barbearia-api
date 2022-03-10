package com.teste.barbearia.model.entity;

import com.teste.barbearia.provider.dto.interfaces.EnderecoDTO;

public class Endereco {
    private Long id;
    private String cidade;
    private String estado;
    private String complemento;
    private String pais;
    private String postal;
    public Endereco(){};
    public Endereco(EnderecoDTO enderecoDTO){
      this.cidade = enderecoDTO.getCidade();
      this.estado = enderecoDTO.getEstado();
      this.complemento = enderecoDTO.getComplemento();
      this.pais = enderecoDTO.getPais();
      this.postal = enderecoDTO.getPostal();
    }
    
    public Long getId() {
      return id;
    }
    public void setId(Long id) {
      this.id = id;
    }
    public String getCidade() {
      return cidade;
    }
    public void setCidade(String cidade) {
      this.cidade = cidade;
    }
    public String getEstado() {
      return estado;
    }
    public void setEstado(String estado) {
      this.estado = estado;
    }
    public String getComplemento() {
      return complemento;
    }
    public void setComplemento(String complemento) {
      this.complemento = complemento;
    }
    public String getPais() {
      return pais;
    }
    public void setPais(String pais) {
      this.pais = pais;
    }
    public String getPostal() {
      return postal;
    }
    public void setPostal(String postal) {
      this.postal = postal;
    }
    
    
}
