package realestate;

public class Panel extends RealEstate implements PanelInterface {
    private int floor;
    private boolean isInsulated;

    public Panel(String city, double price, int sqm, double numberOfRooms,
                 Genre genre, int floor, boolean isInsulated) {
        super(city, price, sqm, numberOfRooms, genre);
        this.floor = floor;
        this.isInsulated = isInsulated;
    }

    @Override
    public long getTotalPrice() {
        double baseWithCity = super.getTotalPrice(); // includes city bonus
        double modified = baseWithCity;

        if (floor >= 0 && floor <= 2) {
            modified *= 1.05;
        } else if (floor == 10) {
            modified *= 0.95;
        }

        if (isInsulated) {
            modified *= 1.05;
        }

        return Math.round(modified);
    }

    @Override
    public boolean hasSameAmount(RealEstate other) {
        return this.getTotalPrice() == other.getTotalPrice();
    }

    @Override
    public long roomprice() {
        if (numberOfRooms == 0) return 0;
        return Math.round((price * sqm) / numberOfRooms);
    }

    @Override
    public String toString() {
        return String.format("Panel [city=%s, price/m²=%.0f Ft, sqm=%d, rooms=%.1f, floor=%d, insulated=%s, totalPrice=%d Ft, avgSqmPerRoom=%.2f]",
                city, price, sqm, numberOfRooms, floor, isInsulated ? "yes" : "no",
                getTotalPrice(), averageSqmPerRoom());
    }
}
