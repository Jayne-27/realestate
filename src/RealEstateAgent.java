package realestate;

import java.io.*;
import java.util.*;

public class RealEstateAgent {

    // TreeSet ordered by total price (cheapest first)
    private static final TreeSet<RealEstate> properties = new TreeSet<>(
            Comparator.comparingLong(RealEstate::getTotalPrice)
    );

    public static void main(String[] args) {
        loadFromFile("realestates.txt");
        generateReport();
    }

    private static void loadFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] p = line.split("#");

                if (p[0].equals("REALESTATE")) {
                    RealEstate re = new RealEstate(
                        p[1],
                        Double.parseDouble(p[2]),
                        Integer.parseInt(p[3]),
                        Double.parseDouble(p[4]),
                        Genre.valueOf(p[5])
                    );
                    properties.add(re);

                } else if (p[0].equals("PANEL")) {
                    int floor = Integer.parseInt(p[6]);
                    boolean insulated = p[7].equalsIgnoreCase("yes");

                    Panel panel = new Panel(
                        p[1],
                        Double.parseDouble(p[2]),
                        Integer.parseInt(p[3]),
                        Double.parseDouble(p[4]),
                        Genre.valueOf(p[5]),
                        floor,
                        insulated
                    );
                    properties.add(panel);
                }
            }
        } catch (Exception e) {
            System.out.println("File not found or error – loading sample data instead.");
            loadSampleData();
        }
    }

    private static void loadSampleData() {
        properties.add(new RealEstate("Budapest", 250000, 100, 4, Genre.CONDOMINIUM));
        properties.add(new RealEstate("Debrecen", 220000, 120, 5, Genre.FAMILYHOUSE));
        properties.add(new Panel("Budapest", 180000, 70, 3, Genre.CONDOMINIUM, 4, false));
        properties.add(new Panel("Debrecen", 120000, 35, 2, Genre.CONDOMINIUM, 0, true));
        properties.add(new Panel("Nyíregyháza", 170000, 80, 3, Genre.CONDOMINIUM, 7, false));
    }

    private static void generateReport() {
        if (properties.isEmpty()) {
            System.out.println("No properties!");
            return;
        }

        StringBuilder sb = new StringBuilder();

        // 1 & 6 – Average square meter price
        double avgSqmPrice = properties.stream()
                .mapToDouble(p -> p.getPrice())
                .average().orElse(0);
        sb.append(String.format("Average square meter price of real estate: %.0f Ft/m²\n", avgSqmPrice));

        // 2 – Cheapest property
        RealEstate cheapest = properties.first();
        sb.append(String.format("The price of the cheapest property: %d Ft\n", cheapest.getTotalPrice()));

        // 3 – Most expensive Budapest condominium → avg sqm/room
        double budapestBestAvgSqmPerRoom = properties.stream()
                .filter(p -> p.getCity().equalsIgnoreCase("Budapest"))
                .filter(p -> p.getGenre() == Genre.CONDOMINIUM)
                .max(Comparator.comparingLong(RealEstate::getTotalPrice))
                .map(RealEstate::averageSqmPerRoom)
                .orElse(0.0);
        sb.append(String.format("The average square meter value per room of the most expensive apartment in Budapest: %.2f m²/room\n",
                budapestBestAvgSqmPerRoom));

        // 4 & 7 – Total price of all properties
        long totalAll = properties.stream()
                .mapToLong(RealEstate::getTotalPrice)
                .sum();
        sb.append(String.format("The total price of the properties: %d Ft\n", totalAll));

        // 5 – Condominium properties ≤ average total price
        long avgTotalPrice = (long) properties.stream()
                .mapToLong(RealEstate::getTotalPrice)
                .average().orElse(0);

        sb.append("List of condominium properties whose total price does not exceed the average price of properties:\n");
        properties.stream()
                .filter(p -> p.getGenre() == Genre.CONDOMINIUM)
                .filter(p -> p.getTotalPrice() <= avgTotalPrice)
                .sorted(Comparator.comparingLong(RealEstate::getTotalPrice))
                .forEach(p -> sb.append(String.format("  - %s (%s) – %d Ft\n",
                        p.getCity(), p instanceof Panel ? "Panel" : "RealEstate", p.getTotalPrice())));

        // Repeat 1 and 4 again (exactly as the assignment asks)
        sb.append(String.format("Average square meter price of real estate: %.0f Ft/m²\n", avgSqmPrice));
        sb.append(String.format("The total price of the properties: %d Ft\n", totalAll));

        // Output
        System.out.println(sb);

        // Save to file
        try (PrintWriter pw = new PrintWriter("outputRealEstate.txt")) {
            pw.print(sb);
            System.out.println("\nReport saved to outputRealEstate.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Could not save file.");
        }
    }
}