package realestate;

import java.util.logging.Logger;

public class RealEstate implements PropertyInterface {
    private static final Logger log = LoggerConfig.getLogger();

    protected String city;
    protected double price;        // price per square meter in HUF
    protected int sqm;
    protected double numberOfRooms;
    protected Genre genre;

    /**
     * Constructs a new RealEstate object.
     *
     * @param city           the city where the property is located
     * @param price          price per square meter in HUF
     * @param sqm            area in square meters
     * @param numberOfRooms  number of rooms (can be half-room → double)
     * @param genre          type of property (FAMILYHOUSE, CONDOMINIUM, FARM)
     */
    public RealEstate(String city, double price, int sqm, double numberOfRooms, Genre genre) {
        log.info("Creating RealEstate: " + city + ", " + sqm + " m²");
        this.city = city;
        this.price = price;
        this.sqm = sqm;
        this.numberOfRooms = numberOfRooms;
        this.genre = genre;
    }

    @Override
    public void makeDiscount(int percentage) {
        log.info("Applying " + percentage + "% discount on " + city);
        this.price *= (100 - percentage) / 100.0;
    }

    @Override
public long getTotalPrice() {
    log.info("Calculating total price for property in " + city);
    double basePrice = price * sqm;

    double cityMultiplier = 1.0;
    if ("budapest".equalsIgnoreCase(city)) {
        cityMultiplier = 1.30;
    } else if ("debrecen".equalsIgnoreCase(city)) {
        cityMultiplier = 1.20;
    } else if ("nyíregyháza".equalsIgnoreCase(city) || "nyiregyhaza".equalsIgnoreCase(city)) {
        cityMultiplier = 1.15;
    }

    return Math.round(basePrice * cityMultiplier);
}

    @Override
    public double averageSqmPerRoom() {
        log.info("Calculating average m²/room for " + city);
        return numberOfRooms == 0 ? 0 : sqm / numberOfRooms;
    }

    @Override
    public String toString() {
        return String.format("RealEstate[city=%s, price/m²=%.0f Ft, sqm=%d, rooms=%.1f, genre=%s, total=%d Ft]",
                city, price, sqm, numberOfRooms, genre, getTotalPrice());
    }

    // Getters (for internal use)
    public String getCity() { return city; }
    public double getPrice() { return price; }
    public int getSqm() { return sqm; }
    public double getNumberOfRooms() { return numberOfRooms; }
    public Genre getGenre() { return genre; }
}