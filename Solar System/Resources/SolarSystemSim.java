import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SolarSystemSim extends JPanel {
    private Celestial sun;
    private Celestial earth;
    private Celestial moon;
    private Celestial comet;
    private Celestial asteroid;
    private Celestial[] planets;

    public SolarSystemSim() {
        setBackground(Color.BLACK);

        // Create celestial objects
        sun = new Celestial("Sun", 50, 0, 0, Color.YELLOW); // Sun should be at the center
        earth = new Celestial("Earth", 10, 150, 180, new Color(0, 0, 255)); // Blue
        moon = new Celestial("Moon", 5, 30, 0, Color.GRAY);
        comet = new Comet("Comet", 5, 200, 32.1, Color.CYAN, 20);
        asteroid = new Asteroid("Asteroid", 2, 100, 0.2, Color.GRAY, 10);

        // Add more planets
        Celestial mercury = new Celestial("Mercury", 7, 80, 150, new Color(204, 102, 0)); // Brown
        Celestial venus = new Celestial("Venus", 9, 110, 130, new Color(255, 204, 0)); // Yellow
        Celestial mars = new Celestial("Mars", 8, 140, 110, new Color(255, 0, 0)); // Red
        Celestial jupiter = new Celestial("Jupiter", 20, 170, 80, new Color(255, 204, 102)); // Light Brown
        Celestial saturn = new Celestial("Saturn", 18, 200, 50, new Color(204, 153, 102)); // Light Brown
        Celestial uranus = new Celestial("Uranus", 14, 230, 20, new Color(0, 153, 204)); // Light Blue
        Celestial neptune = new Celestial("Neptune", 16, 260, 10, new Color(0, 0, 102)); // Dark Blue

        planets = new Celestial[]{sun, mercury, venus, earth, mars, moon, comet, asteroid, jupiter, saturn, uranus, neptune};

        // Create a timer to update the simulation
        Timer timer = new Timer(20, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateSolarSystem(0.01); // Use a fixed time step
                repaint();
            }
        });
        timer.setRepeats(true); // Ensure that the timer continues to fire
        timer.start();
    }

    public void updateSolarSystem(double timeStep) {
        for (Celestial planet : planets) {
            if (!planet.getName().equals("Sun")) {
                planet.move(timeStep);
            }
        }
    }
    

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        sun.draw(g2d, centerX, centerY);
        for (Celestial planet : planets) {
            planet.draw(g2d, centerX, centerY);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Solar System Simulator");
        SolarSystemSim solarSystemSim = new SolarSystemSim();

        frame.add(solarSystemSim);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
