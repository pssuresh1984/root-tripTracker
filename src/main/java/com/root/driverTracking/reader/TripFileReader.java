package com.root.driverTracking.reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TripFileReader {

    public static List<String[]> readTripData(String fileName) {
        List<String[]> fileData = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNext()) {
                fileData.add(scanner.nextLine().split("\\s+"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileData;
    }
}
