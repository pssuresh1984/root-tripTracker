package com.root.driverSummary.helper;

import java.time.Duration;
import java.time.LocalTime;

/**
 * This Class contains all the validations and computations for populating model objects.
 */
public class TripDataHelper {
    private static long tripIDCounter = 0;
    private static long driverIDCounter = 0;

    private static Boolean hasDriver(String[] tripData) {

        if (tripData.length > 1 && tripData[1] != null && (tripData[0].contains("Driver") || tripData[0].contains("Trip"))) {
            return true;
        } else {
            //Ignore this trip as its gone past 24 hours or end time is less than start time
            System.out.println("/***** Validation Required *****/");
            System.out.println("File validation required : Invalid trip data found");
        }
        return false;
    }

    public static String getDriver(String[] tripData) {

        if (hasDriver(tripData)) {
            return tripData[1];
        } else {
            return null;
        }
    }

    public static Boolean isValidTrip(String[] tripData) {
        if (tripData.length == 5 && LocalTime.parse(tripData[3]).isAfter(LocalTime.parse(tripData[2]))) {
            double speed;
            speed = calculateSpeed(LocalTime.parse(tripData[2]), LocalTime.parse(tripData[3]),
                    Double.parseDouble(tripData[4]));
            if (speed > 5 && speed < 100) {
                return true;
            } else {
                //Ignore trips that are less than 5 miles or greater than 100 miles
                System.out.println("Trip ignored for driver " + tripData[1] + " for not driving in valid speed range");
            }
        }
        return false;
    }

    private static LocalTime covertStringToTime(String inputTime) {
        if (inputTime != null) {
            return LocalTime.parse(inputTime);
        }
        return null;
    }

    public static LocalTime getLocalTime(String startTime) {
        return covertStringToTime(startTime);
    }

    private static Double convertToMiles(String miles) {
        if (miles != null) {
            return Double.parseDouble(miles);
        }
        return null;
    }

    public static Double getMiles(String miles) {
        return convertToMiles(miles);
    }

    public static Long createDriverID() {
        return driverIDCounter++;
    }

    public static Long createTripID() {
        return tripIDCounter++;
    }

    private static double calculateSpeed(LocalTime startTime, LocalTime endTime, Double miles) {
        double duration = Duration.between(startTime, endTime).toMinutes();
        return averageSpeed(duration,miles);
    }

    public static double averageSpeed(Double totalDuration, Double totalMiles) {
        if (totalDuration > 0) {
            return totalMiles / totalDuration * 60;
        } else {
            return totalMiles * 60;
        }
    }
}
