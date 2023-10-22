import knapsackProblem.Algorithm;

import java.util.ArrayList;
import java.util.Random;

public class Algorithms {
    ArrayList<Algorithm> algorithmsList = new ArrayList<>();
    //ArrayList<WeakReference<Algorithm>> algorithmsList = new ArrayList<>();

    public Algorithm getRandAlgoritm() {
        if (algorithmsList.size() > 0) {
            Random rand = new Random();
            int index = Math.abs(rand.nextInt()) % algorithmsList.size();
            return algorithmsList.get(index);
        } else return null;
    }
    public void loadAlgoritms() throws InstantiationException, IllegalAccessException {
        AlgorithmClassLoader cl = new AlgorithmClassLoader();
        algorithmsList = cl.getAlgorithms();
    }
    public void unLoadAlgoritms(){
        algorithmsList = new ArrayList<>();
        System.gc();
    }

    public void setAlgorithmsList(ArrayList<Algorithm> algorithmsList) {
        this.algorithmsList = algorithmsList;
    }
}
