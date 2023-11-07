import java.awt.Color;
import java.awt.Graphics2D;

public class Asteroid extends Celestial {
    private double rotationSpeed;

    public Asteroid(String name, double radius, double distance, double velocity, Color color, double rotationSpeed) {
        super(name, radius, distance, velocity, color);
        this.rotationSpeed = rotationSpeed;
    }

    @Override
    public void move(double timeStep) {
        super.move(timeStep);

        // Update the rotation angle of the asteroid based on its rotation speed
        angle += rotationSpeed * timeStep;

        // Ensure that the angle stays within the range [0, 360 degrees]
        angle = angle % 360;
    }

    @Override
    public void draw(Graphics2D g, int centerX, int centerY) {
        // Calculate the position of the celestial object
        int x = (int) (centerX + distance * Math.cos(Math.toRadians(angle)));
        int y = (int) (centerY + distance * Math.sin(Math.toRadians(angle)));

        g.setColor(color);
        g.fillOval(x - (int) radius / 2, y - (int) radius / 2, (int) radius, (int) radius);
    }
}
