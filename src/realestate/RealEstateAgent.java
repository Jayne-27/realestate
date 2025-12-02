package realestate;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

public class RealEstateAgent {

    private static final Logger log = LoggerConfig.getLogger();

    // TreeSet ordered by total price (cheapest first)
    private static final TreeSet<RealEstate> properties = new TreeSet<>(
            Comparator.comparingLong(RealEstate::getTotalPrice)
    );

    public static void main(String[] args) {
        log.info("=== RealEstateAgent application started ===");
        loadFromFile("realestates.txt");
        generateReport();
        log.info("=== Application finished successfully ===");
    }

    /**
     * Loads properties from the specified text file.
     * Falls back to sample data if file is missing or corrupted.
     *
     * @param filename name of the input file
     */
    private static void loadFromFile(String filename) {
        log.info("Attempting to load properties from file: " + filename);

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue;

                String[] p = line.split("#");
                if (p.length < 6) {
                    log.warning("Skipping invalid line " + lineNumber + ": too few fields");
                    continue;
                }

                try {
                    if (p[0].equals("REALESTATE")) {
                        RealEstate re = new RealEstate(
                            p[1],
                            Double.parseDouble(p[2]),
                            Integer.parseInt(p[3]),
                            Double.parseDouble(p[4]),
                            Genre.valueOf(p[5])
                        );
                        properties.add(re);
                        log.info("Loaded RealEstate: " + p[1] + " (" + p[3] + " m²)");

                    } else if (p[0].equals("PANEL")) {
                        if (p.length < 8) {
                            log.warning("Skipping invalid PANEL line " + lineNumber + ": missing floor/insulated");
                            continue;
                        }

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
                        log.info("Loaded Panel: " + p[1] + ", floor " + floor + ", insulated=" + insulated);
                    }
                } catch (Exception e) {
                    log.severe("Error parsing line " + lineNumber + ": " + line + " → " + e.getMessage());
                }
            }
            log.info("Successfully loaded " + properties.size() + " properties from file.");

        } catch (FileNotFoundException e) {
            log.severe("File not found: " + filename + " → Loading sample data instead.");
            loadSampleData();
        } catch (IOException e) {
            log.severe("IO error reading file: " + e.getMessage() + " → Loading sample data.");
            loadSampleData();
        } catch (Exception e) {
            log.severe("Unexpected error during file loading: " + e.getMessage());
            loadSampleData();
        }
    }

    /** Loads fallback sample data when file is unavailable */
    private static void loadSampleData() {
        log.info("Loading built-in sample data...");
        properties.add(new RealEstate("Budapest", 250000, 100, 4, Genre.CONDOMINIUM));
        properties.add(new RealEstate("Debrecen", 220000, 120, 5, Genre.FAMILYHOUSE));
        properties.add(new Panel("Budapest", 180000, 70, 3, Genre.CONDOMINIUM, 4, false));
        properties.add(new Panel("Debrecen", 120000, 35, 2, Genre.CONDOMINIUM, 0, true));
        properties.add(new Panel("Nyíregyháza", 170000, 80, 3, Genre.CONDOMINIUM, 7, false));
        log.info("Sample data loaded: " + properties.size() + " properties");
    }

    /** Generates the full statistical report and saves it to file */
    private static void generateReport() {
        log.info("Generating statistical report...");

        if (properties.isEmpty()) {
            log.warning("No properties to generate report!");
            System.out.println("No properties!");
            return;
        }

        StringBuilder sb = new StringBuilder();

        // 1 & 6 – Average square meter price
        double avgSqmPrice = properties.stream()
                .mapToDouble(RealEstate::getPrice)
                .average().orElse(0);
        sb.append(String.format("Average square meter price of real estate: %.0f Ft/m²\n", avgSqmPrice));

        // 2 – Cheapest property
        RealEstate cheapest = properties.first();
        sb.append(String.format("The price of the cheapest property: %d Ft\n", cheapest.getTotalPrice()));

        // 3 – Most expensive Budapest condominium → avg sqm/room
        double budapestBestAvgSqmPerRoom = properties.stream()
                .filter(p -> "budapest".equalsIgnoreCase(p.getCity()))
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

        // Repeat 1 and 7 as required
        sb.append(String.format("Average square meter price of real estate: %.0f Ft/m²\n", avgSqmPrice));
        sb.append(String.format("The total price of the properties: %d Ft\n", totalAll));

        // Output to console
        System.out.println(sb);

        // Save to file
        try (PrintWriter pw = new PrintWriter("outputRealEstate.txt")) {
            pw.print(sb);
            log.info("Report successfully saved to outputRealEstate.txt");
            System.out.println("\nReport saved to outputRealEstate.txt");
        } catch (FileNotFoundException e) {
            log.severe("Could not save report file: " + e.getMessage());
        }
    }
}