package modules;

import java.util.Collection;

import org.apache.commons.math3.analysis.ParametricUnivariateFunction;

import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.fitting.AbstractCurveFitter;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresBuilder;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem;
import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.apache.commons.math3.linear.DiagonalMatrix;



class MyFunc implements ParametricUnivariateFunction {
//    public double value(double t, double... v) {
//        return v[0]-v[1]*Math.exp(-v[2]*t-v[3])*Math.cos(v[4]*t-v[5]);
//    }

    // Jacobian matrix of the above. In this case, this is just an array of
    // partial derivatives of the above function, with one element for each parameter.
//    public double[] gradient(double t, double... v) {
//        return new double[] {
//            v[0],
//            -Math.exp(-v[2]*t-v[3])*Math.cos(v[4]*t-v[5]),
//            v[2]*Math.exp(-v[2]*t-v[3])*Math.cos(v[4]*t-v[5]),
//            v[4]*v[1]*Math.exp(-v[2]*t-v[3])*Math.sin(v[4]*t-v[5]),
//            -v[5]*v[1]*Math.exp(-v[2]*t-v[3])*Math.sin(v[4]*t-v[5])
//        };
 //   }
	
	 public double value(double t, double... v) {
	        return v[0]*Math.log(t)+v[1];
	    }

	    // Jacobian matrix of the above. In this case, this is just an array of
	    // partial derivatives of the above function, with one element for each parameter.
	    public double[] gradient(double t, double... v) {
	        return new double[] {
	        	     Math.log(t), 
	        	     1 
	        };
	    }
	
	
}


