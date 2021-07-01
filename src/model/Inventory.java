package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Inventory model that controls the part and product data and stores them.
 *
 * <p><b>FUTURE ENHANCEMENT</b> I would like to store the inventory data permanently on a computer file. Additionally,
 *  I would create field that could store date created and modified for each data.</p>
 *
 * @author Rifatul Karim
 * @version 1.0
 */
public class Inventory {

    /**
     * Stores all the part data
     */
    private static final ObservableList<Part> parts = FXCollections.observableArrayList();

    /**
     * Stores all the product data
     */
    private static final ObservableList<Product> products = FXCollections.observableArrayList();

    /**
     * Add a new part data to the inventory
     *
     * @param newPart part data
     */
    public static void addPart(Part newPart) {
        parts.add(newPart);
    }

    /**
     * Add a new product data to the inventory
     *
     * @param newProduct part data
     */
    public static void addProduct(Product newProduct) {
        products.add(newProduct);
    }

    /**
     * Look for a part data that matches the given id
     *
     * @param partID Id of a part
     * @return part data
     */
    public static Part lookupByPartID(int partID) {
        for(Part part : parts) {
            if (part.getId() == partID) {
                return part;
            }
        }
        return null;
    }

    /**
     * Look for a product data that matches the given id
     *
     * @param productID Id of a product
     * @return product data
     */
    public static Product lookupByProductID(int productID) {
        for(Product product : products) {
            if (product.getId() == productID) {
                return product;
            }
        }
        return null;
    }

    /**
     * Look for a part data that partially matches the given name
     *
     * @param partName text to search
     * @return list of part data
     */
    public static ObservableList<Part> lookupByPartName(String partName) {
        ObservableList<Part> partList = FXCollections.observableArrayList();
        partName = partName.toUpperCase();

        for (Part part : parts) {
            if(part.getName().toUpperCase().contains(partName.trim())) {
                partList.add(part);
            }
        }

        return partList;
    }

    /**
     * Look for a product data that partially matches the given name
     *
     * @param productName text to search
     * @return list of product data
     */
    public static ObservableList<Product> lookupByProductName(String productName) {
        ObservableList<Product> productList = FXCollections.observableArrayList();

        for (Product product : products) {
            if(product.getName().toUpperCase().contains(productName.toUpperCase().trim())) {
                productList.add(product);
            }
        }

        return productList;
    }

    /**
     * Update a specific part data on the inventory
     *
     * @param index Id of a part
     * @param selectedPart selected part from the main screen part table
     */
    public static void updatePart (int index, Part selectedPart) {
        int loop = parts.size();
        int selectedPartId = selectedPart.getId();

        for (int i = 0; i < loop; i++) {
            int id = parts.get(i).getId();
            if (id == selectedPartId) {
                parts.set(i, selectedPart);
                break;
            }
        }

    }

    /**
     * Update a specific product data on the inventory
     *
     * @param index Id of a product
     * @param selectedProduct selected product from the main screen part table
     */
    public static void updateProduct (int index, Product selectedProduct) {
        int loop = products.size();
        int selectedProductId = selectedProduct.getId();

        for (int i = 0; i < loop; i++) {
            int id = products.get(i).getId();
            if (id == selectedProductId) {
                products.set(i, selectedProduct);
                break;
            }
        }

    }


    /**
     * Delete a specific part data on the inventory
     *
     * @param selectedPart selected part from the main screen part table
     * @return true if the data was found.
     */
    public static boolean deletePart (Part selectedPart) {
        for (Part part : parts) {
            if(part.getId() == selectedPart.getId()) {
                parts.remove(part);
                return true;
            }
        }
        return false;
    }

    /**
     * Delete a specific product data on the inventory
     *
     * @param selectedProduct selected product from the main screen product table
     * @return false if the selected product has associate parts.
     */
    public static boolean deleteProduct (Product selectedProduct) {
        if (selectedProduct.getAssociatedParts().size() == 0) {
            for (Product product : products) {
                if (product.getId() == selectedProduct.getId()) {
                    products.remove(selectedProduct);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns all the available parts from the inventory
     *
     * @return list of parts
     */
    public static ObservableList<Part> getAllParts () {
        return parts;
    }

    /**
     * Returns all the available product from the inventory
     *
     * @return list of product
     */
    public static ObservableList<Product> getProducts() {
        return products;
    }

    static {
        Part part1 = new Inhouse(1, "Brakes", 12.99, 15, 1, 20, 1000);
        Part part2 = new Outsourced(2, "Tires", 14.99, 15, 1, 28, "Appollo Hospital");
        Part part3 = new Inhouse(3, "Rim", 56.99, 15, 1, 20, 101);
        Part part4 = new Outsourced(4, "Saddle", 9.99, 15, 1, 25, "KFC");
        Part part5 = new Outsourced(5, "Part1", 9.99, 15, 1, 25, "KFC");
        Part part6 = new Outsourced(6, "Part12", 19.99, 15, 1, 25, "KFC");
        Part part7 = new Outsourced(7, "Part22", 92.99, 15, 1, 25, "KFC");
        Part part8 = new Outsourced(8, "Part222", 49.99, 15, 1, 25, "KFC");
        Part part9 = new Outsourced(9, "Part32", 69.99, 15, 1, 25, "KFC");
        Part part10 = new Outsourced(10, "PartR", 77.77, 77, 1, 100, "King of Kings");

        addPart(part1);
        addPart(part2);
        addPart(part3);
        addPart(part4);
        addPart(part5);
        addPart(part6);
        addPart(part7);
        addPart(part8);
        addPart(part9);
        addPart(part10);

        Product product1 = new Product(1, "Giant Bicycle", 299.99, 3, 1, 5);
        Product product2 = new Product(2, "Small Bicycle", 199.99, 30, 10, 50);
        Product product3 = new Product(3, "Medium Bicycle", 249.99, 20, 5, 30);
        Product product4 = new Product(4, "Old Bicycle", 99.99, 90, 1, 100);
        Product product5 = new Product(5, "Electric Bicycle", 1099.99, 2, 1, 3);

        List<Part> partList1 = new ArrayList<Part>();
        List<Part> partList2 = new ArrayList<Part>();
        List<Part> partList3 = new ArrayList<Part>();
        partList1.add(part1);
        partList1.add(part2);
        partList2.add(part3);
        partList2.add(part4);
        partList2.add(part5);
        partList2.add(part5);
        partList2.add(part6);
        partList2.add(part10);

        ObservableList<Part> associatedPart1 = FXCollections.observableList(partList1);
        ObservableList<Part> associatedPart2 = FXCollections.observableList(partList2);
        ObservableList<Part> associatedPart3 = FXCollections.observableList(partList3);

        product1.setAssociatedParts(associatedPart1);
        product2.setAssociatedParts(associatedPart1);
        product3.setAssociatedParts(associatedPart2);
        product4.setAssociatedParts(associatedPart1);
        product5.setAssociatedParts(associatedPart3);

        addProduct(product1);
        addProduct(product2);
        addProduct(product3);
        addProduct(product4);
        addProduct(product5);
    }

}
