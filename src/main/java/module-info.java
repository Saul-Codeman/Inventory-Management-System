module com.example.wgusoftware1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.wgusoftware1 to javafx.fxml;
    exports com.example.wgusoftware1;
}