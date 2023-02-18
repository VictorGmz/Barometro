package com.grupon5.barometro;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author victo
 */
public class VistaBarometroController implements Initializable {

    ObservableList<Barometro> listaObs = FXCollections.observableArrayList();
    ObservableList<String> combo = FXCollections.observableArrayList();
    ResourceBundle rb;
    ArrayList<Barometro> predicciones = new ArrayList<>();
    double mediaPredicciones = 0;
    private ChangeListener<Barometro> barometroChange;
    LocalDate dia;
    String ruta = "com/grupon5/barometro/iconosBarometro/";
    int idioma;
    @FXML
    private Button btnBorrar;

    @FXML
    private Button btnCalibrador;

    @FXML
    private Button btnIdioma;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnPrediccion;

    @FXML
    private ComboBox<String> cbHora;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private ImageView ivIcono;

    @FXML
    private Label lbInfo;

    @FXML
    private Label lbPrediccion;

    @FXML
    private ListView<Barometro> lvLista;

    @FXML
    private ProgressBar progresBar;

    @FXML
    private TextField tfAltura;

    @FXML
    private TextField tfPresion;

    @FXML
    private AnchorPane panelDe;

    @FXML
    private AnchorPane panelIz;

    @FXML
    private AnchorPane panelSuperior;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    private FXMLLoader getFXMLLoader() {
        FXMLLoader loader = new FXMLLoader();
        //Le da a nuestro FXMLLoder la dirección del archivo .properties
        loader.setResources(ResourceBundle.getBundle("com.grupon5.barometro.i18n/cadenas",
                Locale.getDefault()));
        //Le cambia la location al fxml. Para que vuelva a cargarse en el idioma deseado
        loader.setLocation(getClass().getResource("vistaBarometro.fxml"));
        return loader;
    }
    private Task copyWorker;

    public Task createWorker() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(250);
                    updateProgress(i + 1, 10);
                }
                progresBar.setVisible(false);
                return true;
            }
        };
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        copyWorker = createWorker();
        rb = ResourceBundle.getBundle("com.grupon5.barometro.i18n/cadenas",
                Locale.getDefault());
        // Vinculamos el progreso del worker con la barra
        progresBar.progressProperty().bind(copyWorker.progressProperty());

        //Bloqueamos el resto de pantalla hasta que la progressBar finalice
        panelDe.disableProperty().bind(progresBar.progressProperty().isNotEqualTo(1., 0.));
        panelIz.disableProperty().bind(progresBar.progressProperty().isNotEqualTo(1., 0.));
        panelSuperior.disableProperty().bind(progresBar.progressProperty().isNotEqualTo(1., 0.));
        new Thread(copyWorker).start();

        Image icon;
        //Seteamos la imagen correspondiente al idioma
        switch (Locale.getDefault().toString()) {
            case "en":
                icon = new Image(ruta + "reino-unido.png", 32, 32, false, true);
                btnIdioma.setGraphic(new ImageView(icon));
                break;
            case "fr":
                icon = new Image(ruta + "francia.png", 32, 32, false, true);
                btnIdioma.setGraphic(new ImageView(icon));
                break;
            case "it":
                icon = new Image(ruta + "italia.png", 32, 32, false, true);
                btnIdioma.setGraphic(new ImageView(icon));
                break;
            default:
                icon = new Image(ruta + "espana.png", 32, 32, false, true);
                btnIdioma.setGraphic(new ImageView(icon));
                break;
        }
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
            rb = ResourceBundle.getBundle("com.grupon5.barometro.i18n/cadenas",
                    Locale.getDefault());
            lbInfo.setText(rb.getString("lbInfo1"));
            lbInfo.setAccessibleText(rb.getString("lbInfo1"));
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
        rb = ResourceBundle.getBundle("com.grupon5.barometro.i18n/cadenas",
                Locale.getDefault());
        lbInfo.setText(rb.getString("lbInfo2"));
        lbInfo.setAccessibleText(rb.getString("lbInfo2"));
    }

    public VistaBarometroController(){
    }
    /**
     * Estudia los datos obtenidos y los compara para darnos una predicción del
     * tiempo que va a hacer. Además nos dice la precisión de dicha predicción
     *
     * @param event
     */
    @FXML
    private void accionBotonPrediccion(ActionEvent event) {
        rb = ResourceBundle.getBundle("com.grupon5.barometro.i18n/cadenas",
                Locale.getDefault());
        //Definimos un string con la ruta donde se encuentran nuestros iconos
        //Datos necesarios para prediccion
        String prediccion = "";
        if (listaObs.size() < 24 || listaObs.isEmpty()) {
            Image icon = new Image(ruta + "error-icon.png");
            ivIcono.setImage(icon);
            lbPrediccion.setText(rb.getString("lbPrediccion3"));
            lbPrediccion.setAccessibleText(rb.getString("lbPrediccion3"));
            prediccion = "datos insuficientes";
        } else {
            /*
            prediccion = prediccion((new Barometro("", "",
                Double.parseDouble(tfAltura.getText()))).getPresion(), mediaPredicciones);
            */
        }
    }

    public String prediccion(double presion, double media) {
        String prediccion = "";

        //"Cuando sube la presión, te puedes ir de excursión"
        if (media < presion) {
            /*Image icon = new Image(ruta + "sun-icon.png");
            ivIcono.setImage(icon);*/
            prediccion = "Va a hacer bueno";
        } //"Si la presión baja y viene mezquino, mejor quedarse en el casino"
        else {
            /*Image icon = new Image(ruta + "icons8-cloud-with-rain-48.png");
            ivIcono.setImage(icon);*/
            prediccion = "Va a hacer frio";
        }
        //Si hay mucha variación entre la presion a nivel del mar y la media
        //de los datos obtenidos se dice que será más probable que pase
        if (Math.abs(media - presion) < 10) {
            /*lbPrediccion.setText(rb.getString("lbPrediccion1"));
            lbPrediccion.setAccessibleText(rb.getString("lbPrediccion1"));*/
            prediccion += " pasajeramente";
        } else {
            /*lbPrediccion.setText(rb.getString("lbPrediccion2"));
            lbPrediccion.setAccessibleText(rb.getString("lbPrediccion2"));*/
            prediccion += " durante varias horas";
        }
        return prediccion;
    }
    //Metemos iconos en los botones correspondientes

    public void anyadirImagenes() {
        Image icon = new Image(ruta + "plus.png", 24, 24, false, true);
        btnNuevo.setGraphic(new ImageView(icon));
        icon = new Image(ruta + "padlock-icon.png", 24, 24, false, true);
        btnCalibrador.setGraphic(new ImageView(icon));
        icon = new Image(ruta + "delete.png", 24, 24, false, true);
        btnBorrar.setGraphic(new ImageView(icon));
        icon = new Image(ruta + "medidor-de-agua.png", 24, 24, false, true);
        btnPrediccion.setGraphic(new ImageView(icon));
    }

    //Permite poder modificar la altura de nuevo
    @FXML
    private void bloqueaAltura(MouseEvent event) {
        tfAltura.setEditable(true);
    }

    private void eliminaInfo() {
        ivIcono.setImage(null);
        lbPrediccion.setText("");
        lbPrediccion.setAccessibleText("");
        lbInfo.setText("");
    }

    @FXML
    void cambioIdioma(ActionEvent event) {
        switch (Locale.getDefault().toString()) {
            case "en":
                Locale.setDefault(Locale.FRENCH);
                break;
            case "fr":
                Locale.setDefault(Locale.ITALIAN);
                break;
            case "it":
                Locale.setDefault(new Locale("es_ES"));
                break;
            default:
                Locale.setDefault(Locale.ENGLISH);
                break;
        }
        try {
            //Creamos un nuevo Parent con la nueva Localización
            Parent pane = getFXMLLoader().load();
            //Cargamos este parent en nuestra vista
            App.getPrimaryStage().getScene().setRoot(pane);
        } catch (IOException ieo) {
        }
        //Mostramos nuestra vista
        App.getPrimaryStage().show();
    }
}
