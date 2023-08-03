package Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherApp {
    private static final String API_URL = "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=b6907d289e10d714a6e88b30761fae22";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            printMenu();
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    getWeatherData(scanner);
                    break;
                case 2:
                    getWindSpeedData(scanner);
                    break;
                case 3:
                    getPressureData(scanner);
                    break;
                case 0:
                    System.out.println("Exiting the program...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }

            // Consume the newline character after reading the integer input
            scanner.nextLine();

        } while (option != 0);

        scanner.close();
    }
    private static void printMenu() {
        System.out.println("Choose an option:");
        System.out.println("1. Get weather");
        System.out.println("2. Get Wind Speed");
        System.out.println("3. Get Pressure");
        System.out.println("0. Exit");
    }

    private static void getWeatherData(Scanner scanner) {
        System.out.print("Enter the date (yyyy-MM-dd HH:mm:ss): ");
        String date = scanner.next();

        String apiUrl = API_URL + "&dt=" + date;
        String jsonResponse = makeRequest(apiUrl);
        if (jsonResponse.isEmpty()) {
            System.out.println("No data found for the given date.");
            return;
        }

        double temperature = extractTemperature(jsonResponse);
        System.out.println("Temperature on " + date + ": " + temperature + "°F");
    }

    private static void getWindSpeedData(Scanner scanner) {
        System.out.print("Enter the date (yyyy-MM-dd HH:mm:ss): ");
        String date = scanner.next();

        String apiUrl = API_URL + "&dt=" + date;
        String jsonResponse = makeRequest(apiUrl);
        if (jsonResponse.isEmpty()) {
            System.out.println("No data found for the given date.");
            return;
        }

        double windSpeed = extractWindSpeed(jsonResponse);
        System.out.println("Wind speed on " + date + ": " + windSpeed + " m/s");
    }

    private static void getPressureData(Scanner scanner) {
        System.out.print("Enter the date (yyyy-MM-dd HH:mm:ss): ");
        String date = scanner.next();

        String apiUrl = API_URL + "&dt=" + date;
        String jsonResponse = makeRequest(apiUrl);
        if (jsonResponse.isEmpty()) {
            System.out.println("No data found for the given date.");
            return;
        }

        double pressure = extractPressure(jsonResponse);
        System.out.println("Pressure on " + date + ": " + pressure + " hPa");
    }

    private static String makeRequest(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static double extractTemperature(String jsonResponse) {
        // Manually extract the temperature from the JSON response
        int index = jsonResponse.indexOf("\"temp\":");
        if (index != -1) {
            int endIndex = jsonResponse.indexOf(",", index);
            if (endIndex != -1) {
                String tempValue = jsonResponse.substring(index + 7, endIndex);
                return Double.parseDouble(tempValue);
            }
        }
        return -1.0;
    }

    private static double extractWindSpeed(String jsonResponse) {
        // Manually extract the wind speed from the JSON response
        int index = jsonResponse.indexOf("\"speed\":");
        if (index != -1) {
            int endIndex = jsonResponse.indexOf(",", index);
            if (endIndex != -1) {
                String speedValue = jsonResponse.substring(index + 8, endIndex);
                return Double.parseDouble(speedValue);
            }
        }
        return -1.0;
    }

    private static double extractPressure(String jsonResponse) {
        // Manually extract the pressure from the JSON response
        int index = jsonResponse.indexOf("\"pressure\":");
        if (index != -1) {
            int endIndex = jsonResponse.indexOf(",", index);
            if (endIndex != -1) {
                String pressureValue = jsonResponse.substring(index + 11, endIndex);
                return Double.parseDouble(pressureValue);
            }
        }
        return -1.0;
    }
}