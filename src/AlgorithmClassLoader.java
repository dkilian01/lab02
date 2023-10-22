import knapsackProblem.Algorithm;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

public class AlgorithmClassLoader {

    public ArrayList<Algorithm> getAlgorithms() throws IllegalAccessException, InstantiationException {
        ArrayList<Algorithm> algorithms = new ArrayList<>();
        File files[] = getFiles(System.getProperty("user.dir") + "/algorytmy" + "/knapsackProblem" + "/");
        for (File file : files) {
            Class aClass = loadClass(file);

            if (aClass.getSuperclass().getName().equals("knapsackProblem.Algorithm")) {
                Object obj = aClass.newInstance();
                Algorithm algorithm = (Algorithm) obj;
                algorithms.add(algorithm);
            }
        }
        return algorithms;
    }

    private File[] getFiles(String pathToFolder) {
        File folder = new File(pathToFolder);
        File[] listOfFiles = folder.listFiles();
        ArrayList<File> classFiles = new ArrayList<>();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                if (listOfFiles[i].toString().endsWith(".class")) classFiles.add(listOfFiles[i]);
            }
        }
        return classFiles.toArray(new File[0]);
    }

    private Class loadClass(File file) {
        String classname = file.getName().substring(0, file.getName().indexOf("."));
        String path = file.getParentFile().getParentFile().getPath() + "\\";
        File folder = new File(path);
        try {
            URL classUrl = folder.toURI().toURL();
            URL[] urls = new URL[]{classUrl};
            ClassLoader ucl = new URLClassLoader(urls, getClass().getClassLoader());
            return ucl.loadClass("knapsackProblem." + classname);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
