package com.example.project.flowfree;

import com.example.project.Game;
import com.example.project.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FlowfreeController implements Initializable {

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Flow Free Game Started");

        /*
          1) wordset = 25 wordset arrangement
          2) gameTurn = <Team.RED, Team.BLUE>
          3) firstTeamWords = 9 words assigned to gameTurn team
          4) secondTeamWords = 8 words for other team
          5) neutralWords = choose remaining words as bystanders
          6) gameState = <CodenamesState.STARTING, CodenamesState.CREATING_CLUE, CodenamesState.GUESSING, CodenamesState.NEXT_SPYMASTER...>
        */
    }
}
