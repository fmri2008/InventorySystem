/**
 *
 * @author Dawei Li
 */

 package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Product> productInventory = FXCollections.observableArrayList();;
    private static ObservableList<Part> partInventory = FXCollections.observableArrayList();
    
    private static int partCount = 0;
    private static int productCount = 0;
    
    public static ObservableList<Product> getProductInventory() {
        return productInventory;
    }
    
    public static ObservableList<Part> getPartInventory() {
        return partInventory;
    }
    
    public static int getPartCount() {
        return partCount;
    }
    
    public static int getProductCount() {
        return productCount;
    }
    
    public static void addProduct(Product product) {
        productInventory.add(product);
        productCount++;
    }
    
    public static boolean removeProduct(int productID) {
        for (Product product : productInventory) {
            if (product.getProductID() == productID) {
                productInventory.remove(product);
                return true;
            }
        }
        return false;
    }
    
    public static Product lookupProduct(int productID) {
        for (Product product : productInventory) {
            if (product.getProductID() == productID)
                return product;
        }
        return null;
    }
    
    public static void updateProduct(int productID, Product newProduct) throws Exception {
//        productInventory.set(--productID, newProduct);
        for (Product product : productInventory) {
            if (product.getProductID() == productID) {
                product.setAssociatedParts(newProduct.getAssociatedParts());
                product.setName(newProduct.getName());
                product.setInStock(newProduct.getInStock());
                product.setPrice(newProduct.getPrice());
                product.setMax(newProduct.getMax());
                product.setMin(newProduct.getMin());
                return;
            }
        }
        throw new Exception("product does not exist");
    }
    
    public static void addPart(Part part) {
        partInventory.add(part);
        partCount++;
        
    }
    
    public static boolean deletePart(Part part) {
        for (Part p : partInventory) {
            if (p.getPartID() == part.getPartID()) {
                partInventory.remove(p);
                return true;
            }
        }
        return false;
    }
    
    public static Part lookupPart(int partID) {
        for (Part part : partInventory) {
            if (part.getPartID() == partID)
                return part;
        }
        return null;
    }
    
    public static void updatePart(int partID, Part newPart) throws Exception {
//        partInventory.set(--partID, newPart); 
        for (Part part : partInventory) {
            if (part.getPartID() == partID) {
                part.setName(newPart.getName());
                part.setInStock(newPart.getInStock());
                part.setPrice(newPart.getPrice());
                part.setMax(newPart.getMax());
                part.setMin(newPart.getMin());
                return;
            }
        }
        throw new Exception("Part does not exist");
    }
}
