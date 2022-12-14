/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupon5.barometro;

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.LocalDateTimeStringConverter;

/**
 *
 * @author victo
 */
public class DatosIniBarometro {
    
    private ObservableList<Barometro> barometro;

    public ObservableList<Barometro> getBarometro() {
        return barometro;
    }

    public void setBarometro(ObservableList<Barometro> barometro) {
        this.barometro = barometro;
    }

    public DatosIniBarometro() {
        barometro = FXCollections.observableArrayList(Barometro.extractor);
        barometro.addAll(
            new Barometro("p1","a1","f1","h1"),
            new Barometro("p2","a2","f2","h2"),
            new Barometro("p3","a3","f3","h3"),
            new Barometro("p4","a4","f4","h4"),
            new Barometro("p5","a5","f5","h5"),
            new Barometro("p6","a6","f6","h6")
            );
    }
    
    

    
}
