
public class Main {
	
	public static void main(String [ ] args)
	{
	      BitFeld a = new BitFeld(120);
	      BitFeld b = new BitFeld(120);
	      BitFeld c;
	      
	      a.ausgeben();
	      a.setInt(513215123);
	      b.setInt(513215123);
	      a.ausgeben();
	      b.ausgeben();
	      
	      c = a.add(b);
	      c.ausgebenInt();
	      c.ausgeben();
	      
	      c = a.sub(b);
	      c.ausgebenInt();
	      c.ausgeben();
	      
	      Gleitpunktzahl x = new Gleitpunktzahl(7.0);
	      Gleitpunktzahl y = new Gleitpunktzahl(3.5);
	      Gleitpunktzahl z;
	      
	      x.ausgeben();
	      x.ausgebenDouble();
	      y.ausgeben();
	      y.ausgebenDouble();
	      
	      z = x.add(y);
	      z.ausgeben();
	      z.ausgebenDouble();
	      
	      z = x.sub(y);
	      z.ausgeben();
	      z.ausgebenDouble();
	      
	}

}
