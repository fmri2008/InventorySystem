/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author fmri2
 */
public class FXMLMainController implements Initializable {

//    @FXML private Button addPart;
    @FXML private TableView<Part> tableViewParts;
    @FXML private TableColumn<?, ?> partIDColumn;
    @FXML private TableColumn<?, ?> partNameColumn;
    @FXML private TableColumn<?, ?> partInventoryLevelColumn;
    @FXML private TableColumn<?, ?> partPriceColumn;
    @FXML private TextField searchPartsTextField;
    
    @FXML private TableView<Product> tableViewProducts;
    @FXML private TableColumn<?, ?> productIDColumn;
    @FXML private TableColumn<?, ?> productNameColumn;
    @FXML private TableColumn<?, ?> productInventoryLevelColumn;
    @FXML private TableColumn<?, ?> productPriceColumn;
    @FXML private TextField searchProductsTextField;
    
    @FXML
    private void addPart(ActionEvent event) throws IOException {
        Parent addPart = FXMLLoader.load(getClass().getResource("FXMLAddPart.fxml"));
        Scene addPartScene = new Scene(addPart, 500, 500);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.hide();
        stage.setScene(addPartScene);
        stage.show();
    }
    
    @FXML
    private void modifyPart(ActionEvent event) throws IOException {
        Part selectedPart = tableViewParts.getSelectionModel().getSelectedItem(); 
        if (selectedPart == null) {
            showErrorMessage("No selection", "No part was selected");
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLModifyPart.fxml"));
            Parent modifyPart = loader.load();
            Scene modifyPartScene = new Scene(modifyPart, 500, 500);
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.hide();
            stage.setScene(modifyPartScene);
            stage.show();
            FXMLModifyPartController controller = loader.getController();
            controller.setPart(selectedPart);
        }
    }
    
    @FXML
    private void deletePart(ActionEvent event) {
        Part selectedPart = tableViewParts.getSelectionModel().getSelectedItem();
        Inventory.deletePart(selectedPart);
        tableViewParts.setItems(Inventory.getPartInventory());
    }
    
    @FXML
    private void searchParts(ActionEvent event) {
        String target = searchPartsTextField.getText();
        ObservableList<Part> parts = Inventory.getPartInventory();
        ObservableList<Part> results = FXCollections.observableArrayList();
        for (Part part : parts) {
            if (Integer.toString(part.getPartID()).equals(target) ||
                part.getName().equals(target)) {
                results.add(part);
            }
        }
        if (results.size() > 0)
            tableViewParts.setItems(results);
        else {
            showErrorMessage("No result", "No part was found");
        }
    }
    
    @FXML
    private void addProduct(ActionEvent event) throws IOException {
        Parent addProduct = FXMLLoader.load(getClass().getResource("FXMLAddProduct.fxml"));
        Scene addProductScene = new Scene(addProduct, 1000, 500);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.hide();
        stage.setScene(addProductScene);
        stage.show();
    }
    
    @FXML
    private void modifyProduct(ActionEvent event) throws IOException {
        Product selectedProduct = tableViewProducts.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showErrorMessage("No selection", "No product was selected");
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLModifyProduct.fxml"));
            Parent modifyPart = loader.load();
            Scene modifyPartScene = new Scene(modifyPart, 1000, 500);
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.hide();
            stage.setScene(modifyPartScene);
            stage.show();
            FXMLModifyProductController controller = loader.getController();
            controller.setProduct(selectedProduct);
        }
    }
    
    @FXML 
    private void deleteProduct(ActionEvent event) {
        Product selectedProduct = tableViewProducts.getSelectionModel().getSelectedItem();
        if (!selectedProduct.getAssociatedParts().isEmpty()) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Confirmation");
            alert.setHeaderText("The selected product has at least one part associated with it."
                    + "\nAre you sure to delete it?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Inventory.removeProduct(selectedProduct.getProductID());
                tableViewProducts.setItems(Inventory.getProductInventory());
            }
        }
        else {
            Inventory.removeProduct(selectedProduct.getProductID());
            tableViewProducts.setItems(Inventory.getProductInventory());
        }
    }
    
    @FXML 
    private void searchProducts(ActionEvent event) {
        String target = searchProductsTextField.getText();
        ObservableList<Product> products = Inventory.getProductInventory();
        ObservableList<Product> results = FXCollections.observableArrayList();
        for (Product product : products) {
            if (Integer.toString(product.getProductID()).equals(target) ||
                product.getName().equals(target)) {
                results.add(product);
            }
        }
        if (results.size() > 0)
            tableViewProducts.setItems(results);
        else {
            showErrorMessage("No result", "No product was found");
        }
    }
    
    @FXML
    private void exit(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
    
    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Input Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
        
        tableViewParts.setItems(Inventory.getPartInventory());
        tableViewProducts.setItems(Inventory.getProductInventory());
    }
    
}
