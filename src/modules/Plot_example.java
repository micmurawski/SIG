package modules;

import ij.gui.Plot;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.fitting.SimpleCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;


public class Plot_example {
	
	public static void main(String[] args) {
		
		ArrayList<WeightedObservedPoint> points = new ArrayList<WeightedObservedPoint>();
		double[] x = { 1.1, 20.2, 100.3 }; 
		  double[] y = { 5.9, 4.8, 3.7 };
		  double[] v0 = {1.0,1.0};
		  double[] fitx= new double[100]; 
		  double[] fity= new double[100];
		  
		   
		  for (int i = 0; i < x.length; i++) 
			  points.add(new WeightedObservedPoint(1, x[i], y[i]));
		  		
		  		ParametricUnivariateFunction f = new ParametricUnivariateFunction() {
					
		  			@Override
		  			public double value(double t,double[] v) {
		  		        return v[0]*Math.log(t)+v[1];
		  		    }

		  		    // Jacobian matrix of the above. In this case, this is just an array of
		  		    // partial derivatives of the above function, with one element for each parameter.
		  		    public double[] gradient(double t,double[] v) {
		  		        return new double[] {
		  		        	     Math.log(t), 
		  		        	     1 
		  		        };
		  		    }
				};
		  		
		  		SimpleCurveFitter fitter=SimpleCurveFitter.create(f, v0);
		  		final double coeffs[] = fitter.fit(points);
		        System.out.println(Arrays.toString(coeffs));
		  		
				
		  		for(int i=1;i<100;i++){
		  			fitx[i]=i*(x[2]-x[0])/100;
		  			fity[i]=coeffs[0]*Math.log(fitx[i])+coeffs[1];
		  			System.out.println(fitx[i]+" "+fity[i]);
		  			//
		  		}
		  		
		        
		        

		        
		  
		
		Plot plot = new Plot("Curve fit", "log(x)", "y", fitx, fity);
		plot.addPoints(x, y, Plot.CROSS); 
		//plot.addPoints(x, y, Plot.CROSS); 
		plot.show();
        
    }

}
