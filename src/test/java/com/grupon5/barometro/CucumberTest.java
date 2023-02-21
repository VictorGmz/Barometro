/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupon5.barometro;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;

/**
 *
 * @author victo
 */
public class CucumberTest {

    double media, presion;
    VistaBarometroController barometro;

    //Media mayor a presion
    @Cuando("la diferencia es mas de diez")
    public void la_diferencia_es_mas_de_diez() {
        
    }

    @Entonces("hara buen tiempo durante varias horas")
    public void hara_buen_tiempo_durante_varias_horas() {
        
    }

    
    @Cuando("la diferencia es menos de diez")
    public void la_diferencia_es_menos_de_diez() {
        
    }

    @Entonces("hara buen tiempo pasajeramente")
    public void hara_buen_tiempo_pasajeramente() {
        
    }

      //Media menor a presion
    @Cuando("la diferencia es menos de diez unidades")
    public void la_diferencia_es_menos_de_diez_unidades() {
        
    }

    @Entonces("hará frio durante varias horas")
    public void hará_frio_durante_varias_horas() {
        
    }

    
    @Cuando("la diferencia es mayor de diez unidades")
    public void la_diferencia_es_mayor_de_diez_unidades() {
        
    }

    @Entonces("hará frio pasajeramente")
    public void hará_frio_pasajeramente() {
        
    }

}
