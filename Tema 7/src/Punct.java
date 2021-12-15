import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Punct
{
    private double coordX;
    private double coordY;
    private int number;
    private int parent;
    int getParent(){return parent;}
    void setParent(int Parent){parent=Parent;}
    public Punct(double coordX, double coordY, int number)
    {
        this.coordX = coordX;
        this.coordY = coordY;
        this.number = number;
    }

    public double getCoordX() {
        return coordX;
    }
    public void setCoordX(double coordX) {
        this.coordX = coordX;
    }
    public double getCoordY() {
        return coordY;
    }
    public void setCoordY(double coordY) {
        this.coordY = coordY;
    }
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
}