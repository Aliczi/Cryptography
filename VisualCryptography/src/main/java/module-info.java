module com.example.kryptografia8 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.desktop;

    opens com.example.vc to javafx.fxml;
    exports com.example.vc;
}