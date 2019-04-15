package javaProject;

public class Product {

    private String productID;
    private String name;
    private Double price;
    private int quantity;
    private String description;
    private String imagePath;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public Product(String productID, String name, double price, int quantity, String description, String imagePath) {
        setProductID(productID);
        setName(name);
        setPrice(price);
        setQuantity(quantity);
        setDescription(description);
        this.imagePath = imagePath;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    @Override
    public  String toString(){
        return String.format("Product Id %s, Name: %s, Price: %.2f, Quantity: %d, Description: %s, Image Path: %s\n" ,
                this.getProductID(), this.getName(), this.getPrice(),
                this.getQuantity(), this.getDescription(), this.getImagePath());

    }
}

