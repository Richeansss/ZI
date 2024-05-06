module org.example.zi {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.math3;


    opens org.example.zi to javafx.fxml;
    exports org.example.zi;
}