package pcd_ej2_SEMAPHORE;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Hilo extends Thread {

	// ATRIBUTOS
	private Semaphore mutex;
	private Semaphore general;
	private ArrayList<Panel> paneles;
	
	
	// CONSTRUCTOR
	public Hilo(Semaphore mutex, Semaphore general, ArrayList<Panel> paneles) {
		this.general = general;
		this.mutex = mutex;
		this.paneles = paneles;
	}

	
	//GENERAR MATRICES
    public void generarMatriz() {
    	int[][] matriz = new int[10][10];
    	for (int i = 0; i < 3; i++) {
    		for (int j = 0; j < 3; j++) {
    			matriz[i][j] = Programa.rand.nextInt(); // Genera un número aleatorio entero
    		}
    	}
    }
	
    
    //SUMAR MATRICES
    int resultado[][] = new int[3][3];
    public int[][] sumarMatrices(int[][] matriz){
 		for(int i = 0; i<3; i++) {
 			for(int j = 0; j<3; j++) {
 				resultado[i][j] += matriz[i][j] + matriz[i][j];
 			}
 		}
 	return resultado;	
 	}
 	
    
	@Override
	public void run() {
		try {
			general.acquire(); // Permite que entren como max 3 paneles
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			mutex.acquire(); // Para acceder en exclusión mutua a buscar un panel libre y ocuparlo
			Panel panelActual = null; // PREGUNTAR AL PROFESOR POR QUÉ NOS RECOMENDÓ INICIALIZARLO Y DEJARLO A NULL
			
			int i = 0;			
			panelActual = paneles.get(i); 
			while((i<Programa.NUM_PANELES)&&(panelActual.isOcupado())) { // ESTABA MAL: !panelActual.isOcupado() ya que entraría al bucle y avanzaría cuando haya un panel libre
				panelActual = paneles.get(i); 				
				i++;
			}
			
			panelActual.setOcupado(true);
			mutex.release();
			
			panelActual.escribir_mensaje("Aitor");

			Thread.sleep(1000); // Simulo trabajo de los hilos para aumentar la concurrencia
			
			mutex.acquire();
			panelActual.setOcupado(false);
			mutex.release();
					
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
				
		general.release();
	}
}
;