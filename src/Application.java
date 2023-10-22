import knapsackProblem.Algorithm;
import knapsackProblem.Instance;
import knapsackProblem.Solution;

public class Application implements Runnable {
    public static Cache memory = new Cache();
    static Seeds seeds = new Seeds(600, 50L, 1000L);
    public static Algorithms algorithms = new Algorithms();

    @Override
    public void run() {
        Algorithm algorithm;
        Long seed;
        String msg;
        Instance instance;
        Solution solution;
        long startTime;
        long stopTime;
        long gap;
        boolean flag;
        while (true) {
            startTime = System.currentTimeMillis();
            flag = true;
            synchronized (algorithms){
                algorithm = algorithms.getRandAlgoritm();
            }

            seed = seeds.getRandomSeed();
            if (algorithm != null && seed != null) {
                synchronized (memory) {
                    if (memory.get(seed) != null) flag = false;
                    else memory.put(seed, null);//Blokowanie danego seeda dla naszego algorytmu
                }
                if (flag) {
                    msg = "Thread id: " + Thread.currentThread().getId() + ", seed: " + seed.toString() + ", algorithm: " + algorithm.getName();
                    synchronized (System.out) {
                        System.out.println(msg);
                    }
                    instance = new Instance();
                    instance.generateInstanceFromLong(seed, 15);
                    solution = algorithm.foundSolution(instance);
                    synchronized (memory) {
                        memory.put(seed, solution);
                    }
                }
            }
            algorithm = null;
            stopTime = System.currentTimeMillis();
            gap = stopTime - startTime;
            if(gap < 1000L){
                try {
                    Thread.currentThread().sleep(1000L - gap);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
