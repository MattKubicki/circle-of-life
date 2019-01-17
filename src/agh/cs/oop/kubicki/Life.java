package agh.cs.oop.kubicki;

public class Life {
    private Jungle map;
private Animal animal;

    public Life(Jungle map) {
        this.map = map;
    }

    public Jungle getMap() {
        return map;
    }

    public void day(){
        map.spawnPlants();
        map.moveAnimals();
    }

    public void simulate(){
        day();
    }

}
