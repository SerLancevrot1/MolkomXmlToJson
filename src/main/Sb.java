package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Sb {

    public void showSb(StringBuilder stringBuilder){

        System.out.println(stringBuilder);
        String newFilePath = "C:\\Users\\max\\Desktop\\XmlToJson.txt";

        try {
            File file = new File(newFilePath);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());

                try {
                    FileWriter myWriter = new FileWriter(newFilePath);
                    myWriter.write(String.valueOf(stringBuilder));
                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

            } else {
                System.out.println("File already exists.");
                return;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }



    }
}
