/**
 *
 * @author Dawei Li
 */

package Model;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class OutsourcedPart extends Part {
    private final StringProperty companyName;
    
    public OutsourcedPart () {
        this.companyName = new SimpleStringProperty();
    }
    
    // getter
    public String getCompanyName() {
        return companyName.get();
    }
    
    // setter
    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }
    
    // property
    public StringProperty companyNameProperty() {
        return companyName;
    }
    
    public static String validate(String machineID) {
        String message = "";
        if (machineID == null || machineID.isEmpty()) {
            message += "\nMachine ID cannot be empty";
        }
        return message;
    }
}
