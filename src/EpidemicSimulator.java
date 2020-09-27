import java.util.*; 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.Timer; 

public class EpidemicSimulator extends JPanel implements ActionListener{
	
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 600; 

	Timer tm = new Timer(33, this); 
	
	private int numDots = 150; 
	private Dot[] dotArray = new Dot[numDots];
	private int radius = 5; 
	
	private double socialDistRate; 
	private double deathRate;   
	private double infectionRate; 
	private int timeToRecover = 210; 
	private int timeToDie = 140; 
	
	private int numInfected = 1; 
	private ArrayList<Integer> numInfectedList = new ArrayList<>();  
	
	//constructor 
	public EpidemicSimulator () {
		
		//gets user input (e.g. social distancing rate, death rate, etc.)
		getInputs(); 
		
		//setup for simulation
		for (int i = 0; i < numDots; i++) {
			
			Color c = Color.BLUE; 
			Random rand = new Random();
			int x = rand.nextInt(WIDTH - radius * 3) + radius;	
			int y = rand.nextInt(HEIGHT - radius * 6) + radius;
			int angle = rand.nextInt(360);
			int vel = 5; 
			
			//simulation starts with one dot infected (red) 
			if (i == 0) { 
				c = Color.RED; 
			}
			
			//dots social distance (stay in place)
			boolean moving = true; 
			if (i <=  socialDistRate / 100 * numDots && i != 0) {
				moving = false; 
			}
			
			//creates new instance of Dot object and stores it in dotArray
			dotArray[i] = new Dot(x, y, radius, angle, vel, c, moving, -1, false);
		}
		
		//starts timer, starting the actual simulation
		tm.start(); 
	}
	
	//paints dots and graph onto display 
	public void paintComponent (Graphics g) {
		super.paintComponent(g); 
		drawGraph(g); 
		for (int i = 0; i < numDots; i++) {
			dotArray[i].draw(g);
		}
	}

	//given action performed every time timer "goes off" 
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Random rand = new Random(); 
		
		//move the dots
		for (int i = 0; i < numDots; i++) {
			if (dotArray[i].getMoving() == true) {
				dotArray[i].move(1);
			}
		}
		
		//check if dots touch - infected (red) dots infect healthy (blue) dots when they touch
		for (int i = 0; i < numDots; i++) {
			for (int j = i + 1; j < numDots; j++) {
				
				double radiiDist = Math.sqrt(Math.pow(dotArray[i].getX() - dotArray[j].getX(),2)
						+ Math.pow(dotArray[i].getY() - dotArray[j].getY(), 2));
				
				//dots are touching if distance between radii is less than 2 times the radius
				if (radiiDist < 2 * radius) {
					if (dotArray[i].getColor() == Color.RED && dotArray[j].getColor() == Color.BLUE
							&& rand.nextDouble() <= infectionRate / 100) {
						
						dotArray[j].setColor(Color.RED); 
						numInfected++; 
						
						//some infected (red) dots will die based on death rate
						if (rand.nextDouble() < deathRate / 100) {
							dotArray[j].setWillDie(true);
						}
					}
					//other case - red, blue switched
					else if (dotArray[i].getColor() == Color.BLUE && dotArray[j].getColor() == Color.RED
							&& rand.nextDouble() <= infectionRate / 100) {
						
						dotArray[i].setColor(Color.RED); 
						numInfected++; 
						
						if (rand.nextDouble() < deathRate / 100) {
							dotArray[i].setWillDie(true);
						}
					}
				}
			}
		}
		
		//checks for recoveries, deaths
		for (int i = 0; i < numDots; i++) {
			if (dotArray[i].getColor() == Color.RED) {
				
				dotArray[i].setTimeInfected(dotArray[i].getTimeInfected() + 1); 
				
				//some infected (red) dots die (become black) after set number of days
				if (dotArray[i].getTimeInfected() == timeToDie && dotArray[i].getWillDie() == true) {
					dotArray[i].setColor(Color.BLACK); 
					dotArray[i].setMoving(false); 
					numInfected--; 
				}
				
				//most infected (red) dots recover (become green) after set number of days
				else if (dotArray[i].getTimeInfected() == timeToRecover && dotArray[i].getWillDie() == false) {
					dotArray[i].setColor(new Color(0, 215, 0));
					numInfected--; 
				}
			}
			
		}
		
		numInfectedList.add(numInfected); 
				
		//updates the display with the updated positions/colors by calling paintComponent
		repaint(); 
	}
	
	//draws graph: number of people infected at a given time vs. time passed 
	public void drawGraph(Graphics g) {
		g.setColor(new Color(1f, 0f, 0f, .07f));
		for (int i = 0; i < numInfectedList.size(); i++) {
			g.drawRect(i, 575 - numInfectedList.get(i) * 600 / numDots, 1, numInfectedList.get(i) * 600 / numDots);
		}
	}
	
	//gets user input (e.g. social distancing rate, death rate, etc.)
	public void getInputs() {
		Scanner console = new Scanner(System.in); 
		System.out.print("% of population social distancing: ");
		socialDistRate = console.nextDouble(); 
		System.out.print("Death rate in %: "); 
		deathRate = console.nextDouble();
		System.out.print("Infection rate in %: ");
		infectionRate = console.nextDouble(); 
	}
	
}
