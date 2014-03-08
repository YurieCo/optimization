package knapsack;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestSolver {

	private static String DIR = "C:/Users/monang/Documents/coursera/Discrete Optimization/knapsack";

	private static List<File> provisionalTests = new ArrayList<File>();
	private static List<File> exampleTests = new ArrayList<File>();

	@BeforeClass
	public static void beforeClass() throws Exception {
		
		
		File metadata = new File(DIR, "_metadata");
		Scanner sc = new Scanner(metadata);
		System.out.println(sc.nextLine());
		System.out.println(sc.nextLine());
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

	@Test
	public void testExample_4_0() throws Exception {
		String test = getInput("ks_4_0");
		System.out.println(test);
		Solver.strategy = new SimpleDP();
		int value = Solver.solve(new String[]{ "-file=" + test });
	}

	
	@Test
	public void testSubmit1() throws Exception {
		String test = provisionalTests.get(0).getAbsolutePath();
		System.out.println(test);
		Solver.strategy = new SimpleDP();
		int value = Solver.solve(new String[]{ "-file=" + test });
		assertTrue(value >= 99798);
	}

}
