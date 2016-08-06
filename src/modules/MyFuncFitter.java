package modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.fitting.AbstractCurveFitter;
import org.apache.commons.math3.fitting.SimpleCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresBuilder;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem;
import org.apache.commons.math3.linear.DiagonalMatrix;
import ij.gui.Plot;
import ij.gui.PlotWindow; 
 
import ij.plugin.PlugIn; 
 
import java.awt.Color; 

public class MyFuncFitter extends SimpleCurveFitter{
	
	
	public void SimpleCurveFitter(ParametricUnivariateFunction function,
            double[] initialGuess,
            int maxIter){
		
	}
	
	
    protected LeastSquaresProblem getProblem(Collection<WeightedObservedPoint> points) {
        final int len = points.size();
        final double[] target  = new double[len];
        final double[] weights = new double[len];

        int i = 0;
        for(WeightedObservedPoint point : points) {
            target[i]  = point.getY();
            weights[i] = point.getWeight();
            i += 1;
        }

        final SimpleCurveFitter.TheoreticalValuesFunction model = new
        		SimpleCurveFitter.TheoreticalValuesFunction(new MyFunc(), points);

        return new LeastSquaresBuilder().
            maxEvaluations(Integer.MAX_VALUE).
            maxIterations(Integer.MAX_VALUE).
            start(initialGuess).
            target(target).
            weight(new DiagonalMatrix(weights)).
            model(model.getModelFunction(), model.getModelFunctionJacobian()).
            build();
    }

    public static void main(String[] args) {
        MyFuncFitter fitter = new MyFuncFitter();
        ArrayList<WeightedObservedPoint> points = new ArrayList<WeightedObservedPoint>();

        // Add points here; for instance,
        WeightedObservedPoint point = new WeightedObservedPoint(1.0,
            1.0,
            1.0);
        points.add(point);

        final double coeffs[] = fitter.fit(points);
        System.out.println(Arrays.toString(coeffs));
    }

	
}
