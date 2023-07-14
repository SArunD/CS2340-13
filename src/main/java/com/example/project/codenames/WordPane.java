package com.example.project.codenames;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

// Model this using the Observer Pattern
public class WordPane extends StackPane {
    private String style;
    private Color color;
    private Word word;

    public WordPane(Word word) {
        VBox currBox = new VBox();

        Label text = new Label();
        text.setText(word.getWord());
        text.setFont(Font.font("Segoe UI Black", 11));

        currBox.getChildren().add(text);
        currBox.getChildren().add(new Button("PRESS"));
        currBox.setSpacing(2);
        currBox.setAlignment(Pos.CENTER);
        this.setAlignment(Pos.CENTER);
        this.getChildren().add(currBox);

        this.style = "-fx-border-color: black; -fx-border-radius: 2;";
        this.setStyle(this.style);
        this.word = word;
    }

    public void addBackground() {
        this.style += "-fx-background-color: red;";
        this.setStyle(this.style);
    }
}
