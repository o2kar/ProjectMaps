package maps.helper;

/**
 * Helper class
 * 
 * @author rroesch
 *
 * @param <T1>
 * @param <T2>
 */
public class Pair<T1, T2> {
	T1 var1;
	T2 var2;

	public Pair(T1 var1, T2 var2) {
		super();
		this.var1 = var1;
		this.var2 = var2;
	}

	/**
	 * @return the var1
	 */
	public final T1 getVar1() {
		return var1;
	}

	/**
	 * @return the var2
	 */
	public final T2 getVar2() {
		return var2;
	}

}
