package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.nio.BufferUnderflowException;

public class UserInterface {

    GridPane loginPage;
    HBox headerBar;
    HBox footerBar;

    Button signInButton;
    Label welcomeLabel;
    VBox body;
    Customer loggedInCustomer;

    ProductList productList = new ProductList();
    VBox productPage;
    Button placeOrderButton = new Button("Place Order");

    ObservableList<Product> ItemsInCart = FXCollections.observableArrayList();
    public BorderPane createContent(){
        BorderPane root = new BorderPane();
        root.setPrefSize(800, 600);
//        root.getChildren().add(loginPage); // method to add nodes as children to pane
//        root.setCenter(loginPage); //**
        root.setTop(headerBar); //*
        body = new VBox();
        body.setPadding(new Insets(10));
        root.setCenter(body);
        body.setAlignment(Pos.CENTER); // this will allow the body to the center of border pane
        productPage = productList.getAllProducts();//>
        body.getChildren().add(productPage);

        root.setBottom(footerBar);

        return root;
    }

    public UserInterface(){
        //must be called on constructor so that all the things are created in first group
        createLoginPage();
        createHeaderBar();
        createFooterBar();
    }

    private void createLoginPage(){
        // creating controls
        Text userNameText = new Text("User Name");
        Text passwordText = new Text("Password");

        TextField userName = new TextField("uzer@gmail.com"); // don't have to type userName and password again and again
        userName.setPromptText("Type your user name here");
        PasswordField password = new PasswordField();
        password.setText("abc123");
        password.setPromptText("Type your password here");

        Label messageLable = new Label("Hi");

        Button loginButton = new Button("Login");
        loginPage = new GridPane();
                    // node child, col index, row index
//        loginPage.setStyle(" -fx-background-color:grey;"); // to set whole background color grey
        loginPage.setAlignment(Pos.CENTER);
        loginPage.setHgap(10); // to set the gap between username and its prompt
        loginPage.setVgap(10);
        loginPage.add(userNameText, 0, 0);
        loginPage.add(userName, 1, 0);
        loginPage.add(passwordText, 0, 1);
        loginPage.add(password, 1, 1);
        loginPage.add(messageLable,0, 2);
        loginPage.add(loginButton, 1, 2);

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = userName.getText();
                String pass = password.getText();
                Login login = new Login();

                // we can get the customer here
                loggedInCustomer = login.customerLogin(name, pass);
                if(loggedInCustomer != null){
                    messageLable.setText("Welcome : " + loggedInCustomer.getName());
                    welcomeLabel.setText("Welcome-" + loggedInCustomer.getName());
//                    headerBar.getChildren().add(welcomeLabel); //* uncommnet this
                    body.getChildren().clear();
                    body.getChildren().add(productPage);
                }
                else{
                    messageLable.setText("Login Failed !! please provide correct credentials");
                }
            }
        });
    }
    private void createHeaderBar(){
        Button homeButton = new Button();
        Image image = new Image("C:\\Users\\uzerk\\Desktop\\E-Commerce\\src\\img1.png");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(80);
        homeButton.setGraphic(imageView);

        TextField searchBar = new TextField();
        searchBar.setPromptText("Search here");
        searchBar.setPrefWidth(200);

        Button searchButton = new Button("Search");
        signInButton = new Button("Sign In");
        welcomeLabel = new Label();

        Button cartButton = new Button("Cart");

        Button orderButton = new Button("Orders");

        Button logoutButton = new Button("Logout"); //***


        headerBar = new HBox();
//        headerBar.setStyle(" -fx-background-color:grey;"); // to set whole background color grey
        headerBar.setPadding(new Insets(10));
        headerBar.setSpacing(10);
        headerBar.setAlignment(Pos.CENTER);
        headerBar.getChildren().addAll(homeButton, searchBar, searchButton, signInButton, cartButton, orderButton, logoutButton);//**

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear(); // remove everything and show login page
                body.getChildren().add(loginPage); // display login page
                headerBar.getChildren().remove(signInButton);// whenever we on a login page signInButton will not show on display
            }
        });

        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                VBox productPage = productList.getProductsInCart(ItemsInCart);
                productPage.setAlignment(Pos.CENTER); // move placeOrderButton to the center
                productPage.setSpacing(10); // set
                productPage.getChildren().add(placeOrderButton);
                body.getChildren().add(productPage);
                footerBar.setVisible(false); // all cases need to be handled for this
            }
        });
        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //need list of product and a customer
                if(ItemsInCart == null){
                    // please select a product first to place order
                    showDialog("please add some products in the cart to place order !");
                    return;
                }
                if(loggedInCustomer == null){
                    showDialog("Please login first to place order");
                    return;
                }
                int count = Order.placeMulitpleOrder(loggedInCustomer, ItemsInCart);
                if(count != 0){
                    showDialog("Order for "+count+" products placed successfully");
                }
                else{
                    showDialog("Order failed !!");
                }

            }
        });

        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(productPage);
                footerBar.setVisible(true);
                if(loggedInCustomer == null && headerBar.getChildren().indexOf(signInButton) == -1){
                    headerBar.getChildren().add(signInButton);
                }
            }
        });

        logoutButton.setOnAction(new EventHandler<ActionEvent>() { /////****
            @Override
            public void handle(ActionEvent actionEvent) {
                loggedInCustomer = null;
                body.getChildren().clear(); // Remove everything and show login page
                body.getChildren().add(loginPage); // Display the login page
//                headerBar.getChildren().remove(logoutButton); // Remove the logout button
                if (headerBar.getChildren().indexOf(signInButton) == -1) {
                    headerBar.getChildren().add(signInButton); // Add the sign-in button
                }
            }
        });
    }
    private void createFooterBar(){
        Button buyNowButton = new Button("Buy Now");
        Button addToCartButton = new Button("Add to Cart");

        footerBar = new HBox();
//        headerBar.setStyle(" -fx-background-color:grey;"); // to set whole background color grey
        footerBar.setPadding(new Insets(10));
        footerBar.setSpacing(10);
        footerBar.setAlignment(Pos.CENTER);
        footerBar.getChildren().addAll(buyNowButton, addToCartButton);

        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                if(product == null){
                    // please select a product first to place order
                    showDialog("please select a product first to place order !");
                    return;
                }
                if(loggedInCustomer == null){
                    showDialog("Please login first to place order");
                    return;
                }
                boolean status = Order.placeOrder(loggedInCustomer, product);
                if(status == true){
                    showDialog("Order placed successfully");
                }
                else{
                    showDialog("Order failed !!");
                }
            }
        });

        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                if(product == null){
                    // please select a product first to place order
                    showDialog("please select a product first to add it to Cart !");
                    return;
                }
                ItemsInCart.add(product);
                showDialog("Selected item has been added to the cart successfully ");
            }
        });

    }

    private void showDialog(String message){
        // want to show some alerts
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Message");
        alert.showAndWait();
    }
}
