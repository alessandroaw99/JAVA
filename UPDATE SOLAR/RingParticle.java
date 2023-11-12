import java.awt.Color;

/**
 * The RingParticle class represents a particle within the rings of a celestial object in the solar system simulation.
 * It extends the Celestial class and includes a rotation speed for the particle.
 * RingParticle objects can be created with specific parameters and be part of a larger structure like SaturnRings.
 */
public class RingParticle extends Celestial {

    private double rotationSpeed;

    /**
     * Constructs a new RingParticle object with the specified parameters.
     *
     * @param name          The name of the RingParticle object.
     * @param radius        The radius of the RingParticle object.
     * @param distance      The distance from the center of the solar system.
     * @param velocity      The initial velocity of the RingParticle object.
     * @param color         The color of the RingParticle object.
     * @param rotationSpeed The rotation speed of the RingParticle object.
     */
    public RingParticle(String name, double radius, double distance, double velocity, Color color, double rotationSpeed) {
        super(name, radius, distance, velocity, color);
        this.rotationSpeed = rotationSpeed;
    }
}
