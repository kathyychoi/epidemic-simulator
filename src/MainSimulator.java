/*
 * Program that simulates epidemics by modeling people as dots. 
 * 
 * Blue = healthy 
 * Red = infected
 * Green = recovered
 * Black = deceased 
 */

import java.awt.*;
import java.util.*;
import javax.swing.*; 
	
public class MainSimulator {
	public static void main(String[] args) {	         
		JFrame jf = new JFrame(); 
		jf.setTitle("Epidemic Simulator"); 
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//creates new instance of EpidemicSimulator and adds it to the frame
		EpidemicSimulator es = new EpidemicSimulator(); 
		jf.add(es);
		jf.setSize(1000, 600);
		jf.setVisible(true);
	}
}
