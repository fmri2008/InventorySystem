/**
 *
 * @author Dawei Li
 */

package Model;

import javafx.beans.property.*;


public abstract class Part {
    private final IntegerProperty partID;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty inStock;
    private final IntegerProperty min;
    private final IntegerProperty max;
    
    public Part() {
        partID = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        price = new SimpleDoubleProperty();
        inStock = new SimpleIntegerProperty();
        min = new SimpleIntegerProperty();
        max = new SimpleIntegerProperty();
    }
    
    // getters
    public int getPartID() {
        return partID.get();
    }
    
    public String getName() {
        return name.get();
    }
    
    public double getPrice() {
        return price.get();
    }
    
    public int getInStock() {
        return inStock.get();
    }
    
    public int getMin() {
        return min.get();
    }
    
    public int getMax() {
        return max.get();
    }
    
    // setters
    public void setPartID(int partID) {
        this.partID.set(partID);
    }
    
    public void setName(String name) {
        this.name.set(name);
    }
    
    public void setPrice(double price) {
        this.price.set(price);
    }
    
    public void setInStock(int inStock) {
        this.inStock.set(inStock);
    }
    
    public void setMin(int min) {
        this.min.set(min);
    }
    
    public void setMax(int max) {
        this.max.set(max);
    }
    
    // properties
    public IntegerProperty partIDProperty () {
        return partID;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public DoubleProperty priceProperty () {
        return price;
    }

    public IntegerProperty inStockProperty () {
        return inStock;
    }

    public IntegerProperty minProperty () {
        return min;
    }

    public IntegerProperty maxProperty () {
        return max;
    }
    
    public static String validate(String name, String price, String inStock, String min, String max) {
        StringBuilder message = new StringBuilder();
        if (name == null || name.isEmpty()) {
            message.append("Error(s): \nPart Name cannot be empty");
        }
        
        if (inStock == null || inStock.isEmpty()) {
            message.append("\nPart Inv cannot be empty");
        } else if (!isInt(inStock)) {
            message.append("\nPart Inv must be an integer");
        }
        
        if (price == null || price.isEmpty()) {
            message.append("\nPart Price cannot be empty");
        } else if (!isInt(price) && !isDouble(price)) {
            message.append("\nPart Price must be a double");
        }
        
        if (max == null || max.isEmpty()) {
            message.append("\nPart Max cannot be empty");
        } else if (!isInt(max)) {
            message.append("\nPart Max must be an integer");
        }
                
        if (min == null || min.isEmpty()) {
            message.append("\nPart Min cannot be empty");
        } else if (!isInt(min)) {
            message.append("\nPart Min must be an integer");
        }
        
        if (isInt(min) && isInt(max) && (Integer.parseInt(min) >= Integer.parseInt(max))) {
            message.append("\nPart Max must be greater than Part Min");
        }
        
        if (isInt(min) && isInt(max) && isInt(inStock)) {
            if (Integer.parseInt(inStock) < Integer.parseInt(min)) {
                message.append("\nPart Inv must be greater than or equal to Part Min");
            }
            else if (Integer.parseInt(inStock) > Integer.parseInt(max)) {
                message.append("\nPart Inv must be less than or equal to Part Max");
            }
        }
        return message.toString();
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
    
    private static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
