package de.lubowiecki.productmanagement;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TextField name;

    @FXML
    private TextField anzahl;

    @FXML
    private TextField preis;

    @FXML
    private TextArea beschreibung;

    @FXML
    private TableView<Product> tblProducts;

    //private List<Product> products = new ArrayList<>();

    private ProductRepository repository;

    @FXML
    protected void save() {
        Product product = new Product();
        product.setName(name.getText());
        product.setDescription(beschreibung.getText());

        // TODO: Validieren
        product.setQuantity(Integer.parseInt(anzahl.getText()));

        // TODO: Validieren
        product.setPrice(Double.parseDouble(preis.getText()));

        try {
            repository.save(product);
            clearFields();
            show();
        }
        catch (Exception e) {
            e.printStackTrace(); // TODO: Ausgabe der Probleme in die GUI
        }
    }

    private void clearFields() {
        name.clear();
        beschreibung.clear();
        anzahl.clear();
        preis.clear();
    }

    private void show() {
        try {
            List<Product> products = repository.find(); // Holt die Produkte aus der DB
            tblProducts.setItems(FXCollections.observableList(products));
        }
        catch(Exception e) {
            e.printStackTrace(); // TODO: Ausgabe der Probleme in die GUI
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            repository = new ProductRepository();
        }
        catch(Exception e) {
            e.printStackTrace(); // TODO: Ausgabe der Probleme in die GUI
        }
        show();
    }
}