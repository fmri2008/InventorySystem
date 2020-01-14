/**
 *
 * @author Dawei Li
 */

 package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class InhousePart extends Part {
    private final IntegerProperty machineID;
    
    public InhousePart () {
        super();
        this.machineID = new SimpleIntegerProperty();
    }
    
    // getter
    public int getMachineID() {
        return machineID.get();
    }
    
    // setter
    public void setMachineID(int machineID) {
        this.machineID.set(machineID);
    }
    
    // property
    public IntegerProperty machineIDProperty() {
        return machineID;
    }
    
    public static String validate(String machineID) {
        String message = "";
        if (!isInt(machineID)) {
            message += "\nMachine ID must be an integer";
        }
        return message;
    }
    
    private static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
