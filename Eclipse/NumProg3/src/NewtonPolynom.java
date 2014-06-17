import java.util.Arrays;

/**
 * Die Klasse Newton-Polynom beschreibt die Newton-Interpolation. Die Klasse
 * bietet Methoden zur Erstellung und Auswertung eines Newton-Polynoms, welches
 * uebergebene Stuetzpunkte interpoliert.
 * 
 * @author braeckle
 * 
 */
public class NewtonPolynom implements InterpolationMethod {

	/** Stuetzstellen xi */
	double[] x;

	/**
	 * Koeffizienten/Gewichte des Newton Polynoms p(x) = a0 + a1*(x-x0) +
	 * a2*(x-x0)*(x-x1)+...
	 */
	double[] a;

	/**
	 * die Diagonalen des Dreiecksschemas. Diese dividierten Differenzen werden
	 * fuer die Erweiterung der Stuetzstellen benoetigt.
	 */
	double[] f;

	/**
	 * leerer Konstruktore
	 */
	public NewtonPolynom() {
	};

	/**
	 * Konstruktor
	 * 
	 * @param x
	 *            Stuetzstellen
	 * @param y
	 *            Stuetzwerte
	 */
	public NewtonPolynom(double[] x, double[] y) {
		this.init(x, y);
	}

	/**
	 * {@inheritDoc} Zusaetzlich werden die Koeffizienten fuer das
	 * Newton-Polynom berechnet.
	 */
	@Override
	public void init(double a, double b, int n, double[] y) {
		x = new double[n + 1];
		double h = (b - a) / n;

		for (int i = 0; i < n + 1; i++) {
			x[i] = a + i * h;
		}
		computeCoefficients(y);
	}

	/**
	 * Initialisierung der Newtoninterpolation mit beliebigen Stuetzstellen. Die
	 * Faelle "x und y sind unterschiedlich lang" oder "eines der beiden Arrays
	 * ist leer" werden nicht beachtet.
	 * 
	 * @param x
	 *            Stuetzstellen
	 * @param y
	 *            Stuetzwerte
	 */
	public void init(double[] x, double[] y) {
		this.x = Arrays.copyOf(x, x.length);
		computeCoefficients(y);
	}

	/**
	 * computeCoefficients belegt die Membervariablen a und f. Sie berechnet zu
	 * uebergebenen Stuetzwerten y, mit Hilfe des Dreiecksschemas der
	 * Newtoninterpolation, die Koeffizienten a_i des Newton-Polynoms. Die
	 * Berechnung des Dreiecksschemas soll dabei lokal in nur einem Array der
	 * Laenge n erfolgen (z.B. spaltenweise Berechnung). Am Ende steht die
	 * Diagonale des Dreiecksschemas in der Membervariable f, also f[0],f[1],
	 * ...,f[n] = [x0...x_n]f,[x1...x_n]f,...,[x_n]f. Diese koennen spaeter bei
	 * der Erweiterung der Stuetzstellen verwendet werden.
	 * 
	 * Es gilt immer: x und y sind gleich lang.
	 */
	private void computeCoefficients(double[] y) {
		/* TODO: diese Methode ist zu implementieren */
		
		int len = y.length;
		double[][] coef = new double[len][len];	//speichert Dreieckschema
		a = new double[len];
		f = new double[len];
		
		//initialisieren des Dreiecks mit Startwerten aus y
		for(int i = 0; i < len; i++){
			coef[i][0] = y[i];
		}
		
		// Berechnung der Koeffizienten über das Dreiecksschema
		// start jeweils bei 1 da initialisierung der ersten Spalte bereits abgeschlossen
		for(int k = 1; k < len; k++){
			for(int i = 0; i < len-k;i++){	//-k stellt oberes linkes Dreieck sicher
				coef[i][k]=(coef[i+1][k-1]-coef[i][k-1])/(x[i+k]-x[i]);
			}
		}
		
		// füllen von a
		for (int i = 0; i < len; i++){
			this.a[i]= coef[0][i];
		}
		
		// füllen der Diagonalen in  f
		int j = 0;
		for(int i = len-1; i >= 0; i--){
			this.f[i]=coef[j][i];
			j++;
		}
	}

	/**
	 * Gibt die Koeffizienten des Newton-Polynoms a zurueck
	 */
	public double[] getCoefficients() {
		return a;
	}

	/**
	 * Gibt die Dividierten Differenzen der Diagonalen des Dreiecksschemas f
	 * zurueck
	 */
	public double[] getDividedDifferences() {
		return f;
	}

	/**
	 * addSamplintPoint fuegt einen weiteren Stuetzpunkt (x_new, y_new) zu x
	 * hinzu. Daher werden die Membervariablen x, a und f vergroessert und
	 * aktualisiert . Das gesamte Dreiecksschema muss dazu nicht neu aufgebaut
	 * werden, da man den neuen Punkt unten anhaengen und das alte
	 * Dreiecksschema erweitern kann. Fuer diese Erweiterungen ist nur die
	 * Kenntnis der Stuetzstellen und der Diagonalen des Schemas, bzw. der
	 * Koeffizienten noetig. Ist x_new schon als Stuetzstelle vorhanden, werden
	 * die Stuetzstellen nicht erweitert.
	 * 
	 * @param x_new
	 *            neue Stuetzstelle
	 * @param y_new
	 *            neuer Stuetzwert
	 */
	public void addSamplingPoint(double x_new, double y_new) {
		/* TODO: diese Methode ist zu implementieren */
		
		int len = this.x.length;
		double[] temp = this.f;	//alte f Werte speichern
		
		// Prüft ob x_new bereits vorhanden, wenn ja nichts machen
		if(Arrays.asList(this.x).contains(x_new)) return;
		
		// Vergrößern der Arrays um 1 und alte Werte wieder reinkopieren
		this.x = Arrays.copyOf(this.x, len+1);
		this.a = Arrays.copyOf(this.a, len+1);
		this.f = new double[len+2];
		
		this.x[len+1] = x_new;		// x_new ganz am Ende des Dreieckschmas einfügen
		this.f[0] = y_new;			// y_new ganz am Anfang des Dreieckschemas einfügen
		
		// erweiterte Diagonale berechnen
		for (int i = 0; i < len; i++){
			f[i]=(f[i-1]-temp[i])/(x[i+1]-x[i]);
		}
		
		this.a[len+1] = this.f[len+2];
	}

	/**
	 * {@inheritDoc} Das Newton-Polynom soll effizient mit einer Vorgehensweise
	 * aehnlich dem Horner-Schema ausgewertet werden. Es wird davon ausgegangen,
	 * dass die Stuetzstellen nicht leer sind.
	 */
	@Override
	public double evaluate(double z) {
		/* TODO: diese Methode ist zu implementieren */
		int i = this.a.length-1;
		double p = this.a[i];
		
		while (i > 0){
			p = (this.a[i] + (z-x[i]) * p);
			i--;
		}
		
		return p;
	}
}
