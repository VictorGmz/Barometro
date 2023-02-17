/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.grupon5.barometro;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
    double mediaPredicciones = 0;
    private ChangeListener<Barometro> barometroChange;
    LocalDate dia;
    String ruta = "com/grupon5/barometro/iconosBarometro/";
    
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
    @FXML
    private Label lbPrediccion;
    @FXML
    private Button btnCalibrador;
    @FXML
    private Label lbInfo;
    

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lvLista.setItems(listaObs);
        //Mete los Strings con la hora al combo box
        combosetItems();
        anyadirImagenes();
        //Comenzamos a nivel del mar
        tfAltura.setText("0");

        //Para los dos TextField forzamos a que únicamente entren numeros 
        //tipo Double
        tfAltura.textProperty().addListener((ov, old, neew) -> {
            eliminaInfo();
            if (!"".equals(neew)) {//Se comprueba que el nuevo valor no sea "" o nulo
                if (!neew.matches("\\d*(\\.\\d*)?")) {
                    tfAltura.setText(old);
                }
            } else {
                tfAltura.setText("");
            }
        });
        tfPresion.textProperty().addListener((ov, old, neew) -> {
            eliminaInfo();
            if (!"".equals(neew)) {//Se comprueba que el nuevo valor no sea "" o nulo
                if (!neew.matches("\\d*(\\.\\d*)?")) {
                    tfPresion.setText(old);
                }
            } else {
                tfPresion.setText("");
            }
        });

        lvLista.getSelectionModel().selectedItemProperty().
                addListener(barometroChange = (observable, oldValue, newValue) -> {
            eliminaInfo();

                    if (newValue != null) {
                        tfAltura.setText(newValue.getAltura() + "");
                        tfPresion.setText(newValue.getPresion() + "");
                        dpFecha.setValue(newValue.getFechaLocal());
                        cbHora.setValue(newValue.getHora());
                    }
                });
        btnBorrar.disableProperty().bind(
                lvLista.getSelectionModel().selectedItemProperty().isNull()
        );

        btnNuevo.disableProperty().bind(
                tfAltura.textProperty().isEqualTo("")
                        .or(tfPresion.textProperty().isEqualTo(""))
                        .or(cbHora.getSelectionModel().selectedItemProperty().isNull())
                        .or(dpFecha.getEditor().textProperty().isEmpty())
        );

    }

    /**
     * Llena un comboBox con Strings tipo: 00:00, 01:00,...,23:00
     */
    public void combosetItems() {
        combo.addAll("00:00", "01:00", "02:00", "03:00", "04:00",
                "05:00", "06:00", "07:00", "08:00", "09:00", "10:00",
                "11:00", "12:00", "13:00", "14:00", "15:00", "16:00",
                "17:00", "18:00", "19:00", "20:00", "21:00", "22:00",
                "23:00");
        cbHora.setItems(combo);
    }

    /**
     * Introduce una nueva medida a nuestra lista tomando los datos de la
     * interfaz
     *
     * @param event
     */
    @FXML
    private void accionBotonNuevo(ActionEvent event) throws InterruptedException {
        
        eliminaInfo();
        tfAltura.setEditable(false);
        Barometro aux = new Barometro(dpFecha.getValue().toString(),
                cbHora.getValue().toString(),
                Double.parseDouble(tfAltura.getText()),
                dpFecha.getValue(),
                Double.parseDouble(tfPresion.getText()));
        if (!listaObs.contains(aux)) {
            Barometro b = new Barometro(dpFecha.getValue().toString(),
                    cbHora.getValue().toString(),
                    Double.parseDouble(tfAltura.getText()),
                    dpFecha.getValue(),
                    Double.parseDouble(tfPresion.getText()));
            listaObs.add(b);
            predicciones.add(aux);
            mediaPredicciones += b.getPresion();
            if (predicciones.size() > 1) {
                mediaPredicciones /= 2;
            }
        } else {
            aux = null;
            lbInfo.setText("Presión duplicada. No se puede añadir");
        }
    }

    /**
     * Elimina los datos de la lista observable y del arrayList
     *
     * @param event
     */
    @FXML
    private void accionBotonBorrar(ActionEvent event) {
        
        eliminaInfo();
        listaObs.remove(lvLista.getSelectionModel().getSelectedItem());
        lvLista.getSelectionModel().clearSelection();

        tfPresion.setText("");
        tfAltura.setText("0");
        dpFecha.setValue(null);
        cbHora.setValue(null);
        //labelInfo.setText("Item eliminado.");
    }

    /**
     * Estudia los datos obtenidos y los compara para darnos una predicción del
     * tiempo que va a hacer. Además nos dice la precisión de dicha prediccin
     *
     * @param event
     */
    @FXML
    private void accionBotonPrediccion(ActionEvent event) {
        double presion = (new Barometro("", "", Double.parseDouble(tfAltura.getText()))).getPresion();
        //Definimos un string con la ruta donde se encuentran nuestros iconos

        String precisionPrediccion;
        //Datos necesarios para prediccion
        if (listaObs.size() < 24 || listaObs.isEmpty()) {
            Image icon = new Image(ruta + "error-icon.png");
            ivIcono.setImage(icon);
            //labelPrecision.setText("Datos insuficientes");
        } else {
            //"Cuando sube la presión, te puedes ir de excursión"
            if (mediaPredicciones < presion) {
                Image icon = new Image(ruta + "sun-icon.png");
                ivIcono.setImage(icon);
            } //"Si la presión baja y viene mezquino, mejor quedarse en el casino"
            else {
                Image icon = new Image(ruta + "icons8-cloud-with-rain-48.png");
                ivIcono.setImage(icon);
            }
            //Si hay mucha variación entre la presion a nivel del mar y la media
            //de los datos obtenidos se dice que será más probable que pase
            if (Math.abs(mediaPredicciones - presion) < 10) {
                //labelPrecision.setText("Poco probable");
            } else {
                //labelPrecision.setText("Es probable");
            }
        }
    }
    //Metemos iconos en los botones correspondientes
    
    public void anyadirImagenes() {
        Image icon = new Image(ruta+"plus.png",24,24,false,true);
        btnNuevo.setGraphic(new ImageView(icon));
        icon = new Image(ruta+"padlock-icon.png",24,24,false,true);
        btnCalibrador.setGraphic(new ImageView(icon));
        icon = new Image(ruta+"delete.png",24,24,false,true);
        btnBorrar.setGraphic(new ImageView(icon));
        icon = new Image(ruta+"medidor-de-agua.png",24,24,false,true);
        btnPrediccion.setGraphic(new ImageView(icon));
    }
    //Permite poder modificar la altura de nuevo
    @FXML
    private void bloqueaAltura(MouseEvent event) {
        tfAltura.setEditable(true);
    }

    private void eliminaInfo() {
        lbInfo.setText("");
    }
}