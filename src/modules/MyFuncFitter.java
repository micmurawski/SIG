package modules;

import java.util.ArrayList;

import org.apache.commons.math3.fitting.SimpleCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;

public class MyFuncFitter{

	
	
	public MyFuncFitter(){
		
		
	}
	
	public double[] fit(DataSeries data,double from, double to,double[] v0){
		
		ArrayList<WeightedObservedPoint> points = new ArrayList<WeightedObservedPoint>();
		
		int startIndex=0,endIndex=0;
		if(from>Double.parseDouble(data.getX(0).toString())){
			for(;from>Double.parseDouble(data.getX(startIndex).toString());startIndex++);
		}
		System.out.println("StartIndex"+startIndex);
		if(to<Double.parseDouble(data.getX(data.getItemCount()-1).toString())){
		for(;to>=Double.parseDouble(data.getX(endIndex).toString());endIndex++);
		}else endIndex=data.getItemCount();
		System.out.println("EndIndex"+endIndex);
		
		for(int ii=startIndex;ii<endIndex;ii++){
			points.add(new WeightedObservedPoint(1,
					Double.parseDouble(data.getX(ii).toString())-from,
					Double.parseDouble(data.getY(ii).toString())
					));
			
		}
		//for(WeightedObservedPoint p:points){
		//	System.out.println(p.getX()+" "+p.getY());
	//	}
	//d	System.out.println(points.size());
		
		
		
		
		MyFunc f = new MyFunc();
		SimpleCurveFitter fitter=SimpleCurveFitter.create(f, v0);
		fitter.withMaxIterations(Integer.MAX_VALUE);
		double [] coeffs =fitter.fit(points);
		
		
		return coeffs;
	}
	


	
}
