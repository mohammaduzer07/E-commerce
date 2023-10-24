package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ProductList {
    private TableView<Product> productTable;
    public VBox createTable(ObservableList<Product> data){ /// data is of product type
        // column
        TableColumn id = new TableColumn("ID"); // creating a columns
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name = new TableColumn<>("NAME");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price = new TableColumn<>("PRICE");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        // adding col to the table
        productTable = new TableView<>();
        productTable.getColumns().addAll(id, name, price); // add col to the table
        productTable.setItems(data);
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(productTable);
        return vBox;
    }

//    public VBox getDummyTable(){ // call dummy data
//        // created some dummy data
//        ObservableList<Product> data = FXCollections.observableArrayList();
//        data.add(new Product(1, "Iphone", 50000));
//        data.add(new Product(4,"Laptop", 65000));
//
//        return createTable(data);
//    }

    public VBox getAllProducts(){
        ObservableList<Product> data = Product.getAllProducts();
        return createTable(data);
    }

    public Product getSelectedProduct(){ // selected product will be returned if null then null return
        return productTable.getSelectionModel().getSelectedItem();
    }

    public VBox getProductsInCart(ObservableList<Product> data){
        return createTable(data);
    }
}
