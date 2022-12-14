/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupon5.barometro;

import java.util.Objects;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.util.Callback;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.LocalDateTimeStringConverter;

/**
 *
 * @author victo
 */
public class Barometro {
    
    private LocalDateStringConverter date;
    private LocalDateTimeStringConverter hora;
    private IntegerProperty altura;
    private DoubleProperty presion;
    
    private String dateS;
    private String horaS;
    private String alturaS;
    private String presionS;
    
    public Barometro(LocalDateStringConverter date, LocalDateTimeStringConverter hora, IntegerProperty altura, DoubleProperty presion) {
        this.date = date;
        this.hora = hora;
        this.altura = altura;
        this.presion = presion;
    }
    
      public Barometro(String date, String hora, String altura, String presion) {
          this.dateS=date;
          this.horaS=hora;
          this.alturaS=altura;
          this.presionS= presion;
    }
    
    
    public static Callback<Barometro, Observable[]> extractor = 
            b -> new Observable[]{
                b.getAltura(),b.getPresion()
            
    };
    public LocalDateStringConverter getDate() {
        return date;
    }

    public void setDate(LocalDateStringConverter date) {
        this.date = date;
    }

    public LocalDateTimeStringConverter getHora() {
        return hora;
    }

    public void setHora(LocalDateTimeStringConverter hora) {
        this.hora = hora;
    }

    public IntegerProperty getAltura() {
        return altura;
    }

    public void setAltura(IntegerProperty altura) {
        this.altura = altura;
    }

    public DoubleProperty getPresion() {
        return presion;
    }

    public void setPresion(DoubleProperty presion) {
        this.presion = presion;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.date);
        hash = 29 * hash + Objects.hashCode(this.hora);
        hash = 29 * hash + Objects.hashCode(this.altura);
        hash = 29 * hash + Objects.hashCode(this.presion);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Barometro other = (Barometro) obj;
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.hora, other.hora)) {
            return false;
        }
        if (!Objects.equals(this.altura, other.altura)) {
            return false;
        }
        if (!Objects.equals(this.presion, other.presion)) {
            return false;
        }
        return true;
    }
    
}
