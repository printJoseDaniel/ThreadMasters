package pcd_ej2_SEMAPHORE;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Programa {
    static final Random rand = new Random(); // No sé si es mejor crear una única instancia de random o varias porque la semilla se basa en la hora 

	public static final int NUM_PANELES = 3;
	
	public static void main(String[] args) {
		Panel panel1 = new Panel("panel1", 200, 500, false);
		Panel panel2 = new Panel("panel2", 800, 500, false);
		Panel panel3 = new Panel("panel3", 1500, 500, false);
		
		ArrayList<Panel> paneles = new ArrayList<Panel>();
		paneles.add(panel1);
		paneles.add(panel2);
		paneles.add(panel3);
		
		Semaphore mutex = new Semaphore(1);
		
		Semaphore general = new Semaphore(NUM_PANELES);		
		
		Hilo hilo1 = new Hilo(mutex, general, paneles);
		Hilo hilo2 = new Hilo(mutex, general, paneles);
		Hilo hilo3 = new Hilo(mutex, general, paneles);
		Hilo hilo4 = new Hilo(mutex, general, paneles);
		Hilo hilo5 = new Hilo(mutex, general, paneles);
		Hilo hilo6 = new Hilo(mutex, general, paneles);
		Hilo hilo7 = new Hilo(mutex, general, paneles);
		Hilo hilo8 = new Hilo(mutex, general, paneles);
		Hilo hilo9 = new Hilo(mutex, general, paneles);
		Hilo hilo10 = new Hilo(mutex, general, paneles);
		
		hilo1.start();
		hilo2.start();
		hilo3.start();
		hilo4.start();
		hilo5.start();
		hilo6.start();
		hilo7.start();
		hilo8.start();
		hilo9.start();
		hilo10.start();
	}

}
