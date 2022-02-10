package com.teste.barbearia.model.enuns;

import java.sql.Time;

public enum Horarios {
  
  
  OITO(Time.valueOf("08:00:00")),NOVE(Time.valueOf("09:00:00")),DEZ(Time.valueOf("10:00:00")),ONZE(Time.valueOf("11:00:00")),DOZE(Time.valueOf("12:00:00")),TREZE(Time.valueOf("13:00:00")),QUATORZE(Time.valueOf("14:00:00")),QUINZE(Time.valueOf("15:00:00")),DEZESSEIS(Time.valueOf("16:00:00")),DEZESSETE(Time.valueOf("17:00:00"));
  
  private Time time;
  
  Horarios(Time time){
    this.time = time;
  }
  public Time getHora() {
    return this.time;
  }
  
}
