package ode;

import java.util.Arrays;

/**
 * Das Einschrittverfahren "Expliziter Euler"
 * 
 * @author braeckle
 * 
 */
public class ExpliziterEuler implements Einschrittverfahren {

	public double[] nextStep(double[] y_k, double t, double delta_t, ODE ode) {
		//TODO: diese Methode ist zu implementieren
		
		double[] y_new = new double[y_k.length];			// new array bigger by 1
		System.arraycopy(y_k, 0, y_new, 0, y_k.length-1); 	// copy over old elements
		y_new[y_new.length-1] = y_k[y_k.length-1] + delta_t * ode.auswerten(t, y_k)[y_k.length-1] ;
		return y_new;
	}

}
