package knapsack;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class TestSolver {
	
	@Rule
	public Timeout globalTimeout = new Timeout(30000); // 10 seconds max per method tested

	private static String DIR = "C:/Users/monang/Documents/coursera/Discrete Optimization/knapsack";

	private static List<File> provisionalTests = new ArrayList<File>();
	private static List<File> exampleTests = new ArrayList<File>();

	@BeforeClass
	public static void beforeClass() throws Exception {
		
		
		File metadata = new File(DIR, "_metadata");
		Scanner sc = new Scanner(metadata);
		sc.nextLine();
		sc.nextLine();
		int n = Integer.parseInt(sc.nextLine());
		for (int i = 0; i < n; i++) {
			String line = sc.nextLine();
			File testInput = new File(DIR, line.split(",")[1].trim());
			provisionalTests.add(testInput);
		}
		sc.close();
		
		File sampleDir = new File(DIR, "data");
		for (File file : sampleDir.listFiles()) {
			exampleTests.add(file);
		}
		
	}
	
	private String getInput(String name){
		return new File(DIR, "data/" + name).getAbsolutePath();
	}

	private int solve(String test) throws IOException {
		System.out.println(test);
		int value = Solver.solve(new String[]{ "-file=" + test });
		return value;
	}
	
	@Test
	public void testExample_4_0() throws Exception {
		Solver.strategy = new DynamicProgramming();
		String test = getInput("ks_4_0");
		int value = solve(test);
		assertTrue(value >= 19);
	}

	
	@Test
	public void testSubmit1() throws Exception {
		Solver.strategy = new Recursion(); //
		String test = provisionalTests.get(0).getAbsolutePath();
		int value = solve(test);
		assertTrue(value >= 99798);
	}

	
	@Test
	public void testSubmit2() throws Exception {
		String test = provisionalTests.get(1).getAbsolutePath();

		Solver.strategy = new DynamicProgramming();
		int value2 = solve(test);
		assertTrue(value2 >= 141956);
		
		Solver.strategy = new Memoization();
		int value1 = solve(test);
		assertTrue(value1 >= 141956);
	}
	
	@Test
	public void testAllSubmission() throws Exception {
		for (File file : provisionalTests) {
			String test = file.getAbsolutePath();
			System.out.println(test);
			Solver.strategy = new Recursion();
			int value = Solver.solve(new String[]{ "-file=" + test });
			System.out.println(value);
		}
	}

}
