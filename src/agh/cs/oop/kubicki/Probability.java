package agh.cs.oop.kubicki;

import java.util.ArrayList;
import java.util.Random;

public class Probability {
    private int range;

    public Probability(int range) {
        this.range = range;
    }

    public int randIndex(ArrayList<Integer> probValues){
        ArrayList<Integer> probSum = new ArrayList<>(range);
        probSum.add(0,probValues.get(0));
        for (int i = 1; i < range; i++) {
            probSum.add(i,(probSum.get(i-1) + probValues.get(i)));
        }
        int sum = 0;
        for (int i=0; i < range; i++){
            sum+=probValues.get(i);
        }
        Random rand = new Random();
        int r = rand.nextInt(sum)+1;
        for (int i = 1; i < range; i++){
            if (r > probSum.get(i-1) && r <= probSum.get(i)) {
                return i;
            }
        }
        return 0; //if we found nothing it means that first element has the highest value which is bigger than r
    }
}
