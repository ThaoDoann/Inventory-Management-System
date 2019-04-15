package javaProject;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class ProductList {
    protected static File file =  new File("myProducts.txt");
    private static ArrayList<Product> productList = new ArrayList<>();
    private static Product selectSearchProduct;


    public ProductList(){
        productList = new ArrayList<>();
    }

    public  ProductList(Product[] product){
        //convert product array into ArrayList
        this.productList = new ArrayList<>(Arrays.asList(product));
    }
    public  ProductList(List <Product> products){
        this.productList = new ArrayList<Product>(products);
    }

    //take data of product list from the file
    public ProductList(File file){
        productList = new ArrayList<>();
    }

    public static void writeProductsToFile() {
        PrintWriter output = null;
        try {
            output = new PrintWriter(file);
            for(Product products: productList) {
                output.println(products.getProductID()+","+products.getName()+","+
                        products.getPrice()+","+products.getQuantity()+
                        ","+products.getDescription() +","+products.getImagePath());
            }
        } catch(IOException e) {
            System.out.println("Something is wrong when trying to write: "+e.getMessage());
        } catch(Exception e) {
            System.out.println("Something is wrong anyway: "+e.getMessage());
        }finally {
            if (output!=null) output.close();
        }
    }

    public static List<Product> getProductsFromFile() {
        Scanner input = null;

        try{
            input = new Scanner(file);
            while (input.hasNext()){
                String record = input.nextLine();
                String [] field = record.split(",");

                Product newProduct = new Product(field[0],field[1],
                        Double.parseDouble(field[2]), Integer.parseInt(field[3]),
                        field[4], field[5]);
                productList.add(newProduct);
            }

        }catch(FileNotFoundException  e){
            System.out.print(e.getMessage());
        }
        finally{
            if(input != null) input.close();
        }
        return productList;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProduct (int index, Product newProduct){
        productList.set(index,newProduct);
    }

    public  Product getProduct (int index){
        Product thisProduct =  productList.get(index);
        return thisProduct;
    }

    public void add(Product newProduct){
        productList.add(newProduct);
    }

    public void add(Product[] moreProducts){
        productList.addAll(Arrays.asList(moreProducts));
    }

    public int size(){
        return productList.size();
    }

    public ArrayList<Product> searchByName (String name) {
        ArrayList<Product> match = new ArrayList<>();
        try {
            for (int i = 0; i < productList.size(); i++) {
                if (this.getProduct(i).getName().toLowerCase().contains(name.toLowerCase().trim())) {
                    match.add(getProduct(i));
                }
            }
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "An error occurs", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return match;
    }

    public ArrayList<Product> searchByID (String id) {
        ArrayList<Product> match = new ArrayList<>();
        try {
            for (int i = 0; i < productList.size(); i++) {
                if (this.getProduct(i).getProductID().toLowerCase().contains(id.toLowerCase().trim())) {
                    match.add(getProduct(i));
                }
            }
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "An error occurs", "ERROR", JOptionPane.ERROR_MESSAGE);
         }
        return match;
    }

    public void delete (int index){
        productList.remove(index);
    }

    public void setSelectSearchProduct (Product selectedProduct){
        selectSearchProduct = selectedProduct;
    }

    @Override
    public String toString(){
        String string = "";
        for(int i=0; i<productList.size(); i++)
            string += productList.get(i).toString();
        return string;
    }
}
