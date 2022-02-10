package com.teste.barbearia.model.entity;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;
import javafx.beans.property.adapter.JavaBeanObjectProperty;


public class Prestador{
  
  private String cpf;
  private String nome;
  public String getCpf() {
    return cpf;
  }
  public void setCpf(String cpf) {
    CPFValidator cpfValidator = new CPFValidator(); 
    List<ValidationMessage> erros = cpfValidator.invalidMessagesFor(cpf);
    this.cpf = cpf;
  }
  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }
  @Override
  public String toString() {
    // TODO Auto-generated method stub
    return "Cpf: " + this.cpf + ", Nome: " + this.nome;
  }
  @Override
  public int hashCode() {
    return Objects.hash(cpf);
  }
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Prestador other = (Prestador) obj;
    return Objects.equals(cpf, other.cpf);
  }
}
  
