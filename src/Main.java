import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main implements NativeKeyListener {
    public Main() {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(this);
    }

    ArrayList<Thread> threads = new ArrayList<>();
    int threadsNumber = 5;

    public void runMain(){
        for (int i = 0; i < threadsNumber; i++) {
            threads.add(new Thread(new Application()));
            threads.get(i).start();
        }
        double missedRate;
        long missedCounter;
        long referenceCounter;
        int cacheSize;
        while (true) {
            synchronized (Application.memory){
                missedRate = (double)Application.memory.getMissedCounter() / Application.memory.getReferenceCounter();
                missedCounter = Application.memory.getMissedCounter();
                referenceCounter = Application.memory.getReferenceCounter();
                cacheSize = Application.memory.memory.size();
            }
            synchronized (System.out) {


                System.out.println("Pamięć odwołań ogółem: " + referenceCounter +
                        ", nietrafionych: " + missedCounter + ", współczynnik: " +   String.format("%.2f", missedRate)
                        + ", seedów w pamięci: " +   cacheSize);
            }
            try {
                Thread.currentThread().sleep(750L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        Main main = new Main();
        main.runMain();

    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

        int key = nativeKeyEvent.getKeyCode();
        switch (key) {
            case 38:
                try {
                    Application.algorithms.loadAlgoritms();
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }
                break;
            case 32:
                Application.algorithms.unLoadAlgoritms();
                break;
            default:
        }
    }
}
