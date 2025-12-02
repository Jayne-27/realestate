package realestate;

public class MainTest {
    public static void main(String[] args) {
        RealEstate r1 = new RealEstate("Budapest", 250000, 100, 4, Genre.CONDOMINIUM);
        RealEstate r2 = new RealEstate("Debrecen", 220000, 120, 5, Genre.FAMILYHOUSE);

        Panel p1 = new Panel("Budapest", 180000, 70, 3, Genre.CONDOMINIUM, 4, false);
        Panel p2 = new Panel("Debrecen", 120000, 35, 2, Genre.CONDOMINIUM, 0, true);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println(p1);
        System.out.println(p2);

        p1.makeDiscount(10);
        System.out.println("\nAfter 10% discount on p1:");
        System.out.println(p1);

        System.out.println("\nAverage sqm/room for p2: " + p2.averageSqmPerRoom());
        System.out.println("Average room price (without extras): " + p2.roomprice() + " Ft");

        System.out.println("\nDo p1 and r1 have same total price? " + p1.hasSameAmount(r1));
    }
}
