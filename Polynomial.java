import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Polynomial 
{   
    // fields
    double coefficient[];
    int degree[];

    // methods
    public Polynomial()
    {
        coefficient = new double[]{0.0};
        degree = new int[]{0};
    }

    public Polynomial(double[] coeffs, int degs[])
    {   
        coefficient = new double[coeffs.length];
        degree = new int[degs.length];
        //initialize coefficients and degrees:
        this.coefficient = new double[coeffs.length];
        for (int i=0; i<coeffs.length; i++)
        {
            this.coefficient[i] = coeffs[i];
            this.degree[i] = degs[i];
        }
    }

    // Constructor with a File argument
    public Polynomial(File poly_file) 
    {
        try (BufferedReader br = new BufferedReader(new FileReader(poly_file))) 
        {
            String polyString = br.readLine();
            initializeFromPolynomialString(polyString);
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    // Method to initialize from polynomial string
    private void initializeFromPolynomialString(String polyString) 
    {
        List<Double> coeflist = new ArrayList<>();
        List<Integer> deglist = new ArrayList<>();

        String[] tokens = splitByOperators(polyString);

        double curCoeff = 0.0;
        int curDed = 0;
        boolean nIsNeg = false;

        for (int i = 0; i < tokens.length; i++) 
        {
            if (tokens[i].matches("[+-]?\\d*\\.?\\d+")) 
            {
                curCoeff = Double.parseDouble(tokens[i]);
                if (nIsNeg) {
                    curCoeff = -curCoeff;
                    nIsNeg = false;
                }
                if (i + 1 < tokens.length && tokens[i + 1].equals("x")) {
                    if (i + 2 < tokens.length && tokens[i + 2].matches("\\d+")) {
                        curDed = Integer.parseInt(tokens[i + 2]);
                        i += 2; // Skip "x" and the degree
                    } else {
                        curDed = 1;
                        i++; // Skip "x"
                    }
                }
                coeflist.add(curCoeff);
                deglist.add(curDed);
            } else if (tokens[i].equals("x")) {
                curDed = 1;
                coeflist.add(curCoeff);
                deglist.add(curDed);
            } else if (tokens[i].equals("+")) {
                continue;
            } else if (tokens[i].equals("-")) {
                nIsNeg = true;
            }
        }

        coefficient = new double[coeflist.size()];
        degree = new int[deglist.size()];

        for (int i = 0; i < coeflist.size(); i++) 
        {
            coefficient[i] = coeflist.get(i);
            degree[i] = deglist.get(i);
        }
    }

    // Method to split the expression into tokens
    private String[] splitByOperators(String expression) 
    {
        // Regular expression to split by arithmetic operators: +, -, x
        String regex = "(?<=op)|(?=op)".replace("op", "[+\\-x]");
        return expression.split(regex);
    }
    
    public void saveToFile(String filename) 
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) 
        {
            for (int i = 0; i < coefficient.length; i++) 
            {
                if (i > 0 && coefficient[i] >= 0) 
                {
                    writer.print("+");
                }
                writer.print(coefficient[i]);
                if (degree[i] != 0) 
                {
                    writer.print("x");
                    if (degree[i] != 1) 
                    {
                        writer.print(degree[i]);
                    }
                }
            }
            writer.println();
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public Polynomial add(Polynomial other)
    {   
        int f_length = this.degree.length;

        //Determine the length of the final array(find the total number of unique degrees in the two polys):
        for(int i=0; i<other.degree.length; i++)
        {   
            boolean exist = false;
            for(int j=0; j<this.degree.length; j++)
            {
                if(this.degree[j] == other.degree[i])
                {
                    exist = true;
                    break;
                }
            }
            if(!exist)
            {
                f_length += 1;
            }
        }

        //initialize the final degree and coefficient arrays with "this poly":
        int[] f_degree = new int[f_length];
        double[] f_coefficient = new double[f_length];

        for(int i=0; i<this.degree.length; i++)
        {
            f_degree[i] = this.degree[i];
            f_coefficient[i] = this.coefficient[i];
        }
        
        //'index' helps to track the position in the final array
        int index = this.degree.length;

        /*Nested loop to add the other poly to the final poly
         * 1.same
         * 2.not unique
        */

        for (int i=0; i<other.degree.length; i++)
        {   
            boolean exist = false;
            for(int j=0; j<this.degree.length; j++)
            {
                if(other.degree[i] == this.degree[j])
                {
                    f_coefficient[j] += other.coefficient[i];
                    exist = true;
                    break;
                }
            }
            if(!exist)
            {
                f_coefficient[index] = other.coefficient[i];
                f_degree[index] = other.degree[i];
                index += 1;
            }
        }

        return new Polynomial(f_coefficient, f_degree);

    }

    public Polynomial multiply(Polynomial other)
    {
        Polynomial f_poly = new Polynomial();

        for(int i=0; i<this.degree.length; i++)
        {   
            Polynomial i_poly = new Polynomial(other.coefficient, other.degree);
            for(int j=0; j<other.degree.length; j++)
            {
                i_poly.coefficient[j] *= this.coefficient[i];
                i_poly.degree[j] += this.degree[i];
            }
            f_poly = f_poly.add(i_poly);
        }
        return f_poly;
    }

    public double evaluate(double value)
    {
        double accum = 0;
        
        for(int i=1; i<this.coefficient.length; i++)
        {
            accum = accum + this.coefficient[i] * Math.pow(value, this.degree[i]);
        }

        return accum;
    }

    public boolean hasRoot(double value)
    {   
        double result = evaluate(value);
        return result == 0.0;
    }
}
