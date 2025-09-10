module se233.chapter4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;

    opens se233.chapter4 to javafx.fxml;
    exports se233.chapter4;
    exports se233.chapter4.controller;
    opens se233.chapter4.controller to javafx.fxml;
    exports se233.chapter4.model;
    opens se233.chapter4.model to javafx.fxml;
    exports se233.chapter4.view;
    opens se233.chapter4.view to javafx.fxml;
}