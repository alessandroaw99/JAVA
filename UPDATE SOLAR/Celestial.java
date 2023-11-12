import java.awt.Color;
import java.awt.Graphics2D;

/**
 * The Celestial class represents a celestial object in the solar system simulation.
 * It includes information about the object's name, radius, distance from the center, velocity, color, and angle.
 * Celestial objects can move and be drawn on a graphical display.
 */
public class Celestial {

    private String name;
    protected double radius;
    protected double distance;
    protected double angle;
    protected double velocity;
    protected Color color;
    private double acceleration;

    /**
     * Constructs a new Celestial object with the specified parameters.
     *
     * @param name     The name of the celestial object.
     * @param radius   The radius of the celestial object.
     * @param distance The distance from the center of the solar system.
     * @param velocity The initial velocity of the celestial object.
     * @param color    The color of the celestial object.
     */
    public Celestial(String name, double radius, double distance, double velocity, Color color) {
        this.name = name;
        this.radius = radius;
        this.distance = distance;
        this.angle = 0;
        this.velocity = velocity;
        this.color = color;
        this.acceleration = 0;  // Initialize acceleration
    }

    /**
     * Sets the velocity of the celestial object.
     *
     * @param velocity The new velocity value.
     */
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    /**
     * Gets the velocity of the celestial object.
     *
     * @return The velocity value.
     */
    public double getVelocity() {
        return velocity;
    }

    /**
     * Sets the distance from the center of the solar system.
     *
     * @param distance The new distance value.
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * Gets the distance from the center of the solar system.
     *
     * @return The distance value.
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Gets the radius of the celestial object.
     *
     * @return The radius value.
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Sets the angle of the celestial object.
     *
     * @param angle The new angle value.
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }

    /**
     * Gets the angle of the celestial object.
     *
     * @return The angle value.
     */
    public double getAngle() {
        return angle;
    }

    /**
     * Sets the color of the celestial object.
     *
     * @param color The new color value.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Gets the color of the celestial object.
     *
     * @return The color value.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the acceleration of the celestial object.
     *
     * @param acceleration The new acceleration value.
     */
    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    /**
     * Gets the acceleration of the celestial object.
     *
     * @return The acceleration value.
     */
    public double getAcceleration() {
        return acceleration;
    }

    /**
     * Moves the celestial object based on the specified time step and eccentricity.
     *
     * @param timeStep    The time step for the movement.
     * @param eccentricity The eccentricity affecting the movement.
     */
    public void move(double timeStep, double eccentricity) {
        angle += (360 * velocity * timeStep / (2 * Math.PI * distance * (1 - eccentricity)));
        angle = angle % 360;
        // Update acceleration as needed
    }

    /**
     * Draws the celestial object on a graphical display with optional labels and information.
     *
     * @param g           The Graphics2D object for drawing.
     * @param centerX     The x-coordinate of the center of the display.
     * @param centerY     The y-coordinate of the center of the display.
     * @param zoomFactor  The zoom factor for the display.
     * @param showLabels  Flag indicating whether to display labels.
     * @param showInfo    Flag indicating whether to display additional information.
     */
    public void draw(Graphics2D g, int centerX, int centerY, double zoomFactor, boolean showLabels, boolean showInfo) {
        int x = (int) (centerX + distance * zoomFactor * Math.cos(Math.toRadians(angle)));
        int y = (int) (centerY + distance * zoomFactor * Math.sin(Math.toRadians(angle)));

        g.setColor(color);
        g.fillOval(x - (int) radius / 2, y - (int) radius / 2, (int) radius, (int) radius);

        if (showLabels) {
            g.setColor(Color.WHITE);
            g.drawString(name, x + (int) (radius * zoomFactor), y);

            g.setColor(Color.WHITE);
            g.drawString("Acceleration: " + acceleration, x + (int) (radius * zoomFactor), y + 20);
        }

        if (showInfo) {
            g.setColor(Color.WHITE);
            g.drawString("Acceleration: " + acceleration, 10, 20);
            g.drawString("Zoom Factor: " + zoomFactor, 10, 40);
        }
    }

    /**
     * Draws the celestial object on a graphical display relative to another celestial object.
     *
     * @param g           The Graphics2D object for drawing.
     * @param centerX     The x-coordinate of the center of the display.
     * @param centerY     The y-coordinate of the center of the display.
     * @param relativeTo  The celestial object to which the drawing is relative.
     */
    public void draw(Graphics2D g, int centerX, int centerY, Celestial relativeTo) {
        int xRelativeTo = (int) (centerX + relativeTo.getDistance() * Math.cos(Math.toRadians(relativeTo.getAngle())));
        int yRelativeTo = (int) (centerY + relativeTo.getDistance() * Math.sin(Math.toRadians(relativeTo.getAngle())));

        int x = xRelativeTo + (int) (distance * Math.cos(Math.toRadians(angle)));
        int y = yRelativeTo + (int) (distance * Math.sin(Math.toRadians(angle)));

        g.setColor(color);
        g.fillOval(x - (int) radius / 2, y - (int) radius / 2, (int) radius, (int) radius);
    }

    /**
     * Gets the name of the celestial object.
     *
     * @return The name of the celestial object.
     */
    public String getName() {
        return name;
    }
}

