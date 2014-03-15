package knapsack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.BitSet;


/**
 * DP using disk
 * 
 */
public class DiskBackedDynamicProgramming implements Strategy {
	
	int[] values, weights;
	int C;
	int[][] dp;
	File[] tempFiles;

	@Override
	public void solve(int cap, int[] values, int[] weights, int[] taken) {
		this.values = values;
		this.weights = weights;
		this.C = cap;
		this.dp = new int[2][cap + 1];
		this.tempFiles = new File[values.length + 1];
		
		try {
			Tuple<Integer, int[]> ret = solve();

			for (int i : ret.snd) {
				taken[i] = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	// the state is (cap, n)
	Tuple<Integer, int[]> solve() throws Exception {

		// Initialize base cases (for the sake of clarity)
		for (int n = 0; n < dp.length; n++) {
			dp[n][0] = 0;
		}
		for (int cap = 0; cap < dp[0].length; cap++) {
			dp[0][cap] = 0;
		}
		
		
		// fill the table
		for (int n = 1; n < values.length + 1; n++) {
			BitSet dir = new BitSet(dp[0].length);
			
			int currentRow = n % 2;
			int prevRow = (n - 1) % 2;
			for (int cap = 1; cap < dp[0].length; cap++) {
				int max = dp[prevRow][cap]; // we don't pick current item
				if(weights[n - 1] <= cap) { // we can afford this item
					int tmax = values[n - 1] + dp[prevRow][cap - weights[n - 1]]; // we pick current item
					if (tmax > max) {
						max = tmax;
						dir.set(cap);
					}
				}
				dp[currentRow][cap] = max;
			}
			
			// persist the bitset
			File temp = File.createTempFile("bitset-" + n, null);
			temp.deleteOnExit();
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(temp));
			oos.writeObject(dir);
			oos.close();
			
			tempFiles[n] = temp;
		}
		
		
		// backtrack, start at lower right corner
		int cap = C;
		int[] path = new int[values.length];
		int pi = 0;
		for (int n = values.length; n > 0; n--) {
			
			// read bitset
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(tempFiles[n]));
			BitSet dir = (BitSet) ois.readObject();
			ois.close();
			
			if(dir.get(cap)){ // we picked this item
				path[pi++] = n - 1;
				cap -= weights[n - 1];
			}
		}

		int[] rpath = new int[pi];
		System.arraycopy(path, 0, rpath, 0, pi);
		return Tuple.make(dp[values.length % 2][dp[0].length - 1], rpath); // value at the base

	}
}

