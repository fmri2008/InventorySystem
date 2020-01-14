/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author fmri2
 */
public class FXMLAddPartController implements Initializable {
    
    @FXML private TextField partNameTextField;
    @FXML private TextField partInvTextField;
    @FXML private TextField partPriceTextField;
    @FXML private TextField partMaxTextField;
    @FXML private TextField partMinTextField;
    
    @FXML private RadioButton inHouseRadioButton;
    @FXML private RadioButton outsourcedRadioButton;
    @FXML private GridPane inHouseOrOutsourcedPane;
    @FXML private Label inHouseOrOutsourcedLabel;
    @FXML private TextField inHouseOrOutsourcedTextField;
    
    private boolean isInHouse;
    
    @FXML
    private void inHouseSelected(ActionEvent event) {
        setInHouse();
    }
    
    private void setInHouse() {
        isInHouse = true;
        inHouseOrOutsourcedPane.setHgap(30);
        inHouseOrOutsourcedLabel.setText("Machine ID");
        inHouseOrOutsourcedTextField.setPromptText("Machine ID");
        inHouseRadioButton.setSelected(true);
        outsourcedRadioButton.setSelected(false);
    }
    
    @FXML
    private void outsourcedSelected(ActionEvent event) {
        isInHouse = false;
        inHouseOrOutsourcedPane.setHgap(10);
        inHouseOrOutsourcedLabel.setText("Company Name");
        inHouseOrOutsourcedTextField.setPromptText("Company Name");
        inHouseRadioButton.setSelected(false);
        outsourcedRadioButton.setSelected(true);
    }
    
    @FXML
    private void save(ActionEvent event) throws IOException {
        String partName     = partNameTextField.getText();
        String partInv      = partInvTextField.getText();
        String partPrice    = partPriceTextField.getText();
        String partMax      = partMaxTextField.getText();
        String partMin      = partMinTextField.getText();
        String inOrOut      = inHouseOrOutsourcedTextField.getText();
        
        StringBuilder message = new StringBuilder();
        message.append(Part.validate(partName, partPrice, partInv, partMin, partMax));
        
        if (isInHouse) {
            message.append(InhousePart.validate(inOrOut));
            if (message.length() == 0) {
                InhousePart part = new InhousePart();
                part.setPartID(Inventory.getPartCount() + 1); // partID == partCount + 1
                part.setName(partName);
                part.setInStock(Integer.parseInt(partInv));
                part.setPrice(Double.parseDouble(partPrice));
                part.setMax(Integer.parseInt(partMax));
                part.setMin(Integer.parseInt(partMin));
                part.setMachineID(Integer.parseInt(inOrOut));
                Inventory.addPart(part);
                
                returnToMain(event);
            }
            else {
                showErrorMessage(message.toString());
            }
        }
        else {
            message.append(OutsourcedPart.validate(inOrOut));
            
            if (message.length() == 0) {
                InhousePart part = new InhousePart();
                part.setPartID(Inventory.getPartCount() + 1); // partID == partCount + 1
                part.setName(partName);
                part.setInStock(Integer.parseInt(partInv));
                part.setPrice(Double.parseDouble(partPrice));
                part.setMax(Integer.parseInt(partMax));
                part.setMin(Integer.parseInt(partMin));
                part.setMachineID(Integer.parseInt(inOrOut));
                Inventory.addPart(part);
                
                returnToMain(event);
            }
            else {
                showErrorMessage(message.toString());
            }
        }
    }
    
    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Input Error");
        alert.setHeaderText(message);
        alert.showAndWait();
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
        setInHouse();
    }    
    
}
