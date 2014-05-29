

public class Gauss {

	/**
	 * Diese Methode soll die Loesung x des LGS R*x=b durch
	 * Rueckwaertssubstitution ermitteln.
	 * PARAMETER: 
	 * R: Eine obere Dreiecksmatrix der Groesse n x n 
	 * b: Ein Vektor der Laenge n
	 */
	public static double[] backSubst(double[][] R, double[] b) {
		//TODO: Diese Methode ist zu implementieren
		double temp;		//used for calculation
		// get size of vector for further calculation
		int size = b.length-1;
		// x vector
		double[] x = new double[size+1];

		for	(int i=size; i >= 0 ; i--){
			//punch together already calculated values
			temp = 0.0;
			for(int j=size; j >= i; j--){
				temp = temp + R[i][j]*x[j];
			}
			//calculate specific x value
			x[i] = (b[i] -temp)/R[i][i];
		}
		return x;
	}

	/**
	 * Diese Methode soll die Loesung x des LGS A*x=b durch Gauss-Elimination mit
	 * Spaltenpivotisierung ermitteln. A und b sollen dabei nicht veraendert werden. 
	 * PARAMETER: A:
	 * Eine regulaere Matrix der Groesse n x n 
	 * b: Ein Vektor der Laenge n
	 */
	public static double[] solve(double[][] A, double[] b) {
		//TODO: Diese Methode ist zu implementieren
		double temp;
		double[] tempArr;		//used for calculation
		boolean check = false;
		// get size of vector for further calculation
		int size = b.length-1;
		// x vector
		double[] x = new double[size+1];
		double[][]a = A;	//working matrix
		
		
		for(int i = 0; i <= size ; i++){		
				//sort
				while(check == false){
					check = true;
					for(int j = i; 0 <= size; j++){
						if (Math.abs(a[i][i]) <  Math.abs(a[j][i])){
							tempArr = a[i];
							a[i] = a[j];
							a[j] = tempArr;
							check = false;
						}
					}
				}
				//produce zeroes
				for(int j = i; j <=size; j++){
					temp = a[j+1][j]/a[j][j];		//calculate factor for the row
					for( int k = i; k <= size; k++){
						a[j+1][k]=a[i][j];
					}
				}
				
		}
		
		
		return x;
	}

	/**
	 * Diese Methode soll eine Loesung p!=0 des LGS A*p=0 ermitteln. A ist dabei
	 * eine nicht invertierbare Matrix. A soll dabei nicht veraendert werden.
	 * 
	 * Gehen Sie dazu folgendermassen vor (vgl.Aufgabenblatt): 
	 * -Fuehren Sie zunaechst den Gauss-Algorithmus mit Spaltenpivotisierung 
	 *  solange durch, bis in einem Schritt alle moeglichen Pivotelemente
	 *  numerisch gleich 0 sind (d.h. <1E-10) 
	 * -Betrachten Sie die bis jetzt entstandene obere Dreiecksmatrix T und
	 *  loesen Sie Tx = -v durch Rueckwaertssubstitution 
	 * -Geben Sie den Vektor (x,1,0,...,0) zurueck
	 * 
	 * Sollte A doch intvertierbar sein, kann immer ein Pivot-Element gefunden werden(>=1E-10).
	 * In diesem Fall soll der 0-Vektor zurueckgegeben werden. 
	 * PARAMETER: 
	 * A: Eine singulaere Matrix der Groesse n x n 
	 */
	public static double[] solveSing(double[][] A) {
		//TODO: Diese Methode ist zu implementieren
		return new double[2];
	}

	/**
	 * Diese Methode berechnet das Matrix-Vektor-Produkt A*x mit A einer nxm
	 * Matrix und x einem Vektor der Laenge m. Sie eignet sich zum Testen der
	 * Gauss-Loesung
	 */
	public static double[] matrixVectorMult(double[][] A, double[] x) {
		int n = A.length;
		int m = x.length;

		double[] y = new double[n];

		for (int i = 0; i < n; i++) {
			y[i] = 0;
			for (int j = 0; j < m; j++) {
				y[i] += A[i][j] * x[j];
			}
		}

		return y;
	}
}
