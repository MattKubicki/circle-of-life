package agh.cs.oop.kubicki;

import java.util.*;

import static java.lang.Math.max;

public class Jungle {
    private int width;
    private int height;
    private HashMap<Position,Animal> animals;
    private HashMap<Position,Plant> plants;


    public Jungle(int width, int height) {
        this.width = width;
        this.height = height;
        this.animals = getAdamAndEve();
        this.plants = getEden();
    }

    private HashMap<Position,Plant> getEden(){
        int plantNum = max(width,height);
        HashMap<Position,Plant> result = new HashMap<>();
        for (int i=0; i<4*plantNum; i++){
            Random rand = new Random();
            int j = rand.nextInt(4)-4;
            int g = rand.nextInt(4)-4;
            Position plantPos = new Position(width/2+j,height/2+g);
            Plant plant = new Plant(plantPos);
            result.put(plantPos,plant);
        }
        return result;
    }

    private HashMap<Position,Animal> getAdamAndEve(){
        int animalsNum = (max(width,height));
        HashMap<Position,Animal> result = new HashMap<>();
        for (int i=0; i<animalsNum; i++){
            Random rand = new Random();
            Position animalPos = new Position(rand.nextInt(width),rand.nextInt(height));
            Animal animal = new Animal(animalPos);
            result.put(animalPos,animal);
        }
        return result;
    }

    private void addPlant(Plant plant) {
        plants.put(plant.getPosition(),plant);
    }

    public HashMap<Position, Animal> getAnimals() {
        return animals;
    }


    public void moveAnimals() {
        List<Animal> animalList = new ArrayList<>(this.getAnimals().values());
        for(Animal animal : animalList) {
            if (animal.getEnergy()==0)
                death(animal);
            else
                move(animal);
        }
    }

    public void spawnPlants(){
        //want to have many plants in the middle
        ArrayList<Integer> xProbability = new ArrayList<>(this.width);
        ArrayList<Integer> yProbability = new ArrayList<>(this.height);
        int elementProb = 100/this.width;
        for (int i=0; i<this.width; i++){
            xProbability.add(i,elementProb);
            if (i<(this.width-1)/2) elementProb*=1.3;
            else elementProb/=1.3;
        }
        elementProb = 100/this.height;
        for (int i=0; i<this.height; i++){
            yProbability.add(i,elementProb);
            if (i<(this.height-1)/2) elementProb*=1.3;
            else elementProb/=1.3;
        }
        Probability xprob = new Probability(width);
        int x = xprob.randIndex(xProbability);
        Probability yprob = new Probability(height);
        int y = yprob.randIndex(yProbability);
        Position pos = new Position(x,y);
        if (plants.containsKey(pos) && animals.containsKey(pos))
            return;
        addPlant(new Plant(pos));
    }

    private void move(Animal animal){
        if (animal.getAge() < 100)
            death(animal);
        Direction actualDir = Direction.NORTH;
        Position oldPos = animal.getPosition();
        try {
            actualDir = actualDir.convertFromInt((animal.chooseGenesDir() + animal.getDir().ordinal()) % 8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Position possibleNextAnimalPos = checkBorders(animal,oldPos.addPosition(actualDir.convertToPos()));
        if(!canMoveTo(animal,possibleNextAnimalPos))
            return;
        animal.setPosition(possibleNextAnimalPos);
        animal.setEnergy(animal.getEnergy()-1);
        animals.remove(oldPos);
        animals.put(animal.getPosition(),animal);
    }

    private boolean canMoveTo(Animal animal, Position animalPosition){
        if (animals.containsKey(animalPosition)){
            return animalsMeet(animal,animals.get(animalPosition));
        }
        else if (plants.containsKey(animalPosition)){
            feedAnimal(animal,animalPosition);
            return true;
        }
        else{
            return true;
        }
    }
    private Position checkBorders(Animal animal, Position animalPosition){
        if (animalPosition.getX() < 0){
            animalPosition = new Position(0,animal.getPosition().getY());
        }
        if (animalPosition.getY() < 0){
            animalPosition = new Position(animalPosition.getX(),0);
        }
        if (animalPosition.getX() > width-1){
            animalPosition = new Position(width-1, animalPosition.getY());
        }
        if (animalPosition.getY() > height-1){
            animalPosition = new Position(animalPosition.getX(),height-1);
        }
        return animalPosition;
    }

    private boolean animalsMeet(Animal animal, Animal animalOnPos){
        if (animal.getGender() && animalOnPos.getGender())
            return fight(animal, animalOnPos);
        else if (animal.getGender() != animalOnPos.getGender()) {
            reproduce(animal, animalOnPos);
            return false;
        }
        else return false;
    }

    private void reproduce(Animal dad, Animal mum){
        ArrayList<Integer> childrenGenes = new ArrayList<>();
        for (int i=0; i<8;i++){
            childrenGenes.add(i,((dad.getGenes().get(i) + mum.getGenes().get(i))/2)+1);
        }
        mutate(childrenGenes);
        Animal child = new Animal(new Position(mum.getPosition().getX(),mum.getPosition().getY()+2),childrenGenes,mum.getEnergy()-10);
        Position correctPos = checkBorders(child,child.getPosition());
        child.setPosition(correctPos);
        animals.put(correctPos,child);
    }

    private void mutate(ArrayList<Integer> genes){
        Random rand = new Random();
        int randIndex = rand.nextInt(8);
        int currValue = genes.get(randIndex);
        int randValue = rand.nextInt(genes.get(randIndex)/5);
        genes.add(randIndex,randValue+currValue);
    }

    private boolean fight(Animal attacker, Animal defender){
        if (attacker.getEnergy() + defender.getEnergy() < attacker.getMaxEnergy())
            return false;
        if (attacker.getEnergy()>defender.getEnergy()){
            attacker.setEnergy(attacker.getEnergy()-defender.getEnergy());
            death(defender);
            return true;
        }
        else{
            defender.setEnergy(defender.getEnergy()-attacker.getEnergy());
            death(attacker);
            return false;
        }
    }

    private void feedAnimal(Animal animal, Position animalPosition){
        Plant plant = plants.get(animalPosition);
        animal.addEnergy(plant.getNutritionalValue());
        plants.remove(animalPosition);
    }

    private void death(Animal toBeDead){
        animals.remove(toBeDead.getPosition());
    }

    @Override
    public String toString() {
        StringBuilder map = new StringBuilder();
        map.append(" ");
        for (int i=0; i<width; i++)
            map.append("_ ");
        map.append("\n");
        for (int y = height-1; y>=0; y--){
            map.append("|");
            for (int x=0; x<width; x++){
                Position tmp = new Position(x,y);
                if (animals.containsKey(tmp))
                    map.append(animals.get(tmp).toString());
                else if (plants.containsKey(tmp))
                    map.append("*");
                else map.append(" ");
                if (x!=width-1)
                    map.append(" ");
            }
            map.append("|");
            map.append("\n");
        }
        map.append(" ");
        for (int i=0; i<width; i++)
            map.append("- ");

        return map.toString();
    }
}
