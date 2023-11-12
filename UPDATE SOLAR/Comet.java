import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Comet extends Celestial {
    private List<Point> trail;
    private int maxTrailLength;

    public Comet(String name, double radius, double distance, double velocity, Color color, int maxTrailLength) {
        super(name, radius, distance, velocity, color);
        trail = new ArrayList<>();
        this.maxTrailLength = maxTrailLength;
    }

    @Override
    public void move(double timeStep, double eccentricity) {
        super.move(timeStep, eccentricity);

        // Store the current position in the trail
        trail.add(new Point((int) (distance * Math.cos(Math.toRadians(angle))), (int) (distance * Math.sin(Math.toRadians(angle)))));

        // Keep the trail length within the specified maximum
        while (trail.size() > maxTrailLength) {
            trail.remove(0);
        }
    }

    @Override
    public void draw(Graphics2D g, int centerX, int centerY) {
        // Draw the trail
        g.setColor(color);
        for (Point point : trail) {
            int x = centerX + point.x;
            int y = centerY + point.y;
            g.fillOval(x - (int) radius / 2, y - (int) radius / 2, (int) radius, (int) radius);
        }

        // Draw the current position
        int x = centerX + (int) (distance * Math.cos(Math.toRadians(angle)));
        int y = centerY + (int) (distance * Math.sin(Math.toRadians(angle)));
        g.fillOval(x - (int) radius / 2, y - (int) radius / 2, (int) radius, (int) radius);
    }
}
