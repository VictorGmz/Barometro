/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.grupon5.barometro;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

        ObservableList<Barometro> listaObs = FXCollections.observableArrayList();
        ObservableList<String> combo = FXCollections.observableArrayList();
        
        ArrayList<Barometro> presiones = new ArrayList<>();
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
    private ComboBox cbHora;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lvLista.setItems(listaObs);
        combo.addAll("00:00","01:00","02:00","03:00","04:00"
        ,"05:00","06:00","07:00","08:00","09:00","10:00",
        "11:00","12:00","13:00","14:00","15:00","16:00",
        "17:00","18:00","19:00","20:00","21:00","22:00",
        "23:00");
        cbHora.setItems(combo);
        
        
        
        tfAltura.textProperty().addListener((ov, old, neew) -> {
            if (neew != "") {//Se comprueba que el nuevo valor no sea "" o nulo
                //NO DEJA TECLEAR NINGUN CHAR DISTINTO DE 0-9
                if (soloNumeros(neew)) {
                } else {tfAltura.setText(old);}
            } else {tfAltura.setText("");}//En caso en el que neew sea "" o nulo, nos pondrá el valor a 0
        });
    }    

    @FXML
    private void accionBotonNuevo(ActionEvent event) {
        Barometro aux = new Barometro(dpFecha.getValue().toString()
                ,cbHora.getValue().toString(),
                Double.parseDouble(tfAltura.getText()));
        if (!listaObs.contains(aux)) {
            listaObs.add(new Barometro(dpFecha.getValue().toString()
                ,cbHora.getValue().toString(),
                Double.parseDouble(tfAltura.getText())));
            presiones.add(aux);
        } else {
            aux = null;
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("¡¡Medidas repetidas!!");
            a.show();
        }
        
        tfPresion.setText(((double)Math.round(aux.getPresion() * 100d) / 100d)+" mbar");
    }

    @FXML
    private void accionBotonBorrar(ActionEvent event) {
        listaObs.remove(lvLista.getSelectionModel().getSelectedItem());
        lvLista.getSelectionModel().clearSelection();
    }

    @FXML
    private void accionBotonActualizar(ActionEvent event) {
        
    }
    
    private boolean soloNumeros(String tf) {
        return (tf.matches("[0-9]*"));
    }
    
}
