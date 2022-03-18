package com.teste.barbearia.model.entity;

import java.util.Objects;

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
    @Override
    public int hashCode() {
      return Objects.hash(cidade, estado, id, postal);
    }
    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      Endereco other = (Endereco) obj;
      return Objects.equals(cidade, other.cidade) && Objects.equals(estado, other.estado) && Objects
        .equals(id, other.id) && Objects.equals(postal, other.postal);
    }
    @Override
    public String toString() {
      return "Endereco [id=" + id + ", cidade=" + cidade + ", estado=" + estado + ", complemento="
          + complemento + ", pais=" + pais + ", postal=" + postal + "]";
    }
    
    
}
