package com.example.demo3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public AnchorPane ColorSection;
    public ChoiceBox amountOfCorner;
    public AnchorPane objectDetails;

    ToggleGroup colors = new ToggleGroup();
    ToggleGroup fillOrEmpty = new ToggleGroup();
    ToggleGroup drawOrErase = new ToggleGroup();

    @FXML
    public Label sideCount;
    @FXML
    RadioButton RBRed = new RadioButton();
    @FXML
    RadioButton RBBlue = new RadioButton();
    @FXML
    RadioButton RBGreen = new RadioButton();
    @FXML
    RadioButton RBBlack = new RadioButton();
    @FXML
    RadioButton RBBrown = new RadioButton();
    @FXML
    RadioButton RBPurple = new RadioButton();
    @FXML
    RadioButton RBYellow = new RadioButton();
    @FXML
    RadioButton RBOrange = new RadioButton();

    @FXML
    RadioButton RBFill = new RadioButton();

    @FXML
    RadioButton RBEmpty = new RadioButton();

    @FXML
    RadioButton RBDraw = new RadioButton();

    @FXML
    RadioButton RBMove = new RadioButton();


    @FXML
    private ChoiceBox<String> shapeChoiceBox;

    @FXML
    private Pane Board;

    private double firstX, firstY, secondX, secondY, thirdX, thirdY, fourthX, fourthY, fifthX, fifthY;
    private int mouseClicked = 1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> availableChoices = FXCollections.observableArrayList("Line","Square", "Circle", "Irregular", "Custom Draw");
        shapeChoiceBox.setItems(availableChoices);
        ObservableList<String> corners = FXCollections.observableArrayList("3", "5");
        amountOfCorner.setItems(corners);


        RBBlack.setToggleGroup(colors);
        RBBrown.setToggleGroup(colors);
        RBPurple.setToggleGroup(colors);
        RBRed.setToggleGroup(colors);
        RBBlue.setToggleGroup(colors);
        RBGreen.setToggleGroup(colors);
        RBOrange.setToggleGroup(colors);
        RBYellow.setToggleGroup(colors);
        RBEmpty.setToggleGroup(fillOrEmpty);
        RBFill.setToggleGroup(fillOrEmpty);
        RBDraw.setToggleGroup(drawOrErase);
        RBMove.setToggleGroup(drawOrErase);

    }

    @FXML
    protected void clickAndPrint(MouseEvent event) {
        if (RBDraw.isSelected()) {
            getX_Y(event);

            if (mouseClicked == 2 && (shapeChoiceBox.getValue().equals("Square") || shapeChoiceBox.getValue().equals("Circle") || shapeChoiceBox.getValue().equals("Line")) && RBDraw.isSelected()) {
                switch (shapeChoiceBox.getValue()) {
                    case "Square" -> {
                        Board.getChildren().add(new DrawShape().drawRectangle(firstX, firstY, secondX, secondY, getColor(), fillOrEmpty()));
                    }
                    case "Circle" ->
                            Board.getChildren().add(new DrawShape().drawCircle(firstX, firstY, secondX, secondY, getColor(), fillOrEmpty()));
                    case "Line" ->
                            Board.getChildren().add(new DrawShape().drawLine(firstX, firstY, secondX, secondY, getColor()));


                }
                mouseClicked = 0;
            }
            if (mouseClicked >= 3 && shapeChoiceBox.getValue().equals("Irregular")) {
                if (amountOfCorner.getValue().equals("3")) {
                    Board.getChildren().add(new DrawShape().drawPolygon(new double[]{firstX, firstY, secondX, secondY, thirdX, thirdY}, getColor(), fillOrEmpty()));
                    mouseClicked = 0;

                }
                if (amountOfCorner.getValue().equals("5") && mouseClicked == 5) {
                    Board.getChildren().add(new DrawShape().drawPolygon(new double[]{firstX, firstY, secondX, secondY, thirdX, thirdY, fourthX, fourthY, fifthX, fifthY}, getColor(), fillOrEmpty()));
                    mouseClicked = 0;
                }

            }
            mouseClicked++;
        }
    }
    @FXML
    public void customDraw(MouseEvent event) {
        if (shapeChoiceBox.getValue().equals("Custom Draw") && RBDraw.isSelected())
            Board.getChildren().add(new DrawShape().drawCircle(event.getX(), event.getY(), event.getX() + 5, event.getY() + 5, getColor(), fillOrEmpty()));
    }
    @FXML
    public void move(MouseEvent event) {
        if (RBMove.isSelected()) {
            Board.getChildren().forEach(this::makeDraggable);
        }
        if (RBMove.isSelected()&&(shapeChoiceBox.getValue().equals("Custom Draw")||shapeChoiceBox.getValue().equals("Line"))){
            customMove(event);
        }
    }
    @FXML
    public void objectSet(ActionEvent event) {
        visible();
    }
    private double dragStartX;
    private double dragStartY;

    public void makeDraggable(Node node) {



        node.setOnMousePressed(e -> {
            dragStartX = e.getSceneX() - node.getTranslateX();
            dragStartY = e.getSceneY() - node.getTranslateY();
        });
        node.setOnMouseDragged(e -> {
            node.setTranslateX(e.getSceneX() - dragStartX);
            node.setTranslateY(e.getSceneY() - dragStartY);
        });
    }

    public void customMove(MouseEvent event){
        int a= Board.getChildren().size();

        for (int i = 0; i < a; i++ ){
            Board.getChildren().get(i).setTranslateX(event.getSceneX()-Board.getChildren().get(i).getTranslateX());
            Board.getChildren().get(i).setTranslateY(event.getSceneY()-Board.getChildren().get(i).getTranslateY());
        }



    }

    public String getColor() {
        return ((RadioButton) colors.getSelectedToggle()).getText();
    }

    public boolean fillOrEmpty() {
        return ((RadioButton) fillOrEmpty.getSelectedToggle()).getText().equals("Fill");
    }

    public void reset() {
        Board.getChildren().clear();
        mouseClicked = 1;
    }


    public void visible() {
        if (shapeChoiceBox.getValue().equals("Irregular")) {
            amountOfCorner.setVisible(true);
            sideCount.setVisible(true);
        } else{
            amountOfCorner.setVisible(false);
            sideCount.setVisible(false);
        }

    }
    public void getX_Y(MouseEvent event) {
        if (mouseClicked == 1) {
            firstX = event.getX();
            firstY = event.getY();
        }
        if (mouseClicked == 2) {
            secondX = event.getX();
            secondY = event.getY();
        }
        if (mouseClicked == 3) {
            thirdX = event.getX();
            thirdY = event.getY();
        }
        if (mouseClicked == 4) {
            fourthX = event.getX();
            fourthY = event.getY();
        }
        if (mouseClicked == 5) {
            fifthX = event.getX();
            fifthY = event.getY();
        }

        if (mouseClicked > 8)
            mouseClicked = 0;


    }



}


