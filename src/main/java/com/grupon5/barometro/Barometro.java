/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupon5.barometro;

import static java.lang.Math.pow;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 *
 * @author victo
 */
public class Barometro {

    private static final double CONSTANTE = 1013.25;
    private String fecha;
    private String hora;
    private double altura;
    private double presion;
    private LocalDate fechaLocal;

    
    ResourceBundle rb = ResourceBundle.getBundle("com.grupon5.barometro.i18n/cadenas",
                Locale.getDefault());
    @Override
    public String toString() {
        return fecha + "\t\t" + hora 
                + "\n" +rb.getString("altura") +": "+altura
                + " m\n"+rb.getString("presion") +": "+ presion
                + " mbar\n"+rb.getString("presion")+": "+ ((double) Math.round((presion / 1.333300001162309) * 100d) / 100d) + " mmHg";
    }


    public Barometro (String fecha, String hora, double altura, double presion){
        this.fecha = fecha;
        this.hora = hora;
        if (altura <= 0) {
            altura = 0;
        }
        this.altura = altura;
        this.presion = presion;
    }
    public Barometro(String fecha, String hora, double altura) {
        this.fecha = fecha;
        this.hora = hora;
        if (altura <= 0) {
            altura = 0;
        }
        this.altura = altura;
    }

    public Barometro(String fecha, String hora, double altura, LocalDate ld, double presion) {
        this.fecha = fecha;
        this.hora = hora;
        if (altura <= 0) {
            altura = 0;
        }
        this.altura = altura;
        this.presion= presion;
        this.fechaLocal = ld;
    }

    public LocalDate getFechaLocal() {
        return fechaLocal;
    }

    public void setFechaLocal(LocalDate ld) {
        this.fechaLocal = ld;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.fecha);
        hash = 73 * hash + Objects.hashCode(this.hora);
        hash = (int) (73 * hash + this.altura);
        hash = 73 * hash + (int) (Double.doubleToLongBits(this.presion) ^ (Double.doubleToLongBits(this.presion) >>> 32));
        hash = 73 * hash + Objects.hashCode(this.fechaLocal);
        return hash;
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

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public double getPresion() {
        return presion;
    }

    public void setPresion(double presion) {
        this.presion = presion;
    }
}
