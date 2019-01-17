package agh.cs.oop.kubicki;

import java.util.Random;

public class Plant {
    private Position position;
    private int nutritionalValue;
    private final int maxNutrValue = 15;

    public Plant(Position position) {
        this.position = position;
        this.nutritionalValue = getRandNutrValue();
    }
    private int getRandNutrValue(){
        Random rand = new Random();
        return rand.nextInt(maxNutrValue)+10;
    }

    public Position getPosition() {
        return position;
    }


    public int getNutritionalValue() {
        return nutritionalValue;
    }

    @Override
    public String toString() {
        return "*";
    }
}
