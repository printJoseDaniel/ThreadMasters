package pcd_ej2_SEMAPHORE;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Hilo extends Thread {

	// CONSTANTES 
	public static final int nFilas = 10;
	public static final int nColumnas = 10;
	
	
	// ATRIBUTOS
	private Semaphore mutex;
	private Semaphore general;
	private ArrayList<Panel> paneles;
	private int id;
	
	
	// CONSTRUCTOR
	public Hilo(Semaphore mutex, Semaphore general, ArrayList<Panel> paneles, int id) {
		this.general = general;
		this.mutex = mutex;
		this.paneles = paneles;
		this.id = id;
	}

	
	//GENERAR MATRICES
    public int[][] generarMatriz() {
    	int[][] matriz = new int[nFilas][nFilas];
    	for (int i = 0; i < nFilas; i++) {
    		for (int j = 0; j < nColumnas; j++) {
    			matriz[i][j] = Programa.rand.nextInt(); // Genera un número aleatorio entero
    		}
    	}
    	return matriz;
    }
	
    
    //SUMAR MATRICES
    int resultado[][] = new int[nFilas][nFilas];
    public int[][] sumarMatrices(int[][] matrizA, int[][] matrizB){
 		for(int i = 0; i<nFilas; i++) {
 			for(int j = 0; j<nColumnas; j++) {
 				resultado[i][j] += matrizA[i][j] + matrizB[i][j];
 			}
 		}
 	return resultado;	
 	}
 	
    
    // IMPRIMIR MATRIZ
    public void imprimirMatriz(int[][] matriz) {
    	for (int i = 0; i < Hilo.nFilas; i++) {
    		for(int j = 0; j<Hilo.nColumnas; i++) {
    			System.out.println(matriz[i][j]);
    		}
    		System.out.print("\n");
    	}
    }
    
	@Override
	public void run() {
		// OPERACIONES CON MATRICES 
		int[][] matrizA = generarMatriz();
		int[][] matrizB = generarMatriz();
		int[][] matrizResultado = sumarMatrices(matrizA, matrizB);
		
		// IMPRESIÓN DEL RESULTADO PROTEGIENDO LA PANTALLA
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
			
			panelActual.escribir_mensaje("Usando panel el hilo " + this.id, "Terminando de usar el panel el hilo " + this.id);
			
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