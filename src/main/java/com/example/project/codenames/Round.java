package com.example.project.codenames;

import com.example.project.Game;
import com.example.project.Helper;
import com.example.project.codenames.enums.Player;
import com.example.project.codenames.enums.Type;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

// Observer
public class Round implements PropertyChangeListener {
    private Team team1, team2, activeTeam;
    private ArrayList<Word> words;

    private String currentClue;
    private int currentGuessLimit; // What spymaster selects
    private int currentGuessCount; // What the operatives guess

    private PropertyChangeSupport support;

    public Round() {
        this.support = new PropertyChangeSupport(this);
        this.team1 = this.activeTeam = new Random().nextBoolean() ? new Team(Type.RED, 9) : new Team(Type.BLUE, 9);
        this.team2 = (activeTeam.getType() == Type.RED) ? new Team(Type.BLUE, 8) : new Team(Type.RED, 8);
        updateWordType();
        Collections.shuffle(this.words);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.support.removePropertyChangeListener(listener);
    }

    public ArrayList<Word> getWords() { return this.words; }

    public Team getActiveTeam() { return this.activeTeam; }

    private void updateWordType() {
        this.words = DictionaryService.getGameWords();
        addType(0, 9, team1.getType());
        addType(9, 17, team2.getType());
        this.words.get(24).setType(Type.ASSASSIN);
    }

    private void addType(int start, int end, Type type) {
        for (int i = start; i < end; i++) {
            this.words.get(i).setType(type);
            this.words.get(i).addPropertyChangeListener(this);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        checkSelectedWord((Word) evt.getSource());
    }

    public void checkSelectedWord(Word selected) {
        Team passiveTeam = (this.activeTeam == this.team1) ? this.team2 : this.team1;
        System.out.println(passiveTeam.getType());

        // Assassin ends game
        if (selected.getType() == Type.ASSASSIN) {
            System.out.println("SELECTED Assassin");
            Team winner = passiveTeam;
            endGame(winner.getType());

        // Other team's card ends turn
        } else if (selected.getType() == passiveTeam.getType()) {
            System.out.println("SELECTED Otherteam");
            passiveTeam.decrementCardCount();
            if (passiveTeam.hasWon()) {
                endGame(this.activeTeam.getType());
            } else {
                endTurn();
            }

        // Neutral card ends turn
        } else if (selected.getType() == Type.NEUTRAL) {
            System.out.println("SELECTED Neutral");
            endTurn();

        // Correct card chosen...
        } else if (selected.getType() == activeTeam.getType()) {
            System.out.println("SELECTED Yourteam");
            this.activeTeam.decrementCardCount();
            this.currentGuessCount++;

            if (this.activeTeam.hasWon()) {
                endGame(this.activeTeam.getType());
            } else if (this.currentGuessCount == this.currentGuessLimit) {
                endTurn();
            }
        }
    }

    public void setClue(String clue, int clueCount) {
        // TODO: can use obeservable ?
        if (this.activeTeam.getCurrentPlayer() == Player.SPY_MASTER) {
            this.activeTeam.setCurrentPlayer(Player.OPERATIVE);
            this.currentGuessCount = 0;
            this.currentGuessLimit = ++clueCount; //(they can guess 1 more than what the spymaster says if they get them all right)
            this.currentClue = clue;
        }
    }

    private void endTurn() {
        // TODO: can have observable for the phase and team switching (for ui)
        Team previousTeam = this.activeTeam;
        this.activeTeam = (this.activeTeam == this.team1) ? this.team2 : this.team1;
        this.activeTeam.setCurrentPlayer(Player.SPY_MASTER);
        this.currentClue = "";
        this.support.firePropertyChange("activeTeam", previousTeam , activeTeam);

        Helper.changeGameScreen("codenames/CNBufferScreen.fxml");
    }

    private void endGame(Type winner) {
        this.support.firePropertyChange("winner", null, winner);
        // TODO: observable for ending the game
        System.out.println("Game ends! " + winner);
        Helper.changeGameScreen("codenames/CNEndScreen.fxml");
    }

}
