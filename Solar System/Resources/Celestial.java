import java.awt.Color;
import java.awt.Graphics2D;

public class Celestial {
    private String name;
    protected double radius;
    protected double distance;
    protected double angle;
    protected double velocity;
    protected Color color;

    public Celestial(String name, double radius, double distance, double velocity, Color color) {
        this.name = name;
        this.radius = radius;
        this.distance = distance;
        this.angle = 0;
        this.velocity = velocity;
        this.color = color;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public double getRadius() {
        return radius;
    }
    
    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void move(double timeStep) {
        angle += (360 * velocity * timeStep / (2 * Math.PI * distance));
        angle = angle % 360;
    }

    public void draw(Graphics2D g, int centerX, int centerY) {
        int x = (int) (centerX + distance * Math.cos(Math.toRadians(angle)));
        int y = (int) (centerY + distance * Math.sin(Math.toRadians(angle)));

        g.setColor(color);
        g.fillOval(x - (int) radius / 2, y - (int) radius / 2, (int) radius, (int) radius);
    }

    public void draw(Graphics2D g, int centerX, int centerY, Celestial relativeTo) {
        int xRelativeTo = (int) (centerX + relativeTo.getDistance() * Math.cos(Math.toRadians(relativeTo.getAngle())));
        int yRelativeTo = (int) (centerY + relativeTo.getDistance() * Math.sin(Math.toRadians(relativeTo.getAngle())));

        int x = xRelativeTo + (int) (distance * Math.cos(Math.toRadians(angle)));
        int y = yRelativeTo + (int) (distance * Math.sin(Math.toRadians(angle)));

        g.setColor(color);
        g.fillOval(x - (int) radius / 2, y - (int) radius / 2, (int) radius, (int) radius);
    }

    public String getName() {
        return name;
    }
}
