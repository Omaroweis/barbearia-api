package com.teste.barbearia.model.entity;

import java.util.Calendar;
import java.sql.Date;
import java.sql.Time;

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
  private Long id;
  private Long id_cliente;
  private Long id_prestador;
  private Horarios horario;
  private Date date;
  public Long getId() {
    return id;
  }


  public void setId(Long id) {
    this.id = id;
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
    if(horaString.compareTo("OITO") == 0 || horaString.compareTo("08:00:00") == 0) {
      this.horario = Horarios.OITO;
    }else if(horaString.compareTo("NOVE") == 0 || horaString.compareTo("09:00:00") == 0) {
      this.horario = Horarios.NOVE;
    }else if(horaString.compareTo("DEZ") == 0 || horaString.compareTo("10:00:00") == 0) {
      this.horario = Horarios.DEZ;
    }else if(horaString.compareTo("ONZE") == 0 || horaString.compareTo("11:00:00") == 0) {
      this.horario = Horarios.ONZE;
    }else if(horaString.compareTo("DOZE") == 0 || horaString.compareTo("12:00:00") == 0) {
      this.horario = Horarios.DOZE;
    }else if(horaString.compareTo("TREZE") == 0 || horaString.compareTo("13:00:00") == 0) {
      this.horario = Horarios.TREZE;
    }else if(horaString.compareTo("QUATORZE") == 0 || horaString.compareTo("14:00:00") == 0) {
      this.horario = Horarios.QUATORZE;
    }else if(horaString.compareTo("QUINZE") == 0 || horaString.compareTo("15:00:00") == 0) {
      this.horario = Horarios.QUINZE;
    }else if(horaString.compareTo("DEZESSEIS") == 0 || horaString.compareTo("16:00:00") == 0) {
      this.horario = Horarios.DEZESSEIS;
    }else if(horaString.compareTo("DEZESSETE") == 0 || horaString.compareTo("17:00:00") == 0) 
      this.horario = Horarios.DEZESSETE;
    
  }
  public void setHorario(Time hora) {
    String horaString = hora.toString();
    
    if(horaString.compareTo("08:00:00") == 0) {
      this.horario = Horarios.OITO;
    }else if(horaString.compareTo("09:00:00") == 0) {
      this.horario = Horarios.NOVE;
    }else if(horaString.compareTo("10:00:00") == 0) {
      this.horario = Horarios.DEZ;
    }else if(horaString.compareTo("11:00:00") == 0) {
      this.horario = Horarios.ONZE;
    }else if(horaString.compareTo("12:00:00") == 0) {
      this.horario = Horarios.DOZE;
    }else if(horaString.compareTo("13:00:00") == 0) {
      this.horario = Horarios.TREZE;
    }else if(horaString.compareTo("14:00:00") == 0) {
      this.horario = Horarios.QUATORZE;
    }else if(horaString.compareTo("15:00:00") == 0) {
      this.horario = Horarios.QUINZE;
    }else if(horaString.compareTo("16:00:00") == 0) {
      this.horario = Horarios.DEZESSEIS;
    }else if(horaString.compareTo("17:00:00") == 0) 
      this.horario = Horarios.DEZESSETE;
    
  }
  @Override
  public String toString() {
    
    return "id_cliente: "+this.id_cliente + " id_prestador "+ this.id_prestador + " horario: " + this.horario + " DATA: " + this.date;
  }


  public Long getId_cliente() {
    return id_cliente;
  }


  public void setId_cliente(Long id_cliente) {
    this.id_cliente = id_cliente;
  }


  public Long getId_prestador() {
    return id_prestador;
  }


  public void setId_prestador(Long id_prestador) {
    this.id_prestador = id_prestador;
  }
  
}
