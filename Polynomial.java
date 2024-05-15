public class Polynomial 
{   
    // fields
    double[] coefficient;

    // methods
    public Polynomial()
    {
        coefficient = new double[]{0.0};
    }

    public Polynomial(double[] coeffs)
    {
        coefficient = new double [coeffs.length];
        for (int i=0; i<coeffs.length; i++)
        {
            coefficient[i] = coeffs[i];
        }
    }

    public Polynomial add(Polynomial other)
    {   
        int maxlength = Math.max(this.coefficient.length, other.coefficient.length);
        double[] new_poly_coef = new double[maxlength];

        for(int i=0; i<maxlength; i++)
        {
            double this_coef = i<this.coefficient.length? this.coefficient[i] : 0.0;
            new_poly_coef[i] = this_coef + other.coefficient[i];
        }

        return new Polynomial(new_poly_coef);
    }

    public double evaluate(double value)
    {
        double accum = this.coefficient[0];
        
        for(int i=1; i<this.coefficient.length; i++)
        {
            accum = accum + this.coefficient[i] * Math.pow(value, i);
        }

        return accum;
    }

    public boolean hasRoot(double value)
    {   
        double result = evaluate(value);
        return result == 0.0;
    }
}
