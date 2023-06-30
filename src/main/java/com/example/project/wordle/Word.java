package com.example.project.wordle;

public class Word {
    protected String word;

    public Word(String word) {
        this.word = word;
    }

    public String getWord() { return this.word; }
    public void add(String word) { this.word += word; }
    public void remove() { this.word = this.word.substring(0,this.word.length()-1); }

    public String getLetter(int index) {
        String result = "";
        if (index >= 0 && index < this.word.length()) {
            result = this.word.charAt(index) + "";
        }
        return result;
    }
}
