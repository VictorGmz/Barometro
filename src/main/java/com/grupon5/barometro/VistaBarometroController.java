/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.grupon5.barometro;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author victo
 */
public class VistaBarometroController implements Initializable {

    
    DatosIniBarometro b = new DatosIniBarometro();
    ObservableList<Barometro> listaObservable = b.getBarometro();
    
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnBorrar;
    @FXML
    private Button btnActualizar;
    @FXML
    private ListView<Barometro> lvLista;
    @FXML
    private ImageView ivIcono;
    @FXML
    private TextField tfPresion;
    @FXML
    private TextField tfAltura;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private DatePicker dpHora;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }    

    @FXML
    private void accionBotonNuevo(ActionEvent event) {
        
    }

    @FXML
    private void accionBotonBorrar(ActionEvent event) {
        
    }

    @FXML
    private void accionBotonActualizar(ActionEvent event) {
        
    }
    
}
