package sample;

public class Product {

    String prod_id, prod_name, prod_category, prod_price;


    public Product(String prod_id, String prod_name, String prod_category, String prod_price) {
        this.prod_id = prod_id;
        this.prod_name = prod_name;
        this.prod_category = prod_category;
        this.prod_price = prod_price;
    }

    public String getProd_id() {
        return prod_id;
    }

    public String getProd_name() {
        return prod_name;
    }

    public String getProd_category() {
        return prod_category;
    }

    public String getProd_price() {
        return prod_price;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public void setProd_category(String prod_category) {
        this.prod_category = prod_category;
    }

    public void setProd_price(String prod_price) {
        this.prod_price = prod_price;
    }
}
