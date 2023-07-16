package com.example.project.codenames.controllers;

import com.example.project.Helper;
import com.example.project.Main;
import com.example.project.codenames.CNGame;
import com.example.project.codenames.Round;
import com.example.project.codenames.WordPane;
import com.example.project.codenames.enums.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;

public class CNGameScreenController implements Initializable, PropertyChangeListener {
    @FXML private BorderPane borderPane;
    @FXML private GridPane gridPane;
    @FXML private Label teamDisplay, playerDisplay, topTitle;

    private final Round round = CNGame.getGameInstance().getRound();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("ENTERED: Codenames Game Screen");
        populate();
        handle();
        addTop();
        addBottom();

        System.out.println(this.round.getActiveTeam().getType() + " " + this.round.getActiveTeam().getCurrentPlayer());
    }

    public void populate() {
        this.round.addPropertyChangeListener(this);

        int count = 0;
        for (int i = 0; i < gridPane.getRowCount(); i++) {
            for (int j = 0; j < gridPane.getColumnCount(); j++) {
                gridPane.add(new WordPane(round.getWords().get(count)), j, i);
                count++;
            }
        }
    }

    public void handle() {
        gridPane.getChildren().forEach(item -> {
            if (!(item instanceof Group)) {
                WordPane curr = (WordPane) item;
                VBox currBox = (VBox) curr.getChildren().get(0);

                if (this.round.getActiveTeam().getCurrentPlayer() == Player.OPERATIVE) {
                    // If operative:
                    if (curr.getWord().getIsSelected()) {
                        curr.addBackground();
                    } else {
                        curr.addButton();
                        currBox.getChildren().get(1).addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
                            curr.getWord().select();
                            if (curr.getWord().getIsSelected()) {
                                curr.selectedUpdate();
//                            this.round.checkSelectedWord(curr.getWord());
                            }
                        });
                    }
                } else {
                    // If Spymaster:
                    curr.addBackground();
                }
            }
        });
    }

    private void addTop() {
        teamDisplay.setText(this.round.getActiveTeam().getType().toString());
        teamDisplay.setStyle("-fx-text-fill: " + this.round.getActiveTeam().getType().getColor());
        playerDisplay.setText(this.round.getActiveTeam().getCurrentPlayer().toString());
        topTitle.setText(this.round.getActiveTeam().getCurrentPlayer().getHint());
    }

    private void addBottom() {
        Player currPlayer = this.round.getActiveTeam().getCurrentPlayer();
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(currPlayer.getPath()));
            Helper.setCNGamePane(borderPane);
            Helper.getCNGamePane().setBottom(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
//        System.out.println(evt);
//        if (evt.getPropertyName().equals("activeTeam")) { //TODO make enums for propertynames
//            Team activeTeam = (Team) evt.getNewValue();
//            // switch screen to buffer screen so spy master can give clue
//        }
    }
}
