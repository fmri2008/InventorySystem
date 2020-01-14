/**
 *
 * @author Dawei Li
 */

package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.*;

public class Product {
    private ObservableList<Part> associatedParts;
    private IntegerProperty productID;
    private StringProperty name;
    private DoubleProperty price;
    private IntegerProperty inStock;
    private IntegerProperty min;
    private IntegerProperty max;
    
    public Product() {
        associatedParts = FXCollections.observableArrayList();
        productID = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        price = new SimpleDoubleProperty();
        inStock = new SimpleIntegerProperty();
        min = new SimpleIntegerProperty();
        max = new SimpleIntegerProperty();
    }
    
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    
    public boolean removeAssociatedPart(int partID) {
        for (Part part : associatedParts) {
            if (part.getPartID() == partID) {
                associatedParts.remove(part);
                return true;
            }
        }
        return false;
    }
    
    public boolean removeAssociatedPart(Part part) {
        if (associatedParts.contains(part)) {
            associatedParts.remove(part);
            return true;
        }
        return false;
    }
    
    public Part lookupAssociatedPart(int partID) {
        for (Part part : associatedParts) {
            if (part.getPartID() == partID)
                return part;
        }
        return null;
    }
    
    // getters
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }
    
    public int getProductID() {
        return productID.get();
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
    
    // Setters
    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }
    
    public void setProductID(int productID) {
        this.productID.set(productID);
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
    
    // Properties
    public IntegerProperty productIDProperty() {
        return productID;
    }
    
    public StringProperty nameProperty() {
        return name;
    }
    
    public DoubleProperty priceProperty() {
        return price;
    }
    
    public IntegerProperty inStockProperty() {
        return inStock;
    }
    
    public IntegerProperty minProperty() {
        return min;
    }
    
    public IntegerProperty maxProperty() {
        return max;
    }
    
    public static String validate(String name, String price, String inStock, String min, String max) {
        StringBuilder message = new StringBuilder();
        if (name == null || name.isEmpty()) {
            message.append("Error(s): \nProduct Name cannot be empty");
        }
        
        if (inStock == null || inStock.isEmpty()) {
            message.append("\nProduct Inv cannot be empty");
        } else if (!isInt(inStock)) {
            message.append("\nProduct Inv must be an integer");
        }
        
        if (price == null || price.isEmpty()) {
            message.append("\nnProduct Price cannot be empty");
        } else if (!isInt(price) && !isDouble(price)) {
            message.append("\nnProduct Price must be a double");
        }
        
        if (max == null || max.isEmpty()) {
            message.append("\nnProduct Max cannot be empty");
        } else if (!isInt(max)) {
            message.append("\nnProduct Max must be an integer");
        }
                
        if (min == null || min.isEmpty()) {
            message.append("\nnProduct Min cannot be empty");
        } else if (!isInt(min)) {
            message.append("\nnProduct Min must be an integer");
        }
        
        if (isInt(min) && isInt(max) && (Integer.parseInt(min) >= Integer.parseInt(max))) {
            message.append("\nnProduct Max must be greater than Part Min");
        }
        
        if (isInt(min) && isInt(max) && isInt(inStock)) {
            if (Integer.parseInt(inStock) < Integer.parseInt(min)) {
                message.append("\nnProduct Inv must be greater than or equal to Part Min");
            }
            else if (Integer.parseInt(inStock) > Integer.parseInt(max)) {
                message.append("\nnProduct Inv must be less than or equal to Part Max");
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
