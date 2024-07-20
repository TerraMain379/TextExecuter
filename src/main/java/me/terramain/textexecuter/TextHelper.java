package me.terramain.textexecuter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TextHelper {
    public static String readFile(File file){
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        bufferedReader.lines().forEach(s -> {
            stringBuilder.append(s).append("\n");
        });

        try {
            bufferedReader.close();
        } catch (IOException e) {throw new RuntimeException(e);}
        return stringBuilder.toString();
    }
    public static String readFile(String path){
        return readFile(new File(path));
    }
    public static List<File> readFilesFromFolder(File file){
        List<File> files = new ArrayList<>();
        for (File listFile : file.listFiles()) {
            if (listFile.isDirectory()) files.addAll(readFilesFromFolder(listFile));
            else files.add(listFile);
        }
        return files;
    }
    public static List<File> readFilesFromFolder(String path){
        return readFilesFromFolder(new File(path));
    }

    public static String getFileExtension(File file){
        return getFileExtension(file.getName());
    }
    public static String getFileExtension(String fileName){
        String[] blocks = fileName.split("\\.");
        return blocks[blocks.length-1];
    }


}
