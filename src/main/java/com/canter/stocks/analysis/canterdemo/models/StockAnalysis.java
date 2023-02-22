package com.canter.stocks.analysis.canterdemo.models;

// Model object to display date and percentage moves
public class StockAnalysis {

    // date when the percentage move occurred
    private String date;
    // percentage move
    private String move;

    public StockAnalysis(String d, String m) {
        super();
        this.date = d;
        this.move = m;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }
}

