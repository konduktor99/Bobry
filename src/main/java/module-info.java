module Bobry.bobrowiec {

    requires javafx.graphics;
    exports bobry to javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;

    exports bobry.Controller to javafx.graphics;
    opens bobry.Controller to javafx.fxml;
    opens bobry to javafx.base;
}