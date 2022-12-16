/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.grupon5.barometro;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalQueries;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author victo
 */
public class VistaBarometroController implements Initializable {

    ObservableList<Barometro> listaObs = FXCollections.observableArrayList();
    ObservableList<String> combo = FXCollections.observableArrayList();

    ArrayList<Barometro> predicciones = new ArrayList<>();
    double mediaPredicciones=0;
    private ChangeListener<Barometro> barometroChange;
    LocalDate dia;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnBorrar;
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
    @FXML
    private Button btnPrediccion;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lvLista.setItems(listaObs);
        combosetItems();

        tfAltura.textProperty().addListener((ov, old, neew) -> {
            if (neew != "") {//Se comprueba que el nuevo valor no sea "" o nulo
                //NO DEJA TECLEAR NINGUN CHAR DISTINTO DE 0-9
                if (soloNumeros(neew)) {
                } else {
                    tfAltura.setText(old);
                }
            } else {
                tfAltura.setText("");
            }
        });

        lvLista.getSelectionModel().selectedItemProperty().
                addListener(barometroChange = (observable, oldValue, newValue) -> {

                    if (newValue != null) {
                        tfPresion.setText((((double) Math.round(newValue.getPresion() * 100d) / 100d) + " mbar"));
                        tfAltura.setText(newValue.getAltura() + "");
                        dpFecha.setValue(newValue.getFechaLocal());
                        cbHora.setValue(newValue.getHora());
                    }
                });
        btnBorrar.disableProperty().bind(
                lvLista.getSelectionModel().selectedItemProperty().isNull()
        );
        
    }

    public void combosetItems(){
        combo.addAll("00:00", "01:00", "02:00", "03:00", "04:00",
                "05:00", "06:00", "07:00", "08:00", "09:00", "10:00",
                "11:00", "12:00", "13:00", "14:00", "15:00", "16:00",
                "17:00", "18:00", "19:00", "20:00", "21:00", "22:00",
                "23:00");
        cbHora.setItems(combo);
    }
    
    @FXML
    private void accionBotonNuevo(ActionEvent event) {
        Barometro aux = new Barometro(dpFecha.getValue().toString(),
                cbHora.getValue().toString(),
                Integer.parseInt(tfAltura.getText()),
                dpFecha.getValue());
        if (!listaObs.contains(aux)) {
            Barometro b =new Barometro(dpFecha.getValue().toString(),
                    cbHora.getValue().toString(),
                    Integer.parseInt(tfAltura.getText()),
                    dpFecha.getValue());
            listaObs.add(b);
            predicciones.add(aux);
            mediaPredicciones+= b.getPresion();
            if(predicciones.size()>1)mediaPredicciones/= 2;
        } else {
            aux = null;
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("¡¡Medidas repetidas!!");
            a.show();
        }

        tfPresion.setText(((double) Math.round(aux.getPresion() * 100d) / 100d) + " mbar");
    }

    @FXML
    private void accionBotonBorrar(ActionEvent event) {
        listaObs.remove(lvLista.getSelectionModel().getSelectedItem());
        lvLista.getSelectionModel().clearSelection();

        tfPresion.setText("");
        tfAltura.setText("");
        dpFecha.setValue(null);
        cbHora.setValue(null);
    }

    @FXML
    private void accionBotonPrediccion(ActionEvent event) {
        double presion = (new Barometro("","",0)).getPresion();
        if(mediaPredicciones< presion){
            Image icon = new Image("com/grupon5/barometro/iconosBarometro/icons8-cyclone-48.png");
        ivIcono.setImage(icon);
            System.out.println("La presion es menor");
        }
        else if(mediaPredicciones==presion){
            Image icon = new Image("com/grupon5/barometro/iconosBarometro/icons8-cloud-48.png");
        ivIcono.setImage(icon);
            System.out.println("La presion es igual");
        }
        else{
            Image icon = new Image("com/grupon5/barometro/iconosBarometro/icons8-sun-behind-cloud-48.png");
        ivIcono.setImage(icon);
            System.out.println("La presion es mayor");
        }
    }

    private boolean soloNumeros(String tf) {
        return (tf.matches("[0-9]*"));
    }

    @FXML
    private void calibrarAltura(MouseEvent event) {
        tfAltura.setEditable(true);
    }

}
