package realestate;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class TestRunner {
    public static void main(String[] args) {
        try {
            StringBuilder out = new StringBuilder();

            RealEstate r1 = new RealEstate("Budapest", 250000, 100, 4, Genre.CONDOMINIUM);
            RealEstate r2 = new RealEstate("Debrecen", 220000, 120, 5, Genre.FAMILYHOUSE);

            Panel p1 = new Panel("Budapest", 180000, 70, 3, Genre.CONDOMINIUM, 4, false);
            Panel p2 = new Panel("Debrecen", 120000, 35, 2, Genre.CONDOMINIUM, 0, true);

            out.append("INPUT:\n");
            out.append("r1 = new RealEstate(\"Budapest\", 250000, 100, 4, Genre.CONDOMINIUM)\n");
            out.append("r2 = new RealEstate(\"Debrecen\", 220000, 120, 5, Genre.FAMILYHOUSE)\n");
            out.append("p1 = new Panel(\"Budapest\", 180000, 70, 3, Genre.CONDOMINIUM, 4, false)\n");
            out.append("p2 = new Panel(\"Debrecen\", 120000, 35, 2, Genre.CONDOMINIUM, 0, true)\n\n");

            out.append("RESULTS:\n");
            out.append(r1.toString() + "\n");
            out.append(r2.toString() + "\n");
            out.append(p1.toString() + "\n");
            out.append(p2.toString() + "\n\n");

            p1.makeDiscount(10);
            out.append("After 10% discount on p1:\n");
            out.append(p1.toString() + "\n\n");

            out.append("Average sqm/room for p2: " + p2.averageSqmPerRoom() + "\n");
            out.append("Average room price (without extras): " + p2.roomprice() + " Ft\n\n");

            out.append("Do p1 and r1 have same total price? " + p1.hasSameAmount(r1) + "\n");

            String text = out.toString();

            // Write text output to docs/test-output/output.txt
            File outDir = new File("docs/test-output");
            outDir.mkdirs();
            File txt = new File(outDir, "output.txt");
            try (FileWriter fw = new FileWriter(txt)) {
                fw.write(text);
            }

            // Create screenshots dir
            File imgDir = new File("docs/screenshots");
            imgDir.mkdirs();

            File png = new File(imgDir, "test-output.png");
            textToImage(text, png);

            // Also print to console
            System.out.println(text);
            System.out.println("Test output written to: " + txt.getAbsolutePath());
            System.out.println("Screenshot written to: " + png.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void textToImage(String text, File outFile) throws IOException {
        String[] lines = text.split("\n");

        Font font = new Font("Consolas", Font.PLAIN, 14);
        BufferedImage tmp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = tmp.createGraphics();
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int lineHeight = fm.getHeight();
        int maxWidth = 0;
        for (String line : lines) {
            int w = fm.stringWidth(line);
            if (w > maxWidth) maxWidth = w;
        }
        g2d.dispose();

        int padding = 12;
        int width = Math.max(300, maxWidth + padding * 2);
        int height = Math.max(200, lineHeight * lines.length + padding * 2);

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.BLACK);
        g2d.setFont(font);
        fm = g2d.getFontMetrics();

        int y = padding + fm.getAscent();
        for (String line : lines) {
            g2d.drawString(line, padding, y);
            y += lineHeight;
        }
        g2d.dispose();

        ImageIO.write(img, "png", outFile);
    }
}
