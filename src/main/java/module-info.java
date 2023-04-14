module de.lubowiecki.productmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens de.lubowiecki.productmanagement to javafx.fxml;
    exports de.lubowiecki.productmanagement;
}