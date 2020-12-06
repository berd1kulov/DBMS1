package sample;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField prod_name;

    @FXML
    private TextField price;

    @FXML
    private TableView<Product> tableProduct;

    @FXML
    private TableColumn<Product, String> columnID;

    @FXML
    private TableColumn<Product, String> columnName;

    @FXML
    private TableColumn<Product, String> columnCategory;

    @FXML
    private TableColumn<Product, String> columnPrice;

    @FXML
    private Button add;

    @FXML
    private Button update;

    @FXML
    private TextField search_field;

    @FXML
    private Button search_button;

    @FXML
    private Button delete;

    @FXML
    private TextField productid_field;

    @FXML
    private ChoiceBox<String> category_choice;

    @FXML
    private Button show_button;

    ObservableList<Product> list = FXCollections.observableArrayList();
    ObservableList<Product> search_list = FXCollections.observableArrayList();
    ObservableList choice_list = FXCollections.observableArrayList();


    @FXML
    void initialize() {

        loadData();

        String connectionUrl =
                "jdbc:sqlserver://localhost;"
                        + "database=Assignment3;"
                        + "user=sa;"
                        + "password=Password123@jkl#;"
                        /*+ "encrypt=true;"
                        + "trustServerCertificate=false;"
                        + "loginTimeout=30;"*/;
        update.setOnAction(actionEvent -> {
            if(!prod_name.getText().isEmpty()) {
                Statement st = null;
                try (Connection connection = DriverManager.getConnection(connectionUrl)) {
                    st = connection.createStatement();
                    st.executeUpdate("UPDATE PRODUCT SET PRODUCTNAME="+"'"+prod_name.getText()+"', "+"CATEGORY_NAME="+"'"+category_choice.getSelectionModel().getSelectedItem()+"', "+"PRICE="+"'"+price.getText()+"' "+"WHERE PRODUCTID="+productid_field.getText());
                    System.out.println("Row updated: " + prod_name.getText());

                } catch (Exception ex) {
                    ex.printStackTrace();

                }
            }
        });

        add.setOnAction(actionEvent -> {
            String insertSql = "INSERT INTO PRODUCT VALUES"
                    + "("+productid_field.getText()+", "+"'"+prod_name.getText()+"'"+", "+"'"+category_choice.getSelectionModel().getSelectedItem()+"'"+", "+price.getText()+");";

            ResultSet resultSet1 = null;

            try (Connection connection = DriverManager.getConnection(connectionUrl);
                 PreparedStatement prepsInsertProduct = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);) {

                prepsInsertProduct.execute();
                // Retrieve the generated key from the insert.
                resultSet1 = prepsInsertProduct.getGeneratedKeys();

                // Print the ID of the inserted row.
                while (resultSet1.next()) {
                    System.out.println("Generated: " + resultSet1.getString(1));
                }
            }
            // Handle any errors that may have occurred.
            catch (Exception e) {
                e.printStackTrace();
            }

        });

        delete.setOnAction(actionEvent -> {
            if(!prod_name.getText().isEmpty()) {
                Statement st = null;
                try (Connection connection = DriverManager.getConnection(connectionUrl)) {
                    st = connection.createStatement();
                    st.executeUpdate("DELETE FROM PRODUCT WHERE PRODUCTNAME=" + "'" + prod_name.getText() + "'");
                    System.out.println("Row deleted: " + prod_name.getText());

                } catch (Exception ex) {
                    ex.printStackTrace();

                }
            }
        });

        search_button.setOnAction(actionEvent -> {
            if(!search_list.isEmpty()){
                search_list.clear();
            }
            ResultSet resultSet2 = null;

            try (Connection connection = DriverManager.getConnection(connectionUrl);
                 Statement statement = connection.createStatement();) {

                // Create and execute a SELECT SQL statement.
                String selectSql = "SELECT * FROM PRODUCT";
                resultSet2 = statement.executeQuery(selectSql);

                // Print results from select statement
                while (resultSet2.next()) {
                    if(resultSet2.getString(2).equals(search_field.getText())) {
                        search_list.add(new Product(resultSet2.getString(1), resultSet2.getString(2), resultSet2.getString(3), resultSet2.getString(4)));

                    }
                }
            } catch (SQLException t) {
                t.printStackTrace();
            }
            columnID.setCellValueFactory(new PropertyValueFactory<Product, String>("prod_id"));
            columnName.setCellValueFactory(new PropertyValueFactory<Product, String>("prod_name"));
            columnCategory.setCellValueFactory(new PropertyValueFactory<Product, String>("prod_category"));
            columnPrice.setCellValueFactory(new PropertyValueFactory<Product, String>("prod_price"));
            tableProduct.setItems(search_list);
        });

        show_button.setOnAction(actionEvent -> {
            ResultSet resultSet = null;

            try (Connection connection = DriverManager.getConnection(connectionUrl);
                 Statement statement = connection.createStatement();) {

                String selectSql = "SELECT * FROM PRODUCT";
                resultSet = statement.executeQuery(selectSql);

                while (resultSet.next()) {
                    list.add(new Product(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4)));

                }
            } catch (SQLException t) {
                t.printStackTrace();
            }
            columnID.setCellValueFactory(new PropertyValueFactory<Product, String>("prod_id"));
            columnName.setCellValueFactory(new PropertyValueFactory<Product, String>("prod_name"));
            columnCategory.setCellValueFactory(new PropertyValueFactory<Product, String>("prod_category"));
            columnPrice.setCellValueFactory(new PropertyValueFactory<Product, String>("prod_price"));
            tableProduct.setItems(list);
        });

    }

    private void loadData(){
        choice_list.removeAll(choice_list);
        String a = "ACCESSORY";
        String b = "FASHION";
        String c = "GADGET";
        String d = "BOOKS";
        String e = "FOOTWEAR";
        String f = "FOODS";
        String g = "DRINKS";
        String h = "SOUVENIRS";
        String i = "GLASSES";

        choice_list.addAll(a, b, c, d, e, f, g, h, i);

        category_choice.getItems().addAll(choice_list);

    }
}
