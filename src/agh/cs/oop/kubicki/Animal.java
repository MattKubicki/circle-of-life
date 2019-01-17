package agh.cs.oop.kubicki;

import java.util.ArrayList;
import java.util.Random;

public class Animal {
    private Position position;
    private int energy;
    private Direction dir;
    private ArrayList<Integer> genes;
    private boolean gender;
    private final int maxEnergy = 100;
    private int age = 0;

    public int getAge() {
        return age;
    }

    public int getEnergy() {
        return energy;
    }

    public boolean getGender() {
        return gender;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void addEnergy(int energy){
        if ((this.energy + energy) < maxEnergy){
            setEnergy (this.energy + energy);
        }
        setEnergy(maxEnergy);
    }

    public Animal(Position position){
        this.position = position;
        this.energy = randEnergy();
        this.dir = randDir();
        this.genes = genesCreation();
        this.gender = randGender();
    }

    public ArrayList<Integer> getGenes() {
        return genes;
    }

    public Animal(Position position, ArrayList<Integer> genes, int energy){
        this.position = position;
        this.energy = energy;
        this.dir = randDir();
        this.genes = genes;
        this.gender = randGender();
    }

    private int randEnergy(){
        Random rand = new Random();
        return rand.nextInt(maxEnergy-30)+30;
    }

    private Direction randDir() {
        Random rand = new Random();
        int randIntRes = rand.nextInt(7);
        Direction dir = Direction.NORTH;
        try {
            dir = dir.convertFromInt(randIntRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dir;
    }

    private boolean randGender(){
        Random rand = new Random();
        return rand.nextBoolean();
    }

    private ArrayList<Integer> genesCreation(){
        ArrayList<Integer> randGenes = new ArrayList<>(8);
        Random rand = new Random();
        for (int i=0; i<8; i++){
            int tmp = rand.nextInt(100)+1;
            randGenes.add(i,tmp);
        }
        return randGenes;
    }

    public Direction getDir() {
        return dir;
    }

    public int chooseGenesDir(){
        int n = 8;
        Probability probability = new Probability(n);
        return probability.randIndex(this.genes);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        if (getGender())
            return "M";
        else return "F";
    }
}
