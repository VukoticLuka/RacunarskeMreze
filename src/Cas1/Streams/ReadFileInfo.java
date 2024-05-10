package Cas1.Streams;

import java.io.File;

public class ReadFileInfo {
    public static void main(String[] args) {
        File file = new File("src/Cas1/Streams/in.txt");
        System.out.println("File exists: " + file.exists());

        if(file.exists()){
            System.out.println("Absolute path: " + file.getAbsolutePath());
            System.out.println("Is directory: " + file.isDirectory());
            System.out.println("Path parent: " + file.getParent());

            if(file.isFile()){
                System.out.println("File size: " + file.length());
                System.out.println("Last modified: " + file.lastModified());
                System.out.println("Separator: " +  File.separator);
            }
        }

    }
}
