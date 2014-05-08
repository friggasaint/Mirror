
public class Main {
	
	public static void main(String [ ] args)
	{
	      BitFeld a = new BitFeld(4);
	      BitFeld b = new BitFeld(4);
	      BitFeld c;
	      
	      a.ausgeben();
	      a.setInt(13);
	      b.setInt(9);
	      a.ausgeben();
	      b.ausgeben();
	      
	      c = a.add(b);
	      c.ausgeben();
	      
	      c = a.sub(b);
	      c.ausgeben();
	}

}
