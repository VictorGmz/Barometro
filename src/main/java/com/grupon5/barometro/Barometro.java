/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupon5.barometro;

import static java.lang.Math.pow;
import java.util.Objects;

/**
 *
 * @author victo
 */
public class Barometro {
    
    private final double CONSTANTE =1013.25;
    private String fecha;
    private String hora;
    private double altura;
    private double presion;

    @Override
    public String toString() {
        return fecha + " " + hora +
                "\nAltura: " + altura +
                " m\nPresión: " + ((double)Math.round(presion * 100d) / 100d) +
                " mbar\nPresión: " + ((double)Math.round((presion/1.333300001162309)*100d)/100d) +" mmHg";
    }

    public Barometro(String fecha, String hora, double altura) {
        this.fecha = fecha;
        this.hora = hora;
        this.altura = altura;
        if(altura<=0){
            altura = 0;
        }
        this.presion = CONSTANTE*(pow(1-0.0000225577*altura,5.2559));
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String date) {
        this.fecha = date;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
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
