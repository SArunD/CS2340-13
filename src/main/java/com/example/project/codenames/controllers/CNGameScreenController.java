package com.example.project.codenames.controllers;

import com.example.project.Helper;
import com.example.project.Main;
import com.example.project.codenames.CNGame;
import com.example.project.codenames.Round;
import com.example.project.codenames.Team;
import com.example.project.codenames.WordPane;
import com.example.project.codenames.enums.Player;
import com.example.project.codenames.enums.Type;
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

// TODO: Make enums for propertynames

public class CNGameScreenController implements Initializable, PropertyChangeListener {
    @FXML private BorderPane borderPane;
    @FXML private GridPane gridPane;
    @FXML private Label teamDisplay, playerDisplay, topTitle;
    @FXML private Label redTeamScore, blueTeamScore;

    private final Round round = CNGame.getGameInstance().getRound();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("ENTERED: Codenames Game Screen");

        populate();
        handle();
        updateScores();

        addTop();
        addBottom();
    }

    private void populate() {
        this.round.addPropertyChangeListener(this);

        int count = 0;
        for (int i = 0; i < gridPane.getRowCount(); i++) {
            for (int j = 0; j < gridPane.getColumnCount(); j++) {
                gridPane.add(new WordPane(round.getWords().get(count)), j, i);
                count++;
            }
        }

        if (this.round.getActiveTeam().getType() == Type.RED) {
            redTeamScore.setText("9");
            blueTeamScore.setText("8");
        } else {
            blueTeamScore.setText("9");
            blueTeamScore.setText("8");
        }
    }

    private void handle() {
        gridPane.getChildren().forEach(item -> {
            if (!(item instanceof Group)) {
                WordPane curr = (WordPane) item;
                VBox currBox = (VBox) curr.getChildren().get(0);

                if (this.round.getActiveTeam().getCurrentPlayer() == Player.OPERATIVE) {
                    if (curr.getWord().getIsSelected()) {
                        curr.addBackground();
                    } else {
                        curr.addButton();
                        currBox.getChildren().get(1).addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
                            curr.getWord().select();
                            if (curr.getWord().getIsSelected()) {
                                curr.selectedUpdate();
                                updateScores();
                            }
                        });
                    }
                } else {
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

    private void updateScores() {
        if (this.round.getTeam1().getType() == Type.RED) {
            redTeamScore.setText(String.valueOf(this.round.getTeam1().getNumOfCards()));
            blueTeamScore.setText(String.valueOf(this.round.getTeam2().getNumOfCards()));
        } else {
            blueTeamScore.setText(String.valueOf(this.round.getTeam1().getNumOfCards()));
            redTeamScore.setText(String.valueOf(this.round.getTeam2().getNumOfCards()));
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String incomingEvent = evt.getPropertyName();
        if (incomingEvent.equals(Round.activeTeamEvent)) {
            Helper.changeGameScreen("codenames/CNBufferScreen.fxml");
        } else if (incomingEvent.equals(Round.winnerEvent)) {
            Team winningTeam = (Team) evt.getNewValue();
            CNGame.getGameInstance().clearRound();
            Helper.changeGameScreen("codenames/CNEndScreen.fxml");
        }
    }
}
