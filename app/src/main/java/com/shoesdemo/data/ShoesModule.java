package com.shoesdemo.data;

public class ShoesModule {
    private String letter;
    private Shoes mShoes;

    public ShoesModule(String letter, Shoes shoes) {
        this.letter = letter;
        mShoes = shoes;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public Shoes getShoes() {
        return mShoes;
    }

    public void setShoes(Shoes shoes) {
        mShoes = shoes;
    }
}
