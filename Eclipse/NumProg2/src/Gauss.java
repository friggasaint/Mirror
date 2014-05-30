

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
		double[][]a = A;	//working matrix
		double[] d = b;		// working vector
		
		
		for(int i = 0; i < size ; i++){		
				//pivot search + switch rows
			while(check == false){
				check = true;
				for(int j = i; j < size; j++){
					if (Math.abs(a[i][i]) <  Math.abs(a[j][i])){
						tempArr = a[i];
						a[i] = a[j];
						a[j] = tempArr;
						temp = d[i];
						d[i] = d[j];
						d[j] = temp;
						check = false;
					}
				}
			}
			//produce zeroes
			for(int j = i; j <size; j++){
				for(int k = i+1; k <= size-j; k++){
					temp = (a[j+k][j]/a[j][j]);		//calculate factor for the row
					for( int l = i; l <= size; l++){
						a[j+k][l]=a[j+k][l] - temp * a[j][l];	//matrix calculation
					}
					d[j+k]=d[j+k] - temp * d[j];		//vector calculation
				}
			}
		}
		return backSubst(a, d);
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
		
		// no testing here. just programmed in the dark.
		// also beware dirty coding. just wanted to finish.
		
		double temp;
		double[] tempArr;		//used for calculation
		boolean check = false;
		// get size of vector for further calculation
		int size = A.length-1;
		// x vector
		double[][]a = A;	//working matrix
		
		//working vector filled with zeroes
		double[] d = new double[A.length];
		for	(int i = 0; i < d.length; i++){
			d[i]= 0;
		}
		
		
		for(int i = 0; i < size ; i++){		
				//pivot search + switch rows
			while(check == false){
				check = true;
				for(int j = i; j < size; j++){
					if (Math.abs(a[i][i]) <  Math.abs(a[j][i])){
						tempArr = a[i];
						a[i] = a[j];
						a[j] = tempArr;
						check = false;
					}
				}
				// no pivot detected.
				if (a[i][i] < 1E-10){
					double[][] T = new double [i][i];
					double[] v = new double [i];
					for(int j = 0; j <=i; j++){
						v[j] = -a[j][i];			//negation included
						for(int k = 0; k <= i; k++){
							T[j][k]=a[j][k];
						}
					}
					tempArr = solve(T, v);
					for(int j = 0 ; j <= i ; j++){
						d[i] = tempArr[i];
					}
				}
				
			}
			//produce zeroes
			for(int j = i; j <size; j++){
				for(int k = i+1; k <= size-j; k++){
					temp = (a[j+k][j]/a[j][j]);		//calculate factor for the row
					for( int l = i; l <= size; l++){
						a[j+k][l]=a[j+k][l] - temp * a[j][l];	//matrix calculation
					}
				}
			}
		}
		return d;
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
