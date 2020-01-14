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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author fmri2
 */
public class FXMLAddProductController implements Initializable {

    @FXML private TextField productNameTextField;
    @FXML private TextField productInvTextField;
    @FXML private TextField productPriceTextField;
    @FXML private TextField productMaxTextField;
    @FXML private TextField productMinTextField;
    @FXML private TextField searchTextField;
    
    @FXML private TableView<Part> tableViewAllParts;
    @FXML private TableColumn<?, ?> allPartIDColumn;
    @FXML private TableColumn<?, ?> allPartNameColumn;
    @FXML private TableColumn<?, ?> allPartInventoryLevelColumn;
    @FXML private TableColumn<?, ?> allPartPriceColumn;
    
    @FXML private TableView<Part> tableViewProductParts;
    @FXML private TableColumn<?, ?> productPartIDColumn;
    @FXML private TableColumn<?, ?> productPartNameColumn;
    @FXML private TableColumn<?, ?> productPartInventoryLevelColumn;
    @FXML private TableColumn<?, ?> productPartPriceColumn;
    
    private Product product;
    
    @FXML
    private void search(ActionEvent event) {
        String target = searchTextField.getText();
        ObservableList<Part> parts = Inventory.getPartInventory();
        ObservableList<Part> results = FXCollections.observableArrayList();
        for (Part part : parts) {
            if (Integer.toString(part.getPartID()).equals(target) ||
                part.getName().equals(target)) {
                results.add(part);
            }
        }
        if (results.size() > 0)
            tableViewAllParts.setItems(results);
        else {
            showErrorMessage("No result", "No part was found");
        }
    }
    
    @FXML
    private void add(ActionEvent event) {
        Part selectedPart = tableViewAllParts.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            product.addAssociatedPart(selectedPart);
            tableViewProductParts.setItems(product.getAssociatedParts());
        }
    }
    
    @FXML
    private void delete(ActionEvent event) {
        Part selectedPart = tableViewProductParts.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            product.removeAssociatedPart(selectedPart);
            tableViewProductParts.setItems(product.getAssociatedParts());
        }
    }
    
    @FXML
    private void save(ActionEvent event) throws IOException {
        String productName  = productNameTextField.getText();
        String productInv   = productInvTextField.getText();
        if (productInv == null || productInv.isEmpty())
            productInv = "0";
        String productPrice = productPriceTextField.getText();
        String productMax   = productMaxTextField.getText();
        String productMin   = productMinTextField.getText();
        
        StringBuilder message = new StringBuilder();
        message.append(Product.validate(productName, productPrice, productInv, productMin, productMax));
        ObservableList<Part> associatedParts = product.getAssociatedParts();
        if (associatedParts.isEmpty()) {
            message.append("\nThe product does not have any associated Part");
        } 
        else {
            if (productPrice != null && !productPrice.isEmpty() && isDouble(productPrice)) {
                double productPriceDouble = Double.parseDouble(productPrice);
                double partsPriceTotal = 0.0;
                for (Part part : associatedParts) {
                    partsPriceTotal += part.getPrice();
                }
                if (productPriceDouble < partsPriceTotal) {
                    message.append("The price of product cannot be less than the cost of the parts");
                }
            }
        }
        
        if (message.length() == 0) {
            product.setProductID(Inventory.getProductCount() + 1);
            product.setName(productName);
            product.setInStock(Integer.parseInt(productInv));
            product.setPrice(Double.parseDouble(productPrice));
            product.setMax(Integer.parseInt(productMax));
            product.setMin(Integer.parseInt(productMin));

            Inventory.addProduct(product);
            returnToMain(event);
        }
        else {
            showErrorMessage("Input Error", message.toString());
        }
    }
    
    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Input Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
    
    private boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    
    @FXML
    private void cancel(ActionEvent event) throws IOException {
        returnToMain(event);
    }
    
    private void returnToMain(ActionEvent event) throws IOException {
        Parent main = FXMLLoader.load(getClass().getResource("FXMLMain.fxml"));
        Scene mainScene = new Scene(main, 1000, 500);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.hide();
        stage.setScene(mainScene);
        stage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        allPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        allPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        allPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableViewAllParts.setItems(Inventory.getPartInventory());
        
        product = new Product();
        productPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        productPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPartInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        productPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableViewProductParts.setItems(product.getAssociatedParts());
    }
}
