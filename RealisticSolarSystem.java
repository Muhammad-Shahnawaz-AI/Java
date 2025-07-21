import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import javax.imageio.ImageIO;

public class RealisticSolarSystem extends JPanel implements ActionListener {

    private static final int CENTER_X = 600, CENTER_Y = 400;
    private final Timer timer;
    private final List<Planet> planets = new ArrayList<>();
    private BufferedImage backgroundImage;
    private BufferedImage sunImage;

    public RealisticSolarSystem() {
        setPreferredSize(new Dimension(1200, 800));
        loadImages();
        createPlanets();
        timer = new Timer(20, this); // ~50 FPS
        timer.start();
    }

    private void loadImages() {
        try {
            backgroundImage = ImageIO.read(new File("images/stars_background.jpg"));
            sunImage = ImageIO.read(new File("images/sun.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createPlanets() {
        planets.add(new Planet("Mercury", 20, 70, 88, "images/mercury.png"));
        planets.add(new Planet("Venus", 30, 110, 225, "images/venus.png"));
        planets.add(new Planet("Earth", 35, 150, 365, "images/earth.png"));
        planets.add(new Planet("Mars", 25, 190, 687, "images/mars.png"));
        planets.add(new Planet("Jupiter", 60, 240, 4333, "images/jupiter.png"));
        planets.add(new Planet("Saturn", 55, 300, 10759, "images/saturn.png", true));
        planets.add(new Planet("Uranus", 45, 350, 30687, "images/uranus.png"));
        planets.add(new Planet("Neptune", 45, 400, 60190, "images/neptune.png"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw orbits
        g2d.setColor(new Color(255, 255, 255, 40));
        for (Planet p : planets) {
            g2d.drawOval(CENTER_X - p.orbitRadius, CENTER_Y - p.orbitRadius,
                         2 * p.orbitRadius, 2 * p.orbitRadius);
        }

        // Draw Sun
        g2d.drawImage(sunImage, CENTER_X - 50, CENTER_Y - 50, 100, 100, null);

        // Draw Planets
        for (Planet p : planets) {
            p.updatePosition();
            p.draw(g2d);
        }
    }

    private void drawBackground(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    private class Planet {
        String name;
        int size, orbitRadius, orbitalPeriod;
        double angle;
        BufferedImage image;
        boolean hasRings;

        public Planet(String name, int size, int orbitRadius, int orbitalPeriod, String imagePath) {
            this(name, size, orbitRadius, orbitalPeriod, imagePath, false);
        }

        public Planet(String name, int size, int orbitRadius, int orbitalPeriod, String imagePath, boolean hasRings) {
            this.name = name;
            this.size = size;
            this.orbitRadius = orbitRadius;
            this.orbitalPeriod = orbitalPeriod;
            this.hasRings = hasRings;
            try {
                this.image = ImageIO.read(new File(imagePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.angle = Math.random() * 360;
        }

        public void updatePosition() {
            angle += 360.0 / orbitalPeriod;
            if (angle >= 360) angle -= 360;
        }

        public void draw(Graphics2D g2d) {
            int x = CENTER_X + (int) (orbitRadius * Math.cos(Math.toRadians(angle))) - size / 2;
            int y = CENTER_Y + (int) (orbitRadius * Math.sin(Math.toRadians(angle))) - size / 2;

            if (hasRings) {
                g2d.setColor(new Color(255, 255, 0, 70));
                g2d.drawOval(x - 10, y + size / 2 - 5, size + 20, 10); // simulate ring
            }

            g2d.drawImage(image, x, y, size, size, null);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Realistic Solar System");
        RealisticSolarSystem panel = new RealisticSolarSystem();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
