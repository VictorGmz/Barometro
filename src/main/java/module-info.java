module com.grupon5.barometro {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.grupon5.barometro to javafx.fxml;
    exports com.grupon5.barometro;
}
