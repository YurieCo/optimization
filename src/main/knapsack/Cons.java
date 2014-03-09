package knapsack;

/**
 * Simple LISP-style list to hold the path
 */
public class Cons {
	final int car;
	final Cons cdr;

	public Cons(int car, Cons cdr) {
		this.car = car;
		this.cdr = cdr;
	}
	
}
