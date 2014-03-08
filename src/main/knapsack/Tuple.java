package knapsack;

public class Tuple<T1, T2> {
	final public T1 fst;
	final public T2 snd;

	public static <T1, T2> Tuple<T1, T2> make(T1 fst, T2 snd) {
		return new Tuple<T1, T2>(fst, snd);
	}

	private Tuple(T1 fst, T2 snd) {
		this.fst = fst;
		this.snd = snd;
	}

}
