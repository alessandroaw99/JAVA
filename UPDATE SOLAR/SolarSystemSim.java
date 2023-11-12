import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;

/**
 * The SolarSystemSim class represents a solar system simulation that displays celestial objects and allows user interaction.
 * It extends JPanel and includes functionality for updating and rendering the solar system based on user settings.
 */
public class SolarSystemSim extends JPanel {

    private Celestial sun;
    private Celestial earth;
    private Celestial moon;
    private Celestial comet;
    private Celestial asteroid;
    private Celestial asteroidBelt;
    private Celestial saturnRings;
    private Celestial[] planets;

    private double timeMultiplier = 1.0; // Initial time multiplier
    private double zoomFactor = 1.0; // Initial zoom factor
    private boolean showLabels = true; // Flag to indicate whether to display labels
    private boolean showInfo = false;

    /**
     * Constructs a new SolarSystemSim object, initializes celestial objects, and sets up user interface components.
     */
    public SolarSystemSim() {
        setBackground(Color.BLACK);

        // Create celestial objects
        sun = new Celestial("Sun", 50, 0, 0, Color.YELLOW);
        earth = new Celestial("Earth", 10, 150, 180, new Color(0, 0, 255)); // Blue
        moon = new Celestial("Moon", 5, 30, 0, Color.GRAY);
        comet = new Comet("Comet", 5, 200, 32.1, Color.CYAN, 20);
        asteroid = new Asteroid("Asteroid", 2, 100, 0.2, Color.GRAY, 10);
        asteroidBelt = new AsteroidBelt("AsteroidBelt", 0, 300, 0, Color.GRAY, new ArrayList<Asteroid>());  // Initialize AsteroidBelt

        saturnRings = new SaturnRings("SaturnRings", 25, 200, 0, Color.LIGHT_GRAY, new ArrayList<RingParticle>());  // Initialize SaturnRings

        // Add more planets
        Celestial mercury = new Celestial("Mercury", 7, 80, 150, new Color(204, 102, 0)); // Brown
        Celestial venus = new Celestial("Venus", 9, 110, 130, new Color(255, 204, 0)); // Yellow
        Celestial mars = new Celestial("Mars", 8, 140, 110, new Color(255, 0, 0)); // Red
        Celestial jupiter = new Celestial("Jupiter", 20, 170, 80, new Color(255, 204, 102)); // Light Brown
        Celestial saturn = new Celestial("Saturn", 18, 200, 50, new Color(204, 153, 102)); // Light Brown
        Celestial uranus = new Celestial("Uranus", 14, 230, 20, new Color(0, 153, 204)); // Light Blue
        Celestial neptune = new Celestial("Neptune", 16, 260, 10, new Color(0, 0, 102)); // Dark Blue

        planets = new Celestial[]{sun, mercury, venus, earth, mars, moon, comet, asteroid, asteroidBelt, saturn, saturnRings, uranus, neptune};

        // Create a timer to update the simulation
        Timer timer = new Timer(20, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateSolarSystem(0.01 * timeMultiplier); // Adjust time step based on time multiplier
                repaint();
            }
        });
        timer.setRepeats(true); // Ensure that the timer continues to fire
        timer.start();

        // Create a slider for adjusting time multiplier
        JSlider timeSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, 10);
        timeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                timeMultiplier = timeSlider.getValue() / 10.0;
            }
        });

        // Create a slider for adjusting zoom
        JSlider zoomSlider = new JSlider(JSlider.HORIZONTAL, 1, 200, 100);
        zoomSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                zoomFactor = zoomSlider.getValue() / 100.0;
            }
        });

        // Create checkboxes for toggling labels and info
        JCheckBox labelCheckBox = new JCheckBox("Show Labels", true);
        labelCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLabels = !showLabels;  // Toggle the value
                repaint();
            }
        });

        JCheckBox infoCheckBox = new JCheckBox("Show Info", false);
        infoCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInfo = !showInfo;  // Toggle the value
                repaint();
            }
        });

        // Create labels for sliders
        JLabel timeLabel = new JLabel("Time Multiplier");
        JLabel zoomLabel = new JLabel("Zoom Factor");

        // Add sliders and labels to the frame
        JFrame frame = new JFrame("Solar System Simulator");
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.add(timeLabel, BorderLayout.SOUTH);
        frame.add(timeSlider, BorderLayout.SOUTH);
        frame.add(zoomLabel, BorderLayout.NORTH);
        frame.add(zoomSlider, BorderLayout.NORTH);
        frame.add(labelCheckBox, BorderLayout.WEST);
        frame.add(infoCheckBox, BorderLayout.EAST);

        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Updates the positions of the celestial objects in the solar system based on the specified time step.
     *
     * @param timeStep The time step used to calculate the new positions.
     */
    public void updateSolarSystem(double timeStep) {
        for (Celestial planet : planets) {
            if (!planet.getName().equals("Sun")) {
                double eccentricity = 0.2;
                planet.move(timeStep, eccentricity);
            }
        }
    }

    /**
     * Overrides the paintComponent method to render the solar system on the panel.
     *
     * @param g The Graphics object used for rendering.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        sun.draw(g2d, centerX, centerY, zoomFactor, showLabels, showInfo);  
        for (Celestial planet : planets) {
            if (planet != null) {
                planet.draw(g2d, centerX, centerY, zoomFactor, showLabels, showInfo);  
            }
        }
    }

    /**
     * The main method to start the solar system simulation.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new SolarSystemSim();
    }
}
