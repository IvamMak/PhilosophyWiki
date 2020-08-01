package service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class FileWorker {

    public static void writeResultToFile(Map<String, Integer> resultMap) {
        File file = createFile();

        try (FileWriter fileWriter = new FileWriter(file)) {
            for (Map.Entry<String, Integer> entry : resultMap.entrySet()){
                fileWriter.write(entry.getKey() + " " + entry.getValue());
                fileWriter.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File createFile(){
        String fileAddress = readAddressOfFile();
        File file = null;

        if (!Files.isRegularFile(Paths.get(fileAddress))) {
            try {
                file = Files.createFile(Paths.get(fileAddress)).toFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            file = new File(fileAddress);
        }
        return file;
    }

    private static String readAddressOfFile() {
        String fileAddress = null;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))){
            System.out.println("Введите адрес файла для записи результата: ");
            fileAddress = bufferedReader.readLine();
        } catch (IOException e){
            e.printStackTrace();
        }
        return fileAddress;
    }
}
