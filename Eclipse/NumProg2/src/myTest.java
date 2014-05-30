
public class myTest {
	
	
	public static void main(String[] args){
		double[][] R = {{ 1, 1, 1, 1 ,1 },
						{ 0, 1, 1, 1 ,1 },
						{ 0, 0, 1, 1 ,1 },
						{ 0, 0, 0, 1 ,1 },
						{ 0, 0, 0, 0 ,1 }};
		
		double[] b = {1, 2, 3, 4 ,5};
		
		double[] x = Gauss.backSubst(R, b);
		
		System.out.println("backSubst()");
		for(int i=0; i <b.length; i++){
			System.out.println(x[i]);
		}

		
		double [][] A = {{18, 4, 3},
						 {4, 9, 4},
						 {4, 7, 8}};
		double [] d = {2, 5, 2};
		
		double[] y = Gauss.solve(A,d);
		
		System.out.println("solve()");
		for(int i=0; i <d.length; i++){
			System.out.println(y[i]);
		}
		
		double [][] B = {{3, 5, 4},
						 {6, 7, 8},
						 {1, 2, 3}};
		double[] z = Gauss.solveSing(B);
		System.out.println("solveSing()");
		for(int i=0; i <d.length; i++){
			System.out.println(z[i]);
		}
	}
}
