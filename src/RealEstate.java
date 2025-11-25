package realestate;

public class RealEstate implements PropertyInterface {
    protected String city;
    protected double price;        // price per sqm
    protected int sqm;
    protected double numberOfRooms;
    protected Genre genre;

    public RealEstate(String city, double price, int sqm, double numberOfRooms, Genre genre) {
        this.city = city;
        this.price = price;
        this.sqm = sqm;
        this.numberOfRooms = numberOfRooms;
        this.genre = genre;
    }

    @Override
    public void makeDiscount(int percentage) {
        this.price = this.price * (100 - percentage) / 100.0;
    }

    @Override
    public long getTotalPrice() {
        double basePrice = price * sqm;
        double multiplier = 1.0;

        if (city.equalsIgnoreCase("Budapest")) {
            multiplier = 1.30;
        } else if (city.equalsIgnoreCase("Debrecen")) {
            multiplier = 1.20;
        } else if (city.equalsIgnoreCase("Nyíregyháza")) {
            multiplier = 1.15;
        }

        return Math.round(basePrice * multiplier);
    }

    @Override
    public double averageSqmPerRoom() {
        if (numberOfRooms == 0) return 0;
        return sqm / numberOfRooms;
    }

    @Override
    public String toString() {
        return String.format("RealEstate [city=%s, price/m²=%.0f Ft, sqm=%d, rooms=%.1f, genre=%s, totalPrice=%d Ft, avgSqmPerRoom=%.2f]",
                city, price, sqm, numberOfRooms, genre, getTotalPrice(), averageSqmPerRoom());
    }

    // Getters (needed for Panel comparisons)
    public String getCity() { return city; }
    public double getPrice() { return price; }
    public int getSqm() { return sqm; }
    public double getNumberOfRooms() { return numberOfRooms; }
    public Genre getGenre() { return genre; }
}
