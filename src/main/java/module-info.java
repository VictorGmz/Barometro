module com.grupon5.barometro {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires io.cucumber.junit;

    opens com.grupon5.barometro to javafx.fxml;
    exports com.grupon5.barometro;
}
