/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.grupon5.barometro;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Grupo N5
 */

//EJERCICIO REALIZADO CON JUNIT
public class BaromtroTest {
    
    public BaromtroTest() {
        
    }
    @Test
    public void test1(){
        double presion  = 0 ;
        double mediaPresiones = 9;
        VistaBarometroController t1 = new VistaBarometroController();
        String resultado = t1.prediccion(mediaPresiones, presion);
        assertEquals("Va a hacer bueno pasajeramente", resultado);
    }
    @Test
    public void test2(){
        double presion  = 0 ;
        double mediaPresiones = 11;
        VistaBarometroController t1 = new VistaBarometroController();
        String resultado = t1.prediccion(mediaPresiones, presion);
        assertEquals("Va a hacer bueno durante varias horas", resultado);
    }
    @Test
    public void test3(){
        double presion  = 9 ;
        double mediaPresiones = 0;
        VistaBarometroController t1 = new VistaBarometroController();
        String resultado = t1.prediccion(mediaPresiones, presion);
        assertEquals("Va a hacer frio pasajeramente", resultado);
    }
    @Test
    public void test4(){
        double presion  = 11 ;
        double mediaPresiones = 0;
        VistaBarometroController t1 = new VistaBarometroController();
        String resultado = t1.prediccion(mediaPresiones, presion);
        assertEquals("Va a hacer frio durante varias horas", resultado);
    }
    /*
    @Test
    public void test5(){
        double presion  = 0 ;
        double mediaPresiones = -1;
        VistaBarometroController t1 = new VistaBarometroController();
        String resultado = t1.prediccion(mediaPresiones, presion);
        assertEquals("Va a hacer frio durante varias horas", resultado);
    }*/

}
