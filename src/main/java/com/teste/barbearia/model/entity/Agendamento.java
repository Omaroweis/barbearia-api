package com.teste.barbearia.model.entity;

import java.util.Calendar;
import java.sql.Date;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.teste.barbearia.model.dao.ClienteDAO;
import com.teste.barbearia.model.dao.PrestadorDAO;
import com.teste.barbearia.model.enuns.Horarios;


public class Agendamento {

  private String cpf_cliente;
  private String cpf_prestador;
  private Horarios horario;
  private Date date;


  public String getCpf_cliente() {
    return cpf_cliente;
  }


  public void setCpf_cliente(String cpf_cliente) {
    this.cpf_cliente = cpf_cliente;
  }


  public String getCpf_prestador() {
    return cpf_prestador;
  }


  public void setCpf_prestador(String cpf_prestador) {
    this.cpf_prestador = cpf_prestador;
  }


  public Date getDate() {
    return date;
  }


  public void setDate(Date date) {
    this.date = date;
  }


  public Horarios getHorario() {
    return horario;
  }


  public void setHorario(String horaString) {
    if(horaString.compareTo("OITO") == 0) {
      this.horario = Horarios.OITO;
    }else if(horaString.compareTo("NOVE") == 0) {
      this.horario = Horarios.NOVE;
    }else if(horaString.compareTo("DEZ") == 0) {
      this.horario = Horarios.DEZ;
    }else if(horaString.compareTo("ONZE") == 0) {
      this.horario = Horarios.ONZE;
    }else if(horaString.compareTo("DOZE") == 0) {
      this.horario = Horarios.DOZE;
    }else if(horaString.compareTo("TREZE") == 0) {
      this.horario = Horarios.TREZE;
    }else if(horaString.compareTo("QUATORZE") == 0) {
      this.horario = Horarios.QUATORZE;
    }else if(horaString.compareTo("QUINZE") == 0) {
      this.horario = Horarios.QUINZE;
    }else if(horaString.compareTo("DEZESSEIS") == 0) {
      this.horario = Horarios.DEZESSEIS;
    }else if(horaString.compareTo("DEZESSETE") == 0) 
      this.horario = Horarios.DEZESSETE;
    
  }
  
  @Override
  public String toString() {
    
    return "cpf_cliente: "+this.cpf_cliente + " cpf_prestador "+ this.cpf_prestador + " horario: " + this.horario + " DATA: " + this.date;
  }
  
}
