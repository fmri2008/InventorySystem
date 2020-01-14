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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author fmri2
 */
public class FXMLModifyPartController implements Initializable {

    @FXML private TextField partIDTextField;
    @FXML private TextField partNameTextField;
    @FXML private TextField partInvTextField;
    @FXML private TextField partPriceTextField;
    @FXML private TextField partMaxTextField;
    @FXML private TextField partMinTextField;
    @FXML private TextField inHouseOrOutsourcedTextField;
    @FXML private RadioButton inHouseRadioButton;
    @FXML private RadioButton outsourcedRadioButton;
    @FXML private GridPane inHouseOrOutsourcedPane;
    @FXML private Label inHouseOrOutsourcedLabel;
    
    private Part part;
    private boolean isInHouse;
    
    @FXML
    private void inHouseSelected(ActionEvent event) {
        setInHouse();
    }
    
    @FXML
    private void outsourcedSelected(ActionEvent event) {
        setOutsourced();
    }
    
    @FXML
    private void save(ActionEvent event) throws IOException, Exception {
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
                InhousePart newPart = new InhousePart();
                newPart.setPartID(part.getPartID());
                newPart.setName(partName);
                newPart.setInStock(Integer.parseInt(partInv));
                newPart.setPrice(Double.parseDouble(partPrice));
                newPart.setMax(Integer.parseInt(partMax));
                newPart.setMin(Integer.parseInt(partMin));
                newPart.setMachineID(Integer.parseInt(inOrOut));
                Inventory.updatePart(newPart.getPartID(), newPart);
                
                returnToMain(event);
            }
            else {
                showErrorMessage(message.toString());
            }
        }
        else {
            message.append(OutsourcedPart.validate(inOrOut));
            
            if (message.length() == 0) {
                InhousePart newPart = new InhousePart();
                newPart.setPartID(part.getPartID());
                newPart.setName(partName);
                newPart.setInStock(Integer.parseInt(partInv));
                newPart.setPrice(Double.parseDouble(partPrice));
                newPart.setMax(Integer.parseInt(partMax));
                newPart.setMin(Integer.parseInt(partMin));
                newPart.setMachineID(Integer.parseInt(inOrOut));
                Inventory.updatePart(newPart.getPartID(), newPart);
                
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
    
    public void setPart(Part part) {
        this.part = part;
        partIDTextField.setText(Integer.toString(part.getPartID()));
        partNameTextField.setText(part.getName());
        partInvTextField.setText(Integer.toString(part.getInStock()));
        partPriceTextField.setText(Double.toString(part.getPrice()));
        partMaxTextField.setText(Integer.toString(part.getMax()));
        partMinTextField.setText(Integer.toString(part.getMin()));
        if (part instanceof InhousePart) {
            int machineID = ((InhousePart) part).getMachineID();
            inHouseOrOutsourcedTextField.setText(Integer.toString(machineID));
            setInHouse();
        }
        else {
            String companyName = ((OutsourcedPart) part).getCompanyName();
            inHouseOrOutsourcedTextField.setText(companyName);
            setOutsourced();
        }
    }
    
    private void setInHouse() {
        isInHouse = true;
        inHouseOrOutsourcedPane.setHgap(30);
        inHouseOrOutsourcedLabel.setText("Machine ID");
        inHouseOrOutsourcedTextField.setPromptText("Machine ID");
        inHouseRadioButton.setSelected(true);
        outsourcedRadioButton.setSelected(false);
    }
    
    private void setOutsourced() {
        isInHouse = false;
        inHouseOrOutsourcedPane.setHgap(10);
        inHouseOrOutsourcedLabel.setText("Company Name");
        inHouseOrOutsourcedTextField.setPromptText("Company Name");
        inHouseRadioButton.setSelected(false);
        outsourcedRadioButton.setSelected(true);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
