package realestate;

import java.io.IOException;
import java.util.logging.*;

/**
 * Represents a real estate property with basic attributes and computation methods.
 */
public class RealEstate implements PropertyInterface, Comparable<RealEstate> {
    protected String city;
    protected double price;        // price per square meter in HUF
    protected int sqm;
    protected double numberOfRooms;
    protected Genre genre;

    private static final Logger logger = Logger.getLogger(RealEstate.class.getName());

    static {
        try {
            Handler fileHandler = new FileHandler("realEstateApp.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false); // Only log to file
        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
        }
    }

    /**
     * Constructs a RealEstate object.
     * @param city the city where the property is located
     * @param price price per sqm
     * @param sqm size in square meters
     * @param numberOfRooms number of rooms
     * @param genre the genre/type of property
     */
    public RealEstate(String city, double price, int sqm, double numberOfRooms, Genre genre) {
        logger.info("Constructor called: RealEstate(" + city + ", " + price + ", " + sqm + ", " + numberOfRooms + ", " + genre + ")");
        this.city = city;
        this.price = price;
        this.sqm = sqm;
        this.numberOfRooms = numberOfRooms;
        this.genre = genre;
    }

    /**
     * Applies a discount of the given percentage to the price per square meter.
     * @param percentage the percentage discount to apply
     */
    @Override
    public void makeDiscount(int percentage) {
        logger.info("makeDiscount called with percentage: " + percentage);
        try {
            this.price = this.price * (100 - percentage) / 100.0;
            logger.info("Price after discount: " + this.price);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in makeDiscount: " + e.getMessage(), e);
        }
    }

    /**
     * Calculates the total price of the property considering city price multipliers.
     * @return the total price rounded to the nearest whole number
     */
    @Override
    public long getTotalPrice() {
        logger.info("getTotalPrice called");
        try {
            double basePrice = price * sqm;
            double multiplier = 1.0;

            if (city.equalsIgnoreCase("Budapest")) {
                multiplier = 1.30;
            } else if (city.equalsIgnoreCase("Debrecen")) {
                multiplier = 1.20;
            } else if (city.equalsIgnoreCase("Nyíregyháza")) {
                multiplier = 1.15;
            }

            long total = Math.round(basePrice * multiplier);
            logger.info("Calculated total price: " + total);
            return total;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in getTotalPrice: " + e.getMessage(), e);
            return 0;
        }
    }

    /**
     * Calculates the average square meters per room.
     * @return the average sqm per room, or 0 if number of rooms is 0
     */
    @Override
    public double averageSqmPerRoom() {
        logger.info("averageSqmPerRoom called");
        try {
            if (numberOfRooms == 0) {
                logger.warning("Number of rooms is zero in averageSqmPerRoom");
                return 0;
            }
            double avg = sqm / numberOfRooms;
            logger.info("Calculated avgSqmPerRoom: " + avg);
            return avg;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in averageSqmPerRoom: " + e.getMessage(), e);
            return 0;
        }
    }

    /**
     * Returns a string representation of this RealEstate object.
     * @return a formatted string of property details
     */
    @Override
    public String toString() {
        logger.info("toString called");
        try {
            String str = String.format("RealEstate [city=%s, price/m²=%.0f Ft, sqm=%d, rooms=%.1f, genre=%s, totalPrice=%d Ft, avgSqmPerRoom=%.2f]", 
                    city, price, sqm, numberOfRooms, genre, getTotalPrice(), averageSqmPerRoom());
            logger.info("toString result: " + str);
            return str;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in toString: " + e.getMessage(), e);
            return super.toString();
        }
    }

    /**
     * Compares this RealEstate object with another based on total price.
     * @param other the other RealEstate object
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object
     */
    @Override
    public int compareTo(RealEstate other) {
        logger.info("compareTo called; comparing to another RealEstate's total price.");
        try {
            int result = Long.compare(this.getTotalPrice(), other.getTotalPrice());
            logger.info("compareTo result: " + result);
            return result;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in compareTo: " + e.getMessage(), e);
            return 0;
        }
    }

    /**
     * Gets the city of the property.
     * @return the city name
     */
    public String getCity() { 
        logger.info("getCity called");  
        return city; 
    }

    /**
     * Gets the price per sqm.
     * @return price per square meter
     */
    public double getPrice() { 
        logger.info("getPrice called");  
        return price; 
    }

    /**
     * Gets the size in square meters.
     * @return size in sqm
     */
    public int getSqm() { 
        logger.info("getSqm called");  
        return sqm; 
    }

    /**
     * Gets the number of rooms.
     * @return number of rooms
     */
    public double getNumberOfRooms() { 
        logger.info("getNumberOfRooms called");  
        return numberOfRooms; 
    }

    /**
     * Gets the genre/type of the property.
     * @return genre
     */
    public Genre getGenre() { 
        logger.info("getGenre called");  
        return genre; 
    }
}