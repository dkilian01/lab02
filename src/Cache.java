import knapsackProblem.Solution;

import java.util.HashMap;
import java.util.WeakHashMap;

public class Cache {
    //WeakHashMap<Long, Solution> memory = new WeakHashMap<>();
    HashMap<Long, Solution> memory = new HashMap<>();
    long referenceCounter;
    long missedCounter;

    public void clear(){
        System.gc();
    }

    public void put(long key, Solution value){
        memory.put(key,value);
    }

    public Solution get(Long key){
        referenceCounter++;
        if(memory.containsKey(key)){
            return memory.get(key);
        }else{
            missedCounter++;
            return null;
        }
    }

    public long getReferenceCounter() {
        return referenceCounter;
    }

    public void setReferenceCounter(long referenceCounter) {
        this.referenceCounter = referenceCounter;
    }

    public long getMissedCounter() {
        return missedCounter;
    }

    public void setMissedCounter(long missedCounter) {
        this.missedCounter = missedCounter;
    }
}
