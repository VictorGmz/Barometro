module com.grupon5.barometro {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.grupon5.barometro to javafx.fxml;
    exports com.grupon5.barometro;
}
