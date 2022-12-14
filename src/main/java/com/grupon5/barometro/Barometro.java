/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupon5.barometro;

import java.util.Objects;
import javafx.scene.control.DatePicker;
import javafx.util.converter.LocalDateTimeStringConverter;

/**
 *
 * @author victo
 */
public class Barometro {
    
    private final double CONSTANTE =132925.5;
    private DatePicker fecha;
    private LocalDateTimeStringConverter hora;
    private double altura;
    private double presion;

    public Barometro(DatePicker fecha, LocalDateTimeStringConverter hora, double altura) {
        this.fecha = fecha;
        this.hora = hora;
        this.altura = altura;
        if(altura<=0){
            altura = 0.76;
            this.presion = altura*CONSTANTE;
        }
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
        if (Double.doubleToLongBits(this.altura) != Double.doubleToLongBits(other.altura)) {
            return false;
        }
        if (Double.doubleToLongBits(this.presion) != Double.doubleToLongBits(other.presion)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        return Objects.equals(this.hora, other.hora);
    }

    public DatePicker getFecha() {
        return fecha;
    }

    public void setFecha(DatePicker date) {
        this.fecha = date;
    }

    public LocalDateTimeStringConverter getHora() {
        return hora;
    }

    public void setHora(LocalDateTimeStringConverter hora) {
        this.hora = hora;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getPresion() {
        return presion;
    }

    public void setPresion(double presion) {
        this.presion = presion;
    }
}
