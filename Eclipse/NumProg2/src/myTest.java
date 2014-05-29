
public class myTest {
	
	
	public static void main(String[] args){
		double[][] R = {{ 1, 1, 1, 1 ,1 },
						{ 0, 1, 1, 1 ,1 },
						{ 0, 0, 1, 1 ,1 },
						{ 0, 0, 0, 1 ,1 },
						{ 0, 0, 0, 0 ,1 }};
		
		double[] b = {1, 2, 3, 4 ,5};
		
		double[] x = Gauss.backSubst(R, b);
		
		for(int i=0; i <b.length; i++){
			System.out.println(x[i]);
		}
	}
}
