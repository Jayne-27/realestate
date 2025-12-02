package realestate;

import java.util.logging.Logger;


public class Panel extends RealEstate implements PanelInterface {
    private static final Logger log = LoggerConfig.getLogger();

    private int floor;
    private boolean isInsulated;

    public Panel(String city, double price, int sqm, double numberOfRooms,
                 Genre genre, int floor, boolean isInsulated) {
        super(city, price, sqm, numberOfRooms, genre);
        log.info("Creating Panel on floor " + floor + (isInsulated ? " (insulated)" : ""));
        this.floor = floor;
        this.isInsulated = isInsulated;
    }

    @Override
    public long getTotalPrice() {
        log.info("Calculating Panel total price (floor=" + floor + ", insulated=" + isInsulated + ")");
        double price = super.getTotalPrice();

        if (floor >= 0 && floor <= 2) price *= 1.05;
        else if (floor == 10) price *= 0.95;
        if (isInsulated) price *= 1.05;

        return Math.round(price);
    }

    @Override
    public boolean hasSameAmount(RealEstate other) {
        log.info("Comparing total price with another property");
        return this.getTotalPrice() == other.getTotalPrice();
    }

    @Override
    public long roomprice() {
        log.info("Calculating average room price (no modifiers)");
        return numberOfRooms == 0 ? 0 : Math.round((price * sqm) / numberOfRooms);
    }

    @Override
    public String toString() {
        return String.format("Panel[city=%s, floor=%d, insulated=%s, total=%d Ft]",
                city, floor, isInsulated ? "yes" : "no", getTotalPrice());
    }
}