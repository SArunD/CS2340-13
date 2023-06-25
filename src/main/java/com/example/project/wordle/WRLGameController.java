package com.example.project.wordle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

public class WRLGameController implements Initializable {
    @FXML private GridPane gridPane;

    private int index;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populate();
        index = 0;
    }

    public void populate() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                StackPane currPane = new StackPane();
                currPane.getChildren().add(new Label());
                currPane.setAlignment(Pos.CENTER);
                gridPane.add(currPane, i, j);
            }
        }
//        System.out.println(gridPane.getChildren());
    }

    @FXML public void handle(KeyEvent e) {
//        System.out.println(e.getCode() + " " + e.getCode().getChar());
        if (Character.isLetter(e.getCode().getChar().charAt(0))) {
            if (gridPane.getChildren().get(index) instanceof Group) {
                index++;
            }
//            ((Label) gridPane.getChildren().get(index)).setText(e.getCode().getChar());
            StackPane curr = (StackPane) gridPane.getChildren().get(index);
            ((Label) curr.getChildren().get(0)).setText(e.getCode().getChar());
            index++;
        } else if (e.getCode() == KeyCode.BACK_SPACE) {
//            ((Label) gridPane.getChildren().get(--index)).setText("");
            StackPane curr = (StackPane) gridPane.getChildren().get(--index);
            ((Label) curr.getChildren().get(0)).setText("");
        } else if (e.getCode() == KeyCode.ENTER) {
//            char[] userAnswer = new char[5];
//            for(int i=0; i < 5;i++) {
//                StackPane curr = (StackPane) gridPane.getChildren().get(index);
//                ((Label) curr.getChildren().get(0)).getText();
//                String temp = getGridContents(currentRow, i).toString();
//                userAnswer[i] = temp.charAt(0);
//            }
//            checkAnswer(userAnswer);
        }
    }

    private String correctAnswer = "apple";
    public int currentRow = 0;
    public void checkAnswer(char[] userAnswer) {
        String answer = new String(userAnswer);
        if(DictionaryService.checkIfWordExists(answer)){
            for(int i=0; i < answer.length(); i++) {
                String letter = correctAnswer.substring(i, i+1);
                if(letter.equals(correctAnswer.substring(i,i+1))){
                    //set letter color to green
                } else if(correctAnswer.contains(letter)) {
                    //set letter color to yellow
                } else {
                    //set letter color to white
                }
            }
            currentRow++;
        } else {
            //error message "Word not valid"
        }
    }

    public Node getGridContents(Integer row, Integer column){
        for (Node node : gridPane.getChildren()) {
            if(GridPane.getColumnIndex(node) == row && GridPane.getColumnIndex(node) == column){
                return node;
            }
        }
        return null;
    }
    
}