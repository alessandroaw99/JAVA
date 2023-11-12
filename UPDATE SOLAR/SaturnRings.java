import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

/**
 * The SaturnRings class represents the rings of Saturn in the solar system simulation.
 * It extends the Celestial class and includes additional functionality for handling ring particles.
 */
public class SaturnRings extends Celestial {
    private List<RingParticle> particles;

    /**
     * Constructs a new SaturnRings object with the specified parameters.
     *
     * @param name      The name of the SaturnRings.
     * @param radius    The radius of the SaturnRings.
     * @param distance  The distance of the SaturnRings from the center of the solar system.
     * @param velocity  The velocity of the SaturnRings.
     * @param color     The color of the SaturnRings.
     * @param particles The list of ring particles in the SaturnRings.
     */
    public SaturnRings(String name, double radius, double distance, double velocity, Color color, List<RingParticle> particles) {
        super(name, radius, distance, velocity, color);
        this.particles = particles;
    }

    /**
     * Overrides the move method to update the positions of the SaturnRings and its ring particles.
     *
     * @param timeStep    The time step used to calculate the new positions.
     * @param eccentricity The eccentricity of the orbit (not used for rings).
     */
    @Override
    public void move(double timeStep, double eccentricity) {
        super.move(timeStep, eccentricity);

        // Move each ring particle
        for (RingParticle particle : particles) {
            particle.move(timeStep, eccentricity);
        }
    }

    /**
     * Overrides the draw method to render the SaturnRings and its ring particles on the panel.
     *
     * @param g       The Graphics2D object used for rendering.
     * @param centerX The x-coordinate of the center of the solar system.
     * @param centerY The y-coordinate of the center of the solar system.
     * @param zoomFactor The zoom factor for rendering.
     * @param showLabels Flag to indicate whether to display labels.
     * @param showInfo   Flag to indicate whether to display info.
     */
    @Override
    public void draw(Graphics2D g, int centerX, int centerY, double zoomFactor, boolean showLabels, boolean showInfo) {
        super.draw(g, centerX, centerY, zoomFactor, showLabels, showInfo);

        // Draw each ring particle
        for (RingParticle particle : particles) {
            particle.draw(g, centerX, centerY, this);
        }
    }
}
