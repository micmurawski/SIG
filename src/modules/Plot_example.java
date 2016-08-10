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
		//double[] x = { 1.1, 20.2, 100.3 }; 
		 // double[] y = { 5.9, 4.8, 3.7 };
		 
		  double[] fitx= new double[1000]; 
		  double[] fity= new double[1000];
		  double[] x= new double[25]; 
		  double[] y= new double[25];
		  
		  for(int i=0;i<25;i++){
	  			x[i]=i*1/100.0;
	  			y[i]=1+0.5*Math.exp(-3*x[i])*Math.cos(10*x[i]);
	  		}
		   
		  
		  double[] v0 = {1.,0.5,2,5,0};
		  
		  for (int i = 0; i < x.length; i++) 
			  points.add(new WeightedObservedPoint(1, x[i], y[i]));
		  		
		  		ParametricUnivariateFunction f = new ParametricUnivariateFunction() {
					
		  			@Override
		  			public double value(double t,double[] v) {
		  		        return v[0]-v[1]*Math.exp(-v[2]*t)*Math.cos(v[3]*t-v[4]);
		  		    }

		  		    // Jacobian matrix of the above. In this case, this is just an array of
		  		    // partial derivatives of the above function, with one element for each parameter.
		  		    public double[] gradient(double t,double[] v) {
		  		        return new double[] {
		  		        	     1, 
		  		        	   -Math.exp(-v[2]*t)*Math.cos(v[3]*t-v[4]),
		  		        	   v[1]*v[2]*Math.exp(-v[2]*t)*Math.cos(v[3]*t-v[4]),
		  		        	 v[1]*v[3]*Math.exp(-v[2]*t)*Math.sin(v[3]*t-v[4]),
		  		        	-v[1]*v[4]*Math.exp(-v[2]*t)*Math.sin(v[3]*t-v[4])
		  		        };
		  		    }
				};
				
				
				
				 
				
				 
		  		SimpleCurveFitter fitter=SimpleCurveFitter.create(f, v0);
		  		final double coeffs[] = fitter.fit(points);
		        System.out.println(Arrays.toString(coeffs));
		  		
				
		  	for(int i=0;i<1000;i++){
		  			fitx[i]=i*0.1/100.0;
		  			fity[i]=f.value(fitx[i],coeffs);
		  			System.out.println(fitx[i]+" "+fity[i]);
		  			
		  		}
		  		
		        
		        

		        
		  
		
		Plot plot = new Plot("Curve fit", "x", "y", x, y);
		plot.addPoints(fitx, fity, Plot.CROSS); 
		plot.setLimits(0, 1, 0, 2);
		//plot.addPoints(x, y, Plot.CROSS); 
		plot.show();
		System.out.println(coeffs[2]);
        
    }

}
