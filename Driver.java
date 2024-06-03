import java.io.File;
import java.util.Arrays;;

public class Driver {
	public static void main(String [] args) 
	{	
		double [] c1 = {1,2,3};
		int [] d1 = {0,1,2};
		Polynomial p1 = new Polynomial(c1, d1);
		/*
		double [] c2 = {1,2,3};
		int [] d2 = {1,2,3};
		Polynomial p2 = new Polynomial(c2, d2);

		double [] c3 = {1,6,2,3,5};
		int [] d3 = {2,3,4,0,5};
		Polynomial p3 = new Polynomial(c3, d3);

		Polynomial result1 = p1.add(p2);
		Polynomial result2 = p3.add(p1);

		System.out.println(Arrays.toString(result2.coefficient));
		System.out.println(Arrays.toString(result2.degree));*/

		double [] c4 ={1,1};
		int [] d4 ={1,0};
		Polynomial p4 = new Polynomial(c4, d4);

		double [] c5 ={1,1};
		int [] d5 ={1,0};
		Polynomial p5 = new Polynomial(c5, d5);

		Polynomial result3 = p5.multiply(p1);
		//System.out.println(Arrays.toString(result3.coefficient));
		//System.out.println(Arrays.toString(result3.degree));

		/*String polynomial = "5-3x2+7x8";
		String regex = "(?<=op)|(?=op)".replace("op", "[+\\-x]");
        String[] tokens = polynomial.split(regex);
		System.out.println(Arrays.toString(tokens));*/

		File file = new File("poly.txt");
		Polynomial p6 = new Polynomial(file);
		//System.out.println(Arrays.toString(p6.coefficient));
		//System.out.println(Arrays.toString(p6.degree));

		//p6.saveToFile("poly1.txt");
		result3.saveToFile("poly1.txt");
		
		File file1 = new File("poly1.txt");
		Polynomial p7 = new Polynomial(file1);
		System.out.println(Arrays.toString(p7.coefficient));
		System.out.println(Arrays.toString(p7.degree));
	}	
}