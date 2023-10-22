import java.util.ArrayList;
import java.util.Random;

public class Seeds {
    ArrayList<Long> seedsList = new ArrayList<>();

    public Seeds(){}

    public Seeds(int seedsNumber, Long min, Long max){
        generateSeeds(seedsNumber, min, max);
    }

    public void generateSeeds(int numberOfSeeds, Long min, Long max){
        seedsList = new ArrayList<>();
        int iMin = min.intValue();
        int iMax = max.intValue();

        Random rand = new Random();
        for(int i = 0; i < numberOfSeeds; i++){
            Long next;
            do next = (long) (iMin + Math.abs(rand.nextInt()) % (iMax - iMin));
            while(seedsList.contains(next));
            addSeed(next);
        }
    }
    public Long getRandomSeed(){
        if(seedsList.size()>0) {
            Random rand = new Random();
            int seed = Math.abs(rand.nextInt()) % seedsList.size();
            return seedsList.get(seed);
        }else return null;
    }

    public void addSeed(Long seed){
        seedsList.add(seed);
    }
}
