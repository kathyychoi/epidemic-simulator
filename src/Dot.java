import java.awt.Color;
import java.awt.Graphics;
import java.util.*; 

public class Dot {
	   
	private int x, y; 
	private int radius; 
	private int angle; 
	private int vel; 
	private int velX, velY;
	private Color c; 
	private boolean moving; 
	private int timeInfected; 
	private boolean willDie; 
	
	//constructor 
	public Dot (int x, int y, int radius, int angle, int vel, Color c, boolean moving, 
			int timeInfected, boolean willDie) {
		this.x = x;
	    this.y = y;
	    this.radius = radius; 
	    this.angle = angle;
	    this.vel = vel; 
	    this.velX = (int) (vel * Math.cos(Math.toRadians(angle)));
	    this.velY = - (int) (vel * Math.sin(Math.toRadians(angle)));
	    this.c = c;
	    this.moving = moving; 
	    this.timeInfected = timeInfected; 
	    this.willDie = willDie; 
	}
	
	//method to move the dot
	public void move(int numSteps) {
		
		//moves the dot by velX, velY
		x += velX * numSteps; 
		y += velY * numSteps; 
		
		//detects collision with walls
		if (x < radius) {
			velX *= -1;	//reverses direction of x component of velocity
			x = radius; 
		}
		else if (x > 1000 - radius) {
			velX *= -1;	
			x = 1000 - radius; 
		}
		else if (y < radius) {
			velY *= -1;	//reverses direction of y component of velocity
			y = radius; 
		}
		else if (y > 600 - 5 * radius) {
			velY *= -1; 
			y = 600 - 5 * radius; 
		}
	}
	
	//draw the dot in the given color
	public void draw(Graphics g) {
	    g.setColor(c);
	    g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
	}
	
	//mutator methods
	public void setColor(Color c) {
		this.c = c; 
	}
	public void setMoving(boolean moving) {
		this.moving = moving; 
	}
	public void setTimeInfected(int timeInfected) {
		this.timeInfected = timeInfected; 
	}
	public void setWillDie(boolean willDie) {
		this.willDie = willDie; 
	}
	
	//accessor methods 
	public int getX() {
		return x; 
	}
	public int getY() {
		return y; 
	}
	public int getVelX() {
		return velX; 
	}
	public int getVelY() {
		return velY; 
	}
	public int getRadius() {
		return radius; 
	}
	public int getAngle() {
		return angle; 
	}
	public Color getColor() {
		return c; 
	}
	public boolean getMoving() {
		return moving; 
	}
	public int getTimeInfected() {
		return timeInfected; 
	}
	public boolean getWillDie() {
		return willDie; 
	}
}
